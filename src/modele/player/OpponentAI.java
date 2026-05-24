package modele.player;

import javax.smartcardio.Card;
import java.util.List;

public class OpponentAI {
    private List<Card> queue;
    public OpponentAI(List<Card> queue) {
        this.queue = queue;
    }
    public List<Card> getIntendedPlays(){
        return queue;
    }
}
