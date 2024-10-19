package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import main.basicCard.DefenseCard;
import main.basicCard.Frappe;
import main.basicCard.Heurt;


public class Deck {

    //ATTRIBUTS
    private Stack<Card> cards;
    private Stack<Card> cimetery;
    private Stack<Card> banish;

    //CONSTRUCTEUR
    /**
     * construit une instance deck avec trois piles de cartes : deck, cimetière et bannissement
     */
    public Deck(){
        cards = new Stack<>();
        cimetery = new Stack<>();
        banish = new Stack<>();
    }

    //GETTERS AND SETTERS 
    public Stack<Card> getCimetery() {
        return cimetery;
    }

    public void setCimetery(Stack<Card> cimetery) {
        this.cimetery = cimetery;
    }

    public Stack<Card> getBanish() {
        return banish;
    }

    public void setBanish(Stack<Card> banish) {
        this.banish = banish;
    }


    //METHODES

    /**
     * obtient la taille du deck
     */
    public int getSizeDeck(){
        return cards.size();
    }

    /**
     * obtient la taille du cimetière
     */
    public int getSizeCimetery(){
        return cimetery.size();
    }

    /**
     * obtient la taille du deck de bannissement
     */
    public int getSizeBanish(){
        return banish.size();
    }

    /*
     * Ajoute une carte au deck
     */
    public void addCard(Card card){
        if (card != null){
            cards.push(card);
        }
    }

    /*
     * Enleve une carte
     */
    public void removeCard(Card card){
        cards.pop();
    }

    /*
     * Melange le deck
     */
    public void shuffleDeck(){
        Collections.shuffle(cards);
    }
    
    /*
     * Pioche une carte
     */
    public Card draw() {
        return cards.pop();
    }

    /*
     * Retourne vrai si le deck est vide
     */
    public boolean isEmpty(){
        return cards.empty();
    }

    /**
     * Ajoute une carte dans le cimetière
     * @param Carte, une carte qui va dans le cimetière
     * 
     */
    public void addCimetery(Card carte){
        cimetery.push(carte);
    }

    /**
     * Ajoute une carte dans au deck de banissement
     * 
     * @param Carte, une carte qui va dans le cimetière
     * 
     */
    public void addBanish(Card carte) {
        banish.push(carte);
    }

    /**
     * Ajoute toutes les cartes de la liste spécifiée au cimetière
     * 
     * @param cards une liste de cartes à ajouter au cimetière
     */
    public void addAllToCimetery(List<Card> cards) {
        cimetery.addAll(cards);
    }

    /**
     * Ajoute toutes les cartes de la liste spécifiée au deck
     * 
     * @param cards une liste de cartes à ajouter au deck
     */
    public void addAllToDeck(List<Card> cards) {
        cards.addAll(cards);
    }

    /**
     * Ajoute toutes les cartes de la liste spécifiée au banissement
     * 
     * @param cards une liste de cartes à ajouter au deck de banissement 
     *      
     */
    public void addAllBanish(List<Card> cards) {
        banish.addAll(cards);
    }

    /**
     * Transfère les cartes du cimetière au deck, tout en le mélangant
     * 
     * @param deck, une pile de carte
     */
    public void refillDeckEndOfFightFromCimetery(Deck d) {
        while (!cimetery.isEmpty()) {
            Card carte = cimetery.pop();
            if (!carte.getName().equals("Plaie") && !carte.getName().equals("Slime")){
                d.addCard(carte);
            }
        }
            d.shuffleDeck();
            cimetery.clear();
    }

    /**
     * Transfère les cartes du deck de banissement au deck, tout en le mélangant
     * 
     * @param deck, une pile de carte
     */
    public void refillDeckEndOfFightFromBanish(Deck d) {
        while (!banish.isEmpty()) {
            d.addCard(banish.pop());
        }
        d.shuffleDeck();
        banish.clear();
    }

    /**
     * Transfère les cartes du cimetière au deck, tout en le mélangant 
     * @param deck, une pile de carte
     */
    public void RefillDeck(Deck d) {
        if (d.isEmpty() || d.getSizeDeck() < 5) {
            while (!cimetery.isEmpty()) {
                d.addCard(cimetery.pop());
            }
        }
        d.shuffleDeck();
    }

    /*
     * Méthode pour afficher le cimetière pour faire des tests
     */
    public void displayCimetery() {
        System.out.println("Cimetière:");
        System.out.println(cimetery.toString()); 

    }


    /*
     * Initialiser le deck a partir d'une liste de carte 
     * La rareté et répartition des cartes est défini dans le projet 
     */
    public static Deck initialDeck() {

        //Initialisation du deck qui représenté par une pile et de la liste de cartes
        Deck deckInitial = new Deck();

        //Remplissons la liste avec les cartes du début de partie qui sont frappe, défense et heurt
        List<Card> listofCards = new ArrayList<>(Arrays.asList(new Frappe(), new DefenseCard(), new Heurt()));

        List<Card> pseudoDeck = new ArrayList<>();
        
        for (int i = 0; i < 5; i++){
            pseudoDeck.add(listofCards.get(0));
    
        }   
        for (int i = 0; i < 4; i++){
            pseudoDeck.add(listofCards.get(1));
        }    
        pseudoDeck.add(listofCards.get(2));
        

        //Mélange la liste obtenue 
        Collections.shuffle(pseudoDeck);


        //Ajoute toutes les cartes de pseudoDeck à deckInit
        for (int i = 0; i < pseudoDeck.size(); i++){
            deckInitial.addCard(pseudoDeck.get(i));
        }
        return deckInitial;
    }



    /*
     * Affiche les cartes du deck sous forme d'une chaine de caractère 
     */
    public String toString() {
        String deckString = "Cartes dans le deck :\n";

        for (Card card : cards) {
            deckString += card.toString() + "\n";
        }
        return deckString;
    }

    /**
     * Obtient la liste des cartes actuellement dans le deck.
     * 
     * @return une liste des cartes dans le deck
     */
    public Stack<Card> getCards() {
        return cards;
    } 
}
