/** package principal */
package main;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import librairies.StdDraw;
import main.personnage.Entitee;
import main.personnage.Hero;
import main.personnage.Monstre;
import main.personnage.ListMonsters.Cultiste;
import main.personnage.ListMonsters.Machouilleur;
import main.personnage.ListMonsters.PetitSlimeAcide;
import main.personnage.ListMonsters.PetitSlimePiquant;
import main.personnage.ListMonsters.SlimeAcide;
import main.personnage.ListMonsters.SlimePiquant;
import ressources.Affichage;
import ressources.AssociationTouches;
import ressources.Config;

public class Jeu {

	private Hero hero;
	private List<Card> deckDuJoueur;
	private TeamofMonstres equipemonstre;
	private Room roomFight;

	/**
	 * Constructeur jeu permettant d'initialiser le héro, le deckdujoueur, la room
	 * ainsi que l'equipe de monstres
	 */
	public Jeu() throws Exception {

		this.hero = new Hero(Deck.initialDeck());
		this.deckDuJoueur = new ArrayList<>();
		this.equipemonstre = new TeamofMonstres();
		this.roomFight = new Room();
	}

	/**
	 * @return faux si le combat est terminé
	 */
	public boolean isOver() {
		return false;
	}

	/**
	 * Methode permettant d'afficher les monstres et les boucliers associés
	 * Ainsi que le hero avec sa mana, son bouclier, son énergie, sa pioche, sa main
	 * ainsi que les statuts
	 */
	public void display() {
		StdDraw.clear();
		// Affichage du fond
		String pathBackground = "pictures" + File.separator + "background.jpg";
		Affichage.image(0, Config.X_MAX, 0, Config.Y_MAX, pathBackground);

		String pathHeros = "pictures" + File.separator + "Ironclad.png";
		double heroXMin = Config.X_MAX * 0.2 - 183;
		double heroXMax = Config.X_MAX * 0.2 + 183;
		double heroYMin = Config.Y_MAX * 0.5 - 130;
		double heroYMax = Config.Y_MAX * 0.5 + 130;

		char roomType = roomFight.getRoomType();
		TeamofMonstres eqip = null;

		if (roomType == 'C') {
			eqip = equipemonstre.teamOfMonstreInitial();
		} else if (roomType == 'B') {
			eqip = equipemonstre.teamOfMonstresBoss();
		}

		for (int i = 0; i < eqip.size(); i++) {
			Monstre monster = eqip.get(i);
			AfficherMonstre(monster);
		}

		// Afficher l'image du héros
		Affichage.image(heroXMin, heroXMax, heroYMin, heroYMax, pathHeros);
		// Afficher la barre de vie en dessous du héros
		dessinerBarreDeVie(heroXMin, heroXMax, heroYMin - 20, heroYMin, hero.getCurrentLifePoint(),
				hero.getMaxLifePoint(), Color.RED, hero);
		// dessinerBarreDeVie(Config.X_MAX*0.2 + 50 , Config.X_MAX*0.8 + 60,
		// Config.Y_MAX*0.5 -10 , Config.Y_MAX*0.5, machouilleur.getCurrentLifePoint(),
		// machouilleur.getMaxLifePoint(), Color.RED, machouilleur);

		String pioche = "pictures" + File.separator + "pioche.png";
		// Ajustez les coordonnées et la taille pour afficher à droite de l'image
		// d'énergie
		double imageXPioche = heroXMax - 350; // Ajustez la position horizontale de l'image de pioche
		double imageYPioche = heroYMin - 230; // Ajustez la position verticale de l'image de pioche
		double imageSizePioche = 80; // Ajustez la taille de l'image de pioche
		Affichage.image(imageXPioche, imageXPioche + imageSizePioche, imageYPioche, imageYPioche + imageSizePioche,
				pioche);
		// Ajustez les coordonnées pour afficher le nombre de cartes dans la pioche à
		// côté de l'image de pioche
		double nombreXPioche = imageXPioche + imageSizePioche - 20; // Ajustez la position horizontale du nombre de
																	// cartes dans la pioche
		double nombreYPioche = imageYPioche + imageSizePioche / 9.5; // Ajustez la position verticale du nombre de
																		// cartes dans la pioche au centre de l'image de
																		// pioche
		// Afficher uniquement le nombre de cartes dans la pioche
		Affichage.texteGauche(nombreXPioche, nombreYPioche, Integer.toString(hero.getHandSize()));

		String energie = "pictures" + File.separator + "Cartes" + File.separator + "energie.png";
		// Ajustez les coordonnées pour afficher à droite du héros
		double imageX = heroXMax - 250; // Ajustez la position horizontale de l'image
		double imageY = heroYMin - 250; // Ajustez la position verticale de l'image
		double imagesize = 70; // pour la taille de l'image
		Affichage.image(imageX, imageX + imagesize, imageY, imageY + imagesize, energie);
		// Ajustez les coordonnées pour afficher le texte à côté de l'image
		double texteX = imageX + imagesize - 38; // Ajustez la position horizontale du texte
		double texteY = imageY + imagesize / 9.5; // Ajustez la position verticale du texte au centre de l'image
		Affichage.texteGauche(texteX, texteY, Integer.toString(hero.getMana()));

		// Affichage de l'énergie et le nombre de carte de la pioche, de la défausse et
		// en l'exil
		Affichage.texteDroite(Config.X_MAX, Config.Y_MAX - 20, "Defausse : 0");
		Affichage.texteDroite(Config.X_MAX, Config.Y_MAX - 45, "Exil : 0");

		affichageMain(); // appel la méthode pour afficher la main

		StdDraw.show(); // montre a l'ecran les changements demandés
	}

