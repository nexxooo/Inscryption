package modele.player;

import modele.board.AnimalCard;

public class Graves extends ContainerCard{
    private int m_bones;

    public Graves() {
        super();
        m_bones = 0;
    }

    @Override
    public void addCard(AnimalCard card) {
        super.addCard(card);
        m_bones += card.getBoneCost();
    }

    public int getBones() {return m_bones;}

    public void refillDeck(Deck deck){
        for(AnimalCard card : m_cards){
            deck.addCard(card);
            m_cards.remove(card);
        }
    }

}
