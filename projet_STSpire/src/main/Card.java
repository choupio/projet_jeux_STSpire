package main;

import java.util.Arrays;
import java.util.List;

import main.personnage.Hero;
import main.personnage.Monstre;

//Classe représentant les cartes du jeu 
public abstract class Card {
    //ATTRIBUTS

    private String name;
    private int cost;

    
    /**
     *Liste des noms de cartes banies 
     */
    protected static final List<String> carteBanis = Arrays.asList("Voir rouge", "Volee de coups", "Enflammer", "Desarmement", "Onde de choc", "Invincible", "Offrande", "Slime");


    //CONSTRUCTEUR
    /**
     * Constructeur simple
     *
     * @param name le nom de la carte
     * @param cost le coût de la carte
     */
    public Card(String name, int cost){
        this.name = name;
        this.cost = cost;
    }

    //GETTERS AND SETTERS

    /**
     * Obtient le nom de la carte
     * 
     * @return le nom de la carte
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom de la carte
     * 
     * @param name le nouveau nom 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtient le coût en mana de la carte
     * 
     * @return le coût en mana de la carte
     */
    public int getCost() {
        return cost;
    }

    /**
     * Modifie le coût en mana de la carte
     * 
     * @param cost le nouveau coût en mana
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Obtient la liste des noms de cartes bannies
     * 
     * @return la liste des noms de cartes bannies
     */
    public static List<String> getCartebanis() {
        return carteBanis;
    }

    //METHODES
    /**
     * Méthode abstraite pour jouer la carte
     * 
     * @param monstre le monstre ciblé
     * @param hero    le hero 
     * @param d       le deck 
     * @param c       le cimetière 
     * @param b       le deck de banissement
     * @param e       l'équipe de monstres
     */
    public void play(Monstre monstre, Hero hero, Deck d, Deck c, Deck b, TeamofMonstres e){}

    /**
     * Méthode abstraite pour appliquer les effets
     * 
     * @param h le hero
     * @param m le monstre associé à la carte
     */
    public void applyEffects(Hero h, Monstre m){}

    /**
     *  Réimplémenter la méthode toString pour renvoyer le nom de la carte
     * 
     * @return le nom de la carte
     */
    @Override
    public String toString(){
        return name;
    }
    
}
