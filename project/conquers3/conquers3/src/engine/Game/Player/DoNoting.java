package engine.Game.Player;

import java.io.Serializable;

public class DoNoting extends Move implements Serializable {

    public DoNoting(Player player){
        super(player);
    }

    @Override
    public void undo() {
    }

    @Override
    public void makeMove() {
    }
}
