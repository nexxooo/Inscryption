package modele.Power;

import modele.board.AnimalCard;
import modele.board.Board;
import modele.board.Card;
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
    public void onEndTurn(AnimalCard attacker, int currentRow, int currentCol, Board board){}
    public int modifyDamage(AnimalCard attacker, Card defender,int damage){return damage;}
    public void onReceiveDamage(AnimalCard attacker){}

    public int modifyOpponentAttack(int damage) {
        return damage;
    }
}
