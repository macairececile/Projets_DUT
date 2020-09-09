// @Authors Cecile Macaire & Théo Frison 

package Model;

import java.util.Observable;

public class Cards extends Observable {
	
	public int numCard; // Numéro de la carte
	public boolean returned; // Booleen pour savoir si la carte est retournée ou non 

	
	public Cards(int num, boolean r) {
		this.returned = r;
		this.numCard = num;
	}
	 /**
	  * 
	  * @return Le numéro de la carte
	  */
	public int getNum() {
		return numCard;
	}
	
	/**
	 * Définit le numéro à une carte
	 * @param numCard Le numéro à mettre sur la carte
	 */
	public void setNum(int numCard) {
		this.numCard = numCard;
		
		notifyObservers();
		setChanged();
	}
	
	/**
	 * Savoir si la carte est retournée
	 * @return Vrai si la carté est face visible, faux sinon
	 */
	public boolean getReturned() {
		return returned;
	}
	
	/**
	 * Définit si la carte est retournée ou non
	 * @param returned Mettre vrai si la carte va être retournée, faux pour l'autre cas
	 */
	public void setReturned(boolean returned) {
		this.returned = returned;
		
		notifyObservers();
		setChanged();
	}
	

	
	
	
	
	
	

}
