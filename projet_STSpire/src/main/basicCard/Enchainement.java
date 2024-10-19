package main.basicCard;

import java.util.List;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Enchainement extends Attack {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Enchainement
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Enchainement(){
        super("Enchainement", 1, 8);
    }

    /**
     * Méthode pour jouer la carte de Enchainement
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
        if (h.getMana() >= getCost()){
            // Réduire la mana du héro
            h.setMana(Math.max(0, h.getMana() - getCost()));
            List<Monstre> monstres = e.getTeamOfMonstres();
            for (Monstre targetMonster : monstres){
                applyEffects(h, targetMonster);
            }   
            c.addCimetery(this);
            h.removeCardFromHand(this);  
        }else{
            System.out.println("Pas assez de mana !");
        }
    }
}
