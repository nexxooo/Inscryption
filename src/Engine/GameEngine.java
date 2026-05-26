package Engine;

import modele.Score;
import modele.board.Board;
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
}
