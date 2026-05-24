package modele.player;

import modele.board.AnimalCard;
import modele.board.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerCard {
    protected List<AnimalCard> m_cards;

    public ContainerCard() {
        m_cards = new ArrayList<AnimalCard>();
    }

    public void addCard(AnimalCard card) {
        m_cards.add(card);
    }

    public int getCardCount(){
        return m_cards.size();
    }

    public boolean isEmpty(){
        return m_cards.isEmpty();
    }
}
