package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.personnage.Hero;
import main.personnage.Monstre;
import main.personnage.ListMonsters.Cultiste;
import main.personnage.ListMonsters.Machouilleur;
import main.personnage.ListMonsters.PetitSlimeAcide;
import main.personnage.ListMonsters.PetitSlimePiquant;
import main.personnage.ListMonsters.SlimeAcide;
import main.personnage.ListMonsters.SlimePiquant;

public class TeamofMonstres {
    //ATTRIBUTS 
    private List<Monstre> teamOfMonstres;
    private int numberOfMonsters; //permet de gérer le nombre de monstre qui apparait dans la room, tout en augmentant la difficulté  

    //CONSTRUCTEUR 
    /**
     * Construit une équipe de monstres avec une liste vide et initialise le nombre de monstres à 1
     */
    public TeamofMonstres(){
        teamOfMonstres = new ArrayList<>();
        this.numberOfMonsters = 1;
    }

    //GETTERS AND SETTERS

    /**
     * Obtient la liste des monstres de l'équipe
     *
     * @return la liste des monstres de l'équipe
     */
    public List<Monstre> getTeamOfMonstres() {
        return teamOfMonstres;
    }

    /**
     * Modifie la liste des monstres de l'équipe
     *
     * @param teamOfMonstres la nouvelle liste des monstres de l'équipe
     */
    public void setTeamOfMonstres(List<Monstre> teamOfMonstres) {
        this.teamOfMonstres = teamOfMonstres;
    }

    //METHODES

    /**
     * Obtient la taille de la liste de monstres
     * 
     * @return la taille de la liste de monstres
     */
    public int size() {
        return teamOfMonstres != null ? teamOfMonstres.size() : 0; //utilisation d'expression ternaire pour éviter une erreur Java
    }

    /**
     * 
     * @return Méthode qui incrémente nomberOfMonsters
     */
    public void incrementNumberOfMonsters() {
        numberOfMonsters = Math.min(numberOfMonsters + 1, 2);
    }

    /**
     * Permet d'ajouter un monstre dans la liste
     * 
     * @param Monstre m, un monstre qu'on souhaite ajouter
     */
    public void addMonster(Monstre m) {
        teamOfMonstres.add(m);
    }

    /**
     * @return Verifie si l'équipe de monstre est mort 
     */
    public boolean areDead() {
        for (Monstre monstre : teamOfMonstres) {
            if (monstre != null && !monstre.isDead()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtient le monstre à l'indice donné dans la liste
     * 
     * @param indice l'indice du monstre à récupérer
     * @return le monstre à l'indice donné, ou null si l'indice est invalide
     */
    public Monstre get(int indice) {
        if (teamOfMonstres != null && indice >= 0 && indice < teamOfMonstres.size()) {
            return teamOfMonstres.get(indice);
        } else {
            System.out.println("Indice invalide ou liste de monstres nulle.");
            return null;
        }
    }

    /**
     * Affiche tous les monstres présents dans l'équipe ainsi que leurs statuts.
     * Seuls les monstres encore en vie sont affichés.
     */
    public void showTeam() {
        if (teamOfMonstres.isEmpty()) {
            System.out.println("Aucun monstre dans l'équipe.");
        } else {
            System.out.println("Equipe de monstres à combattre : ");
            for (Monstre m : teamOfMonstres) {
                    System.out.println("Nom : " + m.getName() + ", Points de vie : " + m.getCurrentLifePoint()+ ", Point de bouclier : " + m.getArmor());
                    m.printStatus();
                }
            }
            System.out.println();
    }

    /**
     * @return Vérifie et retire les statuts expirés de tous les monstres de
     *         l'équipe.
     */
    public void checkAndRemoveExpiredStatus() {
        for (Monstre monstre : teamOfMonstres) {
            monstre.checkAndRemoveExpiredStatus();
        }
    }

    /**
     * @return Renvoie les intentions des prochains mouvements de l'équipe de
     *         monstre
     */
    public void displayIntentions() {
        System.out.println("Intentions des monstres : ");
        for (Monstre monstre : teamOfMonstres) {
            System.out.println(monstre.getName() + " : " + monstre.intention());
        }
    }

    /**
     * L'équipe de monstre attaque le héro
     * 
     * @param hero, le héro de notre jeu
     * @param d,    le deck du joueur
     */
    public void playTurn(Hero hero, Deck d) {
        for (Monstre monstre : teamOfMonstres) {
            if (!monstre.isDead()) {
                monstre.play(hero, d);
            }
        }
    }

    /**
     * Permet de créer une équipe de monstre aléatoirement à partir d'une liste des monstres
     * @param list<Monstre> monstre, une liste de monstre 
     * @return une équipe de monstre choisi aléatoirement
     */
    public TeamofMonstres teamOfMonstreInitial() {
        
        Random random = new Random();
        TeamofMonstres t = new TeamofMonstres();

        for(int i = 0; i < this.numberOfMonsters; ++i) {
            int randomValue = random.nextInt(6);
            if (randomValue == 0) {
                t.addMonster(new PetitSlimePiquant());
            } else if (randomValue == 1) {
                t.addMonster(new Machouilleur());
            }else if (randomValue == 2){
                t.addMonster(new PetitSlimeAcide());
            }else if (randomValue == 3){
                t.addMonster(new SlimeAcide());
            }else if (randomValue == 4){
                t.addMonster(new SlimePiquant());
            }else{
                t.addMonster(new Cultiste());
            }
        }
        return t;    
    }

    /**
     * Créer l'équipe de monstre de la salle du boss
     * @return Donne notre cas, la salle du boss est composée de 3 machouilleurs 
     */

     public TeamofMonstres teamOfMonstresBoss(){
        TeamofMonstres bossTeam = new TeamofMonstres();
        bossTeam.addMonster(new Machouilleur());
        bossTeam.addMonster(new Machouilleur());
        bossTeam.addMonster(new Machouilleur());

        return bossTeam;
     }
}
