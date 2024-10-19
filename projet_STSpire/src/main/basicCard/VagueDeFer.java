package main.basicCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.AttackAndDefense;
import main.personnage.Hero;
import main.personnage.Monstre;


public class VagueDeFer extends AttackAndDefense {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de VagueDeFer
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public VagueDeFer(){
        super("Vague de fer", 1, 5,5);
    }

    /**
     * Méthode pour jouer la carte de Vague de fer
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
        System.out.println("Le héro a gagné 5 points de blocage !");
    }
    
}
