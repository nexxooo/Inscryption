package Engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import modele.Score;
import modele.board.AnimalCard;
import modele.board.Board;
import modele.player.HumanPlayer;
import vue.GameView;
import vue.InputHandeler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameEngineTest {

    private final InputStream originalSystemIn = System.in;

    private void setSimulatedInput(String inputString) {
        System.setIn(new ByteArrayInputStream(inputString.getBytes()));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Test
    public void testInitialization() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        InputHandeler input = new InputHandeler();
        GameEngine engine = new GameEngine(player, score, view, input);

        assertEquals(0, score.getScore());
    }

    @Test
    public void testDirectDamageToOpponent() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        InputHandeler input = new InputHandeler();
        GameEngine engine = new GameEngine(player, score, view, input);

        Board board = new Board();

        AnimalCard loup = new AnimalCard("Loup", 2, 3, 2, 0, false);
        board.getSlot(Board.ROW_PLAYER, 0).setCard(loup);

        engine.setBoard(board);


        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);


        assertEquals(3, score.getScore());
    }

    @Test
    public void testDirectDamageFromOpponent() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        InputHandeler input = new InputHandeler();
        GameEngine engine = new GameEngine(player, score, view, input);

        Board board = new Board();

        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 1).setCard(grizzly);

        engine.setBoard(board);


        engine.attackPhase(Board.ROW_OPPONENT_ACTIVE, Board.ROW_PLAYER, false);


        assertEquals(-4, score.getScore());
    }

    @Test
    public void testCardCombatWithoutDeath() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        InputHandeler input = new InputHandeler();
        GameEngine engine = new GameEngine(player, score, view, input);

        Board board = new Board();
        

        AnimalCard coyote = new AnimalCard("Coyote", 1, 2, 0, 4, false);
        board.getSlot(Board.ROW_PLAYER, 2).setCard(coyote);


        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 2).setCard(grizzly);

        engine.setBoard(board);


        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);


        assertEquals(4, grizzly.getHealthPoints());
        assertFalse(grizzly.isDead());

        assertEquals(0, score.getScore());
    }

    @Test
    public void testCardCombatWithDeathAndExcessDamage() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        InputHandeler input = new InputHandeler();
        GameEngine engine = new GameEngine(player, score, view, input);

        Board board = new Board();


        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.getSlot(Board.ROW_PLAYER, 0).setCard(grizzly);


        AnimalCard coyote = new AnimalCard("Coyote", 1, 2, 0, 4, false);
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 0).setCard(coyote);

        engine.setBoard(board);


        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);


        assertTrue(coyote.isDead());
        assertTrue(board.getSlot(Board.ROW_OPPONENT_ACTIVE, 0).isEmpty());
        assertEquals(3, score.getScore());
    }

    @Test
    public void testFlyingBypassesCard() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        InputHandeler input = new InputHandeler();
        GameEngine engine = new GameEngine(player, score, view, input);

        Board board = new Board();


        AnimalCard moineau = new AnimalCard("Moineau", 2, 1, 1, 0, true);
        board.getSlot(Board.ROW_PLAYER, 1).setCard(moineau);


        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 1).setCard(grizzly);

        engine.setBoard(board);


        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);


        assertEquals(6, grizzly.getHealthPoints());

        assertEquals(1, score.getScore());
    }

    @Test
    public void testStoneSacrificeThroughEngine() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        

        AnimalCard chat = new AnimalCard("Chat", 1, 0, 1, 0, false, new modele.Power.NombreuseVie());
        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        player.getDeck().addCard(chat);
        player.getDeck().addCard(grizzly);


        setSimulatedInput("0\n0\n");
        InputHandeler input = new InputHandeler();

        GameEngine engine = new GameEngine(player, score, view, input);
        engine.Stone();


        assertTrue(grizzly.hasPower("Nombreuse Vie"));

        assertEquals(1, player.getDeck().sizeDeck());
        assertEquals(grizzly, player.getDeck().getCard(0));
    }
}
