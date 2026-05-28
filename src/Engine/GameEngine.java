package Engine;

import modele.Score;
import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.Card;
import modele.board.Slot;
import modele.player.*;
import vue.GameView;
import vue.InputHandeler;

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

    }

