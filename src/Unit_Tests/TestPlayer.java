package Unit_Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Player_Related_Stuff.Player;

public class TestPlayer {

	@Test
	public void testPlayerCreation()
	{
		Player player_default = new Player();
		assertEquals("John Doe" + "\n" + "M" + "\n" + 1, player_default.toString());
		Player player_only_health = new Player(10);
		assertEquals("John Doe" + "\n" + "M" + "\n" + 10, player_only_health.toString());
		Player player_only_name_and_gender = new Player("Johnny Storm", "M");
		assertEquals("Johnny Storm" + "\n" + "M" + "\n" + 1, player_only_name_and_gender.toString());
		Player player_all_variables = new Player("Susan Storm", "F", 15);
		assertEquals("Susan Storm" + "\n" + "F" + "\n" + 15, player_all_variables.toString());
		
	}
	
	@Test (expected = Exception.class)
	public void testInvalidPlayerCreation() throws Throwable
	{
		Exception e = new Exception();
		Player player_only_name = new Player ("Johnny Smith");
		assertEquals(e, player_only_name);
	}
	
	@Test
	public void testPlayerMethods()
	{
		Player the_Thing = new Player("Ben Grim", "F", 2);
		the_Thing.setGender("M");
		the_Thing.setHealth(20);
		the_Thing.setName("Ben Grimm");
		assertEquals("Ben Grimm" + "\n" + "M" + "\n" + 20, the_Thing.toString());
		the_Thing.setHealth(0);
		the_Thing = checkHealth(the_Thing);
		assertEquals(null, the_Thing);
	}
	
	public Player checkHealth(Player player)
	{
		if ( player.getHealth() <= 0 )
		{
			System.out.println("The hero" + (player.getGender().equals("M") ? "" : "ine") + " falls dead, succumbing to their wounds as the world fades to black around them...");
			System.out.println("GAME OVER...");
			return null;
		}
		return player;
	}

}
