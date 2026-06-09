package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class PiquesPointuesTest {
    @Test
    public void testPiquesPointues() {
        AnimalCard porcEpic = CardFactory.createAnimalCard("porc-epic").orElseThrow();
        assertTrue(porcEpic.hasPower("Piques Pointues"));

        PiquesPointues piques = new PiquesPointues();
        AnimalCard coyote = CardFactory.createAnimalCard("coyote").orElseThrow();

        piques.onReceiveDamage(coyote);
        assertTrue(coyote.isDead());
    }
}
