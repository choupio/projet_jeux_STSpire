package main.personnage.ListMonsters;

import main.Deck;
import main.Statut.Rituel;
import main.Statut.Status;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Cultiste extends Monstre {
    //ATTRIBUTS
    private int tourActuel;
    private int damage = 6;

    //CONSTRUCTEUR
    /**
     * construit un monstre de type Cultiste avec un nom et une santé spécifiés
     */
    public Cultiste(){
        super("Cultiste", 50);
        this.tourActuel = 1;
    }

    //GETTERS AND SETTERS 

    /**
     * récupère les dégâts infligés 
     * 
     * @return les dégâts infligés
     */
    public int getDamage() {
        return damage;
    }

    /**
     * modifie les dégâts infligés
     * 
     * @param damage la nouvelle valeur des dégâts infligés
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * récupère le tour actuel du monstre
     * 
     * @return le tour actuel du monstre
     */
    public int getTourActuel() {
        return tourActuel;
    }

    /**
     * modifie le tour actuel du monstre
     * 
     * @param tourActuel le nouveau tour actuel du monstre
     */
    public void setTourActuel(int tourActuel) {
        this.tourActuel = tourActuel;
    }

    //METHODE POUR RITUEL

    /**
     * trouve le statut de Rituel parmi les statuts du monstre
     * 
     * @return le statut de Rituel du monstre s'il existe, sinon null
     */
    public Rituel findRituelStatus(){
        for (Status s : getStatuts()){
            if (s instanceof Rituel){
                return (Rituel) s;
            }
        }
        return null;
    }

    /**
     * applique le rituel, ajoutant le statut de Rituel
     */
    public void rituel(){
        addStatut(new Rituel(3));
    }

    /**
     * joue le pattern du monstre envers le hero
     * 
     * @param h le hero
     * @param d le deck
     */
    @Override
    public void play(Hero h, Deck d){
        switch (tourActuel) {
            case 1:
                rituel();
                break;

            default:
                
                attaque(h);

                break;
        }
        tourActuel++;
    }

    /**
     * effectue une attaque contre le hero, en fonction du rituel si présent
     * 
     * @param h le hero
     */
    public void attaque(Hero h){
        Rituel rituel = findRituelStatus();

        int damage = 6; // Initial damage

        if (rituel != null) {
            damage += rituel.getNumberOfApplications();
        }

        setDamage(damage);
        applyEffects(h, this, damage);  
        rituel.decrementNumberOfApplications();     
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
        switch (tourActuel % 2) {
            case 1:
                action = "rituel";
                break;
            case 0:
                action = "attaque";
                break;
        }
        return "L'intention du monstre est d'effectuer " + action;
    }
}
