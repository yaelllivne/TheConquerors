package engine.Game.Army;

import java.io.Serializable;

public class ForceOfArmy implements Serializable {
    private Force force;
    private int numOfSoldier;
    private int power;
    private int maxPower;
    private int cost;
    private int competence;

    public ForceOfArmy(Force force, int numOfSoldier) {
        this.force = force;
        this.numOfSoldier = numOfSoldier;
        this.competence = 100;
        this.cost = numOfSoldier * force.getCost();
        this.power = numOfSoldier * force.getPower();
        this.maxPower = power;
    }

    public ForceOfArmy(ForceOfArmy other) {
        force = other.force;
        numOfSoldier = other.numOfSoldier;
        power = other.power;
        maxPower = other.maxPower;
        cost = other.cost;
        competence = other.competence;
    }

    public int getCompetence() {
        return competence;
    }

    public int getPower() {
        return power;
    }

    public int getCost() {
        return cost;
    }

    public Force getForce() {
        return force;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getNumOfSoldier() {
        return numOfSoldier;
    }

    public void endRound() {
        competence -= force.getLessCompetence();
        if (competence < 0)
            reset();
        else
            power = maxPower * competence / 100;
    }

    public void add(int num) {
        if (num != 0) {
            numOfSoldier += num;
            this.cost += (num * force.getCost());
            int addPower = force.getPower() * num;
            maxPower += addPower;
            power += addPower;
            competence = power * 100 / maxPower;
        } else {
            numOfSoldier = 0;
            cost = 0;
            maxPower = 0;
            power = 0;
            competence = 0;
        }
    }

    public void trainForce() {
        competence = 100;
        power = maxPower;
    }

    public void subCompetence(int lessCompetence) {
        competence -= lessCompetence;
        if (competence < 0)
            reset();
        else
            power = maxPower * competence / 100;
    }

    public void reset() {
        numOfSoldier = 0;
        cost = 0;
        power = 0;
        maxPower = 0;
        competence = 0;
    }

    public int getCostToTrain() {
        return (100 - competence) * cost / 100;
    }

    public int turingForSell() {
        return competence * cost / 100;
    }

    public void setQuantitiy(int newQuantity) {
        int comp = this.competence;
        reset();
        if (newQuantity != 0) {
            add(newQuantity);
            subCompetence(100 - comp);
        }
    }
}

