package servlets;

import com.google.gson.Gson;
import engine.Game.Game.Game;
import engine.Users.GameManager;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class GamesListServlet  extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            GameManager gameManager = ServletUtils.getGameManager(getServletContext());
            Set<Game> gamesList = gameManager.getGames();
            String json = gson.toJson(gamesList);
            out.println(json);
            out.flush();
        }
    }
}