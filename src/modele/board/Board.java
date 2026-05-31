package modele.board;

import modele.player.Deck;

import java.util.Optional;

public class Board {

    private Slot[][] m_grid;

    public static final int ROW_OPPONENT_QUEUE = 0;
    public static final int ROW_OPPONENT_ACTIVE = 1;
    public static final int ROW_PLAYER = 2;

    public Board() {

        m_grid = new Slot[3][4];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                m_grid[row][col] = new Slot();
            }
        }
    }

    public boolean addCard(Card c, int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 4) {
            return false;
        }

        if (!m_grid[row][col].isEmpty()) {
            return false;
        }

        m_grid[row][col].setCard(c);
        return true;
    }

    public Slot getSlot(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 4) {
            return null;
        }
        return m_grid[row][col];
    }
    public void advanceRow(){
        for(int i = 0;i<4;i++){
            if(m_grid[ROW_OPPONENT_ACTIVE][i].isEmpty() && !m_grid[ROW_OPPONENT_QUEUE][i].isEmpty()){
                m_grid[ROW_OPPONENT_ACTIVE][i].setCard(m_grid[ROW_OPPONENT_QUEUE][i].getCard());
                m_grid[ROW_OPPONENT_QUEUE][i].removeCard();
            }
        }
    }

    public void clearBoard(Deck deck){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                Slot slot = getSlot(row, col);
                if(!slot.isEmpty()) {
                    Optional<AnimalCard> optAnimal = slot.getCard().isAnimal();
                    optAnimal.ifPresent(deck::addCard);
                    slot.removeCard();
                }
            }
        }
    }


}