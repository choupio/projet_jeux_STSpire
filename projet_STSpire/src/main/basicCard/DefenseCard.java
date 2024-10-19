package main.basicCard;
import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Defense;
import main.personnage.Hero;
import main.personnage.Monstre;

public class DefenseCard extends Defense {

    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de défense.
     * Initialise les attributs de la carte avec les valeurs spécifiées.
     */
    public DefenseCard(){
        super("Defense", 1, 5);
    }
    
    /**
     * Méthode pour jouer la carte de défense.
     * 
     * @param m Monstre cible (non utilisé ici)
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse (non utilisée ici)
     */
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        super.play(m, h, d, c, b, e);
    }
}
