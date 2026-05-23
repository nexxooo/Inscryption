package modele.board;

import modele.score.Score;

public class Board {
    private Slot[] playerSlots;
    private Slot[] opponentSlots;

    public Board() {
        playerSlots = new Slot[4];
        opponentSlots = new Slot[4];

        for (int i = 0; i < 4; i++) {
            playerSlots[i] = new Slot();
            opponentSlots[i] = new Slot();
        }
    }
    public Boolean addCard(Card c, int side, int index) // un boolean pour le side plutôt ?
    {
        if (index < 0 || index > 3)
        {
            return false;
        }
        if (side == 0) {
            if(!playerSlots[index].isEmpty())
            {
                return false;
            }
            playerSlots[index].setCard(c);
        }
        else if (side == 1) {
            if(!playerSlots[index].isEmpty())
            {
                return false;
            }
            opponentSlots[index].setCard(c);
        }
        else {
            return false;
        }
        return true;
    }

    public void resolveCombat( Score score)
    {
        
    }

}
