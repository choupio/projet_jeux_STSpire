package main.notCommonCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Defense;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Tenacite extends Defense {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Tenacite
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Tenacite(){
        super("Tenacite", 1, 15);
    }

    //METHODE
    /**
     * Méthode pour jouer la carte de Tenicite
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e) {
        super.play(m, h, d, c, b, e);
        h.addCardToHand(new Plaie());
        h.addCardToHand(new Plaie());
    }
}
