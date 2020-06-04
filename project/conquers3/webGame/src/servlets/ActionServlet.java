package servlets;

import com.google.gson.Gson;
import constants.Constants;
import engine.Game.Army.Army;
import engine.Game.Board.Cell;
import engine.Game.Game.Game;
import engine.Game.Player.Player;
import engine.Users.GameManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import static utils.SessionUtils.getUsername;

public class ActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        GameManager gameManager= ServletUtils.getGameManager(getServletContext());
        Game game = gameManager.getGameByName(SessionUtils.getGamename(request));
        switch (action){
            case "getStatus":
                sendStatus(request,response,game);
                break;
            case "getGame":
                sendGame(request,response,game);
                break;
            case "updatePage":
                sendIfoForUpdate(request,response,game);
                break;
            case "getCellClickedInfo":
                getCellClickedInfo(request,response,game);
                break;
            case "buyArmyNewTerritory":
                buyArmyNewTerritory(request,response,game);
                break;
            case "skipTurn":
                skipTurn(request,response,game);
                break;
            case "trainArmy":
                trainArmy(request,response,game);
                break;
            case "buyArmyInMyCell":
                buyArmyInMyCell(request,response,game);
                break;
            case "luckyAttack":
                luckyAttack(request,response,game);
                break;
            case "goodTimming":
                goodTimming(request,response,game);
                break;
            case "exitPlayerBeforeGameStart":
                exitPlayerBeforeGameStart(request,response,game);
                break;
            case "endGame":
                endGameOneplayer(request,response,game);
                break;
            case "exitPlayerAfterGameStarted":
                exitPlayerAfterGameStarted(request,response,game);
                break;
            case "addMessage":
                addMessage(request,response,game);
                break;
            case "chatUpdate":
                chatUpdate(request,response,game);
                break;
        }
    }

    private void chatUpdate(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        int fromIndex = Integer.parseInt(request.getParameter("indexMsg"));
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(game.getChatDelta(fromIndex));
            out.println(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMessage(HttpServletRequest request, HttpServletResponse response, Game game) {
        String userName = getUsername(request);
        String msg = request.getParameter("content");
        game.addMassege(msg, userName);
    }

    private void exitPlayerAfterGameStarted(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        String userName = getUsername(request);
        game.playerLeaveBeforeGameOver(userName);

        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(true);
            out.println(json);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endGameOneplayer(HttpServletRequest request, HttpServletResponse response, Game game) {
        String userName = getUsername(request);
        game.playerLeaveAfterGameOver(userName);
        request.getSession(false).removeAttribute(Constants.GAMENAME);
    }

    private void exitPlayerBeforeGameStart(HttpServletRequest request, HttpServletResponse response, Game game) {
        boolean quit;
        if(!game.getStatus()) {
            String gameName = request.getParameter("gameName");
            String userName = getUsername(request);
            game.DeletePlayer(userName);
            request.getSession(false).removeAttribute(Constants.GAMENAME);
            quit=true;
        }
        else
            quit=false;
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(quit);
            out.println(json);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goodTimming(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        int cellID = Integer.parseInt(request.getParameter("cellID"));
        List<Integer> quantitiesArray = stringToListArrayForQuantity(request.getParameter("quantitiesArray"));
        int totalPower = Integer.parseInt(request.getParameter("totalPower"));
        int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
        Cell cellOnAction = game.getBoard().getCellByID(cellID + 1);
        String errorMessage = "";
        List <Object> listForReturn=new ArrayList<>();
        try (PrintWriter out = response.getWriter()) {
            if (totalPrice == 0) {
                errorMessage = "Please Insert Forces To Buy";
            }
            if (cellOnAction.getMinimalPower() > totalPower) {
                errorMessage = "You Need More Power";
            }
            if (game.getPlayerNow().getTuring() < totalPrice) {
                errorMessage = "You Dont Have Enough Turing!";
            }

            listForReturn.add(errorMessage);

            if (errorMessage.compareTo("") == 0) {
                Player defender=cellOnAction.getPlayer();
                Army armyBefore = cellOnAction.getArmy();
                Boolean ifWin = game.goodTimmingAttack(game.getPlayerNow(), cellID + 1, quantitiesArray);
                if (cellOnAction.getArmy() != null && ifWin)
                    listForReturn.add(cellOnAction.getArmy());
                else
                    listForReturn.add(null);
                listForReturn.add(quantitiesArray);
                listForReturn.add(game.getPlayerNow().getName());
                listForReturn.add(armyBefore);
                listForReturn.add(defender.getName());
                listForReturn.add(defender.getColor());
                listForReturn.add(ifWin);
                listForReturn.add(game.getForces());

            }
            Gson gson = new Gson();
            String json = gson.toJson(listForReturn);
            out.println(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void luckyAttack(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        int cellID = Integer.parseInt(request.getParameter("cellID"));
        List<Integer> quantitiesArray = stringToListArrayForQuantity(request.getParameter("quantitiesArray"));
        int totalPower = Integer.parseInt(request.getParameter("totalPower"));
        int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
        Cell cellOnAction = game.getBoard().getCellByID(cellID + 1);
        String errorMessage = "";
        List <Object> listForReturn=new ArrayList<>();
        try (PrintWriter out = response.getWriter()) {
            if (totalPrice == 0) {
                errorMessage = "Please Insert Forces To Buy";
            }
            if (cellOnAction.getMinimalPower() > totalPower) {
                errorMessage = "You Need More Power";
            }
            if (game.getPlayerNow().getTuring() < totalPrice) {
                errorMessage = "You Dont Have Enough Turing!";
            }

            listForReturn.add(errorMessage);

            if (errorMessage.compareTo("") == 0) {
                Player defender=cellOnAction.getPlayer();
                Army armyBefore = cellOnAction.getArmy();
                Boolean ifWin = game.luckyAttack(game.getPlayerNow(), cellID + 1, quantitiesArray);
                if (cellOnAction.getArmy() != null && ifWin)


                    listForReturn.add(cellOnAction.getArmy());
                else
                    listForReturn.add(null);
                listForReturn.add(quantitiesArray);
                listForReturn.add(game.getPlayerNow().getName());
                listForReturn.add(armyBefore);
                listForReturn.add(defender.getName());
                listForReturn.add(defender.getColor());
                listForReturn.add(ifWin);
                listForReturn.add(game.getForces());

            }
            Gson gson = new Gson();
            String json = gson.toJson(listForReturn);
            out.println(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buyArmyInMyCell(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        int cellID = Integer.parseInt(request.getParameter("cellID"));
        List<Integer> quantitiesArray = stringToListArrayForQuantity(request.getParameter("quantitiesArray"));
        int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
        Cell cellOnAction = game.getBoard().getCellByID(cellID + 1);
        String errorMessage = "";
        List <Object> listForReturn=new ArrayList<>();
        try (PrintWriter out = response.getWriter()) {
            if (totalPrice == 0) {
                errorMessage = "Please Insert Forces To Buy";
            }
            if (game.getPlayerNow().getTuring() < totalPrice) {
                errorMessage = "You Dont Have Enough Turing!";
            }
            listForReturn.add(errorMessage);
            if (errorMessage.compareTo("") == 0) {
                game.addNewArmy(cellID+1, quantitiesArray);
                listForReturn.add(cellOnAction.getArmy());
            }
            Gson gson = new Gson();
            String json = gson.toJson(listForReturn);
            out.println(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void trainArmy(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        int cellID = Integer.parseInt(request.getParameter("cellID"));
        List<Object> listForReturn=new ArrayList<>();
        String errorMessage = "";
        try (PrintWriter out = response.getWriter()) {

            if(!game.trainArmy(cellID+1))
                errorMessage="You Dont H ave Enough Turing!";
            listForReturn.add(errorMessage);
            if(errorMessage.compareTo("")==0)
                listForReturn.add(game.getBoard().getCellByID(cellID+1).getArmy());
            Gson gson = new Gson();
            String json = gson.toJson(listForReturn);
            out.println(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void skipTurn(HttpServletRequest request, HttpServletResponse response, Game game) {
        game.endTurn();
    }

    private void buyArmyNewTerritory(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        int cellID = Integer.parseInt(request.getParameter("cellID"));
        List<Integer> quantitiesArray = stringToListArrayForQuantity(request.getParameter("quantitiesArray"));
        int totalPower = Integer.parseInt(request.getParameter("totalPower"));
        int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
        Cell cellOnAction = game.getBoard().getCellByID(cellID + 1);
        String errorMessage = "";
        List <Object> listForReturn=new ArrayList<>();
        try (PrintWriter out = response.getWriter()) {
            if (totalPrice == 0) {
                errorMessage = "Please Insert Forces To Buy";
            }
            if (cellOnAction.getMinimalPower() > totalPower) {
                errorMessage = "You Need More Power";
            }
            if (game.getPlayerNow().getTuring() < totalPrice) {
                errorMessage = "You Dont Have Enough Turing!";
            }
            listForReturn.add(errorMessage);
            if (errorMessage.compareTo("") == 0) {
                game.conquerOnNewTerritory(cellID+1, quantitiesArray);
                listForReturn.add(cellOnAction.getArmy());
            }
            Gson gson = new Gson();
            String json = gson.toJson(listForReturn);
            out.println(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCellClickedInfo(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        String usernameFromSession = SessionUtils.getUsername(request);
        int cellID = Integer.parseInt(request.getParameter("cellID"));
        Cell cellOnAction=game.getBoard().getCellByID(cellID+1);
        int status=0;
        Army armyInCell=null;
        if(!game.isMyTurn(usernameFromSession)){
            status=0;
            armyInCell=cellOnAction.getArmy();
        }
        else{
            if(game.afterAction()) {
                status = 0;
                armyInCell=cellOnAction.getArmy();
            }
            else {
                switch (cellOnAction.getCellStatus(game.getPlayerNow())) {
                    case YOURS:
                        status = 2;
                        armyInCell = cellOnAction.getArmy();
                        break;
                    case ANOTHERPLAYER:
                        status = 3;
                        break;
                    case NEUTRAL:
                        status = 1;
                        break;
                }
            }
        }
        List<Object> arrayForRightComponent=new ArrayList<>();
        arrayForRightComponent.add(status);
        arrayForRightComponent.add(armyInCell);
        arrayForRightComponent.add(cellOnAction.getYield());
        arrayForRightComponent.add(game.getPlayerNow().getTuring());
        if(cellOnAction.getArmy()!=null)
            arrayForRightComponent.add(cellOnAction.getArmy().getCostForTrain());

        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(arrayForRightComponent);
            out.println(json);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendIfoForUpdate(HttpServletRequest request, HttpServletResponse response, Game game) {
        response.setContentType("application/json");
        String usernameFromSession = SessionUtils.getUsername(request);
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(game.getListForSendInfo(usernameFromSession));
            out.println(json);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendGame(HttpServletRequest request, HttpServletResponse response, Game game) {
        String usernameFromSession = SessionUtils.getUsername(request);
        String  color=game.geMyColorByName(usernameFromSession);
        List<Object> returnObject=new ArrayList<>();
        returnObject.add(game);
        returnObject.add(color);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            String json = gson.toJson(returnObject);
            out.println(json);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendStatus(HttpServletRequest request, HttpServletResponse response,Game game) {
        response.setContentType("application/json");
        List<Object> listForReturn=new ArrayList<>();
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            listForReturn.add(game.getStatus());
            listForReturn.add(game.getPlayers());
            String json = gson.toJson(listForReturn);
            out.println(json);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> stringToListArrayForQuantity(String stringQuantity){
        List<Integer> listForReturn=new ArrayList<>();
        StringTokenizer stringTk=new StringTokenizer(stringQuantity);
        while(stringTk.hasMoreElements()){
            listForReturn.add(Integer.parseInt(stringTk.nextElement().toString()));
        }
        return listForReturn;
    }
}
