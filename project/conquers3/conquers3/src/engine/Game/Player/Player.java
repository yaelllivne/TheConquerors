package engine.Game.Player;


import engine.Game.Board.Cell;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private String color;
    private int turing;
    private int yield;
    private int numberOfCells=0;
    //private List<Cell> myCells;

    public Player(String color, int startTuring, String name) {
        this.color = color;
        turing=startTuring;
        yield=0;
        this.name=name;
        //myCells = new ArrayList<>();
    }

    public Player(Player other) {
        color = other.color;
        this.turing=other.turing;
        this.yield=other.yield;
        name = other.name;
        this.numberOfCells=other.numberOfCells;
        //myCells = new ArrayList<>();
    }

    public void addCell() {
        numberOfCells++;
    }
    public void addTuringFromCell(Cell newCell) {
        yield=yield+newCell.getYield();
    }

    public void deleteCell(Cell deleteCell) {
        //myCells.remove(deleteCell);
        numberOfCells--;
        yield=yield-deleteCell.getYield();
    }


    public void setTuring(int turing) {
        this.turing=turing;
    }

    public String getName() {
        return name;
    }

    public void addTuring(int turing) {
        this.turing+=turing;
    }

    public void subTuring(int turing){
        this.turing-=turing;    }

    public int getTuring() {
        return turing;
    }

    public String getColor() {
        return color;
    }

    public int getNumberOfCells() {
        return numberOfCells;
    }

    public int getTotalYield() {
        return yield;
    }

    public void playerExit() {

    }
}
