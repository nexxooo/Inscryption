package modele.player;

import modele.board.Card;
import modele.board.Board;
import java.util.List;

public class OpponentAI {
    private final List<Card> queue;
    public OpponentAI(List<Card> queue) {
        this.queue = queue;
    }
    public List<Card> getIntendedPlays(){
        return queue;
    }

    public void playTurn(Board board)
    {
        for(int col = 0; col < 4; col++)
        {
            if(board.getSlot(Board.ROW_OPPONENT_QUEUE,col).isEmpty() && !queue.isEmpty())
            {
                Card cardToPlay = queue.removeFirst();
                board.addCard(cardToPlay, Board.ROW_OPPONENT_QUEUE, col);
            }
        }
    }
}
