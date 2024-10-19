package main.rareCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Gourdin extends Attack {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Gourdin
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Gourdin(){
        super("Gourdin", 3, 32);
    }

    /**
     * Méthode pour jouer la carte de Gourdin
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
