package Unit_Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import Player_Related_Stuff.Player;
import Other_Stuff.Enemy;
import Other_Stuff.GameEngine;
import Other_Stuff.NPC;
import Other_Stuff.Room;
import Other_Stuff.World;

public class TestRoom 
{
	
	@Test
	public void testRoomCreation()
	{
		Player player_default = new Player();
		Room room_default = new Room();
		assertNotNull(room_default);
		System.out.println(room_default);
		Room room_1 = new Room(player_default, 0, "Test room", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, false, null, null, null, "An empty room.");
		assertNotNull(room_1);
		System.out.println(room_1);
	}

}
