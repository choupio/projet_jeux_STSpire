package main.TypesCard;


import main.Card;
import main.Deck;
import main.TeamofMonstres;
import main.personnage.Hero;
import main.personnage.Monstre;

public class Mana extends Card {
    //ATTRIBUTS 
    private int mana;

    // CONSTRUCTEUR
    /**
     * construit une carte de mana avec un nom, un coût et une quantité de mana spécifiés
     * 
     * @param cardName le nom de la carte
     * @param cost     le coût en mana de la carte
     * @param mana     la quantité de mana fournie par la carte
     */
    public Mana(String cardName, int cost, int mana) {
        super(cardName, cost);
        this.mana = mana;
    }

    //GETTERS AND SETTERS

    /**
     * récupère la quantité de mana fournie par la carte
     * 
     * @return la quantité de mana de la carte
     */
    public int getMana() {
        return mana;
    }

    /**
     * modifie la quantité de mana fournie par la carte
     * 
     * @param mana nouvelle quantité de mana de la carte
     */
    public void setMana(int mana) {
        this.mana = mana;
    }


    /**
     * joue la carte en appliquant ses effets sur le héros spécifié
     * 
     * @param m la cible de la carte (non utilisée)
     * @param h le héros jouant la carte
     * @param d le deck du joueur
     * @param c le cimetière du joueur
     * @param b le deck de cartes bannies du joueur
     * @param e l'équipe de monstres (non utilisée)
     */
    @Override
    public void play(Monstre m, Hero h, Deck d, Deck c, Deck b, TeamofMonstres e){
        if (h.getMana() >= getCost()){
            h.removeMana(getCost());
            h.addMana(mana);

            if (carteBanis.contains(getName())){
                b.addBanish(this);
            }else{
                c.addCimetery(this);
            }

            h.removeCardFromHand(this);
            }else{
                System.out.println("Pas assez de mana pour jouer cette carte");
            }
    
    }
}
