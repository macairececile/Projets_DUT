// @Authors Cecile Macaire & Th�o Frison 

package Model;

import java.util.Observable;

public class Cards extends Observable {
	
	public int numCard; // Num�ro de la carte
	public boolean returned; // Booleen pour savoir si la carte est retourn�e ou non 

	
	public Cards(int num, boolean r) {
		this.returned = r;
		this.numCard = num;
	}
	 /**
	  * 
	  * @return Le num�ro de la carte
	  */
	public int getNum() {
		return numCard;
	}
	
	/**
	 * D�finit le num�ro � une carte
	 * @param numCard Le num�ro � mettre sur la carte
	 */
	public void setNum(int numCard) {
		this.numCard = numCard;
		
		notifyObservers();
		setChanged();
	}
	
	/**
	 * Savoir si la carte est retourn�e
	 * @return Vrai si la cart� est face visible, faux sinon
	 */
	public boolean getReturned() {
		return returned;
	}
	
	/**
	 * D�finit si la carte est retourn�e ou non
	 * @param returned Mettre vrai si la carte va �tre retourn�e, faux pour l'autre cas
	 */
	public void setReturned(boolean returned) {
		this.returned = returned;
		
		notifyObservers();
		setChanged();
	}
	

	
	
	
	
	
	

}
