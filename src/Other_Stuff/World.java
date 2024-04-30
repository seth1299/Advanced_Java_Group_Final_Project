package Other_Stuff;

import Player_Related_Stuff.Item;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Player_Related_Stuff.Player;

public class World {

	private static List<Enemy> allEnemiesInTheGame;
	private static Player player;
	private static List<Room> rooms;
	private static String[] funnyKeyDiscardQuips = {"You then drop the key in between the cracks in the floor and it is lost forever.", 
			"After practicing some intense make-believe sword-fighting with the key, you accidentally throw it too far and you can't find it again.",
			"You decide to donate the key to some hungry orphans, maybe they can sell it for gold and get some food on the table? Either way, you aren't seeing that key again.",
			"You decide to leave the key under the welcome mat so that the next adventurer has less trouble finding it. You don't think you'll be needing that key again."};
	private static LinkedList<String> DIRECTIONS; // I cannot make this both final and static for some reason (unbeknowst to me), but please do not change this list.
	private static int playerRoomNumber = 1;
	private static Scanner sc = new Scanner (System.in);
	private static boolean showDescription = true;
	
	
	public static List<Enemy> loadEnemies(String filename) throws IOException {

		if (filename == null || filename.isEmpty())
			return null;

		Gson gson = new GsonBuilder().create();

		try (Reader reader = new FileReader(filename)) {
			JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
			List<Enemy> enemies = new ArrayList<>();

			for (JsonElement element : jsonArray) {
				JsonObject jsonObject = element.getAsJsonObject();
				Enemy enemy = gson.fromJson(jsonObject, Enemy.class);
				if (jsonObject.has("dialogue")) {
					enemy.setEnemyDialogue(jsonObject.get("dialogue").getAsString());
				}
				enemies.add(enemy);
			}

			return enemies;
		}
	}

	public static Enemy getEnemyByName(List<Enemy> enemies, String name) {

		if (enemies == null || name == null)
			return null;

		for (Enemy enemy : enemies) {
			if (enemy.getName().equals(name)) {
				return enemy;
			}
		}
		return null; // Enemy not found
	}


	public static Room getRoomByNum(List<Room> rooms, int roomNumber) {

		if (rooms == null)
			return null;

        for (Room room : rooms) {
            if (room.getRoomNum() == roomNumber) {
                return room;
            }
        }

		return null; // Room not found
	}

	public static List<Enemy> getAllEnemiesInTheGame() {
		return allEnemiesInTheGame;
	}

	public static void setAllEnemiesInTheGame(List<Enemy> enemies) {
		allEnemiesInTheGame = enemies;
	}

	public static void playerMovement(String response) {

		if (response == null || response.isBlank())
			return;
		
		Random rand = new Random();
		String[] funnyUnrecognizedCommandResponses = {(response + "? I don't know that one."), (response + "? I hardly know her!"), 
				(response + "? " + response + "?????? You kids and your made up lexicon...")};
		
		while ( !player.getOutOfCombatActions().contains(response) )
		{
			System.out.println("\n" + funnyUnrecognizedCommandResponses[rand.nextInt(0, funnyUnrecognizedCommandResponses.length)]);
			
			System.out.println("\nWhat would you like to do? Type \"help\" for a list of available commands.");
			
			response = sc.nextLine().trim().toUpperCase();
		}
		
		switch ( response )
		{
			case "MOVE":
			case "M":
				do
				{					
					if ( !DIRECTIONS.contains(response) && !response.equals("MOVE") && !response.equals("M"))
						System.out.println("I don't know where the direction \"" + response + "\" is.");
						
					System.out.println("\nMove to where? You can also type \"cancel\" to cancel the movement.");
					response = sc.nextLine().trim().toUpperCase();
					
				} while( !DIRECTIONS.contains(response));
				
				moveRooms(response);
				
				break;
				
			case "HELP":
			case "H":
				player.displayHelp();
				break;
				
			case "SUPERBRIEF":
			case "S":
				if ( showDescription )
				{
					System.out.println("\nRoom descriptions disabled.");
					showDescription = false;
				}
				else
					System.out.println("\nYou've already disabled the room descriptions, " + player.getName() + ". Did you mean to type VERBOSE?");
				break;
				
			case "VERBOSE":
			case "V":
				if ( showDescription )
				{
					System.out.println("\nThe room descriptions are already enabled, " + player.getName() + ". Did you mean to type SUPERBRIEF?");
				}
				else
				{
					System.out.println("\nRoom descriptions enabled.");
					showDescription = true;
				}
				break;
				
			case "LOOK":
			case "L":
				Room playRoom = getRoomByNum(rooms, playerRoomNumber);
				playRoom.display(showDescription);
				break;
				
			case "INVENTORY":
			case "I":
				player.printInventory();
				break;
				
			case "USE":
				player.useItem();
				break;
				
			case "DONTREMINDME":
			case "D":
				if ( player.getRemindMe() )
				{
					System.out.println("Inventory display when using items turned off.");
					player.remindMe(false);
				}
				else
					System.out.println("I'm already not reminding you about your inventory when using items, " + player.getName() + ".");
				break;
				
			case "REMINDME":
			case "R":
				if ( player.getRemindMe() )
					System.out.println("I'm already reminding you about your inventory when using items, " + player.getName() + ".");
				else
				{
					System.out.println("Inventory display when using items turned on.");
					player.remindMe(true);
				}
					
				break;
				
			default:
				break;
		}
		
	}

