package modele;

import modele.Score;
import org.junit.jupiter.api.Test;
import org.testng.asserts.Assertion;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    @Test
    void test1(){
        Score score = new Score();
        score.addScore(5);
        assertEquals(5,score.getScore());

    }

    @Test
    void test2(){
        Score score = new Score();
        Score score2 = new Score();
        score.addScore(5);
        assertEquals(5,score.getEcart(score2));
    }

    @Test
    void test3(){
        Score score = new Score();
        score.addScore(5);
        score.resetScore();
        assertEquals(0,score.getScore());
    }
}