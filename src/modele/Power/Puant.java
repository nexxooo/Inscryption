package modele.Power;

import modele.board.AnimalCard;
import modele.board.Card;

import java.util.Optional;

public class Puant extends Power{
    public Puant(){
        super("Puant");
    }

    @Override
    public void onAttack(AnimalCard attacker, Card defender) {
        Optional<AnimalCard> optdefender = defender.isAnimal();
        optdefender.ifPresent(defenderCard -> defenderCard.setAttack(defender.getAttackPoints() - 1));
    }
}
