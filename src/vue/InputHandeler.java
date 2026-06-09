package vue;

import modele.board.Board;
import modele.board.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InputHandeler {

    private UserChoice m_choice;
    private int m_indexCard;
    private int m_indexSlot;
    private Scanner scanner;
    private int m_stoneChoice;
    private int m_cardChoice;

    public InputHandeler(){
        this.scanner = new Scanner(System.in);
        this.m_stoneChoice = -1;
        this.m_cardChoice = -1;
    }

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

    private void setStoneChoice(int m_stoneChoice) {this.m_stoneChoice = m_stoneChoice;}
    public int getStoneChoice() {return m_stoneChoice;}

    private void setCardChoice(int m_cardChoice) {this.m_cardChoice = m_cardChoice;}
    public int getCardChoice() {return m_cardChoice;}

    public void askChoice(int maxCardIndex){
        System.out.println("\n \u001B[33m--- Actions possibles : 'placer <num_carte> <case>' (ex: placer 1 b2) ou 'fin' ---\u001B[0m");
        System.out.print("> ");
        int cardIndex;
        String input = scanner.nextLine().trim().toLowerCase();
        if(input.isEmpty()){
            System.out.println("\u001B[31m Saisie vide.\u001B[0m");
            askChoice(maxCardIndex);
            return;
        }
        String[] splite = input.split(" ");

        switch (splite[0].toLowerCase()){
            case "fin":
                setChoice(UserChoice.PASSER);
                break;
            case "placer":
                setChoice(UserChoice.PLACER);
                if(splite.length != 3 ){
                    System.out.println("\u001B[31m erreur de saisie \u001B[0m");
                    askChoice(maxCardIndex);
                    return ;
                }
                int card = getCardIndex(splite,maxCardIndex);
                if(card == -1){
                    System.out.println("\u001B[31m Erreur: le numéro de la carte doit être un chiffre.\u001B[0m");
                    askChoice(maxCardIndex);
                    return;
                }
                int slot = getIndexSlot(splite[2]);
                if(slot == -1) {
                    System.out.println("\u001B[31mErreur: la position n'est pas valide\u001B[0m");
                    askChoice(maxCardIndex);
                    return;
                }
                break;
            default:
                System.out.println("\u001B[31mErreur: commande inconnue\u001B[0m");
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
            int cardIndex = Integer.parseInt(card[1]);
            if(cardIndex > maxCardIndex ||cardIndex < 0){
                System.out.println("\u001B[31mErreur: le numéro de la carte est trop grand ou petit.\u001B[0m");
                return -1;

            }
            setIndexCard(cardIndex);
            return 1;
        }
        catch (Exception e) {
            return -1;
        }
    }
    private int parseSlotIndex(String slot) {
        switch (slot.trim().toLowerCase()) {
            case "b1":
                return 0;
            case "b2":
                return 1;
            case "b3":
                return 2;
            case "b4":
                return 3;
            default:
                return -1;
        }
    }

    public Optional<List<Integer>> askSacrifices(int bloodRequired, Board board) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> sacrifices = new ArrayList<>();
        int bloodCount = 0;
        System.out.println("\u001B[33mVous devez sacrifier " + bloodRequired + " créatures.\u001B[0m");
        while (bloodCount < bloodRequired) {
            System.out.println("\u001B[33mEntrez l'index à sacrifier (ou cancel pour annuler) :\u001B[0m");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("cancel")) {
                return Optional.empty();
            } else {
                int index = parseSlotIndex(input);

                if (index == -1) {
                    System.out.println("\u001B[31mErreur : Case invalide.\u001B[0m");
                    continue;
                }
                if (board.isEmpty(Board.ROW_PLAYER, index)) {
                    System.out.println("\u001B[31m Erreur : Cette case est vide !\u001B[0m");
                    continue;
                }
                if (board.getAnimalCard(Board.ROW_PLAYER, index).isEmpty()) {
                    System.out.println("\u001B[31mErreur : Vous ne pouvez sacrifier que des animaux !\u001B[0m");
                    continue;
                }
                if (sacrifices.contains(index)) {
                    System.out.println("\u001B[31mErreur : Vous avez déjà sélectionné cette carte !\u001B[0m");
                    continue;
                }
                sacrifices.add(index);
                bloodCount++;
                System.out.println("\u001B[32m Sacrifice accepté (" + bloodCount + "/" + bloodRequired + ").\u001B[0m ");
            }
        }
        return Optional.of(sacrifices);
    }

    public void askStoneChoice(int indexMax, String message){
        System.out.println("\n \u001B[33m--- " + message + " ---\u001B[0m");
        System.out.print("> ");
        String input = scanner.nextLine().trim().toLowerCase();
        if(input.isEmpty()){
            System.out.println("\u001B[31mSaisie vide.\u001B[0m");
            askStoneChoice(indexMax, message);
            return;
        }
        try{
            int cardIndex = Integer.parseInt(input);
            if(cardIndex >= indexMax || cardIndex < 0){
                System.out.println("\u001B[31mErreur: le numéro de la carte est trop grand ou petit.\u001B[0m");
                askStoneChoice(indexMax, message);
                return;
            }
            setStoneChoice(cardIndex);
        }
        catch (Exception e) {
            System.out.println("\u001B[31mVeuillez entrer un nombre.\u001B[0m");
            askStoneChoice(indexMax, message);
            return;
        }
    }

    public void askCardChoice(int indexMax) {
        System.out.println("\n \u001B[33m--- Choisissez une carte à ajouter à votre deck ---\u001B[0m");
        System.out.print("> ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.isEmpty()) {
            System.out.println("\u001B[31mSaisie vide.\u001B[0m");
            askCardChoice(indexMax);
            return;
        }
        try {
            int choice = Integer.parseInt(input);
            if (choice >= indexMax || choice < 0) {
                System.out.println("\u001B[31mErreur: le numéro de la carte est invalide.\u001B[0m");
                askCardChoice(indexMax);
                return;
            }
            setCardChoice(choice);
        } catch (Exception e) {
            System.out.println("\u001B[31mVeuillez entrer un nombre.\u001B[0m");
            askCardChoice(indexMax);
            return;
        }
    }
}
