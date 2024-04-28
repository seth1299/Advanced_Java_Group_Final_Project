package Other_Stuff;

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
	
	private static List<Enemy> enemies;
	
	public static List<Enemy> loadEnemies(String filename) throws IOException {
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
        for (Enemy enemy : enemies) {
            if (enemy.getName().equals(name)) {
                return enemy;
            }
        }
        return null; // Enemy not found
    }

	public static Room getRoomByNum(List<Room> rooms, int roomNumber) {
		for (Room room : rooms) {
			if (room.getRoomNum().equals(name)) {
				return room;
			}
		}
		return null; // Room not found
	}

	public static boolean checkLocked(room) {
		if(room.getLocked>0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static Room playerMovement(List<Room> rooms, int playerRoom, string response) {
		boolean locked;
		Room playRoom = getRoomByNum(rooms, playerRoom);
		int destinationRoomNum;
		switch(response) {
			case(response.equals("NW")):
			case(response.equals("NORTHWEST")):
				destinationRoom = playRoom.getExitNW();
				break;
			case(response.equals("N")):
			case(response.equals("NORTH")):
				destinationRoomNum = playRoom.getExitN();
				break;
			case(response.equals("NE")):
			case(response.equals("NORTHEAST")):
				destinationRoomNum = playRoom.getExitNE();
				break;
			case(response.equals("W")):
			case(response.equals("WEST")):
				destinationRoomNum = playRoom.getExitW();
				break;
			case(response.equals("E")):
			case(response.equals("EAST")):
				destinationRoomNum = playRoom.getExitE();
				break;
			case(response.equals("SW")):
			case(response.equals("SOUTHWEST")):
				destinationRoomNum = playRoom.getExitSW();
				break;
			case(response.equals("S")):
			case(response.equals("SOUTH")):
				destinationRoomNum = playRoom.getExitS();
				break;
			case(response.equals("SE")):
			case(response.equals("SOUTHEAST")):
				destinationRoomNum = playRoom.getExitSE();
				break;
			case(response.equals("UP")):
				destinationRoomNum = playRoom.getExitUP();
				break;
			case(response.equals("DN")):
			case(response.equals("DOWN")):
				destinationRoomNum = playRoom.getExitDN();
				break;

		}
		if(destinationRoomNum>0) {
			Room destinationRoom = getRoomByNum(destinationRoomNum);
			boolean isLocked = checkLocked(destinationRoom);
			if (isLocked) {
				//need to add inventory check for key here
				if () {
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
		}
		else {
			System.out.println("There are no exits in that direction.");
			return playRoom;
		}
	}

	public static List<Room> loadRooms(String filename) throws IOException {
		Gson gson = new GsonBuilder().create();

		try (Reader reader = new FileReader(filename)) {
			JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
			List<Room> rooms = new ArrayList<>();

			for (JsonElement element : jsonArray) {
				JsonObject jsonObject = element.getAsJsonObject();
				Room room = gson.fromJson(jsonObject, Room.class);
				room.setRoomName(jsonObject.get("name").getAsString());
				room.setRoomNum(jsonObject.get("roomNum"));
				room.setExitN(jsonObject.get("exit n"));
				room.setExitNW(jsonObject.get("exit nw"));
				room.setExitNE(jsonObject.get("exit ne"));
				room.setExitW(jsonObject.get("exit w"));
				room.setExitE(jsonObject.get("exit e"));
				room.setExitSW(jsonObject.get("exit sw"));
				room.setExitS(jsonObject.get("exit s"));
				room.setExitSE(jsonObject.get("exit se"));
				room.setExitUP(jsonObject.get("exit up"));
				room.setExitDN(jsonObject.get("exit dn"));
				room.setLocked(jsonObject.get("locked"));
				room.setRoomItem1(jsonObject.get("item 1").getAsString());
				room.setRoomItem2(jsonObject.get("item2").getAsString());
				room.setRoomItem3(jsonObject.get("item 3").getAsString());
				room.setRoomItem4(jsonObject.get("item 4").getAsString());
				room.setRoomItem5(jsonObject.get("item 5").getAsString());
				room.setEnemy(jsonObject.get("enemy").getAsString());
				room.setRoomDescription(jsonObject.get("description").getAsString());
				rooms.add(room);
			}

			return rooms;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
            enemies = loadEnemies("src/Other_Stuff/enemies.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

		try {
			rooms = loadRooms("src/Other_Stuff/rooms.json");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String response = "", name = "", gender = "";
		
		do
		{
			System.out.println("Hello! What is your name?");
			name = sc.nextLine();
			
			System.out.println("Your name is " + name + "? ('Y' for yes, 'N' for no)");
			response = sc.nextLine();
		} while(!response.trim().equalsIgnoreCase("Y") && !response.trim().equalsIgnoreCase("YES"));
		
		response = "";
		
		do
		{
			System.out.println("And are you (M)ale, (F)emale, or (N)onbinary?");
			gender = sc.nextLine().trim().toUpperCase();
			
			switch(gender)
			{
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

		} while(!response.trim().equalsIgnoreCase("Y") && !response.trim().equalsIgnoreCase("YES"));
		
		response = "";
		Player.PlayerClass playerClass = Player.PlayerClass.NULL;
		
		do
		{
			System.out.println("And are you a (M)age, (R)ogue, or (W)arrior?");
			response = sc.nextLine().trim().toUpperCase();
			
			switch(response)
			{
				case "M":
			    case "MAGE":
			        System.out.println("So you're a mage? (Y/N)");
			        playerClass = Player.PlayerClass.MAGE;
			        break;
			    case "R":
			    case "ROGUE":
			        System.out.println("So you're a rogue? (Y/N)");
			        playerClass = Player.PlayerClass.ROGUE;
			        break;
			    case "W":
			    case "WARRIOR":
			        System.out.println("So you're a warrior? (Y/N)");
			        playerClass = Player.PlayerClass.WARRIOR;
			        break;
			    default:
			        System.out.println("Sorry, I didn't recognize that. Please enter 'M', 'R', or 'W'.");
			        continue;
			}
			
			response = sc.nextLine().trim().toUpperCase();

		} while(!response.trim().equalsIgnoreCase("Y") && !response.trim().equalsIgnoreCase("YES"));
		
		Player player = new Player(name, gender, playerClass);

		int playerRoom = 1;

		//Game Begins Here
		System.out.println("You aren't sure how long you've been in this cell.\nYou're pretty sure that it's more than a week, less than a month.\n" +
				"When you hear a ruckus start somewhere above you, you're pretty sure the chaos you hear is actually in your head until the dungeon guards run up the stairs\n" +
				"A guards helmet comes flying down the stairs, skips off the opposite wall, and slams into your cell door.\n" +
				"The door pops open, and you're sure you've gone mad until you see the gaping hole in the helmet\n" +
				"No, if you'd gone mad the last thing that you would have imagined was being freed from your cell into a castle under attack.\n" +
				"You immediately burst out of the cell, look both ways down the hall, and decide to hide in the storage room until the noise dies down.");

		playerRoom.display();
		response ="";
		response = sc.nextLine().trim().toUpperCase();
	/*
		Enemy bandit = getEnemyByName(enemies, "Bandit");
		Enemy bat1 = getEnemyByName(enemies, "Bat");
		
		//inkedList<Enemy> enemies = new LinkedList<>();
	    enemies.add(bat1);
	    enemies.add(bandit);
		
		//Other_Stuff.GameEngine.startFight(player, enemies);
		//Other_Stuff.GameEngine.startFight(player, bat2);
	    
	    NPC steve = new NPC("Steve", "src/Dialogues/Tutorial_NPC_dialogue.json");
	    steve.speakToPlayer(player);
	*/
		sc.close();
	}

}
