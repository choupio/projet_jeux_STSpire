package main.personnage.ListMonsters;

import main.Deck;
import main.personnage.Hero;
import main.personnage.Monstre;

public class PetitSlimePiquant extends Monstre {
    //ATTRIBUTS 
    private int damage = 2;

    //CONSTRUCTEUR
    /**
     * Constructeur de la classe PetitSlimePiquant
     * Initialise un petit slime piquant avec 12 de points de vie
     */
    public PetitSlimePiquant(){
        super("Petit slime piquant", 12);
    }

    //GETTERS AND SETTERS

    /**
     * Retourne les dégâts 
     *
     * @return les dégâts 
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Modifie les dégâts 
     *
     * @param damage nouvelle valeur des dégâts
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    //METHODES

    /**
     * Effectuant une charge contre le héro lorsqu'il joue
     *
     * @param h héro contre lequel le monstre effectue la charge
     * @param d deck du héro
     */
    @Override
    public void play(Hero h, Deck d){
        charge(h);
    }

    /**
     * Effectue une charge contre le héros, infligeant des dégâts
     *
     * @param h héros contre lequel le monstre effectue la charge
     * 
     */
    public void charge(Hero h){

        applyEffects(h, this, getDamage());
        
    }

    /**
     * Retourne l'intention du monstre, qui est d'effectuer une charge
     *
     * @return l'intention du monstre
     * 
     */
    public String intention(){
        return "L'intention du monstre est d'effectuer une charge";
    }
}

