package main.basicCard;

import main.Card;
import main.Deck;
import main.TeamofMonstres;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Slime extends Card{
    //Carte servant à polluer la main du joueur 
    /**
     * Constructeur de la carte de Slime
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Slime(){
        super("Slime", 1);
    }

    /**
     * Méthode pour jouer la carte de Slime
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        if (h.getMana() >= getCost()){
            h.setMana(h.getMana() - getCost());
            b.addBanish(this);
        }
    }
}
