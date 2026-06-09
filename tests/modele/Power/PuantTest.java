package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class PuantTest {
    @Test
    public void testPuant() {
        AnimalCard punaise = CardFactory.createAnimalCard("punaise").orElseThrow();
        assertTrue(punaise.hasPower("Puant"));

        Puant puant = new Puant();
        int baseAttack = 2;
        int modifiedAttack = puant.modifyOpponentAttack(baseAttack);
        assertEquals(1, modifiedAttack);
    }
}
