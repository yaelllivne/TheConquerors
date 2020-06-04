package engine.Game.Army;



import engine.Game.Player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Army implements Serializable {
    private Player player;
    private List<ForceOfArmy> army;
    private int length;
    private int power;
    private int maxPower;
    private int cost;

    public Army(List<Integer> quantity,List<Force> force,Player player){
        this.player=player;
        length=quantity.size();
        army=new ArrayList<>() ;
        for(int i=0;i<length;i++)
            army.add(new ForceOfArmy(force.get(i),quantity.get(i)));
        refresh();
    }

    public Army(Army other) {
        player=other.player;
        length=other.length;
        power =other.power;
        maxPower=other.maxPower;
        cost =other.cost;
        army=new ArrayList<ForceOfArmy>();
        for(ForceOfArmy force:other.army){
            army.add(new ForceOfArmy(force));
        }
    }

    public List<ForceOfArmy> getArmy() {
        return army;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCost() {
        return cost;
    }

    public int getPower() {
        return power;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public void refresh(){
        cost=0;
        power=0;
        maxPower=0;
        for(int i=0;i<length;i++){
            if(army.get(i).getCompetence()!=0) {
                cost += army.get(i).getCost();
                power += army.get(i).getPower();
                maxPower += army.get(i).getMaxPower();
            }
            else
                army.get(i).reset();
        }
    }

    public void endRound(){
        for(int i=0;i<length;i++)
            army.get(i).endRound();
        refresh();
    }

    public int add(List<Integer> quantity){
        int sumCost=0;
        Integer i=0;
        for(ForceOfArmy force:army) {
            force.add(quantity.get(i));
            sumCost+=(quantity.get(i)*(force.getForce().getCost()));
            i++;
        }
        refresh();
        return sumCost;
    }

    public void sub(int lessCompetaence) {
        for (int i = 0; i < length; i++)
            army.get(i).subCompetence(lessCompetaence);
        refresh();
    }

    public int getCostForTrain() {
        int costForTrain=0;
        for(ForceOfArmy force:army)
            costForTrain+=force.getCostToTrain();
        return costForTrain;
    }

    public void Train() {
        for(ForceOfArmy force:army)
            force.trainForce();
        refresh();
    }

    public void cutHalfCompetece(){
        for(ForceOfArmy force:army)
            force.subCompetence(force.getCompetence()/2);
        refresh();
    }
    public int turingForSell(){
        int total=0;
        for(ForceOfArmy force:army)
            total+=force.turingForSell();
        return total;
    }

}
