package Engine;

import modele.Power.Power;
import modele.Score;
import modele.board.*;
import modele.player.*;
import vue.GameView;
import vue.InputHandeler;
import vue.UserChoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GameEngine {
    private HumanPlayer m_player;
    private OpponentAI m_opponentAI;
    private Board m_board;
    private Score m_score;
    private GameView m_gameView;
    private InputHandeler m_input;

    public GameEngine(HumanPlayer m_player, Score m_score, GameView m_gameView, InputHandeler m_input) {
        this.m_player = m_player;
        this.m_score = m_score;
        this.m_gameView = m_gameView;
        this.m_input = m_input;

        List<Card> queueEcureuils = new ArrayList<>();
        for (int i = 0; i < 50; i++)
        {
            CardFactory.createAnimalCard("ecureuil").ifPresent(queueEcureuils::add);

        }
        this.m_opponentAI = new OpponentAI(queueEcureuils);
    }

    private void attackPhase(int indexAttackRow, int indexDefenseRow, boolean isPlayerAttack) {
        for (int i = 0; i < 4; i++) {
            Slot attackerSlot = m_board.getSlot(indexAttackRow, i);
            Slot defenderSlot = m_board.getSlot(indexDefenseRow, i);
            resolveSlotCombat(attackerSlot, defenderSlot, isPlayerAttack,i);
        }
    }

    private void resolveSlotCombat(Slot attackerSlot, Slot defenderSlot, boolean isPlayerAttack,int col) {
        if (attackerSlot.isEmpty()) {
            return;
        }
        Card baseCard = attackerSlot.getCard();
        Optional<AnimalCard> optAttacker = baseCard.isAnimal();
        if (!optAttacker.isPresent()) {
            return;
        }

        AnimalCard attackerAnimal = optAttacker.get();
        for(Power p : attackerAnimal.getPower()){
            p.onAttack(attackerAnimal,defenderSlot.getCard());
        }
        for(Power power : attackerAnimal.getPower()) {
            power.onDebut(attackerSlot);
        }
        int damage = attackerAnimal.getAttackPoints();
        if (damage <= 0) {
            return;
        }

        if (defenderSlot.isEmpty() || attackerAnimal.isFlying()) {
            applyDirectDamage(attackerAnimal, damage, isPlayerAttack);
        } else {
            applyCardDamage(attackerAnimal, defenderSlot, damage, isPlayerAttack);
        }
        int row = isPlayerAttack ? 2:1;
        for (Power p : attackerAnimal.getPower()) {
            p.onEndTurn(attackerAnimal, row, col, m_board);
        }
    }

    private void applyDirectDamage(AnimalCard attacker, int damage, boolean isPlayerAttack) {
        int delta = isPlayerAttack ? damage : -damage;
        m_score.addScore(delta);
        String target = isPlayerAttack ? "à l'adversaire" : "au joueur";
        System.out.println(attacker.getNom() + " a infligé " + damage + " dégâts directs " + target + ".");
    }

    private void applyCardDamage(AnimalCard attacker, Slot defenderSlot, int damage, boolean isPlayerAttack) {
        Card defenderCard = defenderSlot.getCard();
        int defenderHealth = defenderCard.getHealthPoints();
        defenderCard.takeDamage(damage);
        System.out.println(attacker.getNom() + " a infligé " + damage + " dégâts à " + defenderCard.getNom() + ".");

        if (defenderCard.isDead()) {
            System.out.println(defenderCard.getNom() + " a été éliminé(e) !");
            int excess = damage - defenderHealth;
            if (excess > 0) {
                int delta = isPlayerAttack ? excess : -excess;
                m_score.addScore(delta);
                String target = isPlayerAttack ? "à l'adversaire" : "au joueur";
                System.out.println("Surplus de " + excess + " dégâts infligé " + target + ".");
            }
            removeDeadCard(defenderSlot, isPlayerAttack);
        }
    }

    private void removeDeadCard(Slot slot, boolean isPlayerAttack) {
        if (isPlayerAttack) {
            slot.removeCard();
        } else {
            slot.removeCard(m_player.getGraves());
        }
    }

        public void play() {
            int playerWins = 0;
            int opponentWins = 0;

            // Initialiser le deck une seule fois au début de la partie
            CardFactory.initializeDeck(m_player.getDeck());

            for (int i = 1; i <= 3; i++) {
                System.out.println("==================================");
                System.out.println("        DEBUT DU ROUND " + i);
                System.out.println("==================================");

                // Remettre toutes les cartes de la main et du cimetière dans le deck
                m_player.getHand().refillDeck(m_player.getDeck());
                m_player.getGraves().refillDeck(m_player.getDeck());
                m_player.getGraves().clear(); // réinitialiser le nombre d'os à 0
                m_player.getDeck().shuffle();

                m_score.resetScore();

                round();

                if (m_score.getScore() >= 5) {
                    playerWins++;
                    System.out.println("\nVous avez gagné le Round " + i + " !");
                } else {
                    opponentWins++;
                    System.out.println("\nL'adversaire a gagné le Round " + i + " !");
                }
                System.out.println("Rounds gagnés - Joueur : " + playerWins + " | Adversaire : " + opponentWins + "\n");
            }

            System.out.println("==================================");
            System.out.println("          FIN DE LA PARTIE        ");
            System.out.println("==================================");
            if (playerWins > opponentWins) {
                System.out.println("Félicitations ! Vous avez gagné la partie (" + playerWins + " - " + opponentWins + ") !");
            } else {
                System.out.println("Défaite... L'adversaire a gagné la partie (" + opponentWins + " - " + playerWins + ").");
            }
        }

        private void round () {
            initBoard();
            for (int i = 0; i < 4; i++) {
                m_player.draw();
            }
            while (m_score.getScore() < 5 && m_score.getScore() > -5) {
                playerTurn();
                m_board.advanceRow();
                attackPhase(Board.ROW_OPPONENT_ACTIVE, Board.ROW_PLAYER, false);
                m_opponentAI.playTurn(m_board);
            }
            m_board.clearBoard(m_player.getDeck());

        }

        private void playerTurn(){
            m_player.draw();
            m_gameView.Clear();
            m_gameView.displayBoard(m_board,m_score);
            m_gameView.displayDeck(m_player.getDeck());
            m_gameView.displayHand(m_player);
            m_input.askChoice(m_player.getHand().getMaxIndex());

            while (m_input.getChoice() != UserChoice.PASSER) {
                AnimalCard card = m_player.getHand().getCard(m_input.getIndexCard());
                if(card.getBloodCost() > 0){
                    Optional<List<Integer>> optsacrifice = m_input.askSacrifices(card.getBloodCost(),m_board);
                    if (optsacrifice.isPresent()) {
                        List<Integer> sacrifice = optsacrifice.get();
                        for(Integer i : sacrifice){
                            Optional<AnimalCard> optcards = m_board.getSlot(Board.ROW_PLAYER,i).getCard().isAnimal();
                            AnimalCard cards = optcards.get();
                            if(!cards.hasPower("Nombreuse Vie")) {
                                m_board.getSlot(Board.ROW_PLAYER,i).removeCard(m_player.getGraves());
                            }
                        }
                        if (m_board.getSlot(Board.ROW_PLAYER, m_input.getIndexSlot()).isEmpty()) {
                            m_board.getSlot(Board.ROW_PLAYER,m_input.getIndexSlot()).setCard(card);
                            m_player.getHand().removeCard(m_input.getIndexCard());
                        } else {
                            System.out.println("slot deja pris");
                        }
                    }

                } else if (card.getBoneCost() > 0) {
                    placeCradBones(card,2,m_input.getIndexSlot());
                }
                else {
                    placeCard(card,2,m_input.getIndexSlot());
                }

                m_gameView.Clear();
                m_gameView.displayBoard(m_board,m_score);
                m_gameView.displayDeck(m_player.getDeck());
                m_gameView.displayHand(m_player);
                m_input.askChoice(m_player.getHand().getMaxIndex());
            }
            attackPhase(2,1,true);

        }
        private void placeCard(AnimalCard card,int row,int col){
            if(m_board.getSlot(row,col).isEmpty()) {
                m_board.addCard(card, row, col);
                m_player.getHand().removeCard(m_input.getIndexCard());
            }
            else {
                System.out.println("slot deja pris");
            }
        }
        private void placeCradBones(AnimalCard card,int row,int col){
            if(!m_board.getSlot(row,col).isEmpty()){
                System.out.println("slot deja pris");
                return;
            }
            if(m_player.getBones() < card.getBoneCost()){
                System.out.println("pas assez d'os");
            }
            else {
                m_board.addCard(card, row, col);
                m_player.getHand().removeCard(m_input.getIndexCard());
            }
        }

        private void initBoard(){
            m_board = new Board();
            Random rand = new Random();
            for (int i=0; i< 4;i++){
                int r1 = rand.nextInt(4); //une chance sur quatre d'avoir un obstacle
                int r2 = rand.nextInt(4);
                if (r1 == 1){
                    m_board.getSlot(Board.ROW_PLAYER,i).setCard(CardFactory.createRandomObstacle());
                }
                if(r2 ==1){
                    m_board.getSlot(Board.ROW_OPPONENT_ACTIVE,i).setCard(CardFactory.createRandomObstacle());
                }
            }
        }

    }

