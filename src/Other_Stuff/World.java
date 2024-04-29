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
import java.util.List;
import java.util.Scanner;

import Player_Related_Stuff.Player;

public class World {

	private static List<Enemy> allEnemiesInTheGame;
	private static Player player;
	private static List<Room> rooms;

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

		// TODO: Figure out how to get the room by number.

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

	public static Room playerMovement(List<Room> rooms, int playerRoom, String response) {

		if (rooms == null || response == null)
			return null;

		// TODO: Figure out how to move the player.

		//boolean locked = false;

		Room playRoom = getRoomByNum(rooms, playerRoom);
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
			boolean isLocked = destinationRoom.getLocked();
			if (isLocked) {
				// TODO: Check if this actually works.
				if (player.getItemFromInventory(destinationRoom.getRequiredKey()) != null) {
					destinationRoom.display();
					return destinationRoom;
				} else {
					System.out.println("You need to find the key to enter that room!");
					return playRoom;
				}
			} else {
				destinationRoom.display();
				return destinationRoom;
			}
		} else {
			System.out.println("There are no exits in that direction.");
			return playRoom;
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
					//TODO The Constructor requires key to be an item prior to running, not currently functional
					/*
					if(!jsonObject2.get("requiredKey").getAsString().isEmpty()) {
						//this doesn't work, needs to be a key on set rather than a string
						room2.setRequiredKey(jsonObject2.get("requiredKey"));
					}
					else {
						room2.setRequiredKey(null);
					}
					if(!jsonObject2.get("roomItemsFilepath").getAsString().isEmpty()) {
						//set room items here
						continue;
					}
					if(!jsonObject2.get("enemyFilepath").getAsString().isEmpty()) {
						//set room enemies here
						continue;
					}
					room2.setRoomDescription(jsonObject2.get("description").getAsString());
					rooms.add(room2);

					 */
				}

			}
			return rooms;
		}
	}

		public static void main (String[] args) {
			Scanner sc = new Scanner(System.in);
			//TODO is there a reason this isn't just List<Enemy> enemies = loadEnemies(filename);?
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

			String response = "", name = "", gender = "";

			do {
				System.out.println("Hello! What is your name?");
				name = sc.nextLine();

				System.out.println("Your name is " + name + "? ('Y' for yes, 'N' for no)");
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

			player = new Player(name, gender);

			// TODO: Updated, need to confirm that this works once loading functions run
			Room playerRoom = getRoomByNum(rooms, 1);

			//Game Begins Here
			System.out.println("You aren't sure how long you've been in this cell.\nYou're pretty sure that it's more than a week, less than a month.\n" +
					"When you hear a ruckus start somewhere above you, you're pretty sure the chaos you hear is actually in your head until the dungeon guards run up the stairs\n" +
					"A guards helmet comes flying down the stairs, skips off the opposite wall, and slams into your cell door.\n" +
					"The door pops open, and you're sure you've gone mad until you see the gaping hole in the helmet\n" +
					"No, if you'd gone mad the last thing that you would have imagined was being freed from your cell into a castle under attack.\n" +
					"You immediately burst out of the cell, look both ways down the hall, and decide to hide in the storage room until the noise dies down.");

			// TODO: Need to confirm this works once loading functions run.
			playerRoom.display();
			response = "";
			response = sc.nextLine().trim().toUpperCase();
			sc.close();
		}

	public static List<Room> getRooms() {
		return rooms;
	}

	public static void setRooms(List<Room> rooms) {
		World.rooms = rooms;
	}

	}



