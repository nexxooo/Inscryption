package modele.board;

public class Slot {
    private Card currentCard;

    public Slot () {
        currentCard = null;
    }

    public boolean isEmpty() {
        return currentCard == null;
    }
    public Card getCard() {
        return currentCard;
    }
    public void setCard(Card c) {
        this.currentCard = c;
    }
    public void removeCard() {
        this.currentCard = null;
    }

}
