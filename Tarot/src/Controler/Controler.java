// @Authors Cecile Macaire & Théo Frison 

package Controler;

import Controler.Controler;
import Model.Model;
import View.*;

public class Controler {

	View v; // La vue de l'application
	Model m; // Le model de l'application

	public Controler(Model m) {
		this.m = m;
	}

	/**
	 * En fonction du bouton cliqué, agit en conséquence
	 * @param event événement d'un bouton
	 */
	public void action(Event event) {
		switch (event) {
		case PLAY:
			m.cardsPlayers();
			break;
		case PETITSEC:
			m.addCards();
			break;
		default:
			break;
		}
	}

}
