package main.TypesCard;

import java.util.List;

import main.Card;
import main.Deck;
import main.TeamofMonstres;
import main.Statut.ForceStatus;
import main.Statut.Fragility;
import main.Statut.Status;
import main.Statut.VulnerableStatus;
import main.Statut.WeaknessStatus;
import main.personnage.Hero;
import main.personnage.Monstre;

public class AttackAndDefense extends Card {
    //ATTRIBUTS
    private int damage;
    private int armor;

    // CONSTRUCTEUR
    /**
     * construit une carte d'attaque et de défense avec un nom, un coût, des dégâts
     * et une armure spécifiés
     * 
     * @param cardName le nom de la carte
     * @param cost     le coût en mana de la carte
     * @param damage   les dégâts infligés par la carte
     * @param armor    l'armure fournie par la carte
     */
    public AttackAndDefense(String cardName, int cost, int damage, int armor) {
        super(cardName, cost);
        this.damage = damage;
        this.armor = armor;
    }

    //GETTERS AND SETTERS

    /**
     * retorune les dégâts infligés par la carte
     * 
     * @return les dégâts de la carte
     */
    public int getDamage() {
        return damage;
    }

    /**
     * modifie les dégâts infligés par la carte
     * 
     * @param damage la valeur des dégâts de la carte
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * récupère la valeur de l'armure fournie par la carte
     * 
     * @return la valeur de l'armure de la carte
     */
    public int getArmor() {
        return armor;
    }

    /**
     * modifie la valeur de l'armure fournie par la carte
     * 
     * @param armor nouvelle valeur de l'armure de la carte
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * Calcul les dommages de la carte en fonction des statuts
     * 
     * @param h le hero qui joue la carte
     * @param m   le monstre ciblé
     */
    @Override
    public void applyEffects(Hero h, Monstre m) {

        List<Status> statusList = h.getStatuts();

        int totalAdjustedDamage = damage;

        for (Status status : statusList) {
            if (status instanceof VulnerableStatus) {
                totalAdjustedDamage = ((VulnerableStatus) status).adjustDamage(totalAdjustedDamage);
            } else if (status instanceof ForceStatus) {
                totalAdjustedDamage += ((ForceStatus) status).getAttackBonus();
            } else if (status instanceof WeaknessStatus) {
                totalAdjustedDamage = ((WeaknessStatus) status).adjustDamage(totalAdjustedDamage);
            }
        }

        // Appliquer les dégâts ajustés totaux
        m.receivedDamage(totalAdjustedDamage);
        
    }
    
    /**
     * joue la carte en appliquant ses effets sur la cible spécifiée
     * 
     * @param m la cible de la carte
     * @param h le héros jouant la carte
     * @param d le deck 
     * @param c le cimetière 
     * @param b le deck de cartes bannies 
     * @param e l'équipe de monstres
     */
    @Override
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
         if (h.getMana() >= getCost()){

            h.setMana(Math.max(0, h.getMana() - getCost())); // Mana impossible a etre negative
            applyEffects(h,m);

            Fragility fragility = h.findStatus(Fragility.class);
            if (fragility != null){
                h.setArmor((int) (armor * 0.75));
            }else{
                h.setArmor(armor);
            }

            //après l'effet de la carte, il faut l'enlever de la main du joueur
            if (carteBanis.contains(getName())) {
                b.addBanish(this);
            } else {
                c.addCimetery(this);
            }
            h.removeCardFromHand(this);
        }else{
            System.out.println("Pas assez de mana pour jouer cette carte");
        }
        
    }
}
