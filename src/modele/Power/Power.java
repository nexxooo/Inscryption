package modele.Power;

import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.Slot;

public abstract  class Power {
    String m_name;
    public Power(String name) {
        m_name = name;
    }

    public String getName() {
        return m_name;
    }

    public void onDebut(Slot slot) {}
    public void onAttack(AnimalCard attacker, int currentRow, int currentCol, Board board){}
}
