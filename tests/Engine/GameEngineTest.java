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
        // Place a Wolf (Loup) with 3 Attack, 2 HP on Player Slot 0
        AnimalCard loup = new AnimalCard("Loup", 2, 3, 2, 0, false);
        board.getSlot(Board.ROW_PLAYER, 0).setCard(loup);

        engine.setBoard(board);

        // Execute player's attack phase on opponent
        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);

        // Opponent slot 0 was empty, so player inflicts 3 direct damage
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
        // Place a Grizzly with 4 Attack, 6 HP on Opponent Active Slot 1
        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 1).setCard(grizzly);

        engine.setBoard(board);

        // Execute opponent's attack phase on player
        engine.attackPhase(Board.ROW_OPPONENT_ACTIVE, Board.ROW_PLAYER, false);

        // Player slot 1 was empty, so opponent inflicts 4 direct damage (score decreases by 4)
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
        
        // Player has Coyote (2 ATK, 1 HP) on slot 2
        AnimalCard coyote = new AnimalCard("Coyote", 1, 2, 0, 4, false);
        board.getSlot(Board.ROW_PLAYER, 2).setCard(coyote);

        // Opponent has Grizzly (4 ATK, 6 HP) on slot 2
        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 2).setCard(grizzly);

        engine.setBoard(board);

        // Player attacks
        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);

        // Grizzly has 6 HP, should take 2 damage and remain at 4 HP
        assertEquals(4, grizzly.getHealthPoints());
        assertFalse(grizzly.isDead());
        // Score remains 0 since no direct or excess damage occurred
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

        // Player has Grizzly (4 ATK, 6 HP) on slot 0
        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.getSlot(Board.ROW_PLAYER, 0).setCard(grizzly);

        // Opponent has Coyote (2 ATK, 1 HP) on slot 0
        AnimalCard coyote = new AnimalCard("Coyote", 1, 2, 0, 4, false);
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 0).setCard(coyote);

        engine.setBoard(board);

        // Player attacks
        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);

        // Coyote had 1 HP, Grizzly does 4 damage. Coyote dies, excess of 3 damage goes to score.
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

        // Player has a Moineau (1 ATK, 2 HP, flying = true) on slot 1
        AnimalCard moineau = new AnimalCard("Moineau", 2, 1, 1, 0, true);
        board.getSlot(Board.ROW_PLAYER, 1).setCard(moineau);

        // Opponent has a Grizzly (4 ATK, 6 HP) on slot 1
        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 1).setCard(grizzly);

        engine.setBoard(board);

        // Player attacks
        engine.attackPhase(Board.ROW_PLAYER, Board.ROW_OPPONENT_ACTIVE, true);

        // Grizzly has 6 HP (should not take damage since Moineau flies over it)
        assertEquals(6, grizzly.getHealthPoints());
        // Moineau inflicts 1 direct damage to score
        assertEquals(1, score.getScore());
    }

    @Test
    public void testStoneSacrificeThroughEngine() {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        
        // Create the deck
        AnimalCard chat = new AnimalCard("Chat", 1, 0, 1, 0, false, new modele.Power.NombreuseVie());
        AnimalCard grizzly = new AnimalCard("Grizzly", 6, 4, 3, 0, false);
        player.getDeck().addCard(chat);
        player.getDeck().addCard(grizzly);

        // We simulate entering index 0 (Chat) for sacrifice and then index 0 (Grizzly) for target
        // Note: Chat is deleted first, so Grizzly becomes index 0 in the updated deck list
        setSimulatedInput("0\n0\n");
        InputHandeler input = new InputHandeler();

        GameEngine engine = new GameEngine(player, score, view, input);
        engine.Stone();

        // Grizzly should have Nombreuse Vie now
        assertTrue(grizzly.hasPower("Nombreuse Vie"));
        // Chat should be deleted, deck size is 1
        assertEquals(1, player.getDeck().sizeDeck());
        assertEquals(grizzly, player.getDeck().getCard(0));
    }
}
