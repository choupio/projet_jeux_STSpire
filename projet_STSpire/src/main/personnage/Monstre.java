package main.personnage;

import java.util.List;
import main.Deck;
import main.Statut.ForceStatus;
import main.Statut.Fragility;
import main.Statut.Status;
import main.Statut.VulnerableStatus;
import main.Statut.WeaknessStatus;

//Classe représentant les monstres que le héro va devoir combattre 

public abstract class Monstre extends Entitee {
    
    //ATTRIBUTS
    protected String name;
    protected int armor;
    private int totalForceBonus;

    // CONSTRUCTEUR
    /**
     * Constructeur de la classe Monstre 
     * 
     * @param name nom du monstre
     * @param maxLifePoint Points de vie maximal du monstre 
     * 
     */
    public Monstre(String name, int maxLifePoint) {
        super(maxLifePoint);
        this.name = name;
        this.armor = 0;
    }

    //GETTTERS AND SETTERS 

    /**
     * Retourne le nom du monstre
     *
     * @return Le nom du monstre
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Modifie le nom du monstre
     *
     * @param name le nouveau nom du monstre
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne la valeur de l'armure du monstre
     *
     * @return La valeur de l'armure du monstre
     * 
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Modifie la valeur de l'armure du monstre
     *
     * @param armor la nouvelle valeur de l'armure du monstre
     * 
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * retourne le bonus de force du monstre
     *
     * @return Le bonus de force du monstre
     * 
     */
    public int getTotalForceBonus() {
        return totalForceBonus;
    }

    /**
     * Modifie le bonus de force du monstre
     *
     * @param totalForceBonus le nouveau bonus de force du monstre
     * 
     */
    public void setTotalForceBonus(int totalForceBonus) {
        this.totalForceBonus = totalForceBonus;
    }
    

    // METHODES PROVENANT DE LA CLASSE MERE

    /**
     * Retourne vrai si les points de vie sont inferieurs ou egale à 0 sinon renvoie faux
     */
    @Override
    public boolean isDead(){
        return (currentLifePoint <= 0);
    }

    /**
     * Affiche les statuts du héro si il possède
     */
    public void printStatus() {
        if (!statuts.isEmpty()) {     
            System.out.println("Statuts actuels du : " +getName());
            printStatuts();
        }
    }

    /**
     * Calcul les dommages que le hero reçoit et actualise les points de vie de ce
     * dernier
     * Prend en compte dans le calcul le bouclier
     * 
     * @param damage degat que le héro va subir
     * 
     */
    @Override
    public void receivedDamage(int damage) {

        // Prendre en compte les points de bouclier
        if (getArmor() > 0) {
            if (getArmor() >= damage) {
                setArmor(getArmor() - damage);
                return;
            } else {
                damage -= getArmor();
                setArmor(0);
            }
        }
        setCurrentLifePoint(Math.max(0, getCurrentLifePoint() - damage)); //permet de remettre à 0 les points de vie si cela sont négatifs
    }

    //METHODES GERANT LES STATUTS D'UN MONSTRE

    /**
     * Retourne le statut vulérabilité du monstre
     * 
     * @return le statut vulérabilité du monstre
     */
    public VulnerableStatus getVulnerableStatus() {
        return findStatus(VulnerableStatus.class);
    }

    /**
     * Retourne le statut faiblesse du monstre
     * 
     * @return le statut faiblesse du monstre
     */
    public WeaknessStatus getWeaknessStatus() {
        return findStatus(WeaknessStatus.class);
    }

    /**
     * Retourne le statut fragile du monstre
     * 
     * @return le statut fragile du monstre
     */
    public Fragility getFragility() {
        return findStatus(Fragility.class);

    }

    /**
     * Obtient la durée de l'effet de faiblesse
     *
     * @return la durée de l'effet de fragilité, ou 0
     */
    public int getWeaknessDuration() {
        WeaknessStatus weaknessStatus = getWeaknessStatus();
        return (weaknessStatus != null) ? weaknessStatus.getDuration() : 0;
    }

    /**
     * Obtient la durée de l'effet de vulnérable
     *
     * @return la durée de l'effet de vulnérable, ou 0
     */
    public int getVulnerableDuration() {
        VulnerableStatus vulnerableStatus = getVulnerableStatus();
        return (vulnerableStatus != null) ? vulnerableStatus.getDuration() : 0;
    }

    /**
     * Obtient la durée de l'effet de fragilité 
     *
     * @return la durée de l'effet de fragilité, ou 0
     */
    public int getFragilityDuration() {
        Fragility fragilityStatus = getFragility();
        return (fragilityStatus != null) ? fragilityStatus.getDuration() : 0;
    }


    /**
     * Applique les effets du monstre sur le héros, en prenant en compte les ajustements de dégâts dus aux statuts
     *
     * @param h      le héros sur lequel appliquer les effets
     * @param m      le monstre qui applique les effets
     * @param damage les dégâts de base à appliquer
     */
    public void applyEffects(Hero h, Monstre m, int damage) {
        List<Status> statusList = m.getStatuts();

        int totalAdjustedDamage = damage;
        totalForceBonus = 0;

        for (Status status : statusList) {
            if (status instanceof VulnerableStatus) {
                totalAdjustedDamage = ((VulnerableStatus) status).adjustDamage(totalAdjustedDamage);
            } else if (status instanceof ForceStatus) {
                totalAdjustedDamage += ((ForceStatus) status).getAttackBonus() * ((ForceStatus) status).getNumberOfApplications();
            } else if (status instanceof WeaknessStatus) {
                totalAdjustedDamage = ((WeaknessStatus) status).adjustDamage(totalAdjustedDamage);
            }
        }

        totalAdjustedDamage += totalForceBonus;

        // Appliquer les dégâts ajustés totaux
        System.out.println(getName() + " utilise " + intention() + " et inflige " + totalAdjustedDamage + " points de dégâts au héro !");
        h.receivedDamage(totalAdjustedDamage);
    }

    //METHODES ABSTRAITES 

    
    /**
     * Méthode abstraite représentant l'action du monstre
     *
     * @param h le héros sur lequel le monstre effectue son action
     * @param d le deck du héro
     */
    public abstract void play(Hero h, Deck d);

    /**
     * Méthode abstraite retournant l'intention du monstre 
     *
     * @return Une chaîne de caractères décrivant l'intention du monstre
     */
    public abstract String intention();



    
}
