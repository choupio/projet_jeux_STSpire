package main;

import java.io.IOException;
import java.util.Scanner;

import librairies.StdDraw;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Lancer l'aventure");
            System.out.println("2. Partie Graphique");
            System.out.println("0. Quitter");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Room adventureRoom = new Room();
                    adventureRoom.startAdventure();
                    break;
                case 2:
                    try {
                        Jeu jeu = new Jeu();
                        jeu.initialDisplay();
                        StdDraw.show(); // StdDraw est utilise en mode buffer pour fluidifier l'affichage: utiliser
                                        // StdDraw.show() pour afficher ce qui est dans le buffer
                        while (!jeu.isOver()) {
                            jeu.update();
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'execution du jeu : " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    System.exit(0); // Quitter l'application
            }
            scanner.close();
        }
    }

}
