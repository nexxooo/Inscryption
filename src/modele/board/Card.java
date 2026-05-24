package modele.board;

public abstract class Card {
    private String m_name;
    private int m_healthPoints;
    private int m_maxHealth;
    public Card(String name, int healthPoints, int maxHealth) {
        this.m_name = name;
        this.m_healthPoints = healthPoints;
        this.m_maxHealth = maxHealth;
    }

    public void takeDamage(int amount) {
        if (m_healthPoints < amount) {
            m_healthPoints = 0;
        }
        else  {
            m_healthPoints -= amount;
        }
    }
    public boolean isDead() {
        return m_healthPoints == 0;
    }

    public String getNom(){
        return m_name;
    }
    public int getMaxHealth(){
        return m_maxHealth;
    }
}
