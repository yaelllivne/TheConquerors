package engine.Game.Board;



import engine.Game.Army.Army;
import engine.Game.Player.Player;

import java.io.Serializable;

public class Cell implements Serializable {

    public enum CellStatus {YOURS,ANOTHERPLAYER,NEUTRAL,OCCUPIED,UNREACHABLE}
    private int cellNumber;
    private Location cellLocation;
    private int minimalPower;
    private int yield;
    private Player player;
    private Army army;

    public Cell(Location cellLocation,int yield,int minimalPower,int cellNumber){
        this.cellNumber=cellNumber;
        this.cellLocation=cellLocation;
        this.minimalPower=minimalPower;
        this.yield=yield;
        player=null;
        army=null;
    }

    public Cell(Cell other,Player player){
        cellNumber=other.cellNumber;
        cellLocation=other.cellLocation;
        minimalPower=other.minimalPower;
        yield=other.yield;
        if(player!=null) {
            this.player = player;
            army = new Army(other.army);
            player.addCell();
        }
    }

    public void setArmy(Army army) {
        this.army = army;
    }

    public void setPlayer(Player conqueror) {
        if(this.player!=null)
            this.player.deleteCell(this);
        this.player = conqueror;
        if(this.player!=null) {
            this.player.addCell();
            this.player.addTuringFromCell(this);
        }
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public int getMinimalPower() {
        return minimalPower;
    }

    public int getYield() {
        return yield;
    }

    public Player getPlayer() {
        return player;
    }

    public Army getArmy() {
        return army;
    }

    public void resetCell(){
        if(this.player!=null)
            this.player.deleteCell(this);
        player=null;
        army=null;
    }

    public void updateCellAfterRound(){
        army.endRound();
        player.addTuring(yield);
        int newPower=army.getPower();
        if(newPower<minimalPower) {
            player.deleteCell(this);
            player = null;
            army = null;
        }
    }

    public CellStatus getCellStatus(Player player) {
        if (this.player == null)
            return CellStatus.NEUTRAL;
        if (this.player.getName().compareTo(player.getName()) == 0)
            return CellStatus.YOURS;
        return CellStatus.ANOTHERPLAYER;
    }
    public CellStatus getCellStatus(){
        if(this.player==null)
            return CellStatus.NEUTRAL;
        return CellStatus.OCCUPIED;
    }
    public void undo() {
        player.subTuring(yield);
    }
    public Boolean validCell(){
        return army.getPower()>=minimalPower;
    }

}
