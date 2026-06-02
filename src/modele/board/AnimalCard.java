package modele.board;

import java.util.Optional;

public class AnimalCard extends Card {
    private int m_attackPoints;
    private int m_bloodCost;
    private int m_boneCost;
    private Boolean isFlying;
    public AnimalCard (String name,int hp, int att, int blo, int bone, boolean fly){
        super(name, hp);
        m_attackPoints = att;
        m_bloodCost = blo;
        m_boneCost = bone;
        isFlying = fly;
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
        return getNom()+" PV:"+getMaxHealth()+" ATK:"+getAttackPoints()+"BLOOD:"+getBloodCost()+"BONES:"+getBoneCost();
    }

    @Override
    public String[] getCardAscii() {
        String[] lines = new String[7];
        lines[0] = "*-----------*";
        lines[1] = String.format("| %-9s |", getNom());
        lines[2] = "|-----------|";
        lines[3] = String.format("| PV: %-5d |", getHealthPoints());
        lines[4] = String.format("| Att: %-4d |", getAttackPoints());
        lines[5] = "|           |";
        lines[6] = "*-----------*";
        return lines;
    }

    @Override
    public Optional<AnimalCard> isAnimal() {
        return Optional.of(this);
    }
}
