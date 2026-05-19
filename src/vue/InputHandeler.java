package vue;

import java.util.Scanner;

public class InputHandeler {

    private UserChoice m_choice;
    private int m_indexCard;
    private int m_indexSlot;
    private Scanner scanner;

    public InputHandeler(){this.scanner = new Scanner(System.in);}

    public UserChoice getChoice() {
        return m_choice;
    }

    private void setChoice(UserChoice m_choice) {
        this.m_choice = m_choice;
    }

    public int getIndexCard() {
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

    public void askChoice(int maxCardIndex){
        int cardIndex;
        String input = scanner.nextLine().trim().toLowerCase();
        if(input.isEmpty()){
            System.out.println("Saisie vide.");
            askChoice(maxCardIndex);
            return;
        }
        String[] splite = input.split(" ");

        switch (splite[0].toLowerCase()){
            case "fin":
                setChoice(UserChoice.PASSER);
                break;
            case "piocher":
                setChoice(UserChoice.PIOCHER);
                break;
            case "placer":
                setChoice(UserChoice.PLACER);
                if(splite.length != 3 ){
                    System.out.println("erreur de saisie");
                    askChoice(maxCardIndex);
                    return ;
                }
                int card = getCardIndex(splite,maxCardIndex);
                if(card == -1){
                    System.out.println("Erreur: le numéro de la carte doit être un chiffre.");
                    askChoice(maxCardIndex);
                    return;
                }
                int slot = getIndexSlot(splite[2]);
                if(slot == -1) {
                    System.out.println("Erreur: la position n'est pas valide");
                    askChoice(maxCardIndex);
                    return;
                }
                break;
            default:
                askChoice(maxCardIndex);
                return;
        }
    }
    private int getIndexSlot(String slot) {
        switch (slot) {
            case "b1":
                setIndexSlot(0);
                return 1;
            case "b2":
                setIndexSlot(1);
                return 1;
            case "b3":
                setIndexSlot(2);
                return 1;
            case "b4":
                setIndexSlot(3);
                return 1;
            default:
                return -1;
        }
    }
    private int getCardIndex(String[] card,int maxCardIndex){
        try{
            int cardIndex = Integer.parseInt(card[1]) -1;
            if(cardIndex > maxCardIndex ||cardIndex < 0){
                System.out.println("Erreur: le numéro de la carte est trop grand ou petit.");
                return -1;

            }
            setIndexCard(cardIndex);
            return 1;
        }
        catch (Exception e) {
            return -1;
        }
    }
}
