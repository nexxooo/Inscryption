package modele.board;

import java.util.Optional;

public abstract class Card {
    private String m_name;
    private int m_healthPoints;
    private int m_maxHealth;
    public Card(String name, int healthPoints) {
        this.m_name = name;
        this.m_healthPoints = healthPoints;
        this.m_maxHealth = healthPoints;
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
    public int getHealthPoints(){
        return m_healthPoints;
    }
    public abstract String[] getCardAscii();
    public int getAttackPoints(){
        return 0;
    }


    public Optional<AnimalCard> isAnimal(){
        return Optional.empty();
    }

    public void reHeal(){
        m_healthPoints = m_maxHealth;
    }
}