	/**
	 * @param monstre, un monstre
	 *                 affiche le monstre pris en paramètre de la méthode
	 */
	public void AfficherMonstre(Monstre monstre) {

		if (monstre instanceof Machouilleur) {
			Machouilleur machouilleur = (Machouilleur) monstre;
			// Affichage du machouilleur
			String mach = "pictures" + File.separator + "monstres" + File.separator + "jaw-worm-pretty.png";
			Affichage.image(Config.X_MAX * 0.7 - 183, Config.X_MAX * 0.7+ 183, Config.Y_MAX * 0.48 - 130,
					Config.Y_MAX * 0.48 + 130, mach);
			Affichage.texteCentre(1250, 325,
					"PV : " + machouilleur.getCurrentLifePoint() + "/" + machouilleur.getMaxLifePoint());

			// Affichage de blocage et de force pour le machouilleur
			if (machouilleur.getArmor() > 0) {
				String Armure = "pictures" + File.separator + "intentions" + File.separator + "Defend.png";
				// Ajustez les coordonnées et la taille pour réduire l'icône d'armure
				double imageSizeArmure = 40; // Ajustez la taille de l'icône d'armure
				double imageXArmure = Config.X_MAX * 0.8 - 90; // Ajustez la position horizontale de l'icône d'armure
				double imageYArmure = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'icône d'armure
				Affichage.image(imageXArmure, imageXArmure + imageSizeArmure, imageYArmure,
						imageYArmure + imageSizeArmure, Armure);
				// Ajustez les coordonnées pour afficher le texte "Armure: X" au bord de l'icône
				double texteXArmure = imageXArmure + imageSizeArmure - 5; // Ajustez la position horizontale du texte au
																			// bord de l'icône
				double texteYArmure = imageYArmure + imageSizeArmure / 6; // Centrez le texte verticalement par rapport
																			// à l'icône
				Affichage.texteCentre(texteXArmure, texteYArmure, Integer.toString(machouilleur.getArmor()));
			}

			if (machouilleur.getVulnerableDuration() > 0) {

				String vulnerable = "pictures" + File.separator + "statuts" + File.separator + "Icon_Vulnerable.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeVulnerable = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXVulnerable = Config.X_MAX * 0.8 + 90; // Ajustez la position horizontale de l'image vers la
																	// droite
				double imageYVulnerable = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'image
				// Afficher l'image de vulnérabilité avec la nouvelle taille
				Affichage.image(imageXVulnerable, imageXVulnerable + imageSizeVulnerable, imageYVulnerable,
						imageYVulnerable + imageSizeVulnerable, vulnerable);
				// Ajuster les coordonnées pour afficher le texte "Vulnerable for: X" au bord de
				// l'image
				double texteXVulnerable = imageXVulnerable + imageSizeVulnerable + 5; // Ajustez la position horizontale
																						// du texte au bord de l'image
				double texteYVulnerable = imageYVulnerable + imageSizeVulnerable / 2; // Centrez le texte verticalement
																						// par rapport à l'image
				Affichage.texteCentre(texteXVulnerable, texteYVulnerable,
						Integer.toString(machouilleur.getVulnerableDuration()));
			}
			if (machouilleur.getWeaknessDuration() > 0) {

				String weak = "pictures" + File.separator + "statuts" + File.separator + "Icon_Weak.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeWeak = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXWeak = Config.X_MAX * 0.8 - 135; // Ajustez la position horizontale de l'image vers la
																// gauche
				double imageYWeak = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Weak" avec la nouvelle taille
				Affichage.image(imageXWeak, imageXWeak + imageSizeWeak, imageYWeak, imageYWeak + imageSizeWeak, weak);
				// Ajuster les coordonnées pour afficher le texte "Faible de: X" au bord de
				// l'image
				double texteXWeak = imageXWeak - 5; // Ajustez la position horizontale du texte au bord de l'image
				double texteYWeak = imageYWeak + imageSizeWeak / 2; // Centrez le texte verticalement par rapport à
																	// l'image
				Affichage.texteCentre(texteXWeak, texteYWeak, Integer.toString(machouilleur.getWeaknessDuration()));
			}

			if (machouilleur.getTotalForceBonus() > 0) {

				String strength = "pictures" + File.separator + "statuts" + File.separator + "Strength.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeStrength = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXStrength = Config.X_MAX * 0.8 + 2; // Ajustez la position horizontale de l'image vers la
																// droite
				double imageYStrength = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Strength" avec la nouvelle taille
				Affichage.image(imageXStrength, imageXStrength + imageSizeStrength, imageYStrength,
						imageYStrength + imageSizeStrength, strength);
				// Ajuster les coordonnées pour afficher le texte "Force: X" au bord de l'image
				double texteXStrength = imageXStrength + imageSizeStrength + 5; // Ajustez la position horizontale du
																				// texte au bord de l'image
				double texteYStrength = imageYStrength + imageSizeStrength / 2; // Centrez le texte verticalement par
																				// rapport à l'image
				Affichage.texteCentre(texteXStrength, texteYStrength,
						Integer.toString(machouilleur.getTotalForceBonus()));
			}
		}

		if (monstre instanceof Cultiste) {
			Cultiste cultiste = (Cultiste) monstre;
			// Affichage du machouilleur
			String mach = "pictures" + File.separator + "monstres" + File.separator + "Cultist-pretty.png";
			Affichage.image(Config.X_MAX * 0.8 - 183, Config.X_MAX * 0.8 + 183, Config.Y_MAX * 0.5 - 130,
					Config.Y_MAX * 0.5 + 130, mach);
			Affichage.texteCentre(1450, 325,
					"PV : " + cultiste.getCurrentLifePoint() + "/" + cultiste.getMaxLifePoint());

			// Affichage de blocage et de force pour le machouilleur
			if (cultiste.getArmor() > 0) {
				String Armure = "pictures" + File.separator + "intentions" + File.separator + "Defend.png";
				// Ajustez les coordonnées et la taille pour réduire l'icône d'armure
				double imageSizeArmure = 40; // Ajustez la taille de l'icône d'armure
				double imageXArmure = Config.X_MAX * 0.8 - 90; // Ajustez la position horizontale de l'icône d'armure
				double imageYArmure = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'icône d'armure
				Affichage.image(imageXArmure, imageXArmure + imageSizeArmure, imageYArmure,
						imageYArmure + imageSizeArmure, Armure);
				// Ajustez les coordonnées pour afficher le texte "Armure: X" au bord de l'icône
				double texteXArmure = imageXArmure + imageSizeArmure - 5; // Ajustez la position horizontale du texte au
																			// bord de l'icône
				double texteYArmure = imageYArmure + imageSizeArmure / 6; // Centrez le texte verticalement par rapport
																			// à l'icône
				Affichage.texteCentre(texteXArmure, texteYArmure, Integer.toString(cultiste.getArmor()));
			}

			if (cultiste.getVulnerableDuration() > 0) {

				String vulnerable = "pictures" + File.separator + "statuts" + File.separator + "Icon_Vulnerable.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeVulnerable = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXVulnerable = Config.X_MAX * 0.8 + 90; // Ajustez la position horizontale de l'image vers la
																	// droite
				double imageYVulnerable = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'image
				// Afficher l'image de vulnérabilité avec la nouvelle taille
				Affichage.image(imageXVulnerable, imageXVulnerable + imageSizeVulnerable, imageYVulnerable,
						imageYVulnerable + imageSizeVulnerable, vulnerable);
				// Ajuster les coordonnées pour afficher le texte "Vulnerable for: X" au bord de
				// l'image
				double texteXVulnerable = imageXVulnerable + imageSizeVulnerable + 5; // Ajustez la position horizontale
																						// du texte au bord de l'image
				double texteYVulnerable = imageYVulnerable + imageSizeVulnerable / 2; // Centrez le texte verticalement
																						// par rapport à l'image
				Affichage.texteCentre(texteXVulnerable, texteYVulnerable,
						Integer.toString(cultiste.getVulnerableDuration()));
			}
			if (cultiste.getWeaknessDuration() > 0) {

				String weak = "pictures" + File.separator + "statuts" + File.separator + "Icon_Weak.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeWeak = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXWeak = Config.X_MAX * 0.8 - 135; // Ajustez la position horizontale de l'image vers la
																// gauche
				double imageYWeak = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Weak" avec la nouvelle taille
				Affichage.image(imageXWeak, imageXWeak + imageSizeWeak, imageYWeak, imageYWeak + imageSizeWeak, weak);
				// Ajuster les coordonnées pour afficher le texte "Faible de: X" au bord de
				// l'image
				double texteXWeak = imageXWeak - 5; // Ajustez la position horizontale du texte au bord de l'image
				double texteYWeak = imageYWeak + imageSizeWeak / 2; // Centrez le texte verticalement par rapport à
																	// l'image
				Affichage.texteCentre(texteXWeak, texteYWeak, Integer.toString(cultiste.getWeaknessDuration()));
			}

			if (cultiste.getTotalForceBonus() > 0) {

				String strength = "pictures" + File.separator + "statuts" + File.separator + "Strength.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeStrength = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXStrength = Config.X_MAX * 0.8 + 2; // Ajustez la position horizontale de l'image vers la
																// droite
				double imageYStrength = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Strength" avec la nouvelle taille
				Affichage.image(imageXStrength, imageXStrength + imageSizeStrength, imageYStrength,
						imageYStrength + imageSizeStrength, strength);
				// Ajuster les coordonnées pour afficher le texte "Force: X" au bord de l'image
				double texteXStrength = imageXStrength + imageSizeStrength + 5; // Ajustez la position horizontale du
																				// texte au bord de l'image
				double texteYStrength = imageYStrength + imageSizeStrength / 2; // Centrez le texte verticalement par
																				// rapport à l'image
				Affichage.texteCentre(texteXStrength, texteYStrength, Integer.toString(cultiste.getTotalForceBonus()));
			}
		}

		if (monstre instanceof PetitSlimeAcide) {
			PetitSlimeAcide petitSlimeAcide = (PetitSlimeAcide) monstre;
			// Affichage du machouilleur
			String mach = "pictures" + File.separator + "monstres" + File.separator + "Spike_Slime_M.png";
			Affichage.image(Config.X_MAX * 0.85 - 183, Config.X_MAX * 0.85 + 183, Config.Y_MAX * 0.5 - 130,
					Config.Y_MAX * 0.5 + 130, mach);
			Affichage.texteCentre(1500, 325,
					"PV : " + petitSlimeAcide.getCurrentLifePoint() + "/" + petitSlimeAcide.getMaxLifePoint());

			// Affichage de blocage et de force pour le machouilleur
			if (petitSlimeAcide.getArmor() > 0) {
				String Armure = "pictures" + File.separator + "intentions" + File.separator + "Defend.png";
				// Ajustez les coordonnées et la taille pour réduire l'icône d'armure
				double imageSizeArmure = 40; // Ajustez la taille de l'icône d'armure
				double imageXArmure = Config.X_MAX * 0.8 - 90; // Ajustez la position horizontale de l'icône d'armure
				double imageYArmure = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'icône d'armure
				Affichage.image(imageXArmure, imageXArmure + imageSizeArmure, imageYArmure,
						imageYArmure + imageSizeArmure, Armure);
				// Ajustez les coordonnées pour afficher le texte "Armure: X" au bord de l'icône
				double texteXArmure = imageXArmure + imageSizeArmure - 5; // Ajustez la position horizontale du texte au
																			// bord de l'icône
				double texteYArmure = imageYArmure + imageSizeArmure / 6; // Centrez le texte verticalement par rapport
																			// à l'icône
				Affichage.texteCentre(texteXArmure, texteYArmure, Integer.toString(petitSlimeAcide.getArmor()));
			}

			if (petitSlimeAcide.getVulnerableDuration() > 0) {

				String vulnerable = "pictures" + File.separator + "statuts" + File.separator + "Icon_Vulnerable.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeVulnerable = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXVulnerable = Config.X_MAX * 0.8 + 90; // Ajustez la position horizontale de l'image vers la
																	// droite
				double imageYVulnerable = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'image
				// Afficher l'image de vulnérabilité avec la nouvelle taille
				Affichage.image(imageXVulnerable, imageXVulnerable + imageSizeVulnerable, imageYVulnerable,
						imageYVulnerable + imageSizeVulnerable, vulnerable);
				// Ajuster les coordonnées pour afficher le texte "Vulnerable for: X" au bord de
				// l'image
				double texteXVulnerable = imageXVulnerable + imageSizeVulnerable + 5; // Ajustez la position horizontale
																						// du texte au bord de l'image
				double texteYVulnerable = imageYVulnerable + imageSizeVulnerable / 2; // Centrez le texte verticalement
																						// par rapport à l'image
				Affichage.texteCentre(texteXVulnerable, texteYVulnerable,
						Integer.toString(petitSlimeAcide.getVulnerableDuration()));
			}
			if (petitSlimeAcide.getWeaknessDuration() > 0) {

				String weak = "pictures" + File.separator + "statuts" + File.separator + "Icon_Weak.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeWeak = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXWeak = Config.X_MAX * 0.8 - 135; // Ajustez la position horizontale de l'image vers la
																// gauche
				double imageYWeak = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Weak" avec la nouvelle taille
				Affichage.image(imageXWeak, imageXWeak + imageSizeWeak, imageYWeak, imageYWeak + imageSizeWeak, weak);
				// Ajuster les coordonnées pour afficher le texte "Faible de: X" au bord de
				// l'image
				double texteXWeak = imageXWeak - 5; // Ajustez la position horizontale du texte au bord de l'image
				double texteYWeak = imageYWeak + imageSizeWeak / 2; // Centrez le texte verticalement par rapport à
																	// l'image
				Affichage.texteCentre(texteXWeak, texteYWeak, Integer.toString(petitSlimeAcide.getWeaknessDuration()));
			}

			if (petitSlimeAcide.getTotalForceBonus() > 0) {

				String strength = "pictures" + File.separator + "statuts" + File.separator + "Strength.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeStrength = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXStrength = Config.X_MAX * 0.8 + 2; // Ajustez la position horizontale de l'image vers la
																// droite
				double imageYStrength = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Strength" avec la nouvelle taille
				Affichage.image(imageXStrength, imageXStrength + imageSizeStrength, imageYStrength,
						imageYStrength + imageSizeStrength, strength);
				// Ajuster les coordonnées pour afficher le texte "Force: X" au bord de l'image
				double texteXStrength = imageXStrength + imageSizeStrength + 5; // Ajustez la position horizontale du
																				// texte au bord de l'image
				double texteYStrength = imageYStrength + imageSizeStrength / 2; // Centrez le texte verticalement par
																				// rapport à l'image
				Affichage.texteCentre(texteXStrength, texteYStrength,
						Integer.toString(petitSlimeAcide.getTotalForceBonus()));
			}
		}

		if (monstre instanceof PetitSlimePiquant) {
			PetitSlimePiquant petitSlimePiquant = (PetitSlimePiquant) monstre;
			// Affichage du machouilleur
			String mach = "pictures" + File.separator + "monstres" + File.separator + "Spike_Slime_S.png";
			Affichage.image(Config.X_MAX * 0.55 - 183, Config.X_MAX * 0.55 + 183, Config.Y_MAX * 0.5 - 130,
					Config.Y_MAX * 0.5 + 130, mach);
			Affichage.texteCentre(1000, 325,
					"PV : " + petitSlimePiquant.getCurrentLifePoint() + "/" + petitSlimePiquant.getMaxLifePoint());

			// Affichage de blocage et de force pour le machouilleur
			if (petitSlimePiquant.getArmor() > 0) {
				String Armure = "pictures" + File.separator + "intentions" + File.separator + "Defend.png";
				// Ajustez les coordonnées et la taille pour réduire l'icône d'armure
				double imageSizeArmure = 40; // Ajustez la taille de l'icône d'armure
				double imageXArmure = Config.X_MAX * 0.8 - 90; // Ajustez la position horizontale de l'icône d'armure
				double imageYArmure = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'icône d'armure
				Affichage.image(imageXArmure, imageXArmure + imageSizeArmure, imageYArmure,
						imageYArmure + imageSizeArmure, Armure);
				// Ajustez les coordonnées pour afficher le texte "Armure: X" au bord de l'icône
				double texteXArmure = imageXArmure + imageSizeArmure - 5; // Ajustez la position horizontale du texte au
																			// bord de l'icône
				double texteYArmure = imageYArmure + imageSizeArmure / 6; // Centrez le texte verticalement par rapport
																			// à l'icône
				Affichage.texteCentre(texteXArmure, texteYArmure, Integer.toString(petitSlimePiquant.getArmor()));
			}

			if (petitSlimePiquant.getVulnerableDuration() > 0) {

				String vulnerable = "pictures" + File.separator + "statuts" + File.separator + "Icon_Vulnerable.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeVulnerable = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXVulnerable = Config.X_MAX * 0.8 + 90; // Ajustez la position horizontale de l'image vers la
																	// droite
				double imageYVulnerable = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'image
				// Afficher l'image de vulnérabilité avec la nouvelle taille
				Affichage.image(imageXVulnerable, imageXVulnerable + imageSizeVulnerable, imageYVulnerable,
						imageYVulnerable + imageSizeVulnerable, vulnerable);
				// Ajuster les coordonnées pour afficher le texte "Vulnerable for: X" au bord de
				// l'image
				double texteXVulnerable = imageXVulnerable + imageSizeVulnerable + 5; // Ajustez la position horizontale
																						// du texte au bord de l'image
				double texteYVulnerable = imageYVulnerable + imageSizeVulnerable / 2; // Centrez le texte verticalement
																						// par rapport à l'image
				Affichage.texteCentre(texteXVulnerable, texteYVulnerable,
						Integer.toString(petitSlimePiquant.getVulnerableDuration()));
			}
			if (petitSlimePiquant.getWeaknessDuration() > 0) {

				String weak = "pictures" + File.separator + "statuts" + File.separator + "Icon_Weak.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeWeak = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXWeak = Config.X_MAX * 0.8 - 135; // Ajustez la position horizontale de l'image vers la
																// gauche
				double imageYWeak = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Weak" avec la nouvelle taille
				Affichage.image(imageXWeak, imageXWeak + imageSizeWeak, imageYWeak, imageYWeak + imageSizeWeak, weak);
				// Ajuster les coordonnées pour afficher le texte "Faible de: X" au bord de
				// l'image
				double texteXWeak = imageXWeak - 5; // Ajustez la position horizontale du texte au bord de l'image
				double texteYWeak = imageYWeak + imageSizeWeak / 2; // Centrez le texte verticalement par rapport à
																	// l'image
				Affichage.texteCentre(texteXWeak, texteYWeak,
						Integer.toString(petitSlimePiquant.getWeaknessDuration()));
			}

			if (petitSlimePiquant.getTotalForceBonus() > 0) {

				String strength = "pictures" + File.separator + "statuts" + File.separator + "Strength.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeStrength = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXStrength = Config.X_MAX * 0.8 + 2; // Ajustez la position horizontale de l'image vers la
																// droite
				double imageYStrength = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Strength" avec la nouvelle taille
				Affichage.image(imageXStrength, imageXStrength + imageSizeStrength, imageYStrength,
						imageYStrength + imageSizeStrength, strength);
				// Ajuster les coordonnées pour afficher le texte "Force: X" au bord de l'image
				double texteXStrength = imageXStrength + imageSizeStrength + 5; // Ajustez la position horizontale du
																				// texte au bord de l'image
				double texteYStrength = imageYStrength + imageSizeStrength / 2; // Centrez le texte verticalement par
																				// rapport à l'image
				Affichage.texteCentre(texteXStrength, texteYStrength,
						Integer.toString(petitSlimePiquant.getTotalForceBonus()));
			}
		}
		if (monstre instanceof SlimeAcide) {
			SlimeAcide slimeAcide = (SlimeAcide) monstre;
			// Affichage du machouilleur
			String mach = "pictures" + File.separator + "monstres" + File.separator + "Acid-slime-M.png";
			Affichage.image(Config.X_MAX * 0.45 - 183, Config.X_MAX * 0.45 + 183, Config.Y_MAX * 0.5 - 130,
					Config.Y_MAX * 0.5 + 130, mach);
			Affichage.texteCentre(800, 325,
					"PV : " + slimeAcide.getCurrentLifePoint() + "/" + slimeAcide.getMaxLifePoint());

			// Affichage de blocage et de force pour le machouilleur
			if (slimeAcide.getArmor() > 0) {
				String Armure = "pictures" + File.separator + "intentions" + File.separator + "Defend.png";
				// Ajustez les coordonnées et la taille pour réduire l'icône d'armure
				double imageSizeArmure = 40; // Ajustez la taille de l'icône d'armure
				double imageXArmure = Config.X_MAX * 0.8 - 90; // Ajustez la position horizontale de l'icône d'armure
				double imageYArmure = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'icône d'armure
				Affichage.image(imageXArmure, imageXArmure + imageSizeArmure, imageYArmure,
						imageYArmure + imageSizeArmure, Armure);
				// Ajustez les coordonnées pour afficher le texte "Armure: X" au bord de l'icône
				double texteXArmure = imageXArmure + imageSizeArmure - 5; // Ajustez la position horizontale du texte au
																			// bord de l'icône
				double texteYArmure = imageYArmure + imageSizeArmure / 6; // Centrez le texte verticalement par rapport
																			// à l'icône
				Affichage.texteCentre(texteXArmure, texteYArmure, Integer.toString(slimeAcide.getArmor()));
			}

			if (slimeAcide.getVulnerableDuration() > 0) {

				String vulnerable = "pictures" + File.separator + "statuts" + File.separator + "Icon_Vulnerable.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeVulnerable = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXVulnerable = Config.X_MAX * 0.8 + 90; // Ajustez la position horizontale de l'image vers la
																	// droite
				double imageYVulnerable = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'image
				// Afficher l'image de vulnérabilité avec la nouvelle taille
				Affichage.image(imageXVulnerable, imageXVulnerable + imageSizeVulnerable, imageYVulnerable,
						imageYVulnerable + imageSizeVulnerable, vulnerable);
				// Ajuster les coordonnées pour afficher le texte "Vulnerable for: X" au bord de
				// l'image
				double texteXVulnerable = imageXVulnerable + imageSizeVulnerable + 5; // Ajustez la position horizontale
																						// du texte au bord de l'image
				double texteYVulnerable = imageYVulnerable + imageSizeVulnerable / 2; // Centrez le texte verticalement
																						// par rapport à l'image
				Affichage.texteCentre(texteXVulnerable, texteYVulnerable,
						Integer.toString(slimeAcide.getVulnerableDuration()));
			}
			if (slimeAcide.getWeaknessDuration() > 0) {

				String weak = "pictures" + File.separator + "statuts" + File.separator + "Icon_Weak.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeWeak = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXWeak = Config.X_MAX * 0.8 - 135; // Ajustez la position horizontale de l'image vers la
																// gauche
				double imageYWeak = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Weak" avec la nouvelle taille
				Affichage.image(imageXWeak, imageXWeak + imageSizeWeak, imageYWeak, imageYWeak + imageSizeWeak, weak);
				// Ajuster les coordonnées pour afficher le texte "Faible de: X" au bord de
				// l'image
				double texteXWeak = imageXWeak - 5; // Ajustez la position horizontale du texte au bord de l'image
				double texteYWeak = imageYWeak + imageSizeWeak / 2; // Centrez le texte verticalement par rapport à
																	// l'image
				Affichage.texteCentre(texteXWeak, texteYWeak, Integer.toString(slimeAcide.getWeaknessDuration()));
			}

			if (slimeAcide.getTotalForceBonus() > 0) {

				String strength = "pictures" + File.separator + "statuts" + File.separator + "Strength.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeStrength = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXStrength = Config.X_MAX * 0.8 + 2; // Ajustez la position horizontale de l'image vers la
																// droite
				double imageYStrength = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Strength" avec la nouvelle taille
				Affichage.image(imageXStrength, imageXStrength + imageSizeStrength, imageYStrength,
						imageYStrength + imageSizeStrength, strength);
				// Ajuster les coordonnées pour afficher le texte "Force: X" au bord de l'image
				double texteXStrength = imageXStrength + imageSizeStrength + 5; // Ajustez la position horizontale du
																				// texte au bord de l'image
				double texteYStrength = imageYStrength + imageSizeStrength / 2; // Centrez le texte verticalement par
																				// rapport à l'image
				Affichage.texteCentre(texteXStrength, texteYStrength,
						Integer.toString(slimeAcide.getTotalForceBonus()));
			}
		}
		if (monstre instanceof SlimePiquant) {
			SlimePiquant slimePiquant = (SlimePiquant) monstre;
			// Affichage du machouilleur
			String mach = "pictures" + File.separator + "monstres" + File.separator + "Acid-slime-S.png";
			Affichage.image(Config.X_MAX * 0.95 - 183, Config.X_MAX * 0.95 + 183, Config.Y_MAX * 0.5 - 130,
					Config.Y_MAX * 0.5 + 130, mach);
			Affichage.texteCentre(1700, 325,
					"PV : " + slimePiquant.getCurrentLifePoint() + "/" + slimePiquant.getMaxLifePoint());

			// Affichage de blocage et de force pour le machouilleur
			if (slimePiquant.getArmor() > 0) {
				String Armure = "pictures" + File.separator + "intentions" + File.separator + "Defend.png";
				// Ajustez les coordonnées et la taille pour réduire l'icône d'armure
				double imageSizeArmure = 40; // Ajustez la taille de l'icône d'armure
				double imageXArmure = Config.X_MAX * 0.8 - 90; // Ajustez la position horizontale de l'icône d'armure
				double imageYArmure = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'icône d'armure
				Affichage.image(imageXArmure, imageXArmure + imageSizeArmure, imageYArmure,
						imageYArmure + imageSizeArmure, Armure);
				// Ajustez les coordonnées pour afficher le texte "Armure: X" au bord de l'icône
				double texteXArmure = imageXArmure + imageSizeArmure - 5; // Ajustez la position horizontale du texte au
																			// bord de l'icône
				double texteYArmure = imageYArmure + imageSizeArmure / 6; // Centrez le texte verticalement par rapport
																			// à l'icône
				Affichage.texteCentre(texteXArmure, texteYArmure, Integer.toString(slimePiquant.getArmor()));
			}

			if (slimePiquant.getVulnerableDuration() > 0) {

				String vulnerable = "pictures" + File.separator + "statuts" + File.separator + "Icon_Vulnerable.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeVulnerable = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXVulnerable = Config.X_MAX * 0.8 + 90; // Ajustez la position horizontale de l'image vers la
																	// droite
				double imageYVulnerable = Config.Y_MAX * 0.5 + 100; // Ajustez la position verticale de l'image
				// Afficher l'image de vulnérabilité avec la nouvelle taille
				Affichage.image(imageXVulnerable, imageXVulnerable + imageSizeVulnerable, imageYVulnerable,
						imageYVulnerable + imageSizeVulnerable, vulnerable);
				// Ajuster les coordonnées pour afficher le texte "Vulnerable for: X" au bord de
				// l'image
				double texteXVulnerable = imageXVulnerable + imageSizeVulnerable + 5; // Ajustez la position horizontale
																						// du texte au bord de l'image
				double texteYVulnerable = imageYVulnerable + imageSizeVulnerable / 2; // Centrez le texte verticalement
																						// par rapport à l'image
				Affichage.texteCentre(texteXVulnerable, texteYVulnerable,
						Integer.toString(slimePiquant.getVulnerableDuration()));
			}
			if (slimePiquant.getWeaknessDuration() > 0) {

				String weak = "pictures" + File.separator + "statuts" + File.separator + "Icon_Weak.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeWeak = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXWeak = Config.X_MAX * 0.8 - 135; // Ajustez la position horizontale de l'image vers la
																// gauche
				double imageYWeak = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Weak" avec la nouvelle taille
				Affichage.image(imageXWeak, imageXWeak + imageSizeWeak, imageYWeak, imageYWeak + imageSizeWeak, weak);
				// Ajuster les coordonnées pour afficher le texte "Faible de: X" au bord de
				// l'image
				double texteXWeak = imageXWeak - 5; // Ajustez la position horizontale du texte au bord de l'image
				double texteYWeak = imageYWeak + imageSizeWeak / 2; // Centrez le texte verticalement par rapport à
																	// l'image
				Affichage.texteCentre(texteXWeak, texteYWeak, Integer.toString(slimePiquant.getWeaknessDuration()));
			}

			if (slimePiquant.getTotalForceBonus() > 0) {

				String strength = "pictures" + File.separator + "statuts" + File.separator + "Strength.png";
				// Ajuster les coordonnées et la taille de l'image
				double imageSizeStrength = 40; // Ajustez la taille de l'image selon vos besoins
				double imageXStrength = Config.X_MAX * 0.8 + 2; // Ajustez la position horizontale de l'image vers la
																// droite
				double imageYStrength = Config.Y_MAX * 0.5 + 80; // Ajustez la position verticale de l'image
				// Afficher l'image "Strength" avec la nouvelle taille
				Affichage.image(imageXStrength, imageXStrength + imageSizeStrength, imageYStrength,
						imageYStrength + imageSizeStrength, strength);
				// Ajuster les coordonnées pour afficher le texte "Force: X" au bord de l'image
				double texteXStrength = imageXStrength + imageSizeStrength + 5; // Ajustez la position horizontale du
																				// texte au bord de l'image
				double texteYStrength = imageYStrength + imageSizeStrength / 2; // Centrez le texte verticalement par
																				// rapport à l'image
				Affichage.texteCentre(texteXStrength, texteYStrength,
						Integer.toString(slimePiquant.getTotalForceBonus()));
			}
		}
	}

