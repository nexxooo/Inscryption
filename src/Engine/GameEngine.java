package Engine;

import modele.Score;
import modele.board.Board;
import modele.board.Card;
import modele.board.Slot;
import modele.player.*;
import vue.GameView;
import vue.InputHandeler;

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

    private void Attackphase(int index_attack_row ,int index_defense_row,boolean isPlayerAttack){
        for(int i =0;i<4;i++) {
            Slot attackerSlot =m_board.getSlot(index_attack_row, i);
            Slot defenderSlot = m_board.getSlot(index_defense_row, i);
            if ( !attackerSlot.isEmpty()){
                Card attackCard = m_board.getSlot(index_attack_row, i).getCard();
                int damage = attackCard.getAttackPoints();
                if(defenderSlot.isEmpty()|| attackCard.isFlying()){
                     damage = isPlayerAttack ? damage : -damage;
                    m_score.addScore(damage);
                }
                else {
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
