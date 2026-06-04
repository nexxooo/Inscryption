package modele.player;
import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class OpponentAITest {
    @Test
    public void testPlayTurn()
    {
        List<Card> queue = new ArrayList<>();
        queue.add(new AnimalCard("Ecureuil", 1, 1, 0, 0, false));
        OpponentAI ai = new OpponentAI(queue);
        Board board = new Board();
        ai.playTurn(board);
        assertTrue(ai.getIntendedPlays().isEmpty());
        assertFalse(board.getSlot(Board.ROW_OPPONENT_QUEUE, 0).isEmpty());
        assertEquals("Ecureuil", board.getSlot(Board.ROW_OPPONENT_QUEUE, 0).getCard().getNom());
    }
}
