package main.notCommonCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Mana;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Saigne extends Mana {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Saigne
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Saigne(){
        super("Saignée", 0, 2);
    }

    @Override 
    /**
     * Méthode pour jouer la carte de Saigne
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        //Vérifions les points de vie du héro pour la jouer 
        if (h.getCurrentLifePoint() > 3){
            h.setCurrentLifePoint(h.getCurrentLifePoint() - 3);
            super.play(m, h, d, c, b, e);
        }else{
            System.out.println("Vous n'avez pas assez de points de vie pour la jouer");
        }
    }
    
}
