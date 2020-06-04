package servlets;

import engine.Users.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExitGameServlet extends HttpServlet {
    private  final String INDEX_URL="index.html";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        userManager.removeUser(SessionUtils.getUsername(request));
        SessionUtils.clearSession(request);
    }
}
