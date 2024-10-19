package main.notCommonCard;

import main.Deck;
import main.TeamofMonstres;
import main.Statut.VulnerableStatus;
import main.Statut.WeaknessStatus;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Uppercut extends Attack {
    // CONSTRUCTEUR
    /**
     * Constructeur de la carte de Uppercut
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Uppercut() {
        super("Uppercut", 2, 12);
    }

    /**
     * Méthode pour jouer la carte de Uppercut
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
        if (!m.isDead()) {
            VulnerableStatus vulnerableStatus = new VulnerableStatus(1);
            WeaknessStatus weaknessStatus = new WeaknessStatus(1);
            m.addStatut(weaknessStatus);
            m.addStatut(vulnerableStatus);
            System.out.println("L'ennemi est désormais faible et vulnérable");
        }
    }
}
