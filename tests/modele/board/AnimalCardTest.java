package modele.board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AnimalCardTest
{
    @Test
    public void TestGetHealth()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        assertEquals(1,animalCard.getHealthPoints());
    }
    @Test
    public void TestGetNom()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        assertEquals("Chat",animalCard.getNom());
    }
    @Test
    public void TestAttaque()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        assertEquals(0,animalCard.getAttackPoints());

    }
    @Test
    public void TestBloodCost()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        assertEquals(1,animalCard.getBloodCost());

    }
    @Test
    public void TestFlying()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        assertEquals(false,animalCard.isFlying());

    }
    @Test
    public void TestBoneCost()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        assertEquals(0,animalCard.getBoneCost());
    }
    @Test
    public void TestTakeDamage()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        animalCard.takeDamage(2);
        assertEquals(0,animalCard.getHealthPoints());
    }
    @Test
    public void TestIsDead()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        animalCard.takeDamage(2);
        assertEquals(true,animalCard.isDead());
    }
    @Test
    public void TestIsAlive()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        assertEquals(false,animalCard.isDead());
    }
    @Test
    public void TestToString()
    {
        AnimalCard animalCard = new AnimalCard("Chat",1,0,1,0,false );
        assertEquals("Chat PV : 1 ATK : 0 BLOOD : 1 BONES : 0",animalCard.toString());
    }

}
