package main.basicCard;


import main.Deck;
import main.TeamofMonstres;
import main.Statut.WeaknessStatus;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Manchette extends Attack{
    /**
     * Constructeur de la carte de Manchette
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Manchette(){
        super("Manchette", 2, 12);
    }

    /**
     * Méthode pour jouer la carte de Manchette
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
            WeaknessStatus weaknessStatus = new WeaknessStatus(2); // Durée du statut
            m.addStatut(weaknessStatus);
            System.out.println("L'ennemi est désormais faible !");
        }
    }
}
