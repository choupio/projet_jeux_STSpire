package main.basicCard;

import main.Deck;
import main.TeamofMonstres;
import main.TypesCard.Defense;
import main.personnage.Hero;
import main.personnage.Monstre;

public class MemePasMal extends Defense{
    //CONSTRUCTEUR
    /**
     * Constructeur de la carte de Meme pas mal
     * Initialise les attributs de la carte avec les valeurs spécifiées
     */
    public MemePasMal(){
        super("Meme pas mal", 1, 8);
    }

    /**
     * Méthode pour jouer la carte de Meme Pas Mal
     * 
     * @param m Monstre cible
     * @param h Héros qui joue la carte
     * @param d Deck principal du jeu
     * @param c Cimetière des cartes
     * @param b Deck de banissement des cartes
     * @param e Équipe de monstres adverse
     */
    @Override
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        super.play(m,h,d,c,b,e);
        h.drawCard(d, c);
    }
}
