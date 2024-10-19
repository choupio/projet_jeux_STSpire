package main.basicCard;

import java.util.List;
import java.util.Random;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class EpeeBoomerang extends Attack {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de EpeeBoomerang
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public EpeeBoomerang(){
        super("Epee Boomerang", 1, 3);
    }

    /**
     * Méthode pour jouer la carte de EpeeBoomerang
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
        Random random = new Random();

        if (h.getMana() >= getCost()){
            // Réduire la mana du héro
            h.setMana(Math.max(0, h.getMana() - getCost()));

            List<Monstre> monstres = e.getTeamOfMonstres();
            for (int i = 0; i < 3; i++) {
                // On choisit aléatoirement un nombre pour cibler un monstre à chaque attaque
                int randomNumber = random.nextInt(monstres.size());
                Monstre targetMonstre = monstres.get(randomNumber);
                applyEffects(h, targetMonstre);
            }

            c.addCimetery(this);
            h.removeCardFromHand(this);
        }else{
            System.out.println("Pas assez de mana ! ");
        }
    }

}
