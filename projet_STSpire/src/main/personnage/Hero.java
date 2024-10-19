package main.personnage;
import main.Card;
import main.Deck;

import java.util.ArrayList;

import java.util.List;


//Classe représentant le héro de notre jeu

public class Hero extends Entitee{
    
    //ATTRIBUTS SPECIFIQUE AU HERO
    private int mana;
    private int armor;
    private Deck deck;
    private List<Card> playerHand;

    //CONSTRUCTEUR 
    /**
     * Constructeur du héros
     *
     * @param deck deck appartenant au héro
     * 
     */
    public Hero(Deck deck){
        super(70);
        this.deck = deck;
        this.armor = 0;
        this.mana = 3;
        this.playerHand = new ArrayList<>();
    }

    //GETTERS AND SETTERS 

    /**
     * Obtient la valeur actuelle de l'armure 
     *
     * @return La valeur actuelle de l'armure
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Définit la valeur actuelle de l'armure 
     *
     * @param armor La nouvelle valeur de l'armure
     * 
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }


    /**
     * Obtient le mana actuel
     *
     * @return Le mana actuel
     * 
     */
    public int getMana() {
        return mana;
    }

    /**
     * Définit le mana actuel
     *
     * @param mana La nouvelle valeur de mana
     * 
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * Obtient le deck
     *
     * @return Le deck 
     * 
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Définit le deck
     *
     * @param deck Le nouveau deck 
     * 
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**
     * Obtient la main du joueur
     *
     * @return La liste des cartes présent dans la main du joueur
     * 
     */
    public List<Card> getPlayerHand() {
        return playerHand;
    }

    /**
     * Définit une nouvelle main
     *
     * @param playerHand La nouvelle liste de cartes à mettre dans la main du joueur 
     */
    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

    //METHODES HORS CATEGORIE 
    /**
     * Ajoute une valeur mana à la mana actuelle
     * 
     * @param addMana la valeur a ajouter à la mana actuelle
     */
    public void addMana(int addMana) {
        this.mana += addMana;
    }

    /**
     * Suprime de la mana au joueur 
     * 
     * @param removeMana la valeur de mana à ajouter 
     * 
     */
    public void removeMana(int removeMana) {
        mana = Math.max(0, mana - removeMana);
    }

    /*
     * Remets la mana a la valeur initial, c'est à dire 3 dans notre cas
     */
    public void resetMana() {
        this.mana = 3;
    }

    /**
     * Remet la valeur de l'armure à 0
     */
    public void resetArmor() {
        this.armor = 0;
    }

    //METHODES PROVENANT DE LA CLASSE MERE 

    /*
     * Retourne vrai si les points de vie sont inferieurs ou egale à 0 sinon renvoie faux 
     */
    @Override
    public boolean isDead() {
        return (currentLifePoint == 0 || currentLifePoint < 0);
    }

    /**
     * Calcul les dommages que le hero reçoit et actualise les points de vie de ce dernier
     * Prend en compte dans le calcul le bouclier 
     * 
     * @param damage degat que le héro va subir
     * 
     */
    @Override
    public void receivedDamage(int damage) {
        if (armor > 0) {
            if (armor >= damage) {
                armor -= damage;
                return;
            } else {
                damage -= armor;
                armor = 0;
            }
        }
        currentLifePoint = Math.max(0, currentLifePoint - damage); // permet de remettre à 0 les points de vie si cela sont négatifs
    }

    /**
     * Affiche les statuts du héro si il possède
     */
    public void printStatus() {
        if (!statuts.isEmpty()){
            System.out.println("Statuts actuels du héro : ");
            printStatuts();
        }
    }

    //METHODES LIES A LA GESTION DES CARTES DANS LE DECK OU DANS LA MAIN DU JOUEUR 

    /**
     * Envoie toutes les cartes de la main dans le cimetière
     */
    public void discardHand() {
        for (Card card : playerHand) {
            deck.addCimetery(card);
        }
    }

    /**
     * Enlève la carte en paramètre de la main du joueur
     *
     * @param card La carte à enlever
     * 
     */
    public void removeCardFromHand(Card card) {
        playerHand.remove(card);
    }

    /**
     * retourne le nombre de carte présent dans la main du joueur
     */
    public int getHandSize() {
        return playerHand.size();
    }

    /**
     * Ajoute une carte à la main du joueur
     *
     * @param card La carte à ajouter
     */
    public void addCardToHand(Card card) {
        playerHand.add(card);
        System.out.println("Carte ajoutée à votre main : " + card.getName());
    }


    /**
     * Pioche une carte a partir du deck
     * Si le deck est vide, on le remplit avec le cimetiere
     * 
     * @param deck le deck du hero
     * @param c le cimetiere
     */
    public void drawCard(Deck deck, Deck c) {
        if (deck.isEmpty()) {
            c.RefillDeck(deck);
        }
        playerHand.add(deck.draw());
    }

    /**
     * Pioche 5 cartes
     *
     * @param deck le deck
     * @param c    le cimetière
     *
     */
    public void drawInitialCard(Deck deck, Deck c) {

        for (int i = 0; i < 5; i++) {
            drawCard(deck, c); // Reuse the drawCard method
        }
    }

}
    



