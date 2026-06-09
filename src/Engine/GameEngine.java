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

    void setBoard(Board board) {
        this.m_board = board;
    }

    Board getBoard() {
        return this.m_board;
    }

    void attackPhase(int indexAttackRow, int indexDefenseRow, boolean isPlayerAttack) {
        for (int i = 0; i < 4; i++) {
            resolveSlotCombat(indexAttackRow, indexDefenseRow, isPlayerAttack, i);
            try{
                Thread.sleep(200); // pour laisser le temps de lire
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void resolveSlotCombat(int indexAttackRow, int indexDefenseRow, boolean isPlayerAttack, int col) {
        if (m_board.isEmpty(indexAttackRow, col)) {
            return;
        }
        Card baseCard = m_board.getCard(indexAttackRow, col);
        Optional<AnimalCard> optAttacker = baseCard.isAnimal();
        if (!optAttacker.isPresent()) {
            return;
        }

        AnimalCard attackerAnimal = optAttacker.get();
        for(Power power : attackerAnimal.getPower()) {
            power.onDebut(m_board, indexAttackRow, col);
        }
        int damage = attackerAnimal.getAttackPoints();
        Optional<AnimalCard> optDefender = m_board.getAnimalCard(indexDefenseRow, col);
        if (optDefender.isPresent()) {
            AnimalCard defenderAnimal = optDefender.get();
            for (Power p : defenderAnimal.getPower()) {
                damage = p.modifyOpponentAttack(damage);
            }
        }
        if (damage <= 0) {
            return;
        }

        if (m_board.isEmpty(indexDefenseRow, col) || attackerAnimal.isFlying()) {
            applyDirectDamage(attackerAnimal, damage, isPlayerAttack);
        } else {
            applyCardDamage(attackerAnimal, indexDefenseRow, col, damage, isPlayerAttack);
            if(attackerAnimal.isDead()){
                System.out.println(attackerAnimal.getNom() + " a été éliminé(e) par le retour de dégâts !");
                removeDeadCard(indexAttackRow, col, !isPlayerAttack);
                return;
            }
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

    private void applyCardDamage(AnimalCard attacker, int defRow, int defCol, int damage, boolean isPlayerAttack) {
        Card defenderCard = m_board.getCard(defRow, defCol);
        int defenderHealth = defenderCard.getHealthPoints();
        for(Power p : attacker.getPower()){
            damage = p.modifyDamage(attacker, defenderCard, damage);
        }
        if(!defenderCard.isDead()){
            defenderCard.takeDamage(damage);
            Optional<AnimalCard> optdefenderCard = defenderCard.isAnimal();
            if (optdefenderCard.isPresent()) {
                AnimalCard defenderAnimal = optdefenderCard.get();
                for (Power p : defenderAnimal.getPower()) {
                    p.onReceiveDamage(attacker);
                }
            }
            System.out.println(attacker.getNom() + " a infligé " + damage + " dégâts à " + defenderCard.getNom() + ".");
        }

        if (defenderCard.isDead()) {
            System.out.println(defenderCard.getNom() + " a été éliminé(e) !");
            int excess = damage - defenderHealth;
            if (excess > 0) {
                int delta = isPlayerAttack ? excess : -excess;
                m_score.addScore(delta);
                String target = isPlayerAttack ? "à l'adversaire" : "au joueur";
                System.out.println("Surplus de " + excess + " dégâts infligé " + target + ".");
            }
            removeDeadCard(defRow, defCol, isPlayerAttack);
        }
    }

    private void removeDeadCard(int row, int col, boolean isPlayerAttack) {
        if (isPlayerAttack) {
            m_board.removeCard(row, col);
        } else {
            m_board.removeCard(row, col, m_player.getGraves());
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

                m_player.clearGraves(); // réinitialiser le nombre d'os à 0
                m_player.shuffleDeck();

                m_score.resetScore();

                round();

                // Remettre toutes les cartes de la main et du cimetière dans le deck
                m_player.refillHandToDeck();
                m_player.refillGravesToDeck();

                if (m_score.getScore() >= 5) {
                    playerWins++;
                    System.out.println("\nVous avez gagné le Round " + i + " !");
                } else {
                    opponentWins++;
                    System.out.println("\nL'adversaire a gagné le Round " + i + " !");
                }
                System.out.println("Rounds gagnés - Joueur : " + playerWins + " | Adversaire : " + opponentWins + "\n");

                if(i == 2){
                    chooseNewCard();
                    Stone();
                }
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

    void chooseNewCard() {
        m_gameView.Clear();
        AnimalCard[] choices = CardFactory.createCardChoice();
        m_gameView.displayCardChoices(choices);
        m_input.askCardChoice(choices.length);
        int choiceIndex = m_input.getCardChoice();
        AnimalCard chosenCard = choices[choiceIndex];
        m_player.addCardToDeck(chosenCard);
        System.out.println("Vous avez ajouté " + chosenCard.getNom() + " à votre deck !");
    }

    void Stone(){
        m_gameView.Clear();
        m_gameView.displayDeckList(m_player.getDeck());
        m_input.askStoneChoice(m_player.getDeckSize(), "Choisissez une carte à sacrifier pour récupérer son pouvoir");
        int sacrifice = m_input.getStoneChoice();
        AnimalCard cardSacrifice = m_player.getDeckCard(sacrifice);
        
        m_player.deleteDeckCard(sacrifice);
        
        m_gameView.Clear();
        m_gameView.displayDeckList(m_player.getDeck());
        m_input.askStoneChoice(m_player.getDeckSize(), "Choisissez une carte cible pour recevoir le pouvoir");
        int cible = m_input.getStoneChoice();

        AnimalCard cardCible = m_player.getDeckCard(cible);
        for(Power p : cardSacrifice.getPower()){
            cardCible.addPower(p);
        }
        System.out.println("Pouvoir(s) transféré(s) à " + cardCible.getNom() + " !");
    }

        void round () {
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

        void playerTurn(){
            m_player.draw();
            m_gameView.Clear();
            m_gameView.displayBoard(m_board,m_score);
            m_gameView.displayDeck(m_player.getDeck());
            m_gameView.displayHand(m_player);
            m_input.askChoice(m_player.getHandMaxIndex());

            while (m_input.getChoice() != UserChoice.PASSER) {
                AnimalCard card = m_player.getHandCard(m_input.getIndexCard());
                if(card.getBloodCost() > 0){
                    Optional<List<Integer>> optsacrifice = m_input.askSacrifices(card.getBloodCost(),m_board);
                    if (optsacrifice.isPresent()) {
                        List<Integer> sacrifice = optsacrifice.get();
                        for(Integer i : sacrifice){
                            Optional<AnimalCard> optcards = m_board.getAnimalCard(Board.ROW_PLAYER, i);
                            AnimalCard cards = optcards.get();
                            if(!cards.hasPower("Nombreuse Vie")) {
                                m_board.removeCard(Board.ROW_PLAYER, i, m_player.getGraves());
                            }
                        }
                        if (m_board.isEmpty(Board.ROW_PLAYER, m_input.getIndexSlot())) {
                            m_board.setCard(card, Board.ROW_PLAYER, m_input.getIndexSlot());
                            m_player.removeHandCard(m_input.getIndexCard());
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
                m_input.askChoice(m_player.getHandMaxIndex());
            }
            attackPhase(2,1,true);

        }
        private void placeCard(AnimalCard card,int row,int col){
            if(m_board.isEmpty(row, col)) {
                m_board.addCard(card, row, col);
                m_player.removeHandCard(m_input.getIndexCard());
            }
            else {
                System.out.println("slot deja pris");
            }
        }
        private void placeCradBones(AnimalCard card,int row,int col){
            if(!m_board.isEmpty(row, col)){
                System.out.println("slot deja pris");
                return;
            }
            if(m_player.getBones() < card.getBoneCost()){
                System.out.println("pas assez d'os");
            }
            else {
                m_board.addCard(card, row, col);
                m_player.removeHandCard(m_input.getIndexCard());
            }
        }

        void initBoard(){
            m_board = new Board();
            Random rand = new Random();
            for (int i=0; i< 4;i++){
                int r1 = rand.nextInt(6); //une chance sur 8 d'avoir un obstacle
                int r2 = rand.nextInt(4);
                if (r1 == 1){
                    m_board.setCard(CardFactory.createRandomObstacle(), Board.ROW_PLAYER, i);
                }
                if(r2 ==1){
                    m_board.setCard(CardFactory.createRandomObstacle(), Board.ROW_OPPONENT_ACTIVE, i);
                }
            }
        }

    }

