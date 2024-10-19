package main.basicCard;

import main.Deck;
import main.TeamofMonstres;
import main.Statut.VulnerableStatus;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Heurt extends Attack{
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Heurt
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Heurt(){
        super("Heurt", 2, 8);
    }

    //METHODES
    /**
     * Méthode pour jouer la carte de Heurt
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    @Override
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e) {
        super.play(m, h, d, c, b, e);

        // Après avoir infligé les dégâts, appliquer le statut Vulnérable
        if (!m.isDead()) {
            VulnerableStatus vulnerableStatus = new VulnerableStatus(2); // Durée du statut
            m.addStatut(vulnerableStatus);
            System.out.println("L'ennemi est désormais Vulnérable !");
        }
    }
}
