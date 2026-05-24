package modele;

public class Score {
    private int m_score;

    public Score(){
        m_score = 0;
    }

    public void addScore(int point){
        this.m_score += point;
    }

    public int getScore(){
        return this.m_score;
    }

    public int getEcart(Score score2){
        return this.getScore() - score2.getScore();
    }

    public void resetScore(){
        this.m_score = 0;
    }
}
