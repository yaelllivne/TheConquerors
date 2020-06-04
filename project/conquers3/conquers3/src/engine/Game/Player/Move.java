package engine.Game.Player;


import java.io.Serializable;

public abstract class Move implements Serializable {
    protected Player player;

    public Move(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void undo();
    public abstract void makeMove();
}
