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
        m_bones += 1;
    }

    public void spendBones(int amount) {
        m_bones = Math.max(0, m_bones - amount);
    }

    public int getBones() {return m_bones;}

    public void refillDeck(Deck deck){
        for(AnimalCard card : m_cards){
            deck.addCard(card);
        }
        m_cards.clear();
    }

}
