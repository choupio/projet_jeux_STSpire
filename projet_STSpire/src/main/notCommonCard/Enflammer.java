package main.notCommonCard;

import main.Deck;
import main.TeamofMonstres;
import main.Statut.ForceStatus;
import main.TypesCard.StatusCard;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Enflammer extends StatusCard{
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Enflammer
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Enflammer(){
        super("Enflammer", 1);
    }

    /**
     * Méthode pour jouer la carte de Enflammer
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        ForceStatus existingForceStatus = h.findStatus(ForceStatus.class);
        if (existingForceStatus != null){
            existingForceStatus.incrementNumberOfApplications();
        }else{
            ForceStatus newForceStatus = new ForceStatus(2);
            h.addStatut(newForceStatus);
        }
    }
}
