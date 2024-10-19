package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import main.basicCard.DefenseCard;
import main.basicCard.Enchainement;
import main.basicCard.EpeeBoomerang;
import main.basicCard.Frappe;
import main.basicCard.FrappeDouble;
import main.basicCard.FrappeDuPommeau;
import main.basicCard.Heurt;
import main.basicCard.Manchette;
import main.basicCard.MemePasMal;
import main.basicCard.Plaquage;
import main.basicCard.VagueDeFer;
import main.notCommonCard.Desarmement;
import main.notCommonCard.Enflammer;
import main.notCommonCard.Hemokinesie;
import main.notCommonCard.OndeDeChoc;
import main.notCommonCard.Saigne;
import main.notCommonCard.Tenacite;
import main.notCommonCard.Uppercut;
import main.notCommonCard.VoirRouge;
import main.notCommonCard.VoleeDeCoups;
import main.personnage.Hero;
import main.personnage.Monstre;
import main.rareCard.Gourdin;
import main.rareCard.Invincible;
import main.rareCard.Offrande;

public class Room {
    private Hero hero; 
    private TeamofMonstres eqMonstres;
    private Deck deck;
    private Deck cimetery; 
    private Deck banish;
    private int currentRoom;
    private static final int NOMBRE_SALLES = 15;
    private Scanner scanner;

    //CONSTRUCTEUR
    /**
     * construit une instance de Room avec un héros, une équipe de monstres, un
     * deck, un cimetière, un bannissement et un scanner
     */
    public Room(){
        this.scanner = new Scanner(System.in);
        this.hero = new Hero(Deck.initialDeck());
        this.eqMonstres = new TeamofMonstres();
        this.deck = Deck.initialDeck();
        this.cimetery = new Deck();
        this.banish = new Deck();
        this.scanner = new Scanner(System.in);
    }
    //METHODES 
    
    /**
     * Gestion de la mort du héro pour recommencer une partie
     */
    public void death(){
        System.out.println("Le héros est mort. Voulez-vous recommencer le jeu ? (Oui/Non)");
        String restartChoice = scanner.nextLine();

        if (restartChoice.equalsIgnoreCase("Oui")) {
        // Réinitialiser le héros, les monstres, le deck, etc
        hero = new Hero(Deck.initialDeck());
        eqMonstres = new TeamofMonstres();
        deck = Deck.initialDeck();
        cimetery = new Deck();
        currentRoom = 0;
        } else if (restartChoice.equalsIgnoreCase("Non")) {
            System.out.println("Merci d'avoir joué ! Au revoir !");
            scanner.close();
            System.exit(0); // Termine l'exécution du programme
        } else {
            System.out.println("Choix invalide. Veuillez entrer 'Oui' ou 'Non'.");
            death(); // Redemander le choix en cas d'entrée invalide
        }
    }


    /**
     * Méthode pour lancer l'aventure
     */
    public void startAdventure(){

        while(currentRoom < NOMBRE_SALLES){
            char roomType = getRoomType();
            handleRoom(roomType);
            currentRoom++;

            if (hero.isDead()){death();}
        }

        System.out.println("Félicatation, vous avez terminé le jeu");
        scanner.close();
    }

    /**
     * Méthode pour savoir dans quelle pièce nous nous trouvons
     */
    public char getRoomType(){
        char[] rooms = {'C', 'C', 'R', 'C', 'C', 'C', 'R', 'C', 'C', 'C', 'R', 'C', 'C', 'R', 'B'};
        return rooms[currentRoom];
    }

    /**
     * Méthode pour gérer le type de salle actuelle
     */
    public void handleRoom(char roomType){
        switch (roomType) {
            case 'C':
                doCombat();
                break;
            case 'R':
                doRest();
                break;
            case 'B':
                doFinalBoss();
                break;
        }
        //on ajoute un monstre dans la limite de deux monstres
        if (currentRoom >= 1){
            eqMonstres.incrementNumberOfMonsters();
        }

        // Attendre l'entrée de l'utilisateur pour passer à la salle suivante
        System.out.println("Appuyez sur Entrée pour passer à la salle suivante !");
        scanner.nextLine();
    }


