package modele.board;

import modele.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BoardTest {
    @Test
    public void testAddCardEmptySlot() {
        Board board = new Board();
        AnimalCard ecureuil = new AnimalCard("Ecureuil", 1, 1, 0, 0, false);
        boolean result = board.addCard(ecureuil, Board.ROW_PLAYER, 0);
        assertTrue(result);
        assertFalse(board.isEmpty(Board.ROW_PLAYER, 0));
        assertEquals("Ecureuil", board.getCard(Board.ROW_PLAYER,0).getNom());
    }

    @Test
    public void testAddCardOccupiedSlot()
    {
        Board board = new Board();
        AnimalCard ecureuil = new AnimalCard("Ecureuil", 1, 1, 0, 0, false);
        AnimalCard chat = new AnimalCard("Chat",1,0,1,0,false );
        board.addCard(ecureuil, Board.ROW_PLAYER, 0);
        boolean result = board.addCard(chat, Board.ROW_PLAYER, 0);
        assertFalse(result);
        assertEquals("Ecureuil", board.getCard(Board.ROW_PLAYER,0).getNom());

    }

    @Test
    public void testAdvanceRow()
    {
        Board board = new Board();
        AnimalCard chat = new AnimalCard("Chat",1,0,1,0,false);
        board.addCard(chat, Board.ROW_OPPONENT_QUEUE, 0);
        board.advanceRow();
        assertTrue(board.isEmpty(Board.ROW_OPPONENT_QUEUE, 0));
        assertFalse(board.isEmpty(Board.ROW_OPPONENT_ACTIVE, 0));
        assertEquals("Chat", board.getCard(Board.ROW_OPPONENT_ACTIVE, 0).getNom());
    }
}
