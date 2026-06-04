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
}
