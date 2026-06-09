package Engine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import modele.Score;
import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.Card;
import modele.player.HumanPlayer;
import vue.GameView;
import vue.InputHandeler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationTour {

    private final InputStream originalSystemIn = System.in;

    private void setSimulatedInput(String inputString) {
        System.setIn(new ByteArrayInputStream(inputString.getBytes()));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Test
    public void testSimulationTourComplet() {
        // 1. Initialisation des composants
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();



        AnimalCard ecureuil = new AnimalCard("Ecureuil", 1, 0, 0, 0, false);
        AnimalCard hermine = new AnimalCard("Hermine", 3, 1, 1, 0, false);
        AnimalCard loup = new AnimalCard("Loup", 2, 3, 2, 0, false);
        
        player.getDeck().addCard(ecureuil); // Pioché en premier (index 0)
        player.getDeck().addCard(hermine);  // Pioché en deuxième (index 1)
        player.getDeck().addCard(loup);     // Pioché en troisième (index 2)

        // Initialisation de l'IA adverse
        List<Card> queueEcureuils = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            queueEcureuils.add(new AnimalCard("Ecureuil", 1, 0, 0, 0, false));
        }

        // Simuler les saisies du joueur :

        setSimulatedInput("placer 0 b1\nfin\n");
        InputHandeler input = new InputHandeler();

        GameEngine engine = new GameEngine(player, score, view, input);
        Board board = new Board();
        // Vider le plateau pour éviter les obstacles aléatoires lors du test
        board.getSlot(Board.ROW_PLAYER, 0).removeCard();
        board.getSlot(Board.ROW_PLAYER, 1).removeCard();
        board.getSlot(Board.ROW_PLAYER, 2).removeCard();
        board.getSlot(Board.ROW_PLAYER, 3).removeCard();
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 0).removeCard();
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 1).removeCard();
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 2).removeCard();
        board.getSlot(Board.ROW_OPPONENT_ACTIVE, 3).removeCard();
        
        // Ajouter une carte dans la file d'attente de l'adversaire (qui va avancer au tour de l'IA)
        AnimalCard moineauAdverse = new AnimalCard("Moineau", 2, 1, 1, 0, true);
        board.getSlot(Board.ROW_OPPONENT_QUEUE, 2).setCard(moineauAdverse);

        engine.setBoard(board);

        // 2. Début du tour de jeu simulé
        // La phase playerTurn va piocher l'Ecureuil, l'ajouter à la main,
        // puis appliquer la saisie "placer 0 b1" (poser l'Ecureuil sur B1) et enfin "fin".
        engine.playerTurn();

        // Vérifications après le tour du joueur
        // L'écureuil a bien été retiré de la main
        assertEquals(0, player.getHand().getCardCount());
        // L'écureuil a bien été posé en B1 (ROW_PLAYER, index 0)
        assertFalse(board.getSlot(Board.ROW_PLAYER, 0).isEmpty());
        assertEquals("Ecureuil", board.getSlot(Board.ROW_PLAYER, 0).getCard().getNom());
        
        // L'écureuil a attaqué à la fin du tour du joueur (0 ATK -> score reste à 0)
        assertEquals(0, score.getScore());

        // 3. Tour de l'adversaire
        // La file d'attente de l'adversaire avance (le Moineau passe de QUEUE à ACTIVE sur la colonne 2 (index 2))
        board.advanceRow();
        assertFalse(board.getSlot(Board.ROW_OPPONENT_ACTIVE, 2).isEmpty());
        assertEquals("Moineau", board.getSlot(Board.ROW_OPPONENT_ACTIVE, 2).getCard().getNom());

        // Phase d'attaque de l'adversaire (le Moineau a 1 ATK, vole par-dessus le slot joueur, inflige 1 dégât direct)
        engine.attackPhase(Board.ROW_OPPONENT_ACTIVE, Board.ROW_PLAYER, false);
        assertEquals(-1, score.getScore());

        System.out.println("Simulation de tour complet exécutée avec succès !");
    }
}
