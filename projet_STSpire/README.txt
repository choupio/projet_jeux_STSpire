Wojtecki Benjamin Kouame Ange

ATTENTION POUR QUE TOUT MARCHE, LORS DU DEZIPPAGE, IL FAUT OUVRIR "projet_STPIRE" ET "NON WOJTECKI_KOUAME_PROJET_STSpire"

Présentation du projet : 
Le but de ce projet est de réaliser une version simplifiée du jeu Slay The Spire. 

Slay the Spire est un jeu dans lequel le joueur affronte des monstres en utilisant des cartes pour effectuer différentes actions. C'est une jeu Deck building, c'est-à-dire qu'on gagne des cartes après chaque combat. 

Dans un premier temps, nous avons implémenté la version simplifiée d’un combat. On a modélisé un combat entre le héros et une équipe de monstre composée d'un seul monstre ("Petit slime piquant"). Dans cette version, le héros a comme possibilité de seulement jouer les cartes "Frappe" et "Défense". 
Suite à cette implémentation testé et fonctionnel, nous avons rajouté les cartes qui ont un niveau de complexité de niveau 1 et 2 ("Frappe", "Défense", "Même pas mal", "Vague de fer", "Frappe du pommeau", "Frappe double", "Enchainement", "Saigné", "Hémokinésie", "Gourdin"). On expliquera dans la partie carte, comment nous les avons implémentés. Puis, nous ajoutons un monstre de plus grande complexité qui est le Machouilleur. Sa complexité réside dans les différentes possibilités que le monstre à. Ce qu’on appellera par la suite le pattern du monstre.
Une fois tout cela implémenté et testé, nous avons mis en place le mécanisme des pièces, afin de pouvoir mener plusieurs combats dans la même partie. En plus, nous avons ajouté un mécanisme permettant d’obtenir en récompense une carte lorsque le héros termine une salle.

Tous ces ajouts donnent une version jouable, malheureusement n'ayant pas réussi la partie graphique (Voir partie 10), l’entièreté du jeu se joue en version textuel. Lorsqu’on lance le jeu, on obtient un menu avec la possibilité de jouer la partie textuelle ou la partie graphique. Enfin avec le temps qu'il nous restait, nous avons ajouté toutes les cartes à l'exception de "Forme démoniaque", et de même pour les monstres, où il manque juste l'Héxaghost. Nous avons ajouté un paramètre dans l’équipe de monstre permettant d’augmenter le nombre de monstre à chaque nouvelle salle, tout en mettant une limite qui est de deux monstres dans l’équipe. On pourrait faire des améliorations au niveau de l'attaque, c'est-à-dire que si le monstre est mort on ne peut plus le cibler, ce que notre code fait pas et quand on meurt


Diagramme des classes : 
On représente seulement les classes qui héritent d'autres classes. A noter que pour chaque case, il devrait avoir qu'une seule classe, mais pour des raisons de simplification nous n'avons pas tout écrit, juste l'idée du diagramme des classes.

+-----------------------------------+			+--------------------------------------------------------------------------------------+
|            Entitee                |			|           	                       Status                                          |
+-----------------------------------+ 			+--------------------------------------------------------------------------------------+ 
      ^			         ^				                ^			   ^		       ^	              ^			         ^	
      |			         |				                |			   |		       |		          |		 	         |
+-------------+	    +---------+				+--------------+   +--------------+ +--------------+   +--------------+	  +--------------+
|  Monstres   |	    |  Hero   |				| ForceStatuts |   |  Fragility   | |  Vulerable   |   |   Weakness   |	  |    Rituel    |
+-------------+	    +---------+				+--------------+   +--------------+ +--------------+   +--------------+	  +--------------+
      ^   
      |
+--------------------+
|     Machouilleur   |
| PetitSlime Piquant |
| Petit Slime Acide  |
|     Cultiste       |
|    Slime piquant   |
|     Slime acide    |
+--------------------+


+----------------------------------------------------------------------------------------------------+
|                                                Card                                                |
+----------------------------------------------------------------------------------------------------+
      ^		     ^		                      ^		        ^		               ^
      |		     |		                      |		        |		               |
+---------+	+---------+	                 +---------+	+---------+          +--------------------+
| Attack  |	| Defense |	                 |  Mana   |	| Status  |          |  Attack and defense|
+---------+	+---------+	                 +---------+	+---------+          +--------------------+
      |		       |		                 |		          |   		       |
      |		       |		                 |		          |			       |
+---------------+ +---------------+ +---------------+ +---------------+  +---------------+
| Frappe/etc... | |Defense/etc... | |  Saigne/etc   | | Enflammer/etc |  | Vague de fer  | 
+---------------+ +---------------+ +---------------+ +---------------+  +---------------+

