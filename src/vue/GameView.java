package vue;

import modele.board.Board;
import modele.board.Slot;

public class GameView {
    public GameView() {
    }

    private void displayLine(Board b,int ligne){
        for(int i = 0;i<7;i++){
            for(int y = 0;y<4;y++){
                Slot slot = b.getSlot(ligne,y);
                System.out.print(slot.getASCII()[i]+" ");
            }
            System.out.println();
        }

    }
    public void displayBoard(Board b){
     displayLine(b,0);
        System.out.println("      ||            ||            ||            ||");
        System.out.println("      \\/            \\/            \\/            \\/");
        displayLine(b,1);
        displayLine(b,2);
    }

}
