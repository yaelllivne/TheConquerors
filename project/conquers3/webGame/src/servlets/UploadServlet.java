package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.google.gson.Gson;
import constants.Constants;
import engine.Game.Game.Game;
import engine.Users.GameManager;
import engine.Users.UserManager;
import mypackage.GameDescriptor;
import utils.ServletUtils;
import utils.SessionUtils;
import workOnFile.CheckGameDescriptor;

import static workOnFile.CheckGameDescriptor.checkGameDescriptor;
import static workOnFile.CheckGameDescriptor.deserializeFrom;


public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gameContent = request.getParameter("file");
        String creator = SessionUtils.getUsername(request);
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        try {
            GameDescriptor gameDescriptor = deserializeFrom(gameContent);
            if (gameManager.isGameExists(gameDescriptor.getDynamicPlayers().getGameTitle()))
                out.println(gson.toJson("This Game is Already Exist!"));
            else {
                String message = checkGameDescriptor(gameDescriptor);
                out.println(gson.toJson(message));
                if (message == null)
                    buildGameAndAddToGamesList(gameDescriptor, creator);
            }
        } catch (Exception var8) {
        }
    }

    private void buildGameAndAddToGamesList(GameDescriptor gameDescriptor, String creator) {
        GameManager gameManager = ServletUtils.getGameManager(getServletContext());
        gameManager.addGame(new Game(gameDescriptor, creator));
    }
}
