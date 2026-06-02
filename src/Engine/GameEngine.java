package Engine;

import modele.Score;
import modele.board.*;
import modele.player.*;
import vue.GameView;
import vue.InputHandeler;
import vue.UserChoice;

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
    }

    private void attackPhase(int indexAttackRow, int indexDefenseRow, boolean isPlayerAttack) {
        for (int i = 0; i < 4; i++) {
            Slot attackerSlot = m_board.getSlot(indexAttackRow, i);
            Slot defenderSlot = m_board.getSlot(indexDefenseRow, i);

            if (!attackerSlot.isEmpty()) {
                Card baseCard = attackerSlot.getCard();

                Optional<AnimalCard> optAttacker = baseCard.isAnimal();

                if (optAttacker.isPresent()) {
                    AnimalCard attackerAnimal = optAttacker.get();
                    int damage = attackerAnimal.getAttackPoints();


                        if (defenderSlot.isEmpty() || attackerAnimal.isFlying()) {
                            int delta = isPlayerAttack ? damage : -damage;
                            m_score.addScore(delta);
                        } else {

                            Card defenderCard = defenderSlot.getCard();
                            defenderCard.takeDamage(damage);

                            if (defenderCard.isDead()) {
                                defenderSlot.removeCard();
                            }
                        }
                    }
                }
            }
        }

        private void round () {
            CardFactory.initializeDeck(m_player.getDeck());
            while (m_score.getScore() <= 5 && m_score.getScore() >= -5) {
                playerTurn();
                ///inserer tour IA

            }

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
                            m_board.getSlot(Board.ROW_PLAYER,i).removeCard();
                        }
                        m_board.getSlot(Board.ROW_PLAYER,m_input.getIndexCard()).setCard(card);

                    }

                } else if (card.getBoneCost() > 0) {
                    placeCradBones(card,2,m_input.getIndexSlot());
                }
                else {
                    placeCard(card,2,m_input.getIndexSlot());
                }
                m_input.askChoice(m_player.getHand().getMaxIndex());
            }
            attackPhase(2,1,true);

        }
        private void placeCard(AnimalCard card,int row,int col){
            if(m_board.getSlot(row,col).isEmpty()) {
                m_board.addCard(card, row, col);
            }
            else {
                System.out.println("slot deja pris");
            }
        }
        private void placeCradBones(AnimalCard card,int row,int col){
            if(!m_board.getSlot(row,col).isEmpty()){
                System.out.println("slot deja pris");
            }
            if(m_player.getBones() < card.getBoneCost()){
                System.out.println("pas assez d'os");
            }
            else {
                m_board.addCard(card, row, col);
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