	public static void moveRooms(String response)
	{
		Room playRoom = getRoomByNum(rooms, playerRoomNumber);
		int destinationRoomNum = 0;
		switch (response) {
			case ("NW"):
			case ("NORTHWEST"):
				destinationRoomNum = playRoom.getExitNW();
				break;
			case ("N"):
			case ("NORTH"):
				destinationRoomNum = playRoom.getExitN();
				break;
			case ("NE"):
			case ("NORTHEAST"):
				destinationRoomNum = playRoom.getExitNE();
				break;
			case ("W"):
			case ("WEST"):
				destinationRoomNum = playRoom.getExitW();
				break;
			case ("E"):
			case ("EAST"):
				destinationRoomNum = playRoom.getExitE();
				break;
			case ("SW"):
			case ("SOUTHWEST"):
				destinationRoomNum = playRoom.getExitSW();
				break;
			case ("S"):
			case ("SOUTH"):
				destinationRoomNum = playRoom.getExitS();
				break;
			case ("SE"):
			case ("SOUTHEAST"):
				destinationRoomNum = playRoom.getExitSE();
				break;
			case ("UP"):
				destinationRoomNum = playRoom.getExitUP();
				break;
			case ("DN"):
			case ("DOWN"):
				destinationRoomNum = playRoom.getExitDN();
				break;

		}

		if (destinationRoomNum > 0) {
			
			Room destinationRoom = getRoomByNum(rooms, destinationRoomNum);
			if ( destinationRoom == null )
			{
				System.out.println("Can't find room #" + destinationRoomNum + "!");
				return;
				//return playRoom;
			}
				
			String destinationRoomRequiredKey = destinationRoom.getRequiredKey();
			Item required_key_as_item = player.getItemFromInventoryByName(destinationRoomRequiredKey);
			boolean isLocked = destinationRoom.getLocked();
			
			if (isLocked && destinationRoomRequiredKey != null && !destinationRoomRequiredKey.isEmpty()) 
			{
				String required_key_as_String = required_key_as_item.getName();
				// TODO: Check if this actually works.
				
				if (required_key_as_String.equals(destinationRoomRequiredKey)) 
				{
					Random randomNumber = new Random();
					int randInt = randomNumber.nextInt(0, funnyKeyDiscardQuips.length);
					System.out.println("You unlock the door! " + funnyKeyDiscardQuips[randInt]);
					player.removeItemFromInventory(required_key_as_item, 1);
					destinationRoom.display(showDescription);
					//return destinationRoom;
				} 
				
				else 
				{
					System.out.println("The room is locked. You need the " + destinationRoomRequiredKey + " to enter that room.");
					//return playRoom;
				}
				
			} 
			else 
			{
				destinationRoom.display(showDescription);
				//return destinationRoom;
			}
		}
		else if ( response.equals("CANCEL") || response.equals("C"))
		{
			System.out.println("Movement cancelled.");
		}
		else 
		{
			System.out.println("There are no exits in that direction.");
			//return playRoom;
		}
	}
	
	public static List<Room> loadRooms(String filename) throws IOException {

		if (filename == null || filename.isBlank())
			return null;

		Gson gson = new GsonBuilder().create();

		try (Reader reader = new FileReader(filename)) {
			JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
			List<Room> rooms = new ArrayList<>();

			for (JsonElement element : jsonArray) {
				JsonObject jsonObject = element.getAsJsonObject();
				Room room = gson.fromJson(jsonObject, Room.class);

				for (JsonElement element2 : jsonArray) {
					JsonObject jsonObject2 = element.getAsJsonObject();
					Room room2 = gson.fromJson(jsonObject, Room.class);
					room2.setRoomName(jsonObject2.get("name").getAsString());
					room2.setRoomNum(jsonObject2.get("roomNum").getAsInt());
					room2.setExitN(jsonObject2.get("exit n").getAsInt());
					room2.setExitNW(jsonObject2.get("exit nw").getAsInt());
					room2.setExitNE(jsonObject2.get("exit ne").getAsInt());
					room2.setExitW(jsonObject2.get("exit w").getAsInt());
					room2.setExitE(jsonObject2.get("exit e").getAsInt());
					room2.setExitSW(jsonObject2.get("exit sw").getAsInt());
					room2.setExitS(jsonObject2.get("exit s").getAsInt());
					room2.setExitSE(jsonObject2.get("exit se").getAsInt());
					room2.setExitUP(jsonObject2.get("exit up").getAsInt());
					room2.setExitDN(jsonObject2.get("exit dn").getAsInt());
					if(jsonObject2.get("requiredKey").getAsString().isEmpty()) {
						room2.setRequiredKey(null);
					}
					else {
						room2.setRequiredKey(jsonObject2.get("requiredKey").getAsString());
					}

					if(jsonObject2.get("roomItemsFilepath").getAsString().isEmpty()) {
						room2.setRoomItems(null);

					}
					else {
						room2.addItemsToRoomFromJsonFile(jsonObject2.get("roomItemsFilepath").getAsString());
						//set room items here
						//continue;
					}
					if(jsonObject2.get("enemyFilepath").getAsString().isEmpty()) {
						room2.setEnemies(null);
					}
					else {
						//set room enemies here
						continue;
					}
					if(jsonObject2.get("description").getAsString().isEmpty()) {
						room2.setRoomDescription(null);
					}
					else {
						room2.setRoomDescription(jsonObject2.get("description").getAsString());
					}
					rooms.add(room2);

				}

			}
			return rooms;
		}
	}

