package modele.Power;

import modele.board.AnimalCard;
import modele.board.Card;

import java.util.Optional;

public class Puant extends Power{
    public Puant(){
        super("Puant");
    }

    @Override
    public int modifyOpponentAttack(int damage) {
        return Math.max(0, damage - 1);
    }
}
