package main.notCommonCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;


public class Hemokinesie extends Attack {
    //CONSTRUCTEUR 
    /**
     * Constructeur de la carte de Hemokinesie
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Hemokinesie(){
        super("Hémokinésie", 1,  15);
    }

    /**
     * Méthode pour jouer la carte de Hemokinesie
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    @Override
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        // Vérifions les points de vie du héro pour la jouer
        if (h.getCurrentLifePoint() > 2){
            //le héro perd 2 points de vie 
            h.setCurrentLifePoint(h.getCurrentLifePoint() - 2);
            super.play(m, h, d, c, b, e);
        }else{
            System.out.println("Vous n'avez pas assez de points de vie pour la jouer");
        }
    }
}