PS : Certaines javaDoc des méthodes ont été partiellement réalisées avec chatGPT.

1 - Les entités du jeu (présent dans le dossier personnage) :

On remarque qu'il existe plusieurs entités dans le jeu. D'une part, on a le héros, que nous jouons, et d'autres parts, on a une variété de monstre, qu'on pourra affronter. On observe qu'il existe des caractéristiques communes entre le héros est les monstres. En effet, le héros est caractérisé par des points de vie courant (PV) et maximum, d'énergie qu'on nommera par la suite mana, ainsi qu’une liste de statuts et d'un deck de cartes. Chez les monstres, les caractéristiques sont les points de vie courant (PV) et maximum ainsi qu'un pattern dépendant du monstre et une liste de statuts. En observant bien les entités, on remarque que certaines sont en commun entre le héros et les monstres. Nous pouvons donc créer une classe mère, qu'on nommera "Entitee", dont la classe "Hero" et "Monstre" hériterons. Cela nous permet d’avoir une meilleure encapsulation de notre projet.
 

	~ Classe Entitee (classe mère) :

	Elle est définie comme abstraite grâce à sa signature "public abstract class Entitee{...}", cela nous permet de définir une structure commune pour plusieurs classes tout en offrant la possibilité d'imposer certains comportements aux classes filles. Nous déclarons trois attributs "maxLifePoints", "currentLifePoints", "Status". Ces derniers sont déclarés avec le modificateur protected, permettant aux attributs d’être accessibles dans la classe mère mais aussi dans les classes filles. En faisant ça, nous respectons le principe d'encapsulation vu en cours. 


		- Définition du constructeur : Instancie la classe "Entitee" en initialisant sa quantité maximale de points de vie avec la valeur passé en paramètre. L'entité 			  est également initialisée avec une valeur de points de vie actuelle (dans notre cas, les points de vie actuelles sont égaux aux points de vie max) et avec une 		  liste vide de statuts déclaré comme vide lors de l’initialisation. 

		- Définition des getters et setters pour chacun des attributs. 

	Une fois les getters and setters des attributs créés et le constructeur créé, nous déclarons des méthodes pouvant être utilisée par les classes filles, contribuant toujours à une bonne encapsulation. De plus, les méthodes sont déclarées avec le mot clé "public" indiquant que ces dernières sont accessible à l'extérieur de la classe. 


		- void addStatut(Status status){...} : Permet d'ajouter un statut à la liste des statuts si celle-ci n'est pas déjà présent

		- void removeStatus(Status status){...} : Permet de supprimer un statut spécifique de la liste des statuts de l'entitée

		- <T extends Status> T findStatus(Class<T> statusClass){...} : : Méthode générique, favorisant l'utilisation de cette méthode pour rechercher différents 			  statuts. Son objectif est d'obtenir un statut spécifique parmi la liste des statuts associés à l'entité. Elle prend en paramètre la classe du statut recherché 		  et retourne le statut correspondant s’il est présent dans la liste des statuts, sinon on renvoie null. Pour ce faire, on parcourt une liste de statut, puis 			  pour chaque statut, on vérifie s'il est une instance de la classe spécifiée en paramètre. On fait cela grâce à "isInstance". Si c'est le cas, on renvoie 			  l'objet après avoir effectué un cast vers le type générique, sinon on renvoie null. 
		
		- boolean statusPresent(Status status){...} : Vérifie si le statut passé en paramètre est déjà présent dans la liste des statuts. Si il est présent, on renvoie 		  True sinon False. Pour ce faire, on parcourt la liste des statuts et compare les classes des statuts existants avec la classe du statut passé en paramètre 			  avec l'instruction suivante : "(statusExistant.getClass().equals(status.getClass()))".

		- void printStatuts() :  Méthode permettant d'afficher les statuts associés à une entité (que ce soit un héros ou un monstre). La méthode parcourt la liste des 		  statuts, et à chaque itération, elle vérifie s'ils appartiennent à une instance de statut telle que "ForceStatuts", "WeaknessStatuts", etc...

		- void checkAndRemoveExpiratedStatuts() : On utilise un iterator pour parcourir une liste de statuts associée à une entité. Pour chaque statut, nous vérifions 			  son type à l'aide d'instruction "if". Si le statut est du type spécifié et qu'il a expiré alors il est retiré de la liste à l'aide de la méthode 				  "iterator.remove()".  

		- abstract boolean isDead() : Méthode abstraite qui doit être défini obligatoirement dans les classes filles. Elle permet de déterminer si le héros ou le 			  monstre est mort, renvoyant True si c'est le cas, sinon False

		- abstract void receivedDamage(int damage) : Méthode abstraite, permettant d'appliquer des dégâts à l'entité en prenant en paramètres le montant des dégâts qui 		  est un entier. Elle devra être implémenté obligatoirement dans les classes filles. 


