package engine.Users;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import engine.Game.Game.Game;

public class GameManager {
    private final Set<Game> gameSet;

    public GameManager() {
        gameSet = new HashSet<>();
    }

    public synchronized void addGame(Game game) {
        gameSet.add(game);
    }

    public synchronized void removeGame(Game game) {
        gameSet.remove(game);
    }

    public synchronized Set<Game> getGames() { return Collections.unmodifiableSet(gameSet); }

    public synchronized Game getGameByName(String gameName) { for (Game run : gameSet) {
        if (run.getName().compareTo(gameName) == 0)
            return run;
    }
        return null;}

    public boolean isGameExists(String gamename) {
        for (Game run : gameSet) {
            if (run.getName().compareTo(gamename) == 0)
                return true;
        }
        return false;
    }


}