		public static void main (String[] args) {
			
			DIRECTIONS = new LinkedList<>();
			
			DIRECTIONS.add("N");
			DIRECTIONS.add("NORTH");
			DIRECTIONS.add("NW");
			DIRECTIONS.add("NORTHWEST");
			DIRECTIONS.add("NE");
			DIRECTIONS.add("NORTHEAST");
			DIRECTIONS.add("W");
			DIRECTIONS.add("WEST");
			DIRECTIONS.add("E");
			DIRECTIONS.add("EAST");
			DIRECTIONS.add("S");
			DIRECTIONS.add("SOUTH");
			DIRECTIONS.add("SW");
			DIRECTIONS.add("SOUTHWEST");
			DIRECTIONS.add("SE");
			DIRECTIONS.add("SOUTHEAST");
			DIRECTIONS.add("U");
			DIRECTIONS.add("UP");
			DIRECTIONS.add("DN");
			DIRECTIONS.add("DOWN");
			DIRECTIONS.add("C");
			DIRECTIONS.add("CANCEL");
			
			
			try {
				setAllEnemiesInTheGame(loadEnemies("src/Other_Stuff/enemies.json"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				setRooms(loadRooms("src/Other_Stuff/rooms.json"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			String response = "", nameString = "", gender = "";

			do {
				StringBuilder name = new StringBuilder("");
				System.out.println("Hello! What is your name?");
				response = sc.nextLine();
				
				if ( response.isBlank() )
				{
					System.out.println("Your name cannot be blank.");
					continue;
				}
				
				name.append(response.charAt(0));
				nameString = name.toString().toUpperCase() + response.substring(1).toLowerCase();

				System.out.println("Your name is " + nameString + "? ('Y' for yes, 'N' for no)");
				response = sc.nextLine();
			} while (!response.trim().equalsIgnoreCase("Y") && !response.trim().equalsIgnoreCase("YES"));

			response = "";

			do {
				System.out.println("And are you (M)ale, (F)emale, or (N)onbinary?");
				gender = sc.nextLine().trim().toUpperCase();

				switch (gender) {
					case "M":
					case "MALE":
						gender = "M";
						System.out.println("So you're a man? (Y/N)");
						break;
					case "F":
					case "FEMALE":
						gender = "F";
						System.out.println("So you're a woman? (Y/N)");
						break;
					case "N":
					case "NONBINARY":
						gender = "NB";
						System.out.println("So you're nonbinary? (Y/N)");
						break;
					default:
						System.out.println("Sorry, I didn't recognize that. Please enter 'M', 'F', or 'N'.");
						continue;
				}

				response = sc.nextLine().trim().toUpperCase();

			} while (!response.trim().equalsIgnoreCase("Y") && !response.trim().equalsIgnoreCase("YES"));

			response = "";

			player = new Player(nameString, gender);
			player.addItemsToInventoryFromJsonFile("src/Other_Stuff/Individual_Item_Shit/starting_inventory.json");

			Room playerRoom = getRoomByNum(rooms, playerRoomNumber);

			//Game Begins Here
			System.out.println("\nYou aren't sure how long you've been in this cell.\nYou're pretty sure that it's more than a week, less than a month.\n" +
					"When you hear a ruckus start somewhere above you, you're pretty sure the chaos you hear is actually in your head, until the dungeon guards run up the stairs.\n" +
					"A guard's helmet comes flying down the stairs, skips off the opposite wall, and slams into your cell door.\n" +
					"The door pops open, and you're sure you've gone mad until you see the gaping hole in the helmet\n" +
					"No, if you'd gone mad the last thing that you would have imagined was being freed from your cell into a castle under attack.\n" +
					"You immediately burst out of the cell, look both ways down the hall, and decide to hide in the storage room until the noise dies down.\n");

			playerRoom.display(showDescription);
			
			while(player.getIsDead()==false && playerRoom.getRoomNum()!=100) 
			{
				System.out.println("\nWhat would you like to do? Type \"help\" for a list of available commands.");
				response = sc.nextLine().trim().toUpperCase();
				playerMovement(response);
			}
			sc.close();
		}

	public static List<Room> getRooms() {
		return rooms;
	}

	public static void setRooms(List<Room> rooms) {
		World.rooms = rooms;
	}

	}



