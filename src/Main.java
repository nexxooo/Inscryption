import modele.board.Board;
import vue.GameView;

public class Main
{
    public static void main(String[] args)
    {
        //System.out.println("Another challenger... It has been ages.");
        Board b = new Board();
        GameView view = new GameView();
        view.displayBoard(b);
    }
}
