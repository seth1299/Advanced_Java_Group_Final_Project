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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Player_Related_Stuff.Player;

public class World {

	private static List<Enemy> allEnemiesInTheGame;
	private static Player player;
	private static List<Room> rooms;
	private static String[] jokes = {"Why did the programmer always confuse Halloween and Christmas? Because OCT 31 == DEC 25.", 
	"A SQL query goes into a bar, walks up to two tables and asks, \"Can I join you?\"", "What’s the object-oriented way to become wealthy? Inheritence.",
	"ASCII stupid question, get a stupid ANSI.", "A programmer is sent to the grocery store with instructions to “Buy butter and see whether they have "
	+ "eggs, and if they do, then buy 10. Returning with 10 butters, the programmer says \"They had eggs.\"", "Why did the programmer quit his job? Because "
	+ "he didn't get arrays.", "UNIX is user friendly. It’s just very particular about who its friends are.", "There are 2 types of people in the world. "
	+ "Those who can extrapolate from incomplete data...", "3 Errors walk into a bar. The barman says, \"Normally I’d Throw you all out, but tonight "
	+ "I’ll make an Exception.\"", "What's the best thing about UDP jokes? I don't care if you get them.", "What's the best part about TCP jokes? I get to keep telling them"
	+ " until you get them.", "Hardware (noun): the part of a computer that you can kick.", "What's a programmer's favorite drinking establishment? Foo Bar."};
	private static LinkedList<String> DIRECTIONS; // I cannot make this both final and static for some reason (unbeknowst to me), but please do not change this list.
	private static LinkedList<Integer> alreadyVisitedRooms = new LinkedList<>();
	private static int playerRoomNumber = 1, lastPlayerRoomNumber = 1;
	private static Scanner sc = new Scanner (System.in);
	private static boolean showDescription = true;
	private static Instant startTime, stopTime;
	final World thisWorld = this;
	
	
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
			
			System.out.println("\n> What would you like to do? Type \"help\" for a list of available commands.");
			System.out.print("> ");
			
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
						
