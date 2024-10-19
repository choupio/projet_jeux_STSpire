package main.notCommonCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Mana;
import main.personnage.Hero;
import main.personnage.Monstre;

public class VoirRouge extends Mana {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Voir rouge
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public VoirRouge(){
        super("Voir rouge", 1, 2);
    }

    //METHODE
    /**
     * Méthode pour jouer la carte de Voir rouge
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
