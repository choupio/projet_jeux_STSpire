package main.personnage.ListMonsters;

import main.Deck;
import main.Statut.Fragility;
import main.basicCard.Slime;
import main.personnage.Hero;
import main.personnage.Monstre;

public class SlimePiquant extends Monstre {
    //ATTRIBUTS
    private double randomValue;
    private int damage;

    //CONSTRUCTEUR
    /**
     * Construit un monstre de type SlimePiquant avec un nom et des points de vie
     */
    public SlimePiquant() {
        super("Slime piquant", 50);
        this.damage = 8;
    }

    //GETTERS AND SETTERS

    /**
     * Récupère la valeur aléatoire 
     * 
     * @return la valeur aléatoire
     */
    public double getRandomValue() {
        return randomValue;
    }


    /**
     * Modifie la valeur aléatoire 
     * 
     * @param randomValue la nouvelle valeur aléatoire
     */
    public void setRandomValue(double randomValue) {
        this.randomValue = randomValue;
    }

    /**
     * Récupère les dégâts infligés 
     * 
     * @return les dégâts infligés
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Modifie les dégâts infligés
     * 
     * @param damage la nouvelle valeur des dégâts infligés 
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }


    //METHODES

    /**
     * Joue le pattern du monstre envers le hero
     * 
     * @param h le hero
     * @param d le deck 
     */
    @Override
    public void play(Hero h, Deck d){
        if (getRandomValue() >= 0 && getRandomValue() < 0.70){
            lecher(h);
        }else{
            crachat(h,d);
        }
    }

    /**
     * Effectue l'action de cracher sur le hero infligeant des dégâts et ajoutant
     * une carte Slime au cimetière du hero
     * 
     * @param h le hero
     * @param d le deck
     */
    public void crachat(Hero h, Deck d){

        setDamage(8);
        applyEffects(h, this, damage);
        d.addCimetery(new Slime());
    
    }

    /**
     * Effectue l'action de lécher le hero, appliquant le statut Fragilité a ce dernier
     * 
     * @param h le hero
     */
    public void lecher(Hero h){
        Fragility fragility = new Fragility(1);
        if (!h.statusPresent(fragility)) {
            h.addStatut(new Fragility(1));
        } else {
            fragility.increaseDuration(1);
        }
    }

    /**
     * Obtient une phrase décrivant l'intention du monstre en fonction de son
     * action
     * 
     * @return une phrase décrivant l'intention du monstre
     */
    @Override
    public String intention() {
        String action = "";
            
                randomValue = Math.random();
                if (randomValue >= 0 && randomValue < 0.70) {
                    action = "lecher";
            
                } else {
                    action = "crachat ";
                }
        return "L'intention du monstre est d'effectuer " + action;
    }
}
