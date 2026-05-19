package modele.board;

public class AnimalCard extends Card {
    private int attackPoints;
    private int bloodCost;
    private int boneCost;
    private Boolean isFlying;
    public AnimalCard (String name,int hp, int maxhp, int att, int blo, int bone, boolean fly){
        super(name, hp, maxhp);
        attackPoints = att;
        bloodCost = blo;
        boneCost = bone;
        isFlying = fly;
    }

    public int getAttackPoints(){
        return attackPoints;
    }
    public int getBloodCost(){
        return bloodCost;
    }
    public Boolean isFlying(){
        return isFlying;
    }
}