Intéressons-nous, maintenant aux classes filles, qui sont les classes "Hero" et "Monstre" (présent eux aussi dans le dossier personnage) :



	~ Classe Hero : 

	Déclarons les attributs manquants, qui permettent de définir la classe "Hero". Ces attributs auront le mot clé "private" modifiant ainsi la portée des variables. En effet, ces dernières pourront être utilisées seulement dans la classe. On retrouve ainsi les attributs suivants : "mana", "armor", "deck", "playerHand". A noter que le type de deck est Deck, une classe permettant de représenter une pile de carte (cf. partie 7) et le type de playerHand est une list<Card>, où Card est une classe qu'on a créé pour représenter les cartes et liste est une structure de données commun (cf. partie 4).

		- Constructeur : Créer une instancie d'un "Hero", prenant en paramètre un deck de carte. On appelle l'instruction "super(...)" qui appel le constructeur de la 			  classe mère, le paramètre présent dans super, permet d'initialiser les points de vie maximal du héros. On initialise aussi les points de mana, ainsi que 			  l'armure à 0, et nous déclarons la main du joueur par un tableau vide.
		- Getters and setters de tous les attributs, pour une meilleure encapsulation. 

La classe héro a plusieurs types de méthodes, celles associées à la manipulation de la main du joueur et du deck ainsi que celles que nous devons implémenter en tant que classe fille. Et certaines méthodes n'ont pas de catégories spécifiques. 

	    a) Méthodes hors catégorie :
		
		- void addMana(int addMana) : Méthode prenant un entier et l'ajoute à la valeur actuelle de la mana
		- void removeMana(int removeMana) : Méthode prenant un entier et soustrait cette valeur à la mana actuelle
		- void resetMana() : Méthode permettant de remettre à l'état initial la mana (la valeur est de 3 dans notre cas)
		- void resetArmor() : Methode permettant de remettre à 0 l'armure

	    b) Méthodes lié à la classe mère 

		- boolean isDead() : On utilise la notation @Overide pour indiquer que la méthode de la classe héro doit remplacer la méthode présente dans la classe mère. 			  Cette méthode renvoie vrai, si les points de vie courant du héros sont inférieurs ou égale à 0, sinon on renvoie faux.
		- receivedDamage(int damage) : Méthode permettant de calculer les points de vie après avoir reçu les dommages, on doit faire attention à la prise en compte de 			  la valeur lié à l'armure. De plus, si la valeur est négative, on remet les points de vie à 0
		- void printStatus() : Méthode faisant appel à la méthode présente dans la classe mère "printStatuts()" qui nous permets d'afficher les statuts du héro, s'il en 		  possède  


	    c) Méthodes de la gestion des cartes au niveau du deck ou de la main du joueur 

		- void discardHand() : Méthode permettant d'envoyer toutes les cartes de la main du joueur dans le cimetière, pour faire cela, on appelle une méthode présent 			  dans la classe Deck (Cf. chapitre 7)

		- void removeCardFromHand(Card card) : Prend en paramètre une carte de type Card et l'enlève de la main du joueur. En effet, la main du joueur est une liste de 		  carte de type Card, nous pouvons utiliser les méthodes propres aux ArrayList comme ".remove()"

		- void getHandSize() : Retourne le nombre de carte présent dans la main du joueur en utilisant .size disponible dans la librairie associé aux ArrayList

		- void addCardToHand(Card card) : Prend en paramètre une carte de type Card, et l'ajoute dans la main avec la méthode ".add()" provenant de la librairie associé 		  aux ArrayList
		
		- void drawCard(Deck d, Deck c) : Simule la pioche d'une carte provenant du deck et on l'ajoute dans la main du joueur. On doit vérifier si le jeu de carte 			  n'est pas vide, sinon on obtiendrait une erreur. Pour ce faire, on ajoute une condition pour vérifier si le deck est vide avant de piocher. Si il est vide, on 		  effectue une opération pour le remplir (méthode créé dans la classe Deck).

		- void drawInitialCard(Deck d, Deck c) : Permet de piocher 5 cartes provenant du deck.
		  



	~ Classe Monstre :

	Cette classe hérite de la classe "Entitee", mais étant donné que chacun des monstres qu'on souhaite implémenter ont des caractèristiques communes, on définit cettec classe comme abstraite afin d'avoir des méthodes qu'on doit seulement implémenter dans les classes filles. Chaque monstre possède un nom, une valeur pour l'armure, et une valeur pour gérer le statut force qui est un peu particulier (Cf. Partie 3). Ainsi, on déclare "name", "armor" en protected, afin de pouvoir les utiliser dans les classes filles. De plus, l'attribut totalForceBonus, est lui en privé pour éviter de pouvoir le modifier en dehors de la classe.

		- Constructeur : Créer une instance d'un Monstre, prenant en paramètre une chaine de caractère représentant le nom du monstre. Puis on initialise l'entier 			  représentant l'armure.
		- Getters and Setters associées aux attributs décrit ci-dessus

