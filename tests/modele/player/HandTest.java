package modele.player;
import modele.board.AnimalCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class HandTest {
    @Test
    public void testAddAndRemoveCard() {
        Hand hand = new Hand();
        AnimalCard chat = new AnimalCard("Chat",1,0,1,0,false );
        AnimalCard ecureuil = new AnimalCard("Ecureuil", 1, 1, 0, 0, false);
        hand.addCard(chat);
        hand.addCard(ecureuil);
        assertEquals(2,hand.getCardCount());
        assertEquals(1,hand.getMaxIndex());
        hand.removeCard(0);
        assertEquals(1,hand.getCardCount());
        assertEquals(0,hand.getMaxIndex());
    }
}
