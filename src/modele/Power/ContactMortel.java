package modele.Power;

import modele.board.AnimalCard;
import modele.board.Card;

import java.util.Optional;

public class ContactMortel extends Power{
    public ContactMortel() {
        super("Contact Mortel");
    }

    @Override
    public int modifyDamage(AnimalCard attacker, Card defender,int damage) {
        Optional<AnimalCard> optdefender = defender.isAnimal();
        if(optdefender.isPresent()) {
            AnimalCard defenderAnimal = optdefender.get();
            damage = defender.getHealthPoints();
            return damage;
        }
        return damage;
    }
}