    /**
     * Implémentation d'un combat entre le héro et une équipe de monstre
     */
    public void doCombat() {
        System.out.println("Vous entrez dans une salle de combat !");
        eqMonstres = eqMonstres.teamOfMonstreInitial();

        // Initialisation du combat
        while (!hero.isDead() && !eqMonstres.areDead()) {
            // Affichage des informations de combat
            System.out.println("----------------------");
            System.out.println("Héros, à toi de jouer");
            System.out.println("Points de vie du héros : " + hero.getCurrentLifePoint() + ", Points de défense du héros : " + hero.getArmor());
            System.out.println();
            eqMonstres.showTeam();

            //refill le deck
            cimetery.RefillDeck(deck);

            // Le héros pioche 5 cartes
            hero.drawInitialCard(deck, cimetery);

            // Affichage des intentions des monstres
            eqMonstres.displayIntentions();

            eqMonstres.checkAndRemoveExpiredStatus();

            // Boucle pour permettre au héros de jouer plusieurs cartes
            while (true) {
                System.out.println("----------------------");
                System.out.println("Main du héros : " + hero.getPlayerHand());
                System.out.println("Énergie actuelle : " + hero.getMana());
                hero.printStatus();
                System.out.println("1. Jouer une carte");
                System.out.println("2. Finir le tour");

                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    // Le héros joue une carte
                    System.out.println("Choisissez une carte à jouer (1-" + hero.getPlayerHand().size() + "): ");
                    int cardIndex = Integer.parseInt(scanner.nextLine());

                    if (cardIndex >= 1 && cardIndex <= hero.getPlayerHand().size()) {
                        Card selectedCard = hero.getPlayerHand().get(cardIndex - 1);

                        if (selectedCard.getCost() <= hero.getMana()) {
                            if (selectedCard instanceof EpeeBoomerang || selectedCard instanceof OndeDeChoc || selectedCard instanceof Saigne || selectedCard instanceof Offrande) {
                                selectedCard.play(null, hero, deck, cimetery, banish, eqMonstres);
                                eqMonstres.showTeam();
                            }else{
                                System.out.println("Sur quel monstre voulez-vous jouer la carte? (1-"+ eqMonstres.getTeamOfMonstres().size() + ", 0: Héros): ");
                                int monsterChoice = Integer.parseInt(scanner.nextLine());
                            

                                if (monsterChoice >= 0 && monsterChoice <= eqMonstres.getTeamOfMonstres().size()) {
                                    if (monsterChoice == 0) {
                                        selectedCard.play(null, hero, deck, cimetery, banish, eqMonstres);
                                        System.out.println("Point de vie du héros après avoir joué la carte : "+ hero.getCurrentLifePoint() + " Points de défense : "+hero.getArmor());
                                    } else {
                                       Monstre targetMonster = eqMonstres.getTeamOfMonstres().get(monsterChoice - 1);
                                        selectedCard.play(targetMonster, hero, deck, cimetery, banish, eqMonstres);
                                        eqMonstres.showTeam();
                                    }
                                } else {
                                    System.out.println("Sélection invalide pour le monstre");
                                }
                        }} else {
                            System.out.println("Pas assez de mana pour jouer cette carte");
                        }
                    } else {
                        System.out.println("Choix invalide");
                    }
                } else if (choice == 2) {
                    // Finir le tour du héros
                    break;
                } else {
                    System.out.println("Choix invalide");
                }
            }

            // Les cartes de la main du héros vont dans la défausse
            cimetery.addAllToCimetery(hero.getPlayerHand());
            hero.getPlayerHand().clear();
            hero.resetMana();

            hero.checkAndRemoveExpiredStatus();

            // Les monstres attaquent
            eqMonstres.playTurn(hero, cimetery);


        }
        
        
        cimetery.refillDeckEndOfFightFromCimetery(deck);
        banish.refillDeckEndOfFightFromBanish(deck);
        if (!hero.isDead()) {
            deck.addCard(chooseReward());
        }
    }
 

    /**
     * Implémentation de la récupération du héro dans la salle repos
     */
    public void doRest(){
        System.out.println("Vous entrez dans une salle de repos...Enfin...");
        int gainedHealth = (int) (0.3 * hero.getMaxLifePoint());
        hero.setCurrentLifePoint(hero.getCurrentLifePoint() + gainedHealth);
        if (hero.getCurrentLifePoint() > hero.getMaxLifePoint()){hero.setCurrentLifePoint(hero.getMaxLifePoint());}
        System.out.println("Le héro se repose et regagne "+ gainedHealth + " points de vie");
        
    }

    /**
     * Implémentation d'un combat entre le héro et le boss final qui est 3 machouilleurs dans notre cas 
     * Copie de doCombat() avec modification uniquement sur l'équipe de monstre 
     */
    public void doFinalBoss(){
        System.out.println("Vous entrez dans la salle du boss !!!");
        eqMonstres = eqMonstres.teamOfMonstresBoss();

        // Initialisation du combat
        while (!hero.isDead() && !eqMonstres.areDead()) {
            // Affichage des informations de combat
            System.out.println("----------------------");
            System.out.println("Héros, à toi de jouer");
            System.out.println("Points de vie du héros : " + hero.getCurrentLifePoint() + ", Points de défense du héros : " + hero.getArmor());
            System.out.println();
            eqMonstres.showTeam();

            // refill le deck
            cimetery.RefillDeck(deck);

            // Le héros pioche 5 cartes
            hero.drawInitialCard(deck, cimetery);

            // Affichage des intentions des monstres
            eqMonstres.displayIntentions();

            eqMonstres.checkAndRemoveExpiredStatus();

            // Boucle pour permettre au héros de jouer plusieurs cartes
            while (true) {
                System.out.println("----------------------");
                System.out.println("Main du héros : " + hero.getPlayerHand());
                System.out.println("Énergie actuelle : " + hero.getMana());
                hero.printStatus();
                System.out.println("1. Jouer une carte");
                System.out.println("2. Finir le tour");

                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    // Le héros joue une carte
                    System.out.println("Choisissez une carte à jouer (1-" + hero.getPlayerHand().size() + "): ");
                    int cardIndex = Integer.parseInt(scanner.nextLine());

                    if (cardIndex >= 1 && cardIndex <= hero.getPlayerHand().size()) {
                        Card selectedCard = hero.getPlayerHand().get(cardIndex - 1);

                        if (selectedCard.getCost() <= hero.getMana()) {
                            if (selectedCard instanceof EpeeBoomerang || selectedCard instanceof OndeDeChoc
                                    || selectedCard instanceof Saigne || selectedCard instanceof Offrande) {
                                selectedCard.play(null, hero, deck, cimetery, banish, eqMonstres);
                                eqMonstres.showTeam();
                            } else {
                                System.out.println("Sur quel monstre voulez-vous jouer la carte? (1-" + eqMonstres.getTeamOfMonstres().size() + ", 0: Héros): ");
                                int monsterChoice = Integer.parseInt(scanner.nextLine());

                                if (monsterChoice >= 0 && monsterChoice <= eqMonstres.getTeamOfMonstres().size()) {
                                    if (monsterChoice == 0) {
                                        selectedCard.play(null, hero, deck, cimetery, banish, eqMonstres);
                                        System.out.println("Point de vie du héros après avoir joué la carte : "+ hero.getCurrentLifePoint() + " Points de défense : " + hero.getArmor());
                                    } else {
                                        Monstre targetMonster = eqMonstres.getTeamOfMonstres().get(monsterChoice - 1);
                                        selectedCard.play(targetMonster, hero, deck, cimetery, banish, eqMonstres);
                                        eqMonstres.showTeam();
                                    }
                                } else {
                                    System.out.println("Sélection invalide pour le monstre");
                                }
                            }
                        } else {
                            System.out.println("Pas assez de mana pour jouer cette carte");
                        }
                    } else {
                        System.out.println("Choix invalide");
                    }
                } else if (choice == 2) {
                    // Finir le tour du héros
                    break;
                } else {
                    System.out.println("Choix invalide");
                }
            }

            // Les cartes de la main du héros vont dans la défausse
            cimetery.addAllToCimetery(hero.getPlayerHand());
            hero.getPlayerHand().clear();
            hero.resetMana();

            hero.checkAndRemoveExpiredStatus();

            // Les monstres attaquent
            eqMonstres.playTurn(hero, cimetery);

        }

        cimetery.refillDeckEndOfFightFromCimetery(deck);
        banish.refillDeckEndOfFightFromBanish(deck);
        if (!hero.isDead()){
            deck.addCard(chooseReward());
        }
    }

    /* 
     * Permet de héro de choisir une carte parmis 3, il a la possibilité d'en prendre 1 ou 0
    */
    public Card chooseReward() {
        Random random = new Random();
        List<Card> commonListofCards = Arrays.asList(new Frappe(), new DefenseCard(), new Heurt(), new Enchainement(), new FrappeDouble(),
                new FrappeDuPommeau(), new MemePasMal(), new VagueDeFer(), new Plaquage(), new Manchette(), new EpeeBoomerang());

        List<Card> nonCommonListofCards = Arrays.asList(new Hemokinesie(), new Enflammer(), new Saigne(), new Tenacite(), new VoleeDeCoups(), new Uppercut(),
                new VoirRouge(), new Desarmement(), new OndeDeChoc());
                
        List<Card> rareListofCards = Arrays.asList(new Gourdin(), new Invincible(), new Offrande());

        List<Card> options = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            double randomValueTypeCard = Math.random();
            if (randomValueTypeCard >= 0 && randomValueTypeCard < 0.60) {
                options.add(commonListofCards.get(random.nextInt(commonListofCards.size())));
            } else if (randomValueTypeCard >= 0.60 && randomValueTypeCard < 0.97) {
                options.add(nonCommonListofCards.get(random.nextInt(nonCommonListofCards.size())));
            } else {
                options.add(rareListofCards.get(random.nextInt(rareListofCards.size())));
            }
        }

        System.out.println("Choisissez une carte parmi les options suivantes :");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i).getName());
        }
        System.out.println("0. Aucune carte");

        int choice = -1;
        while (choice < 0 || choice > options.size()) {
            System.out.print("Entrez le numéro de la carte choisie (0 pour aucune) : ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next();
            }
        }

        if (choice >= 1) {
            Card chosenCard = options.get(choice - 1);
            System.out.println("Vous avez choisi la carte : " + chosenCard.getName());
            return chosenCard;
        } else {
            System.out.println("Aucune carte choisie.");
            return null;
        }        
    }
}
