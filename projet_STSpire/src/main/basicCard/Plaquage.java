package main.basicCard;

import main.Deck;
import main.TeamofMonstres;
import main.Statut.ForceStatus;
import main.Statut.VulnerableStatus;
import main.Statut.WeaknessStatus;
import main.TypesCard.Attack;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Plaquage extends Attack{
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Plaquage
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public Plaquage(){
        super("Plaquage", 1, 0);
    }

    //on doit modifier dans cette carte la méthode applyEffect
    //Car c'est la seule qui prend en considération les points 
    //de blocagee pour attaquer
    /**
     * Applique les effets de la carte, y compris tout effet de statut.
     * 
     * @param h le hero qui joue la carte.
     * @param m   La cible de la carte.
     */
    public void applyEffects(Hero h, Monstre m) {

        WeaknessStatus weaknessStatus = h.findStatus(WeaknessStatus.class);
        ForceStatus forceStatus = h.findStatus(ForceStatus.class);
        VulnerableStatus vulnerableStatus = h.findStatus(VulnerableStatus.class);


        if (vulnerableStatus != null) {
        // Ajuster les dégâts en fonction du statut de vulnérabilité
            int adjustedDamage = vulnerableStatus.adjustDamage(h.getArmor());
            h.receivedDamage(adjustedDamage);
        }
         else if (forceStatus != null) {
            // Ajuster les dégâts en fonction du statut de force
                int adjustedDamageForce = h.getArmor() + forceStatus.getAttackBonus();
                m.receivedDamage(adjustedDamageForce);

        } else if (weaknessStatus != null) {
            // Ajuster les dégâts en fonction du statut de faiblesse
            int adjustedDamage = weaknessStatus.adjustDamage(h.getArmor());
            m.receivedDamage(adjustedDamage);
        } else {
                // Si pas de statut de faiblesse, appliquer les dégâts normaux
                m.receivedDamage(h.getArmor());
  
        }
        
    }

    /**
     * Méthode pour jouer la carte de Plaquage
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

            // Réduire la mana du héros
            h.setMana(Math.max(0, h.getMana() - getCost())); // Mana impossible a etre negative
            applyEffects(h, m);

            if (carteBanis.contains(getName())) {
                b.addBanish(this);
            } else {
                c.addCimetery(this);
            }
            h.removeCardFromHand(this);

        } else {
            System.out.println("Pas assez de mana pour jouer cette carte");
        }
    }
    
}
