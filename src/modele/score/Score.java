package modele.score;

public class Score {
    private int score;
    public Score(){
        this.score = 0;
    }
    public int addWeight(int amount){
        this.score += amount;
    }
    public boolean checkVictory(){
        return this.score >= 5;
    }
    public int getScore(){
        return score;
    }

}
