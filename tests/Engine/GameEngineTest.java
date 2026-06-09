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
        board.setCard(loup, Board.ROW_PLAYER, 0);

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
        board.setCard(grizzly, Board.ROW_OPPONENT_ACTIVE, 1);

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
        board.setCard(coyote, Board.ROW_PLAYER, 2);


        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.setCard(grizzly, Board.ROW_OPPONENT_ACTIVE, 2);

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
        board.setCard(grizzly, Board.ROW_PLAYER, 0);


        AnimalCard coyote = new AnimalCard("Coyote", 1, 2, 0, 4, false);
        board.setCard(coyote, Board.ROW_OPPONENT_ACTIVE, 0);

        engine.setBoard(board);


        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);


        assertTrue(coyote.isDead());
        assertTrue(board.isEmpty(Board.ROW_OPPONENT_ACTIVE, 0));
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
        board.setCard(moineau, Board.ROW_PLAYER, 1);


        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.setCard(grizzly, Board.ROW_OPPONENT_ACTIVE, 1);

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
        player.addCardToDeck(chat);
        player.addCardToDeck(grizzly);


        setSimulatedInput("0\n0\n");
        InputHandeler input = new InputHandeler();

        GameEngine engine = new GameEngine(player, score, view, input);
        engine.Stone();


        assertTrue(grizzly.hasPower("Nombreuse Vie"));

        assertEquals(1, player.getDeckSize());
        assertEquals(grizzly, player.getDeckCard(0));
    }

    @Test
    public void testPlayerWinsRound() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        
        setSimulatedInput("fin\nfin\nfin\n");
        InputHandeler input = new InputHandeler();

        GameEngine engine = new GameEngine(player, score, view, input) {
            @Override
            void initBoard() {
                Board board = new Board();
                board.setCard(new AnimalCard("Loup", 2, 3, 2, 0, false), Board.ROW_PLAYER, 0);
                board.removeCard(Board.ROW_PLAYER, 1);
                board.removeCard(Board.ROW_PLAYER, 2);
                board.removeCard(Board.ROW_PLAYER, 3);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 0);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 1);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 2);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 3);
                setBoard(board);
            }
        };

        engine.round();

        assertTrue(score.getScore() >= 5);
    }

    @Test
    public void testPlayerLosesRound() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        
        setSimulatedInput("fin\nfin\nfin\n");
        InputHandeler input = new InputHandeler();

        GameEngine engine = new GameEngine(player, score, view, input) {
            @Override
            void initBoard() {
                Board board = new Board();
                board.setCard(new AnimalCard("Grizzly", 6, 4, 3, 0, false), Board.ROW_OPPONENT_ACTIVE, 0);
                board.removeCard(Board.ROW_PLAYER, 0);
                board.removeCard(Board.ROW_PLAYER, 1);
                board.removeCard(Board.ROW_PLAYER, 2);
                board.removeCard(Board.ROW_PLAYER, 3);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 1);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 2);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 3);
                setBoard(board);
            }
        };

        engine.round();

        assertTrue(score.getScore() <= -5);
    }

    @Test
    public void testContactMortelDoesNotKillObstacle() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        InputHandeler input = new InputHandeler();
        GameEngine engine = new GameEngine(player, score, view, input);

        Board board = new Board();
        
        AnimalCard vipere = new AnimalCard("Vipere", 1, 1, 2, 0, false, new modele.Power.ContactMortel());
        board.setCard(vipere, Board.ROW_PLAYER, 0);

        modele.board.ObstacleCard rocher = new modele.board.ObstacleCard("Rocher", 5);
        board.setCard(rocher, Board.ROW_OPPONENT_ACTIVE, 0);

        engine.setBoard(board);

        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);

        assertEquals(4, rocher.getHealthPoints());
        assertFalse(rocher.isDead());
        assertFalse(board.isEmpty(Board.ROW_OPPONENT_ACTIVE, 0));
    }

    @Test
    public void testPlayGamePlayerVictory() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        
        setSimulatedInput("fin\nfin\nfin\nfin\n0\n0\n0\nfin\nfin\n");
        InputHandeler input = new InputHandeler();

        GameEngine engine = new GameEngine(player, score, view, input) {
            @Override
            void initBoard() {
                Board board = new Board();
                board.setCard(new AnimalCard("Loup", 2, 3, 2, 0, false), Board.ROW_PLAYER, 0);
                board.removeCard(Board.ROW_PLAYER, 1);
                board.removeCard(Board.ROW_PLAYER, 2);
                board.removeCard(Board.ROW_PLAYER, 3);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 0);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 1);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 2);
                board.removeCard(Board.ROW_OPPONENT_ACTIVE, 3);
                setBoard(board);
            }
        };

        engine.play();
    }
}