	/**
	 * // Méthode pour dessiner la barre de vie
	 * 
	 * @param xMin,        un double
	 * @param xMax,        un double
	 * @param yMin,        un double
	 * @param yMax,        un double
	 * @param vieActuelle, un entier
	 * @param vieMax,      un entier
	 * @param couleur,     une Couleur
	 * @param h,           une Entite
	 */
	public static void dessinerBarreDeVie(double xMin, double xMax, double yMin, double yMax, int vieActuelle,
			int vieMax, Color couleur, Entitee h) {
		// Calculer la proportion de vie actuelle par rapport à la vie maximale
		double proportionVie = (double) vieActuelle / vieMax;

		// Définir la couleur du rectangle (rouge pour représenter la vie)
		StdDraw.setPenColor(couleur);

		// Dessiner le rectangle rempli avec la proportion de vie actuelle
		double largeurRectangle = (xMax - xMin) * proportionVie;
		double hauteurRectangle = yMax - yMin;
		largeurRectangle /= 3;
		hauteurRectangle /= 2;

		double largeurRectangleVieMax = (xMax - xMin) / 3;

		double x = (xMin + xMax) / 2.0 - 10;

		// Écrire le nombre de points de vie restants en dessous de la barre de vie
		String texteVie = h.getCurrentLifePoint() + " / " + h.getMaxLifePoint();
		double xTexte = x + largeurRectangleVieMax / 2;
		double yTexte = yMin - 15; // Ajustez la position verticale du texte
		Font styleLettre = new Font("StyleLettre", Font.PLAIN, 14);
		Affichage.texteCentre(xTexte, yTexte, texteVie, styleLettre, Color.WHITE);
		// Utiliser la fonction rectanglePlein pour dessiner le rectangle rempli

		int contourBarreDeVie = 2;
		Affichage.rectanglePlein(x - contourBarreDeVie, x + largeurRectangleVieMax + contourBarreDeVie,
				yMin - contourBarreDeVie, yMin + hauteurRectangle + contourBarreDeVie, Color.BLACK);
		Affichage.rectanglePlein(x, x + largeurRectangle, yMin, yMin + hauteurRectangle, couleur);
	}

