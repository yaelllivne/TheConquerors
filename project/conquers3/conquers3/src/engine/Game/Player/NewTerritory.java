package engine.Game.Player;

import engine.Game.Army.Army;
import engine.Game.Army.Force;
import engine.Game.Board.Cell;

import java.io.Serializable;
import java.util.List;

public class NewTerritory extends Move implements Serializable {
    private Cell cell;
    private  int cost;
    private List<Integer> quantity;
    List<Force> forces;

    public NewTerritory(Cell cell, List<Integer> quantity, Player player,List<Force> forces){
        super(player);
        this.quantity=quantity;
        this.cell=cell;
        this.forces=forces;
        makeMove();
    }

    @Override
    public void undo() {
        player.addTuring(cost);
    }

    @Override
    public void makeMove() {
        cell.setArmy(new Army(quantity,forces,player));
        cell.setPlayer(player);
        cost=cell.getArmy().getCost();
        player.subTuring(cost);
    }
}
