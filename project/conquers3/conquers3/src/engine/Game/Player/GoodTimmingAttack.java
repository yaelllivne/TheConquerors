package engine.Game.Player;


import engine.Game.Army.Army;
import engine.Game.Board.Cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.Serializable;
import java.util.Random;

    public class GoodTimmingAttack extends Move implements Serializable {
        private Player defender;
        private int turingAttackerBefore;
        private int turingDefenderBefore;
        private Cell cell;
        private Player winner = null;
        Army armyOfAttacker;

        public GoodTimmingAttack(Player player, Cell cell, Army armyOfAttacker) {
            super(player);
            this.cell = cell;
            this.defender = cell.getPlayer();
            turingAttackerBefore = player.getTuring();
            turingDefenderBefore = defender.getTuring();
            this.armyOfAttacker = armyOfAttacker;
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
            List<Integer> result = new ArrayList<>();
            player.subTuring(armyOfAttacker.getCost());
            for (int i = 0; i < armyOfAttacker.getArmy().size(); i++) {
                result.add(armyOfAttacker.getArmy().get(i).getNumOfSoldier() - cell.getArmy().getArmy().get(i).getNumOfSoldier());
            }

            for (int i = armyOfAttacker.getArmy().size() - 1; i >= 0; i--) {
                if (result.get(i) != 0) {
                    if (result.get(i) < 0)
                        winner = cell.getPlayer();
                    else
                        winner = player;
                    i = -1;
                }
            }
            if (winner == null)
                cell.resetCell();

            else {
                for (int i = 0; i < armyOfAttacker.getArmy().size(); i++) {
                    if (winner == player) {

                        if (result.get(i) < 0)
                            armyOfAttacker.getArmy().get(i).setQuantitiy(0);
                        else
                            armyOfAttacker.getArmy().get(i).setQuantitiy(result.get(i));
                    }
                    if (winner == defender) {
                        if (result.get(i) > 0)
                            cell.getArmy().getArmy().get(i).setQuantitiy(0);
                        else
                            cell.getArmy().getArmy().get(i).setQuantitiy(-(result.get(i)));
                    }
                }
                if (winner == player)
                    cell.setArmy(armyOfAttacker);
                cell.setPlayer(winner);

                cell.getArmy().refresh();
                if(!cell.validCell()){
                    winner.addTuring(cell.getArmy().turingForSell());
                    cell.resetCell();
                }
            }
        }
    }

