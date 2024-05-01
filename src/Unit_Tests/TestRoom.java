package Unit_Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Other_Stuff.Room;

public class TestRoom 
{
	
	@Test
	public void testRoomCreation()
	{
		Room room_default = new Room();
		assertNotNull(room_default);
		System.out.println(room_default);
		Room room_1 = new Room(0, "Test room", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, false, null, null, null, "An empty room.");
		assertNotNull(room_1);
		System.out.println(room_1);
	}

}
