package modele.board;

import modele.Power.Power;

import java.util.List;
import java.util.Optional;

public class AnimalCard extends Card {
    private int m_attackPoints;
    private int m_bloodCost;
    private int m_boneCost;
    private Boolean isFlying;
    private List<Power> m_power = new java.util.ArrayList<>();
    public AnimalCard (String name,int hp, int att, int blo, int bone, boolean fly){
        super(name, hp);
        m_attackPoints = att;
        m_bloodCost = blo;
        m_boneCost = bone;
        isFlying = fly;
    }
    public AnimalCard (String name,int hp, int att, int blo, int bone, boolean fly,Power power){
        super(name, hp);
        m_attackPoints = att;
        m_bloodCost = blo;
        m_boneCost = bone;
        isFlying = fly;
       addPower(power);
    }

    @Override
    public int getAttackPoints(){
        return m_attackPoints;
    }
    public int getBloodCost(){
        return m_bloodCost;
    }
    public boolean isFlying(){
        return isFlying;
    }
    public int getBoneCost(){return m_boneCost;}

    @Override
    public String toString() {
        String res= getNom() + " PV : " + getMaxHealth() + " ATK : " + getAttackPoints() + " BLOOD : " + getBloodCost() + " BONES : " + getBoneCost();
        if(!m_power.isEmpty()){
            res += " Power: ";
            for(Power p : m_power){
                res += p.getName()+"  ";
            }
        }
        return res;
    }

    @Override
    public String[] getCardAscii() {
        String[] lines = new String[7];
        lines[0] = "*-----------*";
        lines[1] = String.format("| %-9s |", getNom());
        lines[2] = String.format("| PV: %-5d |", getHealthPoints());
        lines[3] = String.format("| Att: %-4d |", getAttackPoints());
        
        List<String> powers = new java.util.ArrayList<>();
        if (isFlying()) {
            powers.add("Volant");
        }
        for (Power p : getPower()) {
            powers.add(p.getShortName());
        }
        
        String powerStr = "";
        if (!powers.isEmpty()) {
            powerStr = String.join(" ", powers);
            if (powerStr.length() > 9) {
                powerStr = powerStr.substring(0, 9);
            }
        }
        lines[4] = String.format("| %-9s |", powerStr);
        lines[5] = "|           |";
        lines[6] = "*-----------*";
        return lines;
    }

    @Override
    public Optional<AnimalCard> isAnimal() {
        return Optional.of(this);
    }

    public  List<Power> getPower() {
        return m_power;
    }
    public boolean hasPower(String powerName){
        for(Power power : m_power){
            if(power.getName().equals(powerName)) {
                return true;
            }
        }
        return false;
    }
    public void addPower(Power power){
        m_power.add(power);
    }

    public void setAttack(int att){
        m_attackPoints = att;
    }
}
