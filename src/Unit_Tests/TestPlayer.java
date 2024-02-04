package Unit_Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Player_Related_Stuff.Item;
import Player_Related_Stuff.Item.ItemType;
import Player_Related_Stuff.Player;
import Player_Related_Stuff.Player.PlayerClass;

public class TestPlayer {
	
	/**
	 * Tests the creation of a Player object with a default constructor.
	 */
	@Test
	public void testPlayerCreation()
	{
		Player player_default = new Player();
		assertEquals("Name: John Doe" + "\n" + "Gender: M" + "\n" + "Player class: Warrior" + "\n" + "Health: " + 100, player_default.toString());		
	}
	
	/**
	 * Tests some of the methods of the Player class by uusing a Player object to access them.
	 */
	@Test
	public void testPlayerMethods()
	{
		Player the_Thing = new Player("Ben Grim", "F", PlayerClass.WARRIOR);
		the_Thing.setGender("M");
		the_Thing.setHealth(20);
		the_Thing.setName("Ben Grimm");
		assertEquals("Name: Ben Grimm" + "\n" + "Gender: M" + "\n" + "Player class: Warrior" + "\n" + "Health: " + 20, the_Thing.toString());
	}
	
	/**
	 * Specifically tests the player's inventory.
	 */
	@Test
	public void testPlayerInventory()
	{
		Player Sally_McKnight = new Player("Sally McKnight", "F", PlayerClass.MAGE);
		Sally_McKnight.addItemsToInventoryFromTextFile("items.txt");
		Sally_McKnight.printInventory();
	}
	
	/**
	 * Specifically tests the player's spell list.
	 */
	@Test
	public void testPlayerSpells()
	{
		Player Merlin = new Player("Merlin", "M", PlayerClass.MAGE);
		Merlin.displaySpellList();
		Player Arthur = new Player("Arthur", "M", PlayerClass.WARRIOR);
		Arthur.displaySpellList(); // Should not display anything
	}
}