Les méthodes présentent dans la classe, sont pour la gestion des statuts du monstre et certaines sont les implémentations des méthodes de la classe mère.

		- boolean isDead() : On utilise la notation @Overide pour indiquer que la méthode de la classe Monstre doit remplacer la méthode présente dans la classe mère. 			  Cette méthode renvoie un boolean vrai, si les points de vie courant du héros sont inférieurs ou égale à 0, sinon on renvoie faux.

		- void printStatus() : Méthode faisant appel à la méthode présentante dans la classe mère "printStatuts()" qui nous permets d'afficher les statuts du héros, si 		  il en possède.  

		- receivedDamage(int damage) : Méthode permettant de calculer les points de vie après avoir reçu les dommages, on doit faire attention à la prise en compte de 			  la valeur lié a l'armure.

		- getVunerableStatus() : Permet de retourner le statut de vulnérabilité du monstre, s'il existe

		- getWeaknessStatus() : Permet de retourner le statut de faiblesse du monstre, s'il existe

		- getFragility() : Permet de retourner le statut de fragile du monstre, s'il existe

		- getWeaknessDuration() : Permet d'obtenir la durée de l'effet faiblesse s'il existe, sinon renvoie false

		- getVulnerableDuration() : Permet d'obtenir la durée de l'effet vulnérabilité s'il existe, sinon renvoie false

		- getFragilityDuration() : Permet d'obtenir la durée de l'effet fragile s'il existe, sinon renvoie false

		- applyEffects(Hero h, Monstre m, int damage) : Sert à appliquer les effets des statuts du monstre sur les dégâts qu'il inflige au héros. Elle est importante 			  pour le calcul des dommages. On récupère dans un premier temps, la liste des statuts du monstre à l'aide de la méthode "getStatuts". Ensuite, on parcourt 			  cette liste pour ajuster les dégâts en fonction des différents types de statuts. On a un traitement particulier pour le statut "Force", où les dégâts sont 			  augmentés en fonction du nombre d'application du statut et du bonus d'attaque associé. Enfin la méthode appel la méthode "receivedDamage" appliqué au héros, 			  pour lui transmettre les dégâts ajustés.

		- abstract void play(Hero h, Deck d) : Méthode abstraite à implémenter dans les classes filles. Elle permet aux monstres de faire leur action pendant leur tour 

    	- abstract String intention(): Méthode abstraite à implémenter dans les classes filles. Elle permet de montrer l'intention du monstre au héro à chaque début de 		  tour 



2 - Les différents monstre (présent dans le dossier personnage et dans le sous dossier ListMonsters)

