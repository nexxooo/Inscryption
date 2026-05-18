package vue;

public class InputHandeler {

    private UserChoice m_choice;
    private int m_indexCard;
    private int m_indexSlot;

    public InputHandeler(){};

    public UserChoice getM_choice() {
        return m_choice;
    }

    private void setChoice(UserChoice m_choice) {
        this.m_choice = m_choice;
    }

    public int getIindexCard() {
        return m_indexCard;
    }

    private void setIndexCard(int m_indexCard) {
        this.m_indexCard = m_indexCard;
    }

    public int getIndexSlot() {
        return m_indexSlot;
    }

    private void setIndexSlot(int m_indexSlot) {
        this.m_indexSlot = m_indexSlot;
    }

    public void askChoice(){
        //System.out.println("Actions possibles: \n[fin] Terminer votre tour \n[piocher] Piocher une carte \n[placer <numero carte> <position>] Placer une carte sur le plateau");
        String input = System.console().readLine().trim().toLowerCase();
        String[] splite = input.split(" ");

        if(splite.length == 0 || splite.length > 3){
            System.out.println("erreur de saisie");
            askChoice();
        }

        switch (splite[0]){
            case "fin":
                setChoice(UserChoice.PASSER);
                break;
            case "piocher":
                setChoice(UserChoice.PIOCHER);
            case "placer":

        }



    }
}
