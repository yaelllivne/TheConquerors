package engine.Game.Board;

import java.io.Serializable;

public class Location implements Serializable {
    private int row;
    private int column;

    Location(int row,int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