Dans cette partie, nous allons parler des différents monstres que nous avons implémenté. Tout en sachant qu'on à modéliser tous les monstres à l'exception de l'Héxaghost. Tous ces monstres héritent de la classe "Monstre". Elles devront par conséquent implémenter les méthodes abstraites présente dans la classe mère. A noter que certains monstres on des patterns avec des pourcentages d'attaques, pour résoudre ce problème, nous avons utilisé la méthode Math.random() afin de générer un nombre aléatoire entre 0 et 1. Cela permet de modéliser d'une certaine manière le hasard

	
	~ Classe PetitSlimePiquant (PSP)
	
	C'est le monstre le plus simple à modéliser puisqu'il n'a qu'une attaque. Son unique attribut est un entier attaque, qu'on nommera damage. S'ensuit le constructeur permettant d'initialiser un nouveau monstre de type PSP avec un nom spécifique (PSP) et un nombre de points de vie maximal.

		- Getters et Setters de l'attribut damage
		- void play(Hero h, Deck d) : On écrit @Overide pour indique que la méthode de la classe doit remplacer la méthode dans la classe mère. Etant donné qu'il fait 			  en permanence charge, il suffit d'appeler la méthode "charge(h)".
		- void charge(Hero h) : Représente l'action du monstre, où il inflige des dégats au héro en utilisant la méthode "applyEffects(...)" provenant de Monstre
		- String intention() : Cette méthode permet de retourner une chaine de caractère décrivant l'intention du monstre, qui est dans notre cas charge.
 
	~ Classe Petit Slime Acide (PSA)

	Le Petit Slime Acide est un monstre basique avec un comportement alternatif entre "Charge" et "Lecher". Il possède les attributs suivants : "tourActuel", "armor", et "damage". S'ensuit le constructeur permettant d'initialiser un nouveau monstre de type PSA avec un nom spécifique (PSA) et un nombre de points de vie maximal.

		- Getters et Setters des attributs de la classe
		- void charge(Hero h) : Inflige des dégâts au héros en utilisant la méthode "applyEffects(...)" provenant de "Monstre"
		- void lecher(Hero h) : Applique le statut de fragilité au héros
		- void play(Hero h, Deck d) : Joue le tour du Petit Slime Acide en alternant entre "Charge" et "Lecher"
		- String intention() : Donne l'intention du slime pour le tour actuel, indiquant s'il compte faire "Lecher" ou "Charge".

	~ Classe Machouilleur

	Le Machouilleur est un monstre avec un pattern assez complexe à modéliser. Pour ce faire, nous avons utilisé les attributs suivants : "tourActuel", "armor", "randomValue" et "damage". S'ensuit du constructeur permettant d'initialiser un nouveau monstre de type Machouilleur avec un nom spécifique et un nombre de points de vie maximal et initialisant les autres attributs.

		- Getters et setters des attributs 
		- void charge(Hero h) : Effectue une charge infligeant des dégâts et ajoutant de l'armure
		- void morsure(Hero h) : Inflige des dégâts
		- void gronder() : Ajoute de l'armure et applique le statut Force au Machouilleur
		- void play(Hero h, Deck d) : Joue le pattern du Machouilleur envers le héros, en fonction du tour et de la valeur aléatoire venant de "randomValue"
		- String intention() : Obtient une phrase décrivant l'intention du Machouilleur en fonction de son action.


	~ Classe Cultiste

	Le Cultiste est un monstre qui peut effectuer un rituel ou une attaque. Il fait rituel en préparation (cad au tour 1) puis après il ne fait que d'attaquer. Il possède un attribut "tourActuel" pour suivre le déroulement des tours et un attribut "damage" pour représenter les dégâts.
	
		- Getters et Setters des attributs "damage" et "tourActuel"
		- Rituel findRituelStatus() : Recherche et renvoie le statut de Rituel parmi les statuts présent chez le Cultiste
		- void play(Hero h, Deck d) : Joue le pattern du Cultiste envers le héros, faisant rituel au premier tour puis attaque au tour d'après
		- void attaque(Hero h) : Effectue une attaque contre le héros, en prenant on considération si le monstre à Rituel, si oui faut faire des modifications de 			  dommage sinon on applique directement l'attaque 
		- String intention() : Obtient une phrase décrivant l'intention du Cultiste en fonction de son action pour chaque tour 

	~ Classe SlimeAcide

	Le Slime Acide est un monstre qui possède trois actions possibles : "Crachat", "Lecher", et "Charge". Il a des attributs tels que "randomValue" et "damage"

		- Getters et Setters de l'attribut des attributs
		- void crachat(Hero h, Deck d) : inflige des dégâts et ajoutant une carte Slime au cimetière du héros
		- void charge(Hero h) :Inflige des dégâts en utilisant la méthode "applyEffects(...)" provenant de Monstre
		- void lecher(Hero h) : Applique le statut Fragilité à ce dernier
		- void play(Hero h, Deck d) : Joue le pattern du monstre en alternant entre "Crachat", "Lecher", et "Charge" de manière aléatoire grâce à randomValue

	~ Classe SlimePiquant

	Le Slime Piquant est un monstre qui possède deux actions possibles : "Lecher" et "Crachat". Il a des attributs tels que "randomValue" et "damage"

		- Getters et Setters des attributs
		- void crachat(Hero h, Deck d) :Inflige des dégâts et ajoutant une carte Slime au cimetière
		- void lecher(Hero h) : Applique le statut Fragilité 
		- void play(Hero h, Deck d) : Joue le pattern du monstre envers le héros en choisissant entre "Lecher" et "Crachat" de façon aléatoire
		- String intention() : Donne une phrase décrivant l'intention du monstre, indiquant s'il compte faire "Lecher" ou "Crachat"


3 - Les différents statuts (présent dans le dossier Statut)