	/**
	 * Dresse une pile de carte à l'index 0
	 */
	public void mainduplayer() {
		deckDuJoueur.add(hero.getDeck().getCards().get(0));
		hero.getDeck().getCards().remove(0);
	}

	/**
	 * Tire 5 cartes depuis la pile
	 */
	public void drawFive() {
		for (int i = 0; i < 5; i++)
			mainduplayer();
	}

	/**
	 * @param card, une carte
	 * @return un String, le chemin permettant d'afficher la carte
	 */
	public String getPathCard(Card card) {
		switch (card.getName()) {
			case "Frappe":
				return "pictures\\Cartes\\Strike_R.png";
			case "Defense":
				return "pictures\\Cartes\\Defend_R.png";
			case "Heurt":
				return "pictures\\Cartes\\Bash.png";
			default:
				return "pictures\\Cartes\\gourdin.png";
		}
	}

	/**
	 * Permet de calculer la hauteur, longueur, et la largueur, les espaces entre
	 * les cartes, ainsi que la position
	 * de la main de carte sur l'écran
	 */
	public void affichageMain() {
		drawFive();
		int largeurCard = 150;
		int hauteurCard = 200;

		double x = Config.X_MAX * 0.5;
		double y = Config.Y_MAX * 0.1;

		int spearationCard = 5;

		int nbCards = deckDuJoueur.size();
		double initalX = x - (nbCards - 1) * (largeurCard / 2 + spearationCard);

		for (Card card : deckDuJoueur) {
			String cardImage = getPathCard(card);
			Affichage.image(initalX - largeurCard / 2, initalX + largeurCard / 2, y, y + hauteurCard, cardImage);
			initalX += (largeurCard + spearationCard);
		}
	}

	public void initialDisplay() {
		AssociationTouches.init();
		Config.init();
		StdDraw.enableDoubleBuffering(); // rend l'affichage plus fluide: tout draw est mis en buffer et ne s'affiche
											// qu'au prochain StdDraw.show();
		display();
	}

	public void update() {
		String toucheSuivante = AssociationTouches.trouveProchaineEntree(); // cette fonction boucle jusqu'a la
																			// prochaine entree de l'utilisateur
		if (toucheSuivante.equals("Droite")) {
			roomFight.doCombat();
			System.out.println("boom");
			display();
		} else {
			System.out.println("Autre touche");
			display();
		}
	}
}
