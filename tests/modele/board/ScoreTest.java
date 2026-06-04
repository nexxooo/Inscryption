package modele.board;

import modele.Score;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ScoreTest {

    @Test
    public void testAddScore(){
        Score score = new Score();
        score.addScore(5);
        assertEquals(5,score.getScore());
        score.addScore(-2);
        assertEquals(3,score.getScore());

    }

    @Test
    public void testResetScore(){
        Score score = new Score();
        score.addScore(5);
        score.resetScore();
        assertEquals(0,score.getScore());
    }

    @Test
    public void testCheckVictory1(){
        Score score = new Score();
        score.addScore(5);
        assertTrue(score.checkVictory());

    }
    @Test
    public void testCheckVictory2(){
        Score score = new Score();
        score.addScore(-5);
        assertTrue(score.checkVictory());
    }
    @Test
    public void testCheckVictory3(){
        Score score = new Score();
        score.addScore(3);
        assertFalse(score.checkVictory());
    }
}