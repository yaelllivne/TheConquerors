package servlets;

import com.google.gson.Gson;
import constants.Constants;
import engine.Game.Game.Game;
import engine.Users.GameManager;
import engine.Users.UserManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static utils.SessionUtils.getUsername;

public class JoinGameServlet extends HttpServlet {
    private final String GAME_URL = "./html/gameRoom.html";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("application/json");
       // try (PrintWriter out = response.getWriter()) {
            //Gson gson = new Gson();
            String gameName = request.getParameter("gameName");
            GameManager gameManager = ServletUtils.getGameManager(getServletContext());
            Game game = gameManager.getGameByName(gameName);
            String userName = getUsername(request);
            game.AddPlayer(userName);

            request.getSession(true).setAttribute(Constants.GAMENAME, gameName.trim());
            //String json = gson.toJson(true);
            //out.println(json);
            //out.flush();
        //}
    }
}
