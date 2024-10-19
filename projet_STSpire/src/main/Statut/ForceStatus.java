package main.Statut;

public class ForceStatus extends Status {

    //ATTRIBUTS
    private int attackBonus;
    private int numberOfApplications;
    

    //CONSTRUCTEUR
    /**
     * Constructeur de la classe ForceStatus
     * 
     * @param attackBonus le bonus d'attaque associé à ce statut
     */
    public ForceStatus(int attackBonus){
        super("Force");
        this.attackBonus = attackBonus;
        this.numberOfApplications = 1;
    }

    //GETTERS AND SETTERS

    /**
     * Retourne le bonus d'attaque
     * 
     * @return le bonus d'attaque
     */
    public int getAttackBonus() {
        return attackBonus;
    }
    
    /**
     * Obtient le nombre d'applications 
     * 
     * @return le nombre d'applications
     */
    public int getNumberOfApplications() {
        return numberOfApplications;
    }

    /**
     * Incrémente le nombre d'applications de ce statut.
     */
    public void incrementNumberOfApplications() {
        numberOfApplications++;
    }

    //METHODE 

    /**
     * réimplémente la méthode toString pour afficher le statut de force
     * 
     * @return une chaîne de caractères représentant le statut de force avec son bonus d'attaque
     */
    @Override
    public String toString() {
        return "ForceStatus: Bonus d'attaque = " + getAttackBonus();
    }
}
