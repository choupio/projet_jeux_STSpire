package main.notCommonCard;

import main.Deck;
import main.TeamofMonstres;
import main.Statut.ForceStatus;
import main.TypesCard.StatusCard;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Desarmement extends StatusCard {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Desarmement
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Desarmement(){
        super("Desarmement", 1);
    }

    //METHODES
    /**
     * Méthode pour jouer la carte de Desarmement
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
        m.addStatut(new ForceStatus(-2));
    }
    
}
