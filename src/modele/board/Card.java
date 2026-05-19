package modele.board;

public abstract class Card {
    private String name;
    private int healthPoints;
    private int maxHealth;
    public Card(String name, int healthPoints, int maxHealth) {
        this.name = name;
        this.healthPoints = healthPoints;
        this.maxHealth = maxHealth;
    }

    public void takeDamage(int amount) {
        if (healthPoints < amount) {
            healthPoints = 0;
        }
        else  {
            healthPoints -= amount;
        }
    }
    public boolean isDead() {
        return healthPoints == 0;
    }
}
