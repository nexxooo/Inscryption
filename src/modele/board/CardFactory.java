package modele.board;

import modele.player.Deck;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CardFactory {
    public CardFactory() {
    }

    public static Optional<AnimalCard> createAnimalCard(String name){
        switch (name.toLowerCase()){
            case "ecureuil":
                return Optional.of(new AnimalCard("Ecureuil", 1, 0, 0,0,false));
            case "chat":
                return Optional.of(new AnimalCard("Chat", 1, 0, 1,0,false));
            case "grizzly":
                return Optional.of(new AnimalCard("Grizzly", 6, 4, 3,0,false));
            case "coyote":
                return Optional.of(new AnimalCard("Coyote", 1, 2, 0,4,false));
            case "moineau":
                return Optional.of(new AnimalCard("Moineau", 2, 1, 1,0,true));
            case "corbeau":
                return Optional.of(new AnimalCard("Corbeau", 3, 2, 2,0,true));
            case "hermine":
                return Optional.of(new AnimalCard("Hermine", 3, 1, 1,0,false));
            case "louveteau":
                return Optional.of(new AnimalCard("Louveteau", 1, 1, 1,0,false));
            case "loup":
                return Optional.of(new AnimalCard("Loup", 2, 3, 2,0,false));
            case "punaise":
                return Optional.of(new AnimalCard("Punaise", 2, 1, 0,2,false));
            case "elan":
                return Optional.of(new AnimalCard("Elan", 4, 2, 2,0,false));
            case "vipere":
                return Optional.of(new AnimalCard("Vipère", 1, 1, 2,0,false));
            case "porc-epic":
                return Optional.of(new AnimalCard("Proc-épic", 2, 1, 1,0,false));
            default:
                return Optional.empty();
        }
    }

    private static void addMultipleCard(String nom, int amount, Deck deck ){
        for (int i = 0; i < amount; i++){
         Optional<AnimalCard> optcard = createAnimalCard(nom);
         optcard.ifPresent(deck::addCard);
        }
    }

    private static String getRandomAnimalName(){
        List<String> lst = List.of("Ecureuil", "chat","grizzly","coyote","moineau","corbeau","hermine","louveteau","loup","punaise","elan","vipere","porc-epic");
        Random rand = new Random();
        int r1 = rand.nextInt(0, lst.size());
        return lst.get(r1);
    }

    public static void initializeDeck(Deck deck){
        addMultipleCard("ecureuil",8,deck);
        addMultipleCard(getRandomAnimalName(),7,deck);
    }
}
