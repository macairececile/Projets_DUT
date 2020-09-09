// @Authors Cecile Macaire & Théo Frison 

package Model;

import java.util.*;

public class Model extends Observable {
	ArrayList<Cards> packageCards = new ArrayList<Cards>(78); // Paquet de cartede base
	ArrayList<Cards> packagePlayer1 = new ArrayList<Cards>(); // Main du joueur 1
	ArrayList<Cards> packagePlayer2 = new ArrayList<Cards>(); // Main du joueur 2
	ArrayList<Cards> packagePlayer3 = new ArrayList<Cards>(); // Main du joueur 3
	ArrayList<Cards> packagePlayer4 = new ArrayList<Cards>(); // Main du joueur 4
	ArrayList<Cards> packageDog = new ArrayList<Cards>(6); // Cartes constituant le chien
	Players p = new Players(); // On instancie les joueurs de la partie

	public Model() {
		addCards(); // On crée le paquet de cartes de base
		cardsPlayers(); // On distribue les cartes au joueurs
	}

	/**
	 * 
	 * @return Le paquet de base
	 */
	public ArrayList<Cards> getPackageCards() {
		return packageCards;
	}

	/**
	 * 
	 * @return La main du joueur 1
	 */
	public ArrayList<Cards> getpackagePlayer1() {
		return packagePlayer1;
	}

	/**
	 * 
	 * @return La main du joueur 2
	 */
	public ArrayList<Cards> getpackagePlayer2() {
		return packagePlayer2;
	}

	/**
	 * 
	 * @return La main du joueur 3
	 */
	public ArrayList<Cards> getpackagePlayer3() {
		return packagePlayer3;
	}

	/**
	 * 
	 * @return La main du joueur 4
	 */
	public ArrayList<Cards> getpackagePlayer4() {
		return packagePlayer4;
	}

	/**
	 * 
	 * @return Les cartes du chien
	 */
	public ArrayList<Cards> getpackageDog() {
		return packageDog;
	}

	/**
	 * On crée toutes les cartes que l'on met dans le paquet de base
	 */
	public void addCards() {
		for (int i = 1; i <= 78; i++) {
			packageCards.add(new Cards(i, false));
		}
	}

	/**
	 * Le paquet de cartes est mélangé puis on distribue les cartes 3 à 3 aux joueurs
	 * et au chien. Enfin on trie les cartes de la main du joueur 1 et du chien.
	 */
	public void cardsPlayers() {

		java.util.Collections.shuffle(packageCards); //On mélange le paquet de cartes de base

		int nbDog = 0;
		Random rand = new Random();
		while (!packageCards.isEmpty()) {
			for (int i = 1; i <= p.players.size(); i++) {
				if (i == 1) {
					for (int j = 0; j < 3; j++) {
						distributeCard(packagePlayer1);
					}
				} else if (i == 2) {
					for (int j = 0; j < 3; j++) {
						distributeCard(packagePlayer2);
					}
				} else if (i == 3) {
					for (int j = 0; j < 3; j++) {
						distributeCard(packagePlayer3);
					}
				} else {
					for (int j = 0; j < 3; j++) {
						distributeCard(packagePlayer4);
					}

				}
				if (nbDog != 6) {
					int alea = rand.nextInt(100);
					if (alea <= 100) {
						distributeCard(packageDog);
					}
					nbDog++;
				}
			}
		}
		sortCards(packagePlayer1);
		sortCards(packageDog);
		setChanged();
		notifyObservers();

	}

	/**
	 * Distribue une carte à un joueur ou au chien
	 * 
	 * @param packagePlayer Main ou chien à qui donner la carte
	 */
	public void distributeCard(ArrayList<Cards> packagePlayer) {
		Cards c = packageCards.get(0);
		packagePlayer.add(c);
		packageCards.remove(0);
		setChanged();
		notifyObservers(c);
	}

	/**
	 * 
	 * @param cards Main d'un joueur
	 * @return Vrai si le petit est sec, faux sinon
	 */
	public boolean petitSec(ArrayList<Cards> cards) {
		boolean atout = false, petit = false, excuse = false;
		for (Cards c : cards) {
			if (c.getNum() <= 49 && c.getNum() >= 30) {
				atout = true;
			}
			if (c.getNum() == 29) {
				petit = true;
			}
			if (c.getNum() == 78) {
				excuse = true;
			}
		}
		if (petit && !atout && !excuse) {
			return true;
		} else {
			return false;
		}
	}

/**
 * Trie les cartes d'une main ou du chien
 * @param cards La main d'un joueur ou le chien
 */
	public void sortCards(ArrayList<Cards> cards) {

		int longueur = cards.size();
		Cards tampon = null;
		boolean permut;

		do {
			permut = false;
			for (int i = 0; i < longueur - 1; i++) {
				if (cards.get(i).getNum() > cards.get(i + 1).getNum()) {
					tampon = cards.get(i);
					cards.set(i, cards.get(i + 1));
					cards.set(i + 1, tampon);
					permut = true;
				}
			}
		} while (permut);
		setChanged();
		notifyObservers();
	}

}