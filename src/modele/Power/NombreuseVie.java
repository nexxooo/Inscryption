package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import modele.board.Slot;

import java.util.Optional;

public class NombreuseVie extends Power {
    public NombreuseVie() {
        super("Nombreuse Vie");
    }

    @Override
    public void onDebut(Slot slot) {
        Optional<AnimalCard> optcrad = CardFactory.createAnimalCard("loup");
        slot.setCard(optcrad.get());
    }
}
