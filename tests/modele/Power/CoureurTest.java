package modele.Power;

import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.CardFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoureurTest {
    @Test
    public void testCoureur() {
        Board board = new Board();
        AnimalCard elan = CardFactory.createAnimalCard("elan").orElseThrow();
        board.addCard(elan, Board.ROW_PLAYER, 0);

        Coureur coureur = new Coureur();
        coureur.onEndTurn(elan, Board.ROW_PLAYER, 0, board);

        assertTrue(board.getSlot(Board.ROW_PLAYER, 0).isEmpty());
        assertEquals("Elan", board.getSlot(Board.ROW_PLAYER, 1).getCard().getNom());
    }
}
