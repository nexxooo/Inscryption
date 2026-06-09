package modele.player;
import modele.board.AnimalCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class DeckTest {
    @Test
    public void testGetTopCard() {
        Deck deck = new Deck();
        AnimalCard chat = new AnimalCard("Chat",1,1,1,0,false);
        deck.addCard(chat);
        Optional<AnimalCard> drawnCard = deck.getTopCard();
        assertTrue(drawnCard.isPresent());
        assertEquals(chat,drawnCard.get());
    }
    @Test
    public void testGetTopCardEmptyCard()
    {
        Deck deck = new Deck();
        Optional<AnimalCard> drawnCard = deck.getTopCard();
        assertFalse(drawnCard.isPresent());
    }

    @Test
    public void testDeleteCard() {
        Deck deck = new Deck();
        AnimalCard chat = new AnimalCard("Chat",1,1,1,0,false);
        AnimalCard grizzly = new AnimalCard("Grizzly",6,4,3,0,false);
        deck.addCard(chat);
        deck.addCard(grizzly);
        assertEquals(2, deck.sizeDeck());
        deck.deleteCard(0);
        assertEquals(1, deck.sizeDeck());
        assertEquals(grizzly, deck.getCard(0));
    }

    @Test
    public void testStoneSacrificePowerTransfer() {
        AnimalCard chat = new AnimalCard("Chat", 1, 0, 1, 0, false, new modele.Power.NombreuseVie());
        AnimalCard loup = new AnimalCard("Loup", 2, 3, 2, 0, false);
        
        assertTrue(chat.hasPower("Nombreuse Vie"));
        assertFalse(loup.hasPower("Nombreuse Vie"));
        
        for(modele.Power.Power p : chat.getPower()) {
            loup.addPower(p);
        }
        
        assertTrue(loup.hasPower("Nombreuse Vie"));
    }

    @Test
    public void testAddCardToDeck() {
        Deck deck = new Deck();
        assertEquals(0, deck.sizeDeck());
        AnimalCard hermine = new AnimalCard("Hermine", 3, 1, 1, 0, false);
        deck.addCard(hermine);
        assertEquals(1, deck.sizeDeck());
        assertEquals(hermine, deck.getCard(0));
    }
}
