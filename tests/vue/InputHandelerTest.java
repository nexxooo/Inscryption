package vue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputHandelerTest {

    private final InputStream systemIn = System.in;

    // Cette méthode crée un faux clavier pour simuler les saisies du joueur
    private InputHandeler createHandlerWithInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        return new InputHandeler();
    }

    @AfterEach
    public void restoreSystemInput() {

        System.setIn(systemIn);
    }

    @Test
    public void testSaisieFin() {
        InputHandeler handler = createHandlerWithInput("fin\n");
        handler.askChoice(3);
        assertEquals(UserChoice.PASSER, handler.getChoice(), "Le choix devrait être PASSER");
    }



    @Test
    public void testSaisiePlacerValide() {

        InputHandeler handler = createHandlerWithInput("placer 2 b3\n");

        handler.askChoice(3); // On suppose qu'on a 4 cartes en main (index max = 3)

        assertEquals(UserChoice.PLACER, handler.getChoice(), "Le choix devrait être PLACER");
        assertEquals(2, handler.getIndexCard(), "L'index de la carte devrait être 2");
        assertEquals(2, handler.getIndexSlot(), "L'index de la case devrait être 2 (B3)");
    }

    @Test
    public void testSaisiePlacerErreurPuisValide() {

        String simulation = "bonjour\nplacer 9 b1\nplacer 1 b1\n";
        InputHandeler handler = createHandlerWithInput(simulation);

        handler.askChoice(3);


        assertEquals(UserChoice.PLACER, handler.getChoice());
        assertEquals(1, handler.getIndexCard(), "La carte devrait être 1");
        assertEquals(0, handler.getIndexSlot(), "La case devrait être 0 (B1)");
    }
}