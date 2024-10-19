package main.TypesCard;

import main.Card;
import main.Deck;
import main.TeamofMonstres;
import main.Statut.Fragility;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Defense extends Card{

    //ATTRIBUTS
    private int armor; 
    
    //CONSTRUCTEUR
    /**
     * construit une carte de défense avec un nom, un coût et une armure spécifiés
     * 
     * @param cardName le nom de la carte
     * @param cost     le coût en mana de la carte
     * @param armor    l'armure fournie par la carte
     */
    public Defense(String cardName, int cost, int armor){
        super(cardName, cost);
        this.armor = armor;
    }

    /**
     * joue la carte en appliquant ses effets sur le héros spécifié
     * 
     * @param m la cible de la carte (non utilisée)
     * @param h le héros jouant la carte
     * @param d le deck 
     * @param c le cimetière 
     * @param b le deck de cartes bannies 
     * @param e l'équipe de monstres (non utilisée)
     */
    @Override
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        if (h.getMana() >= getCost()){
            h.setMana(Math.max(0, h.getMana() - getCost())); // Mana impossible a etre negative

            Fragility fragility = h.findStatus(Fragility.class);
            
            if (fragility != null){
                h.setArmor((int) (armor * 0.75));
            }else{
                h.setArmor(armor);
            }

            //après l'effet de la carte, il faut l'enlever de la main du joueur
            if (carteBanis.contains(getName())) {
                b.addBanish(this);
            } else {
                c.addCimetery(this);
            }
            h.removeCardFromHand(this);
            
        }else{
            System.out.println("Pas assez de mana pour jouer cette carte");
        }
    }
}
