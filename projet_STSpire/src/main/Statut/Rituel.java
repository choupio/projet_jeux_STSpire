package main.Statut;

import main.personnage.Monstre;

public class Rituel extends Status {
    // ATTRIBUTS
    private int numberOfApplications;

    // CONSTRUCTEUR
    public Rituel(int numberOfApplications) {
        super("Rituel");
        this.numberOfApplications = numberOfApplications;
    }

    // GETTERS AND SETTERS
    public int getNumberOfApplications() {
        return numberOfApplications;
    }

    // Ajouter une méthode pour augmenter le nombre d'applications
    public void incrementNumberOfApplications() {
        numberOfApplications++;
    }

    public void decrementNumberOfApplications(){
        numberOfApplications--;
        if (numberOfApplications < 0){
            numberOfApplications = 0;
        }
    }

    // Méthode spécifique pour le rituel du cultiste
    public void applyRitual(Monstre m) {
        // À la fin de son tour, le cultiste gagne un point de force par point de rituel
        int force = getNumberOfApplications();
        for (int i = 0; i < force; i++){
            incrementNumberOfApplications();
        }
    }
    
}
