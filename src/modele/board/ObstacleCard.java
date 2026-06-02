package modele.board;

public class ObstacleCard extends Card {
    public ObstacleCard(String name, int hp) {
        super(name, hp);
    }
    @Override
    public String[] getCardAscii() {
        String[] lines = new String[7];
        lines[0] = "*-----------*";
        lines[1] = String.format("| %-9s |", getNom());
        lines[2] = "|-----------|";
        lines[3] = String.format("| PV: %-5d |", getHealthPoints());
        lines[4] = "|           |";
        lines[5] = "|           |";
        lines[6] = "*-----------*";
        return lines;
    }
}
