package servlets;

import constants.Constants;
import engine.Users.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Constants.USERNAME;

public class LoginServlet extends HttpServlet {
    private final String LOBBY_URL = "./html/lobby.html";
    private final String LOGIN_URL = "./index.html";
    private final String USER_NAME_ERROR = "/loginError.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in servlet");
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        if (usernameFromSession == null) {
            String usernameFromParameter = request.getParameter(USERNAME);
            if (usernameFromParameter == null) {
                response.sendRedirect(LOGIN_URL);
            } else {
                usernameFromParameter = usernameFromParameter.trim();
                synchronized (this) {
                    String errorMessage;
                    if(usernameFromParameter=="") {
                        errorMessage = "Please Insert Your Name";
                        request.setAttribute(Constants.USER_NAME_ERROR, errorMessage);
                        getServletContext().getRequestDispatcher(USER_NAME_ERROR).forward(request, response);
                    }
                    else {
                        if (userManager.isUserExists(usernameFromParameter)) {
                            errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                            request.setAttribute(Constants.USER_NAME_ERROR, errorMessage);
                            getServletContext().getRequestDispatcher(USER_NAME_ERROR).forward(request, response);
                        } else {

                            userManager.addUser(usernameFromParameter);
                            request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);
                            response.sendRedirect(LOBBY_URL);
                        }
                    }
                }
            }
        } else {
            response.sendRedirect(LOBBY_URL);
        }
    }
}
