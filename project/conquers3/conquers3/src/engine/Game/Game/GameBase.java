package engine.Game.Game;

import engine.Game.Board.Board;
import engine.Game.Player.Player;
import mypackage.GameDescriptor;
import mypackage.Teritory;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameBase implements Serializable {
    protected Board board;
    protected int numOfPlayers;
    protected int currentNumOfPlayers;
    protected List<Player> players;
    protected int numOfRounds;
    protected int curentRound;
    public enum Status {START,MIDDLE,END}

    public GameBase(Board board, int numOfPlayers, List<Player> players, int numOfRounds) {
        this.board = board;
        this.numOfPlayers = numOfPlayers;
        this.players = players;
        this.numOfRounds = numOfRounds;
        curentRound=0;
    }

    public GameBase(GameDescriptor gameDescriptor) {
        int columns = gameDescriptor.getGame().getBoard().getColumns().intValue();
        int rows = gameDescriptor.getGame().getBoard().getRows().intValue();
        int[] yields = new int[columns * rows];
        int[] minimalPowers = new int[columns * rows];
        if (gameDescriptor.getGame().getTerritories().getDefaultProfit() != null) {
            int defYield = gameDescriptor.getGame().getTerritories().getDefaultProfit().intValue();
            int defMinimalPowers = gameDescriptor.getGame().getTerritories().getDefaultArmyThreshold().intValue();
            for (int i = 0; i < columns * rows; i++) {
                yields[i] = defYield;
                minimalPowers[i] = defMinimalPowers;
            }
        }
        for (Teritory run : gameDescriptor.getGame().getTerritories().getTeritory()) {
            yields[run.getId().intValue() - 1] = run.getProfit().intValue();
            minimalPowers[run.getId().intValue() - 1] = run.getArmyThreshold().intValue();
        }
        this.board = new Board(columns, rows, yields, minimalPowers);
        this.numOfPlayers = gameDescriptor.getDynamicPlayers().getTotalPlayers().intValue();
        this.numOfRounds = gameDescriptor.getGame().getTotalCycles().intValue();
        this.curentRound = 0;
        this.currentNumOfPlayers=0;
        this.players=new ArrayList<>();
        /*
        players = new ArrayList<>();
        players.add(new Player("fb7b7b", gameDescriptor.getGame().getInitialFunds().intValue(), gameDescriptor.getPlayers().getPlayer().get(0).getName()));
        players.add(new Player("ffb45e", gameDescriptor.getGame().getInitialFunds().intValue(), gameDescriptor.getPlayers().getPlayer().get(1).getName()));
        if (numOfPlayers > 2) {
            players.add(new Player("b3f975", gameDescriptor.getGame().getInitialFunds().intValue(), gameDescriptor.getPlayers().getPlayer().get(2).getName()));
            if (numOfPlayers == 4)
                players.add(new Player("c773f4", gameDescriptor.getGame().getInitialFunds().intValue(), gameDescriptor.getPlayers().getPlayer().get(3).getName()));
        }
         */
    }

    public int getCurentRound() {
        return curentRound;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public int getNumOfRounds() {
        return numOfRounds;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Status getGameStatus() {
        if (curentRound == 0)
            return Status.START;
        if (curentRound == numOfRounds)
            return Status.END;
        return Status.MIDDLE;
    }

    public Player getWinner() {
        Comparator<Player> comparator = Comparator.comparing( Player::getTotalYield );
        Player winner= players.stream().max(comparator).get();
        for(Player run:players){
            if(winner.getColor().compareTo(run.getColor())!=0){
                if(winner.getTotalYield()==run.getTotalYield())
                    return null;
            }
        }
        return winner;
    }
}
