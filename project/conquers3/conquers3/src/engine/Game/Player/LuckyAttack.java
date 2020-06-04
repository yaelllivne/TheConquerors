package engine.Game.Player;



import engine.Game.Army.Army;
import engine.Game.Board.Cell;

import java.io.Serializable;
import java.util.Random;

public class LuckyAttack extends Move implements Serializable {
    private Player defender;
    private int turingAttackerBefore;
    private int turingDefenderBefore;
    private Cell cell;
    private Player winner;
    Army armyOfAttacker;
    public LuckyAttack(Player player, Cell cell, Army armyOfAttacker) {
        super(player);
        this.cell=cell;
        this.defender=cell.getPlayer();
        turingAttackerBefore=player.getTuring();
        turingDefenderBefore=defender.getTuring();
        this.armyOfAttacker=armyOfAttacker;
        makeMove();
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public void undo() {
        defender.setTuring(turingDefenderBefore);
        player.setTuring(turingAttackerBefore);
    }

    @Override
    public void makeMove() {
        boolean stronger;
        int losePower;
        player.subTuring(armyOfAttacker.getCost());
        int powerAttecker=armyOfAttacker.getPower();
        int powerDefender=cell.getArmy().getPower();
        int allPower=powerAttecker+powerDefender;
        Random randomGenerator = new Random();
        int numRandom=randomGenerator.nextInt(allPower);
        if(numRandom<powerAttecker) {
            winner = player;
            cell.setArmy(armyOfAttacker);
            cell.setPlayer(player);
            if(powerAttecker<powerDefender)
                stronger = false;
            else
                stronger = true;
            losePower=powerDefender;
        }
        else {
            winner = defender;
            if(powerAttecker<powerDefender)
                stronger=true;
            else
                stronger=false;
        losePower=powerAttecker;
        }
        if(stronger)
            cell.getArmy().sub(losePower*100/cell.getArmy().getMaxPower());
        else
            cell.getArmy().cutHalfCompetece();
        if(!cell.validCell()){
            winner.addTuring(cell.getArmy().turingForSell());
            cell.resetCell();
        }

    }
}
