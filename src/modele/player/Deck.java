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
            return Optional.of(m_cards.getFirst());
        }
        return Optional.empty();

    }
    public void shuffle(){
        Collections.shuffle(m_cards);
    }
}
