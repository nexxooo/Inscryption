package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import modele.board.Board;
import org.junit.Test;
import static org.junit.Assert.*;

public class CroissanceTest {
    @Test
    public void testCroissance() {
        Board board = new Board();
        AnimalCard louveteau = CardFactory.createAnimalCard("louveteau").orElseThrow();
        board.setCard(louveteau, Board.ROW_PLAYER, 0);

        Croissance croissance = new Croissance();
        croissance.onDebut(board, Board.ROW_PLAYER, 0);

        assertEquals("Loup", board.getCard(Board.ROW_PLAYER, 0).getNom());
    }
}
