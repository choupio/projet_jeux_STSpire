package main.basicCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class FrappeDuPommeau extends Attack{

    // CONSTRUCTEUR
    /**
     * Constructeur de la carte de Frappe du pommeau
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public FrappeDuPommeau(){
        super("Frappe du pommeau", 1, 9);
    }

    /**
     * Méthode pour jouer la carte de Frappe du pommeau
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
        super.play(m, h, d, c, d, e);
        h.drawCard(d, c);

    }
}
