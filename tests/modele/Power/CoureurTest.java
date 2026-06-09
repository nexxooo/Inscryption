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

        assertTrue(board.isEmpty(Board.ROW_PLAYER, 0));
        assertEquals("Elan", board.getCard(Board.ROW_PLAYER, 1).getNom());
    }
}
