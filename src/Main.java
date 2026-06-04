import modele.Score;
import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.Card;
import modele.player.*;
import vue.GameView;

public class Main
{
    public static void main(String[] args)
    {
        //System.out.println("Another challenger... It has been ages.");
       Board b = new Board();
    GameView view = new GameView();
        Score score = new Score();
        view.Clear();

       view.displayBoard(b,score);
        Deck d = new Deck();
        view.displayDeck(d);

        AnimalCard cd1 = new AnimalCard("test",10,10,10,0,false);
        AnimalCard cd2 = new AnimalCard("test",10,10,10,0,false);
        HumanPlayer p1 = new HumanPlayer();
        p1.getHand().addCard(cd1);
        p1.getHand().addCard(cd2);
        view.displayHand(p1);

    }
}
