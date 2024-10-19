package main.Statut;

public class VulnerableStatus extends Status {
    //ATTRIBUTS
    private int duration;

    //CONSTRUCTEUR

    /**
     * Constructeur de la classe VulnerableStatus
     * 
     * @param duration la durée du statut
     */
    public VulnerableStatus(int duration) {
        super("Vulnérable");
        this.duration = duration;
    }

    //GETTERS AND SETTERS 

    /**
     * Retourne la durée du statut de vulnérabilité
     * 
     * @return La durée du statut
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Retourne la durée du statut de vulnérabilité
     * 
     * @param duration la nouvelle durée du statut
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // METHODE

    /**
     * Augmente la durée du statut de vulnérabilité
     * 
     * @param duration La durée à ajouter
     */
    public void increaseDuration(int duration) {
        this.duration += duration;
    }

    /**
     * Réduit la durée du statut de vulnérabilité et affiche un message si l'effet prend fin
     * 
     * @return true si la durée est toujours positive sinon false
     */
    public boolean reduceDuration() {
            duration--;

            if (duration == 0) {
                System.out.println("L'effet Vulnérable a pris fin.");
            }
            
        return duration > 0;
    }

    /**
     * Ajuste les dégâts en fonction de la vulnérabilité
     * 
     * @param damage Les dégâts à ajuster
     * @return Les dégâts ajustés arrondi à l'inférieur 
     */
    public int adjustDamage(int damage) {
        return (int) Math.floor(damage * 1.5); // Augmente les dégâts de 50% et arrondit à l'inférieur
    }
}
