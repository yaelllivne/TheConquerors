package engine.Game.Player;

import engine.Game.Board.Cell;

import java.io.Serializable;

public class Train extends Move implements Serializable {
    private Cell cell;
    private  int cost;

    public Train(Cell cell){
        super(cell.getPlayer());
        this.cell=cell;
        makeMove();
    }

    @Override
    public void undo() {
        cell.getPlayer().addTuring(cost);
    }

    @Override
    public void makeMove() {
        cost=cell.getArmy().getCostForTrain();
        player.subTuring(cost);
        cell.getArmy().Train();
    }
}
