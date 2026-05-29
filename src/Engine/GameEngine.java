package Engine;

import modele.Score;
import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.Card;
import modele.board.Slot;
import modele.player.*;
import vue.GameView;
import vue.InputHandeler;
import vue.UserChoice;

import java.util.Optional;

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

                    if (damage > 0) {
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
    }
        private void round () {
            while (m_score.getScore() <= 5 && m_score.getScore() >= -5) {

            }
        }

        private void playerTurn(){
            m_player.draw();
            m_gameView.displayBoard(m_board,m_score);
            m_gameView.displayDeck(m_player.getDeck());
            m_gameView.displayHand(m_player);
            m_input.askChoice(m_player.getHand().getMaxIndex());

            while (m_input.getChoice() != UserChoice.PASSER) {
                AnimalCard card = m_player.getHand().getCard(m_input.getIndexCard());
                if(card.getBloodCost() > 0){

                } else if (card.getBoneCost() > 0) {

                }
                else {
                    placeCard(card,2,m_input.getIndexSlot());
                }
                m_input.askChoice(m_player.getHand().getMaxIndex());
            }

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

    }

