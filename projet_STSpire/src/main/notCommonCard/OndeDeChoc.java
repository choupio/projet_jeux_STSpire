package main.notCommonCard;

import java.util.List;

import main.Deck;
import main.TeamofMonstres;
import main.Statut.VulnerableStatus;
import main.Statut.WeaknessStatus;
import main.TypesCard.StatusCard;
import main.personnage.Hero;
import main.personnage.Monstre;

public class OndeDeChoc extends StatusCard {
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Onde de choc
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public OndeDeChoc(){
        super("Onde de choc", 2);
    }

    /**
     * Méthode pour jouer la carte de Onde de choc
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

                for (Monstre targetMonster : monstres) {
                    // Vérifier si l'effet existe déjà sur le monstre
                    WeaknessStatus weaknessEffect = targetMonster.getWeaknessStatus();
                    VulnerableStatus vulnerableEffect = targetMonster.getVulnerableStatus();

                    if (weaknessEffect != null) {
                        // Augmenter la durée de l'effet existant
                        weaknessEffect.increaseDuration(3);
                    } else {
                        // Créer un nouvel effet s'il n'existe pas
                        targetMonster.addStatut(new WeaknessStatus(3));
                    }

                    if (vulnerableEffect != null) {
                        // Augmenter la durée de l'effet existant
                        vulnerableEffect.increaseDuration(3);
                    } else {
                        // Créer un nouvel effet s'il n'existe pas
                        targetMonster.addStatut(new VulnerableStatus(3));
                    }
                }

                b.addBanish(this);
                h.removeCardFromHand(this);  
            }else{
                System.out.println("Pas assez de mana !");
         }
    }
}
