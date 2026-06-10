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
        Optional<AnimalCard> oldCardOpt = board.getAnimalCard(row, col);
        Optional<AnimalCard> optcrad = CardFactory.createAnimalCard("loup");
        if (optcrad.isPresent() && oldCardOpt.isPresent()) {
            AnimalCard newLoup = optcrad.get();
            AnimalCard oldLouveteau = oldCardOpt.get();
            for (Power p : oldLouveteau.getPower()) {
                if (!(p instanceof Croissance)) {
                    newLoup.addPower(p);
                }
            }
            board.setCard(newLoup, row, col);
        }
    }
}
