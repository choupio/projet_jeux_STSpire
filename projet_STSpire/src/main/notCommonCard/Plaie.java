package main.notCommonCard;

import main.Card;
import main.Deck;
import main.TeamofMonstres;
import main.personnage.Hero;
import main.personnage.Monstre;
//Carte servant à polluer la main du joueur 
public class Plaie extends Card{
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Plaie
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Plaie(){
        super("Plaie", 0);
    }

    /**
     * Méthode pour jouer la carte de Plaie
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        System.out.println("Cette carte ne peut pas être joué !");
    }
}
