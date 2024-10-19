package main.personnage.ListMonsters;

import main.Deck;
import main.Statut.Fragility;
import main.Statut.WeaknessStatus;
import main.personnage.Hero;
import main.personnage.Monstre;

public class PetitSlimeAcide extends Monstre {
    // ATTRIBUTS
    private int tourActuel;
    private int armor;
    private int damage = 2;

    // CONSTRUCTEUR
    /**
     * Construit un petit slime acide avec des valeurs par défaut
     */
    public PetitSlimeAcide() {
        super("Petit slime acide", 8);
        this.armor = 0;
        this.tourActuel = 1;
    }

    // SETTERS AND GETTERS

    /**
     * récupère les dégâts infligés par le slime
     * 
     * @return les dégâts infligés par le slime
     */
    public int getDamage() {
        return damage;
    }

    /**
     * modifie les dégâts infligés par le slime
     * 
     * @param damage les nouveaux dégâts infligés par le slime
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * récupère le tour actuel du slime
     * 
     * @return le tour actuel du slime
     */
    public int getTourActuel() {
        return tourActuel;
    }

    /**
     * modifie le tour actuel du slime
     * 
     * @param tourActuel le nouveau tour actuel du slime
     */
    public void setTourActuel(int tourActuel) {
        this.tourActuel = tourActuel;
    }

    /**
     * récupère l'armure du slime
     * 
     * @return l'armure du slime
     */
    public int getArmor() {
        return armor;
    }

    /**
     * modifie l'armure du slime
     * 
     * @param armor la nouvelle armure du slime
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * réinitialise les dégâts du slime
     */
    public void reinitialiserDamage() {
        this.damage = 2;
    }

    //METHODES

    /**
     * joue le tour du slime en fonction du tour actuel
     * 
     * @param h le hero
     * @param d le deck
     */
    @Override
    public void play(Hero h, Deck d) {
        switch (tourActuel % 2) {
            case 1:
                lecher(h);
                break;
            case 0:
                charge(h);
                break;
        }
        tourActuel++;
    }

    /**
     * attaque le hero en chargeant
     * 
     * @param h le hero
     */
    public void charge(Hero h) {
        applyEffects(h, this, getDamage());
    }

    /**
     * lèche le hero, appliquant potentiellement le statut de fragilité
     * 
     * @param h le hero
     */
    public void lecher(Hero h) {
        WeaknessStatus weakness = new WeaknessStatus(1);
        if (!h.statusPresent(weakness)){
            h.addStatut(new Fragility(1));
        }else{
            weakness.increaseDuration(1);
        }
    }

    /**
     * donne l'intention du slime pour le tour actuel
     * 
     * @return l'intention du slime
     */
    @Override
    public String intention() {
        String action = "";
        switch (tourActuel % 2) {
            case 1:
                action = "Lecher";
                break;
            case 0:
                action = "charge";
                break;
        }
        return "L'intention du monstre est d'effectuer " + action;
    }
}
