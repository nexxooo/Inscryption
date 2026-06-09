package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class NombreuseVieTest {
    @Test
    public void testNombreuseVie() {
        AnimalCard chat = CardFactory.createAnimalCard("chat").orElseThrow();
        assertTrue(chat.hasPower("Nombreuse Vie"));
    }
}