					System.out.println("\nMove to where? You can also type \"cancel\" to cancel the movement, or type a number to quickly travel to that floor (provided that you have already visited that location). 0 would be a basement, and 5 would be a roof, for example, with each number in between being the 1st floor, 2nd floor, etc.");
					System.out.print("> ");
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
					System.out.println("Room descriptions disabled.");
					showDescription = false;
				}
				else
					System.out.println("You've already disabled the room descriptions, " + player.getName() + ". Did you mean to type VERBOSE?");
				break;
				
			case "VERBOSE":
			case "V":
				if ( showDescription )
				{
					System.out.println("The room descriptions are already enabled, " + player.getName() + ". Did you mean to type SUPERBRIEF?");
				}
				else
				{
					System.out.println("Room descriptions enabled.");
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
				
			case "EQUIPMENT":
				player.printEquipment();
				break;
				
			case "EQUIP":
			case "E":
				if ( showDescription )
					player.printInventory();
				System.out.println("\n> Equip what item?");
				System.out.print("> ");
				response = sc.nextLine().trim();
				Item item = player.getItemFromInventoryByName(response);
				if ( item == null )
				{
					System.out.println("There isn't a \"" + response + "\" in your inventory.");
				}
				else
				{
					if ( item.getType().equals(Item.ItemType.ARMOR))
					{
						player.equipItem(item, Player.EquipmentSlot.ARMOR);
					}
					else if ( item.getType().equals(Item.ItemType.WEAPON))
					{
						player.equipItem(item, Player.EquipmentSlot.WEAPON);
					}
					else
					{
						System.out.println("I don't think you can equip the " + item.getName() + ".");
					}
				}
				break;
					
			case "UNEQUIP":
			case "UN":
				System.out.println("\n> Unequip what item?");
				System.out.print("> ");
				response = sc.nextLine().trim();
				Item item2 = player.getItemFromInventoryByName(response);
				
				if ( item2 == null )
				{
					System.out.println("There isn't a \"" + response + "\" in your inventory.");
				}
				else
				{
					if ( item2.getType().equals(Item.ItemType.ARMOR))
					{
						player.unequipItem(Player.EquipmentSlot.ARMOR);
					}
					else if ( item2.getType().equals(Item.ItemType.WEAPON))
					{
						player.unequipItem(Player.EquipmentSlot.WEAPON);
					}
					else
					{
						System.out.println("I don't think you can equip the " + item2.getName() + ".");
					}
				}
				
				break;
				
			case "JOKE":
			case "J":
				int randInt = rand.nextInt(0, jokes.length);
				System.out.println(jokes[randInt]);
				break;
				
			case "USE":
			case "U":
				player.useItem();
				break;
				
			case "DIAGNOSE":
			case "D":
				System.out.println("Current health: " + player.getHealth());
				break;
				
			case "DONTREMINDME":
			case "DONT":
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
				
			case "CLEAR":
			case "CLS":
				System.out.println("Yeah, you and me both want to clear the screen, pal. But Eclipse decided to be a piece of shit and only have the \"clear\" button"
						+ " work if you right click inside of the terminal.");
				break;
				
			case "TIME":
			case "T":
				Instant now = Instant.now();
				long numSeconds = Duration.between(startTime, now).getSeconds();
				long numMinutes = 0l, numSecondsClone = numSeconds, numHours = 0l;
				System.out.println("You've been playing for " + numSeconds + " seconds!");
				while ( numSecondsClone >= 60 )
				{
					numMinutes++;
					numSecondsClone -= 60;
				}
				long numMinutesClone = numMinutes;
				while ( numMinutesClone >= 60 )
				{
					numHours++;
					numMinutesClone -= 60;
				}
				if ( numMinutes > 0 && numHours == 0 )
					System.out.println("That's " + numMinutes + " minutes and " + numSecondsClone + " seconds!");
				else if ( numHours > 0 )
					System.out.println("That's " + numHours + " hours, " + numMinutesClone + " minutes, and " + numSecondsClone + " seconds!");
				break;
				
			case "ZORK":
				System.out.println("At your service!");
				break;
				
			default:
				break;
		}
		
	}
	
	public static void moveToRoomNumber(int roomNumber)
	{
		Room destinationRoom = getRoomByNum(rooms, roomNumber);
		playerRoomNumber = roomNumber;
		destinationRoom.display(showDescription);
	}

	public static void moveRooms(String response)
	{
		if ( !alreadyVisitedRooms.contains(playerRoomNumber))
			alreadyVisitedRooms.add(playerRoomNumber);
		
		lastPlayerRoomNumber = playerRoomNumber;
		
		Room playRoom = getRoomByNum(rooms, playerRoomNumber);
		if ( playRoom == null )
		{
			System.out.println("Can't find room #" + playerRoomNumber + "!");
			return;
		}	
		
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
			case("U"):
				destinationRoomNum = playRoom.getExitUP();
				break;
			case ("DN"):
			case ("DOWN"):
				destinationRoomNum = playRoom.getExitDN();
				break;
			case("0"):
				if ( alreadyVisitedRooms.contains(10))
				{
					System.out.println("You quickly make your way back to the basement stairs.");
					destinationRoomNum = 10;
				}
				else
					System.out.println("Nice try.");
				break;
			case("1"):
				if ( alreadyVisitedRooms.contains(8))
				{
					System.out.println("You quickly make your way back to the dungeon stairs.");
					destinationRoomNum = 8;
				}
				else
					System.out.println("Nice try.");
				break;
			case("2"):
				if ( alreadyVisitedRooms.contains(33))
				{
					System.out.println("You quickly make your way back to the ground level stairs.");
					destinationRoomNum = 33;
				}
				else
					System.out.println("Nice try.");
				break;
			case("3"):
				if ( alreadyVisitedRooms.contains(54))
				{
					System.out.println("You quickly make your way back to the upper keep stairs.");
					destinationRoomNum = 54;
				}
				else
					System.out.println("Nice try.");
				break;
			case("4"):
				if ( alreadyVisitedRooms.contains(56))
				{
					System.out.println("You quickly make your way back to the secret stairs.");
					destinationRoomNum = 56;
				}
				else
					System.out.println("Nice try.");
				break;
			case("CANCEL"):
			case("C"):
				return;
			

		}

		if (destinationRoomNum > 0) {
			
			Room destinationRoom = getRoomByNum(rooms, destinationRoomNum);
			if ( destinationRoom == null )
			{
				System.out.println("Can't find room #" + destinationRoomNum + "!");
				return;
			}
				
			String destinationRoomRequiredKey = destinationRoom.getRequiredKey();
			Item required_key_as_item = player.getItemFromInventoryByName(destinationRoomRequiredKey);
			boolean isLocked = destinationRoom.getLocked();
			
			if (isLocked && destinationRoomRequiredKey != null && !destinationRoomRequiredKey.isEmpty() && required_key_as_item != null) 
			{
				String required_key_as_String = required_key_as_item.getName();
				
				if (required_key_as_String.equals(destinationRoomRequiredKey)) 
				{
					System.out.println("You unlock the door using the " + destinationRoomRequiredKey + "!");
					destinationRoom.display(showDescription);
					playerRoomNumber = destinationRoomNum;
				} 
				
				else 
					System.out.println("The room is locked. You need the " + destinationRoomRequiredKey + " to enter that room.");
				
			}
			else if ( isLocked && destinationRoomRequiredKey == null )
			{
				System.out.println("Target room is locked, but the 'destinationRoomRequiredKey' variable is null. Returning to previous room... (Target room: " + destinationRoom + ")");
			}
			else if ( isLocked && destinationRoomRequiredKey.isBlank() )
			{
				System.out.println("Target room is locked, but the 'destinationRoomRequiredKey' variable is blank/empty. Returning to previous room... (Target room: " + destinationRoom + ")");
			}
			else if ( isLocked && required_key_as_item == null )
			{
				System.out.println("That room is locked, but you don't have the key. You need the " + destinationRoomRequiredKey + " to unlock this door.");
			}
			else 
			{
				playerRoomNumber = destinationRoomNum;
				destinationRoom.display(showDescription);
			}
				
		}
		
		else if ( response.equals("CANCEL") || response.equals("C"))
			System.out.println("Movement cancelled.");
		
		else if ( !response.equals("1") && !response.equals("2") && !response.equals("3") && !response.equals("4"))
			System.out.println("There are no exits in that direction.");
		
		
		
	}
	
	//@SuppressWarnings("unused")
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
					room.setPlayer(player);
					room.setRoomName(jsonObject.get("name").getAsString());
					room.setRoomNum(jsonObject.get("roomNum").getAsInt());
					room.setExitN(jsonObject.get("exit n").getAsInt());
					room.setExitNW(jsonObject.get("exit nw").getAsInt());
					room.setExitNE(jsonObject.get("exit ne").getAsInt());
					room.setExitW(jsonObject.get("exit w").getAsInt());
					room.setExitE(jsonObject.get("exit e").getAsInt());
					room.setExitSW(jsonObject.get("exit sw").getAsInt());
					room.setExitS(jsonObject.get("exit s").getAsInt());
					room.setExitSE(jsonObject.get("exit se").getAsInt());
					room.setExitUP(jsonObject.get("exit up").getAsInt());
					room.setExitDN(jsonObject.get("exit dn").getAsInt());
					
					if(jsonObject.get("requiredKey").getAsString().isEmpty())
						room.setRequiredKey(null);
					else
						room.setRequiredKey(jsonObject.get("requiredKey").getAsString());

					if(jsonObject.get("roomItemsFilepath").getAsString().isEmpty())
						room.setRoomItems(null);
					else
						room.addItemsToRoomFromJsonFile(jsonObject.get("roomItemsFilepath").getAsString());
					
					if(jsonObject.get("enemyFilepath").getAsString().isEmpty())
						room.setEnemies(null);
					else
						room.addEnemiesToRoomFromJsonFile(jsonObject.get("enemyFilepath").getAsString());
					
					if(jsonObject.get("description").getAsString().isEmpty())
						room.setRoomDescription(null);
					else
						room.setRoomDescription(jsonObject.get("description").getAsString());
					
					rooms.add(room);
				}
			return rooms;
		}
	}

	public static void clearScreen() {
		for(int i=0;i<100;i++) {
			System.out.println();
		}
	}

	public static void titlePrint() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
		clearScreen();
		System.out.println(" _____           _   _       ___  ____     _      _            _    ");
		System.out.println("/  __ \\         | | | |      |  \\/  (_)   | |    | |          | |   ");
		System.out.println("| /  \\/ __ _ ___| |_| | ___  | .  . |_ ___| |_ __| | __ _ _ __| | __");
		System.out.println("| |    / _` / __| __| |/ _ \\ | |\\/| | / __| __/ _` |/ _` | '__| |/ /");
		System.out.println("| \\__/\\ (_| \\__ \\ |_| |  __/ | |  | | \\__ \\ || (_| | (_| | |  |   < ");
		System.out.println(" \\____/\\__,_|___/\\__|_|\\___| \\_|  |_/_|___/\\__\\__,_|\\__,_|_|  |_|\\_\\");
		TimeUnit.SECONDS.sleep(4);
		System.out.println("\nCopyright 2024 by Seth Grimes and Brianna Hacker. All rights reserved.\nCastle Mistdark is a trademark of Seth Grimes and Brianna Hacker.\nRelease 1 / Serial Number 8675309");
	}

		public static void main (String[] args) throws InterruptedException {
			
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
			DIRECTIONS.add("0");
			DIRECTIONS.add("1");
			DIRECTIONS.add("2");
			DIRECTIONS.add("3");
			DIRECTIONS.add("4");

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

			Room playerRoom = getRoomByNum(rooms, playerRoomNumber);
			alreadyVisitedRooms.add(playerRoomNumber);

			titlePrint();

			//Game Begins Here
			System.out.println("\nYou aren't sure how long you've been in this cell.\nYou're pretty sure that it's more than a week, less than a month.\n" +
					"When you hear a ruckus start somewhere above you, you're pretty sure the chaos you hear is actually in your head, until the dungeon guards run up the stairs.\n" +
					"A guard's helmet comes flying down the stairs, skips off the opposite wall, and slams into your cell door.\n" +
					"The door pops open, and you're sure you've gone mad until you see the gaping hole in the helmet.\n" +
					"No, if you'd gone mad the last thing that you would have imagined was being freed from your cell into a castle under attack.\n" +
					"You immediately burst out of the cell, look both ways down the hall, and decide to hide in the storage room until the noise dies down.");

			playerRoom.display(showDescription);
			//StopWatch watch = new StopWatch();
			
			startTime = Instant.now();
			while(player.getIsDead()==false && playerRoomNumber !=100) 
			{
				if ( player.getHasFled() )
				{
					if ( !alreadyVisitedRooms.contains(playerRoomNumber))
						alreadyVisitedRooms.add(playerRoomNumber);
					moveToRoomNumber(lastPlayerRoomNumber);
					player.setHasFled(false);;
				}
				System.out.println("\n> What would you like to do? Type \"help\" for a list of available commands.");
				System.out.print("> ");
				response = sc.nextLine().trim().toUpperCase();
				playerMovement(response);
			}
			
			stopTime = Instant.now();
			long numSeconds = Duration.between(startTime, stopTime).getSeconds();
			long numMinutes = 0l, numSecondsClone = numSeconds, numHours = 0l;
			System.out.println("You played for " + numSeconds + " seconds!");
			while ( numSecondsClone >= 60 )
			{
				numMinutes++;
				numSecondsClone -= 60;
			}
			long numMinutesClone = numMinutes;
			while ( numMinutesClone >= 60 )
			{
				numHours++;
				numMinutesClone -= 60;
			}
			if ( numMinutes > 0 && numHours == 0 )
				System.out.println("That's " + numMinutes + " minutes and " + numSecondsClone + " seconds!");
			else if ( numHours > 0 )
				System.out.println("That's " + numHours + " hours, " + numMinutesClone + " minutes, and " + numSecondsClone + " seconds!");
			
			System.out.println("Thanks for playing Castle Mistdark!");
			response = sc.nextLine();
			sc.close();
		}

	public static List<Room> getRooms() {
		return rooms;
	}

	public static void setRooms(List<Room> rooms) {
		World.rooms = rooms;
	}

	public static LinkedList<Integer> getAlreadyVisitedRooms() {
		return alreadyVisitedRooms;
	}
	
	public static int getPlayerRoomNumber()
	{
		return playerRoomNumber;
	}

	}



