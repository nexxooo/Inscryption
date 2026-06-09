package modele.Power;

import modele.board.AnimalCard;
import modele.board.Board;

public class Coureur extends Power {
    public Coureur() {
        super("Coureur");
    }

    public void onEndTurn(AnimalCard attacker, int currentRow, int currentCol, Board board) {
        int rightCol = currentCol + 1;
        int leftCol = currentCol - 1;

        // 1. Tenter de se déplacer à droite
        if (rightCol < 4 && board.getSlot(currentRow, rightCol).isEmpty()) {
            board.getSlot(currentRow, rightCol).setCard(attacker);
            board.getSlot(currentRow, currentCol).removeCard();
            System.out.println(attacker.getNom() + " s'est déplacé vers la droite.");
        }
        // 2. Tenter de se déplacer à gauche si la droite est bloquée
        else if (leftCol >= 0 && board.getSlot(currentRow, leftCol).isEmpty()) {
            board.getSlot(currentRow, leftCol).setCard(attacker);
            board.getSlot(currentRow, currentCol).removeCard();
            System.out.println(attacker.getNom() + " s'est déplacé vers la gauche.");
        }

    }

}
