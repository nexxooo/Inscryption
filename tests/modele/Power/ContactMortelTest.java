package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContactMortelTest {
    @Test
    public void testContactMortel() {
        AnimalCard vipere = CardFactory.createAnimalCard("vipere").orElseThrow();
        assertTrue(vipere.hasPower("Contact Mortel"));

        ContactMortel contact = new ContactMortel();
        AnimalCard grizzly = CardFactory.createAnimalCard("grizzly").orElseThrow();

        int modifiedDamage = contact.modifyDamage(vipere, grizzly, 1);
        assertEquals(6, modifiedDamage);
    }
}
