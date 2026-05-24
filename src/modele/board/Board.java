package modele.board;

import modele.score.Score;

public class Board {

    private Slot[][] grid;

    public static final int ROW_OPPONENT_QUEUE = 0;
    public static final int ROW_OPPONENT_ACTIVE = 1;
    public static final int ROW_PLAYER = 2;

    public Board() {

        grid = new Slot[3][4];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                grid[row][col] = new Slot();
            }
        }
    }

    public boolean addCard(Card c, int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 4) {
            return false;
        }

        if (!grid[row][col].isEmpty()) {
            return false;
        }

        grid[row][col].setCard(c);
        return true;
    }

    public Slot getSlot(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 4) {
            return null;
        }
        return grid[row][col];
    }
    public void AdvanceRow(){
        for(int i = 0;i<4;i++){
            if(grid[ROW_OPPONENT_ACTIVE][i].isEmpty() && !grid[ROW_OPPONENT_QUEUE][i].isEmpty()){
                grid[ROW_OPPONENT_ACTIVE][i].setCard(grid[ROW_OPPONENT_QUEUE][i].getCard());
                grid[ROW_OPPONENT_QUEUE][i].removeCard();
            }
        }
    }


}