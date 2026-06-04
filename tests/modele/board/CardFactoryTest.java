package modele.board;

import modele.Score;
import modele.player.Deck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

public class CardFactoryTest {
    @Test
    public void testAnimalCard()
    {
        Optional<AnimalCard> optChat = CardFactory.createAnimalCard("cHaT");
        assertTrue(optChat.isPresent());
        AnimalCard chat = optChat.get();
        assertEquals("Chat", chat.getNom());
        assertEquals(1, chat.getAttackPoints());
    }
    @Test
    public void testNotAnimalCard()
    {
        Optional<AnimalCard> optAndreas = CardFactory.createAnimalCard("Andreas");
        assertFalse(optAndreas.isPresent());
    }
    @Test
    public void testRandomObstacle()
    {
        ObstacleCard obstacleCard = CardFactory.createRandomObstacle();
        assertNotNull(obstacleCard);
        boolean valide = obstacleCard.getNom().equals("Sapin") || obstacleCard.getNom().equals("Rocher");
        assertTrue(valide);

    }
    @Test
    public void testInitializeDeck()
    {
        Deck deck = new Deck();
        CardFactory.initializeDeck(deck);
        assertEquals(15,deck.getCardCount());
    }

}
