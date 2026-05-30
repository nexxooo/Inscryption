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

    public String[] getASCII(){
        String[] res = new String[7];
        if(isEmpty()){
            res[0] = "*************";
            res[1] = "*           *";
            res[2] = "*           *";
            res[3] = "*           *";
            res[4] = "*           *";
            res[5] = "*           *";
            res[6] = "*************";
            return res;
        }
        else {
            return m_currentCard.getCardAscii();
        }

    }

}
