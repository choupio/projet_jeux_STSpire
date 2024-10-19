package main.notCommonCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class VoleeDeCoups extends Attack{
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Volee de coups
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public VoleeDeCoups(){
        super("Volee de coups", 1, 2);
    }

    //METHODES
    /**
     * Méthode pour jouer la carte de Volee de coups
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
        if (h.getMana() >= getCost()) {
            System.out.println("Mana avant l'attaque : " + h.getMana());
            // Réduire la mana du héros une seule fois
            h.setMana(Math.max(0, h.getMana() - getCost()));

            // Effectuer les quatres attaques
            applyEffects(h, m);
            applyEffects(h, m);
            applyEffects(h, m);
            applyEffects(h, m);

            // Après l'effet de la carte, il faut l'enlever de la main du joueur
            b.addBanish(this);
            h.removeCardFromHand(this);
        } else {
            System.out.println("Pas assez de mana pour jouer cette carte");
        }
        
    }
}
