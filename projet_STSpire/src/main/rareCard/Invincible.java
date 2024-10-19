package main.rareCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Defense;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Invincible extends Defense{
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Invincible
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Invincible(){
        super("Invincible", 2, 30);
    }

    //METHODE
    /**
     * Méthode pour jouer la carte de Invicible
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
        super.play(m, h, d, c, b, e);
    }

}
