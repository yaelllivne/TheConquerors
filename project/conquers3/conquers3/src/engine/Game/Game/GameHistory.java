package engine.Game.Game;



import engine.Game.Board.Board;
import engine.Game.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameHistory extends GameBase implements Serializable {


    public GameHistory(Board board, int numOfPlayers, List<Player> players, int numOfRounds, int curentRound){
        super(board,numOfPlayers,players,numOfRounds);
        this.curentRound=curentRound;
    }

    static GameHistory saveGame(Game other){
        int index=0;
        List<Player> playersToSave=new ArrayList<>();
        for(Player run:other.players) {
            playersToSave.add(new Player(run));
           // playersToSave.get(index).saveTuringYield();
            index++;
        }
        return new GameHistory(new Board(other.board,playersToSave),other.numOfPlayers,playersToSave,other.numOfRounds,other.curentRound);
    }

}
