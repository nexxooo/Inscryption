package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import modele.board.Slot;
import org.junit.Test;
import static org.junit.Assert.*;

public class CroissanceTest {
    @Test
    public void testCroissance() {
        Slot slot = new Slot();
        AnimalCard louveteau = CardFactory.createAnimalCard("louveteau").orElseThrow();
        slot.setCard(louveteau);

        Croissance croissance = new Croissance();
        croissance.onDebut(slot);

        assertEquals("Loup", slot.getCard().getNom());
    }
}
