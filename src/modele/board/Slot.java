package modele.board;

public class Slot {
    private Card m_currentCard;

    public Slot () {
        m_currentCard = null;
    }

    public boolean isEmpty() {
        return m_currentCard == null;
    }
    public Card getCard() {
        return m_currentCard;
    }
    public void setCard(Card c) {
        this.m_currentCard = c;
    }
    public void removeCard() {
        this.m_currentCard = null;
    }

}
