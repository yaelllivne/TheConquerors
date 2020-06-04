package engine.Game.Army;

import java.io.Serializable;

public class Force implements Serializable {
    private String name;
    private int power;
    private int cost;
    private int lessCompetence;
    private int rank;

    public Force(String name,int power,int cost,int lessCompetence,int rank)
    {
        this.name=name;
        this.power=power;
        this.cost=cost;
        this.lessCompetence = lessCompetence;
        this.rank=rank;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getCost() {
        return cost;
    }

    public int getLessCompetence() {
        return lessCompetence;
    }

    public int getLessCompetenceInTuring(int quantity){
        return (100-lessCompetence)*cost*quantity;
    }
}
