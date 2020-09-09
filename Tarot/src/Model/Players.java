// @Authors Cecile Macaire & Théo Frison 

package Model;

import java.util.ArrayList;
import java.util.Observable;

public class Players extends Observable{

	public ArrayList<Integer> players = new ArrayList<Integer>(); //Liste de joueurs

	public Players() {
		players.add(1);
		players.add(2);
		players.add(3);
		players.add(4);	
	}
}