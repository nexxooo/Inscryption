package modele.player;
import modele.board.AnimalCard;
import modele.board.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Player {
    private Hand m_hand;
    private Deck m_deck;
    private Graves m_graves;

    public Player() {
        m_hand = new Hand();
        m_deck = new Deck();
        m_graves = new Graves();
    }

    public void Draw(){
        Optional<AnimalCard> card = m_deck.getTopCard();
        card.ifPresent(animalCard -> m_hand.addCard(animalCard));
    }

    public int getBones(){
        return m_graves.getBones();
    }

    public String getHandAsString(){
        return m_hand.getListAsString();
    }
    public Hand getHand(){
        return m_hand;
    }
}
