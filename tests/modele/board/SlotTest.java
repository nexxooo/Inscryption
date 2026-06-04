package modele.board;

import modele.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SlotTest {
    @Test
    public void testSlot()
    {
        Slot slot = new Slot();
        assertTrue(slot.isEmpty());
        AnimalCard chat = new AnimalCard("Chat",1,0,1,0,false );
        slot.setCard(chat);
        assertFalse(slot.isEmpty());
        assertEquals("Chat",slot.getCard().getNom());
        slot.removeCard();
        assertTrue(slot.isEmpty());
    }
}
