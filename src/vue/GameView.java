package vue;

import modele.Score;
import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.Slot;
import modele.player.Deck;
import modele.player.Player;

import java.util.List;

public class GameView {
    public GameView() {
    }

    private void displayLine(Board b,int ligne,Score sc){
        for(int i = 0;i<7;i++){
            for(int y = 0;y<4;y++) {
                Slot slot = b.getSlot(ligne, y);
                if (ligne == 1 && i == 4 && y == 0) {
                    System.out.print(getSCore(sc)[0] + "    " + slot.getASCII()[i] + " ");
                }
                else if (ligne == 1 && i == 5 && y == 0) {
                    System.out.print(getSCore(sc)[1] + "         " + slot.getASCII()[i] + " ");
                }
                else {
                    System.out.print("          " + slot.getASCII()[i] + " ");
                }
            }
            System.out.println();
        }


    }
    public void displayBoard(Board b,Score sc){
     displayLine(b,0,sc);
        System.out.println("               ||                      ||                      ||                      ||");
        System.out.println("               \\/                      \\/                      \\/                       \\/");
        displayLine(b,1,sc);
        displayLine(b,2,sc);
        System.out.println();
    }

    public void displayHand(Player p){
        System.out.println(p.getHandAsString());
    }

    public String[] getSCore(Score sc){
        String[] res = new String[2];
        res[0] = "Score:";
        res[1] = ""+sc.getScore();
        return res;

    }
    public void displayDeck(Deck deck){
        System.out.println("          *-----------*");
        System.out.println("          |           |");
        System.out.println("          |           |");
        System.out.println("          |  PIOCHE   |");
        System.out.printf("          |     %-6d|%n",deck.getCardCount());
        System.out.println("          |           |");
        System.out.println("          *-----------*");

    }

    public void Clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayDeckList(Deck deck){
        List<AnimalCard> list = deck.getList();
        for(int i = 0;i<list.size();i++){
            System.out.println(i+" : "+list.get(i).toString());
        }

    }

}
