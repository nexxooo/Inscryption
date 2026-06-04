import Engine.GameEngine;
import modele.Score;
import modele.player.HumanPlayer;
import vue.GameView;
import vue.InputHandeler;

public class Main {
    public static void main(String[] args) {
        HumanPlayer player = new HumanPlayer();
        Score score = new Score();
        GameView view = new GameView();
        InputHandeler input = new InputHandeler();

        GameEngine engine = new GameEngine(player, score, view, input);
        engine.play();
    }
}
