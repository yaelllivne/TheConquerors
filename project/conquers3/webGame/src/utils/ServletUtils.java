package utils;

import engine.Users.GameManager;
import engine.Users.UserManager;

import javax.servlet.ServletContext;

public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";

	private static final String GAME_MANAGER_ATTRIBUTE_NAME = "gameManager";

	private static final Object userManagerLock = new Object();

	private static final Object gameManagerLock = new Object();

	public static UserManager getUserManager(ServletContext servletContext) {

		synchronized (userManagerLock) {
			if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
			}
		}
		return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
	}
	public static GameManager getGameManager(ServletContext servletContext) {

		synchronized (gameManagerLock) {
			if (servletContext.getAttribute(GAME_MANAGER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(GAME_MANAGER_ATTRIBUTE_NAME, new GameManager());
			}
		}
		return (GameManager) servletContext.getAttribute(GAME_MANAGER_ATTRIBUTE_NAME);
	}
}
