package engine.Game.Player;

import engine.Game.Board.Cell;

import java.io.Serializable;
import java.util.List;

public class AddNewArmyForOldArmy extends Move implements Serializable {
    private Cell cell;
    private  int cost;
    private List<Integer> quantity;

    public AddNewArmyForOldArmy(Cell cell, List<Integer> quantity){
        super(cell.getPlayer());
        this.cell=cell;
        this.quantity=quantity;
        makeMove();
    }

    @Override
    public void undo() {
        player.addTuring(cost);
    }

    @Override
    public void makeMove() {
        cost=cell.getArmy().add(quantity);
        player.subTuring(cost);
    }
}
