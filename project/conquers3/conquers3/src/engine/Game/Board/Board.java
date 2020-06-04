package engine.Game.Board;



import engine.Game.Player.Player;

import java.io.Serializable;
import java.util.List;

public class Board implements Serializable {
    private int numberOfColumns;
    private int numberOfRows;
    private Cell[][] board;

    public Board(int numberOfColumns, int numberOfRows, int[] yields, int[] minimalPowers) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;

        board = new Cell[numberOfRows][numberOfColumns];
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                Location location = new Location(r, c);
                int cellNumber = r * numberOfColumns + c;
                board[r][c] = new Cell(location, yields[cellNumber], minimalPowers[cellNumber], cellNumber+1);
            }
        }
    }

    public Board(Board other, List<Player> playersToSave) {
        numberOfColumns=other.numberOfColumns;
        numberOfRows=other.numberOfRows;
        board = new Cell[numberOfRows][numberOfColumns];
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                if(other.board[r][c].getPlayer()==null)
                    board[r][c] = new Cell(other.board[r][c],null);
                else
                    for(Player run:playersToSave){
                        if(run.getName().compareTo(other.board[r][c].getPlayer().getName())==0)
                            board[r][c] = new Cell(other.board[r][c],run);
                    }
            }
        }
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public Cell[][] getBoard() {
        return board;
    }


    public Cell getCellByID(int cellID){
        cellID--;
        if(cellID>=0&&cellID<=(numberOfRows*numberOfColumns-1)) {
            int row = cellID / numberOfColumns;
            int column = cellID % numberOfColumns;
            return board[row][column];
        }
        return null;
    }

    public int getMinPoweByID(int cellID){
        return getCellByID(cellID).getMinimalPower();
    }

    public void endRound() {
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                if(board[r][c].getCellStatus()== Cell.CellStatus.OCCUPIED)
                    board[r][c].updateCellAfterRound();
            }
        }
    }

    public void undoRound() {
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfRows; c++) {
                if(board[r][c].getCellStatus()== Cell.CellStatus.OCCUPIED)
                    board[r][c].undo();
            }
        }
    }

    public boolean canMoveOnCell(Player player, int cellID) {
        String playerNameInCell="";
        if(player.getNumberOfCells()==0)
            return true;
        int celldown=cellID+numberOfColumns;
        int cellup=cellID-numberOfColumns;
        int cellleft=cellID-1;
        int cellright=cellID+1;
        if(celldown<=numberOfColumns*numberOfRows){
            if(getCellByID(celldown).getPlayer()!=null) {
                playerNameInCell = getCellByID(celldown).getPlayer().getName();
                if (playerNameInCell.compareTo(player.getName()) == 0)
                    return true;
            }
        }
        if(cellup>0){
            if(getCellByID(cellup).getPlayer()!=null) {
                playerNameInCell = getCellByID(cellup).getPlayer().getName();
                if (playerNameInCell.compareTo(player.getName()) == 0)
                    return true;
            }
        }
        if(cellID%numberOfColumns!=1){
            if(getCellByID(cellleft).getPlayer()!=null) {
                playerNameInCell = getCellByID(cellleft).getPlayer().getName();
                if (playerNameInCell.compareTo(player.getName()) == 0)
                    return true;
            }
        }
        if(cellright%numberOfColumns!=1){
            if(getCellByID(cellright).getPlayer()!=null) {
                playerNameInCell = getCellByID(cellright).getPlayer().getName();
                if (playerNameInCell.compareTo(player.getName()) == 0)
                    return true;
            }
        }
        if(getCellByID(cellID).getPlayer()!=null) {
            playerNameInCell = getCellByID(cellID).getPlayer().getName();
            if (playerNameInCell.compareTo(player.getName()) == 0)
                return true;
        }
        return false;
    }
}
