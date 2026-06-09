package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import modele.board.Slot;

import java.util.Optional;

public class Croissance extends Power {
    public Croissance() {
        super("Croissance");
    }

    @Override
    public String getShortName() {
        return "Croiss.";
    }

    @Override
    public void onDebut(Slot slot) {
        Optional<AnimalCard> optcrad = CardFactory.createAnimalCard("loup");
        slot.setCard(optcrad.get());
    }
}
