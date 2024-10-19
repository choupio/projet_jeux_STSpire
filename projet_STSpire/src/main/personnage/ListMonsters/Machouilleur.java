package main.personnage.ListMonsters;

import main.Deck;
import main.Statut.ForceStatus;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Machouilleur extends Monstre {
    private int tourActuel;
    private int armor;
    private double randomValue;
    private int damage;


    //CONSTRUCTEUR
    /**
     * construit un monstre de type Machouilleur avec un nom et des points de vie
     */
    public Machouilleur(){
        super("Machouilleur", 40);
        this.armor = 0;
        this.tourActuel = 1;
    }

    // GETTERS AND SETTERS
    /**
     * récupère la valeur actuelle de l'armure 
     * 
     * @return la valeur actuelle de l'armure
     */
    public int getArmor() {
        return armor;
    }

    /**
     * modifie la valeur actuelle de l'armure 
     * 
     * @param armor la nouvelle valeur d'armure
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * ajoute une valeur spécifiée à l'armure
     * 
     * @param armor la valeur à ajouter à l'armure
     */
    public void ajouterArmure(int armor){
        this.armor += armor;
    }

    /**
     * récupère la valeur des dégâts infligés 
     * 
     * @return la valeur des dégâts
     */
    public int getDamage() {
        return damage;
    }

    /**
     * modifie la valeur des dégâts infligés
     * 
     * @param damage la nouvelle valeur des dégâts
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * récupère la valeur aléatoire 
     * 
     * @return la valeur aléatoire
     */
    public double getRandomValue() {
        return randomValue;
    }

    /**
     * modifie la valeur aléatoire 
     * 
     * @param randomValue la nouvelle valeur aléatoire
     */
    public void setRandomValue(double randomValue) {
        this.randomValue = randomValue;
    }

    /**
     * récupère le tour actuel
     * 
     * @return le tour actuel
     */
    public int getTourActuel() {
        return tourActuel;
    }

    /**
     * modifie le tour actuel 
     * 
     * @param tourActuel le nouveau tour actuel
     */
    public void setTourActuel(int tourActuel) {
        this.tourActuel = tourActuel;
    }
    

    //METHODES
    /**
     * Permet de jouer le pattern du monstre envers le héros
     * 
     * @param Hero h, un hero
     * @param Deck d, un deck
     */
    @Override
    public void play(Hero h, Deck d){
        switch (tourActuel) {
            case 1:
                charge(h);
                break;
            case 2:
                if (getRandomValue() >= 0 && getRandomValue() < 0.45){
                    gronder();
                }else if (getRandomValue() >= 0.45 && getRandomValue() < 0.75){
                    charge(h);
                }else{
                    morsure(h);
                }
                break;
        }
        // Appliquer le statut de Force pour augmenter l'attaque
        tourActuel++;
        if (tourActuel == 3){ tourActuel = 1;}//boucle le nombre de tourActuel
    }

    /**
     * Definition des termes présent dans la pattern
     * 
     * @param Monstre m, un monstre
     * @param Hero    h, un hero
     */
    public void charge(Hero h){
        setDamage(7);
        applyEffects(h, this, damage);
        setArmor(5);
    }

    /**
     * Inflige des dégâts au hero
     * 
     * @param Hero h, un hero
     */
    public void morsure(Hero h){

        setDamage(11);
        applyEffects(h, this, damage);
    }

    /**
     * Ajoute de l'armure et applique le statut Force
     */
    public void gronder(){
        System.out.println(getName()+ " Ajoute 6 points d'armure et se donne le statue Force qui ajoute +3 d'attaque ");
        ajouterArmure(6);

        ForceStatus forceStatus = findStatus(ForceStatus.class);
        if (forceStatus == null) {
            forceStatus = new ForceStatus(3);
            addStatut(forceStatus);
        } else {
            forceStatus.incrementNumberOfApplications();
        }
    }

    /**
     * Obtient une phrase de description de l'intention du monstre en fonction de
     * son action
     * 
     * @return Une phrase décrivant l'intention du monstre
     */
    @Override
    public String intention() {
        String action = "";
        switch (tourActuel) {
            case 1:
                action = "Charge";
                break;
            case 2:
                randomValue = Math.random();
                if (randomValue >= 0 && randomValue < 0.45) {
                    action = "Gronder";
                } else if (randomValue >= 0.45 && randomValue < 0.75) {
                    action = "Charge";
                } else {
                    action = "Morsure";
                }
                break;
        }
        return "L'intention du monstre est d'effectuer " + action;
    }
}
