package modele.board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObstacleCardTest {
        @Test
        public void TestGetHealth()
        {
            ObstacleCard obstacleCard = new ObstacleCard("Rocher",5);
            assertEquals(5,obstacleCard.getHealthPoints());
        }
        @Test
        public void TestGetNom()
        {
            ObstacleCard obstacleCard = new ObstacleCard("Rocher",5);
            assertEquals("Rocher",obstacleCard.getNom());
        }
        @Test
        public void TestTakeDamage()
        {
            ObstacleCard obstacleCard = new ObstacleCard("Rocher",5);
            obstacleCard.takeDamage(2);
            assertEquals(3,obstacleCard.getHealthPoints());
        }
        @Test
        public void TestIsDead()
        {
            ObstacleCard obstacleCard = new ObstacleCard("Rocher",5);
            obstacleCard.takeDamage(6);
            assertEquals(true,obstacleCard.isDead());
        }
        @Test
        public void TestIsAlive()
        {
            ObstacleCard obstacleCard = new ObstacleCard("Rocher",5);
            assertEquals(false,obstacleCard.isDead());
        }
        @Test
        public void TestToString()
        {
            ObstacleCard obstacleCard = new ObstacleCard("Rocher",5);
            assertEquals("Rocher PV : 5",obstacleCard.toString());
        }


}