Nous devons maintenant mettre en œuvre quatre grands types de statuts : "ForceStatus", "Fragile", "Vulnérable" et "Faiblesse". En plus de ceux-ci, il existe un statut particulier appelé "Rituel", qui est exclusivement présent chez le Cultiste. Chacun de ces statuts a un nom, nous pouvons donc créer une classe mère appelée "Statut" à partir de laquelle tous les autres statuts hériteront.

	~ Classe Statut 

	Elle comporte un attribut "name" qui stocke le nom du statut. Le constructeur de la classe permet d'initialiser cet attribut lors de la création d'une instance. De plus, on à défini des getters et setters à l'attribut "name".
	

	~ Classe ForceStatus

	La classe représente un statut lié à la force. Elle possède 2 attributs "attackBonus" qui représente le bonus d'attaque et "numberOfApplications" qui indique le nombre d'applications. Le constructeur initialise ces attributs à 0. A ces attributs, on leur définit des getters and setters. Enfin, nous créons une méthode permettant d'augmenter le nombre d'applications. La méthode toString est redéfini pour afficher les informations du statut.

	~ Classe VulnerableStatus / Class WeaknessStatus / Class Fragility

	La méthode possède un attribut spécifique à la classe qui est duration (un entier), ce dernier permet d'indiquer la période pendant laquelle le statut est actif. La classe propose des méthodes permettant d'augmenter la durée, de la réduire ainsi qu'une méthode permettant d'ajuster les dégâts en prenant en compte le statut  vulnérable/Faiblesse. Les dégâts ajustés sont stockés. 

4- Représentation des cartes à l'aide d'une classe Card

	La classe Card est une classe abstraite représentant une carte. Elle possède les attributs de base tels que le nom de la carte (name), le coût en mana (cost), et une liste de cartes bannies (carteBanis). La liste carteBanis regroupe les noms de cartes qui ne vont pas dans le cimetière mais dans le deck de bannissement lorsqu'elles sont jouées. Il existe un constructeur, des getters et setters pour accéder et modifier ces attributs, ainsi que deux méthodes abstraites : "play()" pour jouer la carte avec des paramètres, et "applyEffects()" pour appliquer les effets des statuts sur les valeurs des cartes.

5 - Les différents types de cartes

	En étudiant les cartes qui sont à implémenter, nous avons constaté qu'il existait 4 grands types de cartes. En effet, on a certaines cartes qui attaquent, d'autres qui on un effet sur la mana, ainsi que certaines modifie l'armure du héro, alors que d'autres attaquent et donne de l'armure en même temps et les dernières sont celles qui donne un statut au héros ou au monstre ciblé par la carte. On a donc défini 5 classes, qui s'appellent respectivement : "Attack", "AttackAndDefense", "Defense", "Mana", et "StatusCard". Toutes ces classes hérite de la classe "Card".

	~ Classe Attack

	Permet de représenter une carte de type attaque. Elle possède l'attribut "damage" qui indique les dégâts infligés envers le monstre par cette dernière. Elle possède un constructeur qui permet de construire une carte d'attaque avec un nom, un cout en mana, et des dégâts spécifique. De plus, elle à des getters et setters pour modifier les valeurs de l'attribut. Enfin, il y a 2 méthodes, la première "ApplyEffect()" permettant de calculer les modifications des dommages en fonction des statuts du héros, ensuite elle applique les dégâts sur le monstre avec l'instruction "m.receivedDamage()". La seconde est la méthode "play()" qui permet de jouer la carte, tout en réduisant la mana du héros, en appliquant les effets sur le monstre et enlèvant la carte de la main du joueur . Cette dernière va au cimitière a part si elle fait partie des cartes bannies. Si c'est le cas, la carte rejoint le deck de bannissement. 

	~ Classe AttackAndDefense

	Permet de représenter une carte de type attaque et défense. Elle possède les mêmes caractéristiques que la classe Attack à l'exepction d'avoir un attribut "armure", qui augmente l'armure du héro. On retrouve cet attribut dans la méthode "play()" où on regarde si le héros à le statut fragile, si oui on fait la modification de la valeur du bouclier, sinon on l'applique directement au héros. 

	~ Classe Defense

	Permet de représenter une carte de défense. Elle possède l'attribut "armor" qui indique l'armure à fournir. Lorsqu'elle est jouée, l'armure du héro augmente avec un réduction si le statut fragile est présent sur le héros. Elle possède un constructeur permettant de créer la carte ainsi qu'une méthode "play()" qui joue la carte en réduisant la mana. On vérifie si le statut fragile est présent, si oui on diminue la valeur de 25%, sinon on l'applique directement. La carte est envoyée au cimetière ou au bannissement si c'est une carte qui doit être banni

	~ Classe Mana
	
	Permet de représenter une carte de mana. Elle possède l'attribut "mana" qui indique la quantité de mana à fournir. Lorsqu'elle est jouée, la réserve de mana du héros est augmenté ou diminuer. Elle est composé d'un constructeur qui construit une instance de la carte. Puis elle possède des getters and setters. Enfin elle à une méthode "play() qui joue la carte en vérifiant la mana du héro, puis la met au cimetière ou au deck de bannissement. 


	~ Classe StatusCard

	Permet de représenter une carte qui va interagir avec les statuts du héro. Elle possède un constructeur permettant d'instancier une carte de type StatusCard, ainsi que de getters and setters puis une méthode "play()" qui joue la carte (les effets à ajouter sont spécifiés dans les cartes qui hérite de cette classe), puis cette carte va au cimetière ou au deck de bannissement. 
	

