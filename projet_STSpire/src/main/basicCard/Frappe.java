package main.basicCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;


public class Frappe extends Attack {

    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Frappe
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Frappe(){
        super("Frappe", 1, 6);
    }

    /**
     * Méthode pour jouer la carte de Enchainement
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        super.play(m, h, d, c, b, e);
    }
}
