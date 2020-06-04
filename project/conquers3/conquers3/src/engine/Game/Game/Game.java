package engine.Game.Game;

import engine.Game.Army.Army;
import engine.Game.Army.Force;
import engine.Game.Board.Board;
import engine.Game.Board.Cell;
import engine.Game.Player.*;

import engine.Users.Massage;
import mypackage.GameDescriptor;
import mypackage.Unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game extends  GameBase implements Serializable {

    private List<Force> forces;
    private List<Move> moves;
    private List<GameHistory> history;
    private Boolean playerExit = false;
    private String name;
    private String creator;
    private Boolean status;
    private int startTuring;
    private int playerIndex;
    private boolean afterAction=false;
    private Player winner=null;
    private String lastAction="";
    private List<Massage> chat;

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public Game(Board board, int numOfPlayers, List<Player> players, int numOfRounds, List<Force> forces, List<Board> boardHistory, List<Move> moves) {

        super(board, numOfPlayers, players, numOfRounds);
        this.forces = forces;
        this.history = new ArrayList<>();
        this.moves = moves;
    }

    public Game(GameDescriptor gameDescriptor, String creator) {
        super(gameDescriptor);
        this.playerIndex=0;
        this.startTuring = gameDescriptor.getGame().getInitialFunds().intValue();
        this.status = false;
        this.creator = creator;
        this.name = gameDescriptor.getDynamicPlayers().getGameTitle();
        moves = new ArrayList<>();
        history = new ArrayList<>();
        forces = new ArrayList<>();
        for (int i = 1; i <= gameDescriptor.getGame().getArmy().getUnit().size(); i++) {
            for (Unit run : gameDescriptor.getGame().getArmy().getUnit()) {
                if ((int) run.getRank() == i)
                    forces.add(new Force(run.getType(), run.getMaxFirePower().intValue(), run.getPurchase().intValue(), run.getCompetenceReduction().intValue(), run.getRank()));
            }
        }
        chat=new ArrayList<>();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Force> getForces() {
        return forces;
    }

    public boolean trainArmy(int cellID) {
        this.afterAction=true;
        Cell cell = board.getCellByID(cellID);
        lastAction=getPlayerNow().getName()+" train his army ("+cellID+")";
        if (cell.getArmy().getCostForTrain() <= cell.getPlayer().getTuring()) {
            moves.add(new Train(cell));
            return true;
        }
        return false;
    }

    public void addNewArmy(int cellID, List<Integer> quantity) {
        this.afterAction=true;
        Cell cell = board.getCellByID(cellID);
        lastAction=getPlayerNow().getName()+" add new army ("+cellID+")";
        moves.add(new AddNewArmyForOldArmy(cell, quantity));
    }

    public Cell.CellStatus getCellStatus(Player player, int cellID) {
        if (!board.canMoveOnCell(player, cellID))
            return Cell.CellStatus.UNREACHABLE;
        return board.getCellByID(cellID).getCellStatus(player);
    }

    public boolean isMyCell(Player player, int cellID) {
        if (board.getCellByID(cellID).getCellStatus(player) == Cell.CellStatus.YOURS)
            return true;
        return false;
    }

    public void conquerOnNewTerritory(int cellID, List<Integer> quantity) {
        Cell cell = board.getCellByID(cellID);
        lastAction=getPlayerNow().getName()+" conquered new territory ("+cellID+")";
        moves.add(new NewTerritory(cell, quantity, getPlayerNow(), forces));
        afterAction=true;
    }

    public void endTurn() {
        if(afterAction==false)
            doNothing(players.get(playerIndex));
        nextTurn();
        afterAction=false;
        if(playerIndex==0)
            endRound();
    }

    public void doNothing(Player player) {
        lastAction=getPlayerNow().getName()+" skip his turn";
        moves.add(new DoNoting(player));
    }

    public void endRound() {
        history.add(GameHistory.saveGame(this));
        board.endRound();
        this.curentRound++;
        if(numOfRounds==curentRound) {
            int maxYield=-1;
            for(Player run:players){
                if(run.getTotalYield()>maxYield) {
                    maxYield = run.getTotalYield();
                    winner=run;
                }
            }
            int draw=0;
            for(Player run:players){
                if(run.getTotalYield()==maxYield) {
                    draw++;
                    if(draw==2){
                        winner=null;
                    }
                }
            }
        }
    }

    public void undoGameRound() {
        /*GameHistory lastRound=history.get(history.size()-1);
        players=lastRound.getPlayers();
        for(Player run:lastRound.getPlayers())
            moves.remove(moves.size() - 1);

        board=lastRound.board;
        curentRound--;
        history.remove(history.size()-1);
        */
        int length = moves.size();
        for (int i = 0; i < numOfPlayers; i++) {
            //moves.get(length-1-i).undo();
            moves.remove(length - 1 - i);
        }
        curentRound--;
        board = history.get(history.size() - 1).board;
        players = history.get(history.size() - 1).players;
        history.remove(history.size() - 1);
        //board.undoRound();

    }

    public List<GameHistory> getHistory() {
        return history;
    }

    public Boolean luckyAttack(Player player, int cellID, List<Integer> quantity) {
        this.afterAction=true;
        Cell cell = board.getCellByID(cellID);
        String nameDedender=cell.getPlayer().getName();
        Army armyOfAttacker = new Army(quantity, forces, player);
        LuckyAttack move = new LuckyAttack(player, cell, armyOfAttacker);
        moves.add(move);
        lastAction=getPlayerNow().getName()+" attack "+ nameDedender +" ("+cellID+")";
        if (move.getWinner().getName().compareTo(player.getName()) == 0)
            return true;
        return false;
    }

    public Boolean goodTimmingAttack(Player player, int cellID, List<Integer> quantity) {
        this.afterAction=true;
        Cell cell = board.getCellByID(cellID);
        String nameDedender=cell.getPlayer().getName();
        Army armyOfAttacker = new Army(quantity, forces, player);
        GoodTimmingAttack move = new GoodTimmingAttack(player, cell, armyOfAttacker);
        moves.add(move);
        lastAction=getPlayerNow().getName()+" attack "+ nameDedender +" ("+cellID+")";
        if (move.getWinner() != null) {
            if (move.getWinner().getName().compareTo(player.getName()) == 0)
                return true;
            return false;
        }
        return null;
    }

    public Move getLastMove() {
        return moves.get(moves.size() - 1);
    }
/*
    public void savePlayers() {
        for (Player run : players)
            run.saveTuringYield();
        for (GameHistory run : this.history)
            for (Player run1 : run.getPlayers())
                run1.saveTuringYield();
    }

    public void loadPlayers() {
        for (Player run : players)
            run.loadTuringYield();
        for (GameHistory run : this.history)
            for (Player run1 : run.getPlayers())
                run1.loadTuringYield();
    }
    */

    public Boolean playerExit(Player playNow) {
        playerExit = true;
        playNow.playerExit();
        players.remove(playNow);
        numOfPlayers--;
        return numOfPlayers == 1;
    }

    public Boolean getPlayerExit() {
        return playerExit;
    }

    public void saveGameForHistory() {
        history.add(GameHistory.saveGame(this));
    }

    public void AddPlayer(String userName) {
        String color="";
        switch (this.currentNumOfPlayers) {
            case 0:
                color = "#fb7b7b";
                break;
            case 1:
                color = "#ffb45e";
                break;
            case 2:
                color = "#b3f975";
                break;
            case 3:
                color = "#c773f4";
                break;
        }

        Player newPlayer = new Player(color, startTuring, userName);
        this.players.add(newPlayer);
        this.currentNumOfPlayers++;

        if(this.currentNumOfPlayers==this.numOfPlayers){
            status=true;
        }
    }


    public void nextTurn() {
        if (playerIndex == currentNumOfPlayers - 1)
            playerIndex = 0;
        else
            playerIndex++;
    }

    public String geMyColorByName(String usernameFromSession) {
        for(Player run:players){
            if(run.getName().compareTo(usernameFromSession)==0)
                return run.getColor();
        }
        return "";
    }

    public List<Object> getListForSendInfo(String namePlayer) {
        List<Object> listInfo=new ArrayList<>();
        listInfo.add(players);
        listInfo.add(playerIndex);
        listInfo.add(getCellColorsArray());
        listInfo.add(getCellActionArray(players.get(playerIndex).getName().compareTo(namePlayer)==0,namePlayer));
        listInfo.add(players.get(playerIndex).getName().compareTo(namePlayer)==0);
        listInfo.add(""+curentRound+"/"+numOfRounds);
        listInfo.add(curentRound!=numOfRounds);
        listInfo.add(winner);
        listInfo.add(lastAction);
        return listInfo;
    }

    private List<Boolean> getCellActionArray(boolean isMyTurn,String namePlayer) {
        List<Boolean> cellActionArray=new ArrayList<>();
        Player player=null;
        for(Player run:players)
            if(run.getName().compareTo(namePlayer)==0)
                player=run;
        for(int i=0;i<board.getNumberOfRows();i++){
            for(int j=0;j<board.getNumberOfColumns();j++){
                Cell run=board.getCellByID(i*board.getNumberOfColumns()+j+1);
                int cellID=i*board.getNumberOfColumns()+j;
                if(!isMyTurn||(isMyTurn&&afterAction)) {
                    if(run.getPlayer()==null)
                        cellActionArray.add(false);
                    else {
                        if (run.getPlayer().getName().compareTo(namePlayer) == 0)
                            cellActionArray.add(true);
                        else
                            cellActionArray.add(false);
                    }
                }
                else
                    cellActionArray.add(board.canMoveOnCell(player,cellID+1));
            }
        }
        return cellActionArray;
    }

    private List<String> getCellColorsArray() {
        List<String> colorsArray=new ArrayList<>();
        for(int i=0;i<board.getNumberOfRows();i++){
            for(int j=0;j<board.getNumberOfColumns();j++){
                Cell run=board.getCellByID(i*board.getNumberOfColumns()+j+1);
                if(run.getPlayer()==null)
                    colorsArray.add("#ffeeab");
                else
                    colorsArray.add(run.getPlayer().getColor());
            }
        }
        return colorsArray;
    }

    public Boolean isMyTurn(String playerName){
            return (players.get(this.playerIndex).getName().compareTo(playerName)==0);
    }

    public Player getPlayerNow() {
        return players.get(playerIndex);
    }

    public boolean afterAction() {return afterAction;
    }

    public void DeletePlayer(String userName) {
        for(int i=0;i<players.size();i++){
            if(players.get(i).getName().compareTo(userName)==0)
                players.remove(players.get(i));
        }
        int numOfCell=board.getNumberOfColumns()*board.getNumberOfRows();
        for(int i=0;i<numOfCell;i++) {
            Cell cell = board.getCellByID(i + 1);
            if(cell.getPlayer()!=null){
                if(cell.getPlayer().getName().compareTo(userName)==0)
                    cell.resetCell();
            }
        }
        currentNumOfPlayers--;
    }

    public void playerLeaveAfterGameOver(String userName) {
         DeletePlayer(userName);
         if(players.size()==0){
             int numOfCell=board.getNumberOfColumns()*board.getNumberOfRows();
             for(int i=0;i<numOfCell;i++)
                 board.getCellByID(i+1).resetCell();
             status=false;
             afterAction=false;
             lastAction="";
             chat=new ArrayList<>();
             curentRound=0;
         }
    }

    public void playerLeaveBeforeGameOver(String userName) {
        DeletePlayer(userName);
        lastAction=userName+" exit";
        afterAction=false;
        if (currentNumOfPlayers == 1) {
            curentRound = numOfRounds;
            winner = players.get(0);
            playerIndex=0;
        }
        else{
            if(playerIndex==currentNumOfPlayers) {
                endRound();
                playerIndex=0;
            }
        }
    }

    public void addMassege(String data,String name){
        chat.add(new Massage(data,name));
    }

    public List<Massage> getChatDelta(int fromStart){
        List<Massage> listForReturn=new ArrayList<>();
        for(int i=fromStart;i<chat.size();i++){
            listForReturn.add(chat.get(i));
        }
        return listForReturn;
    }
}