6 - Les cartes
 
	Toutes les cartes héritent soit de la classe Attack, Defense, AttackAndDefense, Mana, StatutCard, cela dépend de l'effet de la carte. Si l'effet n'est pas complexe, on construit la carte à l'aide du constructeur et de l'instruction "super" puis appelle la méthode "super.play()" dans la méthode "play(...)". Si l'effet est complexe, on redéfini le méthode "play()" pour répondre aux exigences. Les précisions sont en commentaire dans chacune des classes.

	a) Cartes communes (dossier basicCard)

		Les cartes implémentées sont Defense, Enchainement, EpeeBoomerang, Frappe, FrappeDouble, FrappeDuPommeau, Heurt, Manchette, MemePasMal, Plaquage, Slime (carte venant de l'action d'un monstre), et VagueDeFer. Chacune a redéfini la méthode "play()" ou appeler la méthode "play()" présent dans la classe mère.

	b) Cartes non communes (dossier notCommonCard)

		Les cartes implémentées sont Desarmement, Enflammer, Hemokinesie, OndeDeChoc, Plaie(carte venant de l'action d'un monstre), Saigne, Tenacite, Uppercut, VoirRouge, VoleeDeCoups. Chacune a redéfini la méthode "play()" ou appeler la méthode "play()" présent dans la classe mère.

	c) Cartes rare (dossier rareCard)
		Les cartes implémentées sont Gourdin, Invincible et Offrance. Chacune a redéfini la méthode "play()" ou appeler la méthode "play()" présent dans la classe mère.

7 - Le deck 

	Cette classe permet de représenter les deck présent dans le jeu et de pouvoir mettre en place les mécaniques liés aux deck. Dans le jeu, nous avons 3 deck, le premier sert à piocher les cartes, le second sert de défausse et le dernier est la défausse des cartes bannies. Ces 3 decks sont représentés par des piles, nous utilisons la librairie Stack ainsi que les méthodes allant avec. Dans cette classe, nous avons 3 attributs qui sont 3 piles de Card permettant de représenter respectivement le deck, la défausse, et le deck des cartes bannies. Ces attributs ont leurs getters et setters. Intéressons nous maintenant aux méthodes ainsi qu'au constructeur

		- Deck() : Constructeur qui initialise les 3 piles de cartes
		- String toString() : Affiche les cartes du deck sous forme d'une chaîne de caractères
		- void addCard(Card card) : Ajoute une carte au deck 
    	- void removeCard(Card card) : Enlève une carte du deck 
		- boolean isEmpty() : Retourne vrai si le deck est vide 
		- static Deck initialDeck() : Initialise le deck avec une distribution de cartes prédéfinie au début de la partie qui est 5 cartes frappe, 4 cartes défense et 1 		  carte heurt

		a) Methodes liées au deck: 

		- int getSizeDeck() : Obtient la taille du deck
		- void shuffleDeck() : Mélange le deck 
    	- Card draw() : Pioche une carte du deck 
		- void addAllToDeck(List<Card> cards) : Ajoute toutes les cartes de la liste spécifiée au deck
   		- void RefillDeck(Deck d) : Transfère les cartes du cimetière au deck, tout en le mélangeant. On fait attention à respecter que le joueur à toujours 5 cartes 			  dans la main. Cela signifie qu'il faut toujours avoir au moins 5 cartes présents dans le cimetière lors de la pioche. Pour faire cela on utilise la condition 		  suivante : "if (d.isEmpty()|| d.getSizeDeck() < 5)".
		
		b) Methodes liées au cimetière

		- void addCimetery(Card carte) : Ajoute une carte au cimetière
		- void addAllToCimetery(List<Card> cards) : Ajoute toutes les cartes de la liste spécifiée au cimetière
		- void displayCimetery() : Affiche le cimetière (utilisé pour les tests)
    	- void refillDeckEndOfFightFromCimetery(Deck d) : Transfère les cartes du cimetière au deck, tout en le mélangeant. On fait attention aux noms des cartes, 			  puisque certaines cartes ne sont présente que pendant le combat (cad qu'il ne reste pas dans le deck quand on change de salle). On ajoute une instruction 			  permettant de ne pas remettre les cartes dans le deck si ces dernieres s'appellent plaie ou slime.
		- int getSizeCimetery() : Obtient la taille du cimetière

		c) Methodes liées au deck de banissement 

    	- void addAllBanish(List<Card> cards) : Ajoute toutes les cartes de la liste spécifiée au deck de bannissement
    	- void addBanish(Card carte) : Ajoute une carte au deck de bannissement
    	- void refillDeckEndOfFightFromBanish(Deck d) : Transfère les cartes du deck de bannissement au deck, tout en le mélangeant
 		- int getSizeBanish() : Obtient la taille du deck de bannissement


