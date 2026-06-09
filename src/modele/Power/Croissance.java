package modele.Power;

import modele.board.AnimalCard;
import modele.board.CardFactory;
import modele.board.Board;

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
    public void onDebut(Board board, int row, int col) {
        Optional<AnimalCard> optcrad = CardFactory.createAnimalCard("loup");
        board.setCard(optcrad.get(), row, col);
    }
}
