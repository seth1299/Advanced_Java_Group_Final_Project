package Unit_Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import Player_Related_Stuff.Player;
import Player_Related_Stuff.Player.PlayerClass;
import Other_Stuff.Enemy;
import Other_Stuff.GameEngine;
import Other_Stuff.NPC;
import Other_Stuff.World;

public class TestPlayer {
	
	/**
	 * 
	 * Any method marked with "@Ignore" will be ignored and not run when testing, so
	 * make sure to comment out that tag if you want to actually run one of the tests.
	 * 
	 */
	
	private static List<Enemy> enemies;
	
	/**
	 * Tests the creation of a Player object with a default constructor.
	 */
	@Test
	@Ignore
	public void testPlayerCreation()
	{
		Player player_default = new Player();
		assertEquals("Name: John Doe" + "\n" + "Gender: M" + "\n" + "Player class: Warrior" + "\n" + "Health: " + 100, player_default.toString());		
	}

	
	/**
	 * Tests some of the methods of the Player class by using a Player object to access them.
	 */
	@Test
	@Ignore
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
	@Ignore
	public void testPlayerInventory()
	{
		Player Sally_McKnight = new Player("Sally McKnight", "F", PlayerClass.MAGE);
		Sally_McKnight.addItemsToInventoryFromJsonFile("items.json");
		Sally_McKnight.printInventory();
	}
	
	
	/**
	 * Specifically tests the player's spell list.
	 */
	@Test
	@Ignore
	public void testPlayerSpells()
	{
		Player Merlin = new Player("Merlin", "M", PlayerClass.MAGE);
		Merlin.displaySpellList();
		Player Arthur = new Player("Arthur", "M", PlayerClass.WARRIOR);
		Arthur.displaySpellList(); // Should not display anything
	}
	
	
	
	@Test
	@Ignore
	public void testNPCCreation()
	{
		Player player = new Player();
		NPC npc = new NPC("Test NPC", "src/Dialogues/Tutorial_NPC_dialogue.json");
        npc.createDialogueFromJsonFile(player);
        npc.printDialogue(player);
	}
	
	
	@Test
	@Ignore
	public void testPlayerFight()
	{
		Player Fighter_McFighterson = new Player("Fighty McFighterson", "M", PlayerClass.WARRIOR);
		try {
            enemies = World.loadEnemies("src/Other_Stuff/enemies.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
		Enemy enemy = new Enemy("Giant Rat", 10, 1);
		Enemy bat = new Enemy("Bat", 5, 5);
		Enemy bandit = World.getEnemyByName(enemies, "Bandit");
		enemies.add(enemy);
		enemies.add(bat);
		enemies.add(bandit);
		GameEngine.startFight(Fighter_McFighterson, enemies);
	}
	
}
