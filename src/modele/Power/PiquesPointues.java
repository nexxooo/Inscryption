package modele.Power;

import modele.board.AnimalCard;
import modele.board.Card;

public class PiquesPointues extends Power{
    public PiquesPointues(){
        super("Piques Pointues");
    }

    @Override
    public String getShortName() {
        return "P.Pique";
    }

    @Override
    public void onReceiveDamage(AnimalCard attacker) {
        attacker.takeDamage(1);
    }
}
