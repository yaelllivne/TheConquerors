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

public class GamesInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String gameName = request.getParameter("gameName");
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            GameManager gameManager = ServletUtils.getGameManager(getServletContext());
            Game game = gameManager.getGameByName(gameName);
            String json = gson.toJson(game);
            out.println(json);
            out.flush();
        }
    }
}
