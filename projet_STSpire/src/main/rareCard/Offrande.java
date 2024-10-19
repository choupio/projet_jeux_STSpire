package main.rareCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Mana;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Offrande extends Mana {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Offrande
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Offrande(){
        super("Offrande", 0, 2);
    }

    //METHODE
    /**
     * Méthode pour jouer la carte de Offrande
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
        if (h.getMana() >= getCost()) {
            if (h.getCurrentLifePoint() > 6){
                h.setCurrentLifePoint(h.getCurrentLifePoint() - 6);
                h.removeMana(getCost());
                h.addMana(getMana());
                h.drawCard(d, c);
                h.drawCard(d, c);
                h.drawCard(d, c);

                b.addBanish(this);
                
                h.removeCardFromHand(this);

            } else {
                System.out.println("Pas assez de mana pour jouer cette carte");
            }
        }else{
            System.out.println("Pas assez de points de vie pour la jouer !");
        }
    }
}
