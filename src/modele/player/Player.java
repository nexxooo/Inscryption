package modele.player;
import modele.board.AnimalCard;
import modele.board.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Player {
    private Hand m_hand;
    private Deck m_deck;
    private Graves m_graves;

    public Player() {
        m_hand = new Hand();
        m_deck = new Deck();
        m_graves = new Graves();
    }

    public void Draw(){
        Optional<AnimalCard> card = m_deck.getTopCard();
        card.ifPresent(animalCard -> m_hand.addCard(animalCard));
    }

    public int getBones(){
        return m_graves.getBones();
    }

    public String getHandAsString(){
        return m_hand.getListAsString();
    }
    public Hand getHand(){
        return m_hand;
    }

    public void draw(){
        Optional<AnimalCard> card = m_deck.getTopCard();
        card.ifPresent(animalCard -> m_hand.addCard(animalCard));
    }

    public Graves getGraves(){
        return m_graves;
    }

    public void spendBones(int amount) {
        m_graves.spendBones(amount);
    }

    public Deck getDeck(){
        return m_deck;
    }

    // Encapsulation Delegate Methods to prevent getter-chaining
    public void refillHandToDeck() {
        m_hand.refillDeck(m_deck);
    }

    public void refillGravesToDeck() {
        m_graves.refillDeck(m_deck);
    }

    public void clearGraves() {
        m_graves.clear();
    }

    public void shuffleDeck() {
        m_deck.shuffle();
    }

    public void addCardToDeck(AnimalCard card) {
        m_deck.addCard(card);
    }

    public int getDeckSize() {
        return m_deck.sizeDeck();
    }

    public AnimalCard getDeckCard(int index) {
        return m_deck.getCard(index);
    }

    public void deleteDeckCard(int index) {
        m_deck.deleteCard(index);
    }

    public int getHandMaxIndex() {
        return m_hand.getMaxIndex();
    }

    public AnimalCard getHandCard(int index) {
        return m_hand.getCard(index);
    }

    public void removeHandCard(int index) {
        m_hand.removeCard(index);
    }

    public int getHandCardCount() {
        return m_hand.getCardCount();
    }
}
