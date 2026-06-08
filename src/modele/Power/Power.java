package modele.Power;

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
}
