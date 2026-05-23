package modele.player;

import modele.board.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private int bonesCount;
    private List<Card> hand;
    private List<Card> deck;
    public Player(){
        hand = new ArrayList<>();
        deck = new ArrayList<>();
        bonesCount = 0;
    }

    public void drawCard(){
        if(!deck.isEmpty()){
            Card drawnCard = deck.remove(0);
            hand.add(drawnCard);
        }

    }

    public void addBones(int amount)
    {
        if (amount > 0)
        {
            this.bonesCount = amount;
        }
    }
}
