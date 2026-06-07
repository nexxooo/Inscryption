package modele.player;

import modele.board.AnimalCard;

import java.util.Collections;
import java.util.Optional;

public class Deck extends ContainerCard{
    public Deck() {
        super();
    }

    public Optional<AnimalCard> getTopCard(){
        if(!isEmpty()){
            Optional<AnimalCard> card= Optional.of(m_cards.getFirst());
            m_cards.remove(0);
            return card;
        }
        return Optional.empty();

    }
    public void shuffle(){
        Collections.shuffle(m_cards);
    }
}
