package modele.player;

import modele.board.AnimalCard;
import modele.player.Graves;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class GravesTest {

    @Test
    public void testGainBonesOnDeath()
    {
        Graves graves = new Graves();
        AnimalCard chat = new AnimalCard("Chat",1,0,1,0,false );
        graves.addCard(chat);
        assertEquals(1,graves.getBones());
    }

    @Test
    public void testSpendBones()
    {
        Graves graves = new Graves();
        AnimalCard chat = new AnimalCard("Chat",1,0,1,0,false );
        graves.addCard(chat);
        graves.spendBones(1);
        assertEquals(0,graves.getBones());
    }
}