8 - Room 

	La classe Room représente une salle, pouvant être une salle de combat, noté 'C', une salle de repos, noté 'R', ou une salle du boss final, noté 'B'. Elle gère les actions du héros dans chaque salle et celle des monstres présents dans l'équipe de monstre. Le constructeur construit une instance de Room avec un héros, une équipe de monstres, un deck, un cimetière, un deck de bannissement ainsi qu'un scanner pour gérer les entrées de l'utilisateur.

		- death(): Méthode permettant la gestion de la mort du héros pour recommencer ou non une partie

		- startAdventure(): Lance l'aventure, gérant les différentes salles jusqu'à la fin du jeu

		- getRoomType(): Renvoie le type de salle actuelle (C pour combat, R pour repos, B pour boss final). Les salles sont représentées par un tableau de caractère

		- handleRoom(char roomType): Gère le type de salle actuelle en appelant la méthode appropriée. On utilise une suite de switch pour choisir entre combat, repos 			  et boss. A la fin, on incrémente le nombre de monstre afin d'augmenter la difficulté

		- doCombat(): Implémentation d'un combat entre le hero et une équipe de monstres, en utilisant des scanners pour intéragir avec l'utilisateur
 
		- doRest(): Implémentation de la récupération du héros dans la salle de repos

		- doFinalBoss(): C'est une copie de doCombat, la modification est dans l'équipe de monstre. Il doit exister un moyen permettant d'éviter la copie

		- chooseReward(): Permet au héros de choisir une carte parmi trois comme récompense après un combat et renvoie la carte choisie ou null. Si une carte est 			  choisie elle est ajoutée dans le deck

9 - TeamofMonstres 

	Cette classe permet de créer une équipe de monstres. Nous avons ajouté une méthode qui permet d'augmenter la difficulté en augmentant le nombre de monstre présent dans l'équipe. Nous commençons avec 1 monstre dans l'équipe puis montons a 2 dans la salle suivante et après on reste sur deux monstres jusqu'à la fin du jeu. Cette classe prend en attribut une liste de monstres et un entier représentant un nombre de monstre présent dans l'équipe. Ces attributs ont leurs getters et setters. 

		- TeamofMonstres() : Constructeur qui initialise une équipe de monstre avec une liste vide et le nombre initial de monstre égal à 1

		- int size() : Retourne la taille de la liste de monstre

		- void incrementNumberOfMonsters() : Incrémente le nombre de monstres de l'équipe. On l'utilise pour augmenter la difficulté du jeu

		- void addMonster(Monstre m) : Ajoute un monstre à la liste de l'équipe. On utilise les méthodes que nous proposes les listes 

		- boolean areDead() : Vérifie si tous les monstres de l'équipe sont mort

		- void showTeam() : Affiche tous les monstres présents dans l'équipe avec leurs points de vie et statuts

		- Monstre get(int indice) :  retourne le monstre à l'index passé en paramètre

		- void checkAndRemoveExpiredStatus() : Vérifie et retire les statuts expirés de tous les monstres de l'équipe. Pour ce faire, on utilise la méthode du même nom 		  mais déclaré dans le classe monstre 

		- void displayIntentions() : Affiche les intentions des prochains mouvements de l'équipe de monstres

		- void playTurn(Hero hero, Deck d) : L'équipe de monstres attaque le hero en jouant un tour, appliquant les actions de chaque monstre grâce à une boucle for

		- TeamofMonstres teamOfMonstreInitial() : Crée une équipe de monstres initiale de manière aléatoire grâce à la libraire Random() qui permet de générer des 			  nombres aléatoire et pioche parmit une liste représentant les monstres possibles

		- TeamofMonstres teamOfMonstresBoss() :  Crée une équipe spécifique pour la salle du boss, dans notre cas c'est 3 Machouilleurs

10 - Partie graphique

	Nous avons réussi à afficher les monstres présent dans l'équipe de monstre. Ainsi que les statuts du héros et des monstres. On a affiché 
les points de vie des entités. Pour le héros, nous avons affiché la main du joueur ainsi que la mana. Les images proviennent de :
https://slay-the-spire.fandom.com/wiki/Category:Cards







































