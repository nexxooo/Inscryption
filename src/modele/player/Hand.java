package modele.player;

import modele.board.AnimalCard;

public class Hand extends ContainerCard {
    public Hand() {
        super();
    }

    public int getMaxIndex() {
        return getCardCount() - 1;
    }

    public void removeCard(int index) {
        m_cards.remove(index);
    }

    public String getListAsString() {
        String msg = "votre main:\n";
        for (int i = 0; i < getCardCount(); i++) {
            msg += "" + i + ". " + m_cards.get(i).toString()+"\n";
        }
        return msg;

    }

}
