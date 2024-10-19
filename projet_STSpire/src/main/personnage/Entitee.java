package main.personnage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.Statut.ForceStatus;
import main.Statut.Fragility;
import main.Statut.Status;
import main.Statut.VulnerableStatus;
import main.Statut.WeaknessStatus;

//classe représentant les entitées tel que le héro ou les monstres 

public abstract class Entitee {
    
    //ATTRIBUTS


    protected int maxLifePoint;  
    protected int currentLifePoint;
    protected List<Status> statuts;

    // CONSTRUCTEUR


    /**
     * Constructeur de l'entité
     *
     * @param maxLifePoint Points de vie maximum de l'entité
     * 
     */
    public Entitee(int maxLifePoint) {
        this.maxLifePoint = maxLifePoint;
        this.currentLifePoint = maxLifePoint;
        this.statuts = new ArrayList<>();
    }

    //GETTERS AND SETTERS


    /**
     * Obtient la valeur des points de vie maximal de l'entité
     *
     * @return valeur des points de vie maximal
     * 
     */
    public int getMaxLifePoint() {
        return maxLifePoint;
    }
    
    /**
     * Définit la valeur des points de vie maximal de l'entité
     *
     * @param maxLifePoint nouvelle valeur pour les points de vie maximal 
     * 
     */
    public void setMaxLifePoint(int maxLifePoint) {
        this.maxLifePoint = maxLifePoint;
    }

    /**
     * retourne la valeur des points de vie actuel de l'entité
     *
     * @return valeur des points de vie actuel 
     * 
     */
    public int getCurrentLifePoint() {
        return currentLifePoint;
    }

    /**
     * Définit le nombre de points de vie actuel de l'entité.
     *
     * @param currentLifePoint nouvelle valeur des points de vie actuel
     * 
     */
    public void setCurrentLifePoint(int currentLifePoint) {
        this.currentLifePoint = currentLifePoint;
    }

    /**
     * Obtient la liste des statuts actuels de l'entité
     *
     * @return Liste des statuts de l'entité
     * 
     */
    public List<Status> getStatuts() {
        return statuts;
    }

    /**
     * Définit la liste des statuts de l'entité
     *
     * @param statuts Nouvelle liste des statuts
     * 
     */
    public void setStatuts(List<Status> statuts) {
        this.statuts = statuts;
    }

    //METHODES 

    /**
     * Ajoute un statut à l'entité s'il n'est pas déjà présent dans la liste
     *
     * @param status statut à ajouter 
     * 
     */
    public void addStatut(Status status) {
        if (!statusPresent(status)) {
            statuts.add(status);
        }
    }

    /**
     * Supprime un statut spécifique
     *
     * @param status Statut à supprimer
     * 
     */
    public void removeStatut(Status status) {
        statuts.remove(status);
    }

    /**
     * Obtient le statut spécifique s'il est présent parmi les statuts
     * 
     * @param statusClass Classe du statut recherché
     * @param <T>         Type du statut
     * @return Le statut s'il est présent, sinon null
     */
    public <T extends Status> T findStatus(Class<T> statusClass) {
        for (Status s : getStatuts()) {
            if (statusClass.isInstance(s)) {
                return statusClass.cast(s);
            }
        }
        return null;
    }


    /**
     * Vérifie si un statut est présent parmi la liste des statuts.
     *
     * @param status Statut à rechercher.
     * @return true si le statut est présent, sinon false
     * 
     */
    public boolean statusPresent(Status status){
        for (Status statusExistant : statuts) {
            if (statusExistant.getClass().equals(status.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Affiche les statuts actuels de l'entité si il en possède
     * 
     */
    public void printStatuts() {
        if (!statuts.isEmpty()){
            for (Status status : statuts) {
                if (status instanceof ForceStatus) {
                    ForceStatus forceStatus = findStatus(ForceStatus.class);
                    System.out.println("Force : " + forceStatus.toString());
                } else if (status instanceof WeaknessStatus) {
                    System.out.println("Faiblesse");
                } else if (status instanceof VulnerableStatus) {
                    System.out.println("Vulnérabilité");
                }else if (status instanceof Fragility){
                    System.out.println("Fragilité");
                }
            }
        }
        System.out.println();
    }

    
    /**
     * Vérifie et retire les statuts expirés du héros
     */
    public void checkAndRemoveExpiredStatus() {
        Iterator<Status> iterator = statuts.iterator();

        while (iterator.hasNext()) {
            Status status = iterator.next();

            // Vérifie si le statut est de type VulnerableStatus
            if (status instanceof VulnerableStatus) {
                if (!((VulnerableStatus) status).reduceDuration()) {
                    iterator.remove(); // Retire le statut s'il n'a plus de durée
                }
            }

            // Vérifie si le statut est de type WeaknessStatus
            if (status instanceof WeaknessStatus) {
                if (!((WeaknessStatus) status).reduceDuration()) {
                    // Retire le statut s'il n'a plus de durée
                    iterator.remove();
                }
            }

            if (status instanceof Fragility){
                if (!((Fragility) status).reduceDuration()) {
                    // Retire le statut s'il n'a plus de durée
                    iterator.remove();
                }
            }
        }
    }


    /**
     * Vérifie si l'entité est morte
     *
     * @return true si l'entité est morte, sinon false
     * 
     */
    public abstract boolean isDead();


    /**
     * Methode permettant de calculer les dommages reçu par l'entité
     * 
     * @param damage montant des dommages reçus
     * 
     */
    public abstract void receivedDamage(int damage);
}
