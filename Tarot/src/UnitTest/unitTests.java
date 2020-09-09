// @Authors Cecile Macaire & Théo Frison 

package UnitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import Model.*;

public class unitTests {

	@Test
	public void testPetitSec() {

		Model model = new Model();
		ArrayList<Cards> cards = new ArrayList<Cards>();
		cards = model.getpackagePlayer1();
		
		assertFalse(model.petitSec(cards));
	}

	@Test
	public void testaddCards() {
		Model model = new Model();
		model.addCards();
		
		assertEquals(model.getPackageCards().size(), 78);
	}

	@Test
	public void testCardsPlayers() {
		Model model = new Model();
		model.cardsPlayers();
		
		assertEquals(model.getPackageCards().size(), 0);
		assertEquals(model.getpackagePlayer1().size(), 18);
		assertEquals(model.getpackagePlayer2().size(), 18);
		assertEquals(model.getpackagePlayer3().size(), 18);
		assertEquals(model.getpackagePlayer4().size(), 18);
		assertEquals(model.getpackageDog().size(), 6);

	}

	
	@Test
	public void testSort() {
		Model model = new Model();
		ArrayList<Cards> cards = new ArrayList<Cards>();
		cards = model.getpackagePlayer1();
		model.sortCards(cards);
		
		for(int i = 0; i < 17; i++) {
			assertTrue(cards.get(i).numCard < cards.get(i+1).numCard);
		}
	}
}
