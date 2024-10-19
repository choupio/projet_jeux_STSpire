package main.TypesCard;

import main.Card;
import main.Deck;
import main.TeamofMonstres;
import main.personnage.Hero;
import main.personnage.Monstre;

public class StatusCard extends Card {

    // ATTRIBUTS
    private StatusCard status; // Utilisation d'une classe de statut générique

    // CONSTRUCTEUR
    /**
     * construit une carte de statut avec un nom et un coût spécifiés
     * 
     * @param cardName le nom de la carte
     * @param cost     le coût en mana de la carte
     */
    public StatusCard(String cardName, int cost) {
        super(cardName, cost);
    }

    //GETTERS AND SETTERS 

    /**
     * récupère la carte de statut
     * 
     * @return la carte de statut
     */
    public StatusCard getStatus() {
        return status;
    }

    /**
     * joue la carte en appliquant ses effets sur le monstre et le héros spécifiés
     * 
     * @param m la cible de la carte (non utilisée)
     * @param h le héros jouant la carte
     * @param d le deck du joueur
     * @param c le cimetière du joueur
     * @param b le deck de cartes bannies du joueur
     * @param e l'équipe de monstres (non utilisée)
     */
    @Override
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e) {
        if (h.getMana() >= getCost()) {

            // Réduire la mana du héros
            h.setMana(Math.max(0, h.getMana() - getCost())); // Mana ne peut pas être négatif

            // Après l'effet de la carte, il faut l'enlever de la main du joueur
            if (carteBanis.contains(getName())) {
                b.addBanish(this);
            } else {
                c.addCimetery(this);
            }
            h.removeCardFromHand(this);

        } else {
            System.out.println("Pas assez de mana pour jouer cette carte");
        }
    }
}
