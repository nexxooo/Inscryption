package modele.board;

public class AnimalCard extends Card {
    private int m_attackPoints;
    private int m_bloodCost;
    private int m_boneCost;
    private Boolean isFlying;
    public AnimalCard (String name,int hp, int maxhp, int att, int blo, int bone, boolean fly){
        super(name, hp, maxhp);
        m_attackPoints = att;
        m_bloodCost = blo;
        m_boneCost = bone;
        isFlying = fly;
    }

    public int getAttackPoints(){
        return m_attackPoints;
    }
    public int getBloodCost(){
        return m_bloodCost;
    }
    public Boolean isFlying(){
        return isFlying;
    }
    public int getBoneCost(){return m_boneCost;}

    @Override
    public String toString() {
        return getNom()+" PV:"+getMaxHealth()+" ATK:"+getAttackPoints()+"BLOOD:"+getBloodCost()+"BONES:"+getBoneCost();
    }
}
