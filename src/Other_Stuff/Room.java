package Other_Stuff;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Player_Related_Stuff.Item;
import Player_Related_Stuff.Player;

public class Room {
	
	private int roomNum;
	private String roomName;
	private int exitN;
	private int exitNW;
	private int exitNE;
	private int exitW;
	private int exitE;
	private int exitSW;
	private int exitS;
	private int exitSE;
	private int exitUP;
	private int exitDN;
	private boolean locked;
	private List<Enemy> enemies;
	private String roomDescription;
	private String requiredKey;
	private List<Item> roomItems;
	private Player player;
	private World world;

	//Default constructor with garbage values
	public Room() {
		this.roomNum = 0;
		this.roomName = "";
		this.exitN = 0;
		this.exitNW = 0;
		this.exitNE = 0;
		this.exitW = 0;
		this.exitE = 0;
		this.exitSW = 0;
		this.exitS = 0;
		this.exitSE = 0;
		this.exitUP = 0;
		this.exitDN = 0;
		this.locked = false;
		this.setRequiredKey(null);
		this.roomDescription = "";
	}
	
	/**
	 * @param //player The player of the game.
	 * @param roomNum The numerical value of the room, which is used to quickly identify the room and set exits.
	 * @param roomName The name of the room.
	 * @param exitN 
	 * @param exitNW
	 * @param exitNE
	 * @param exitW
	 * @param exitE
	 * @param exitSW
	 * @param exitS
	 * @param exitSE
	 * @param exitUP
	 * @param exitDN
	 * @param locked If the room is locked or not.
	 * @param requiredKey The Item that is required to unlock the room (which should be a "Key" type)
	 * @param roomItemsFilepath The filepath to the items that are in the room, if any.
	 * @param roomDescription The description of the room.
	 */
	public Room(/*Player player,*/ int roomNum, String roomName, int exitN, int exitNW, int exitNE, int exitW, int exitE, int exitSW, int exitS, int exitSE, int exitUP, int exitDN, boolean locked, String requiredKey, String roomItemsFilepath, String enemyFilepath, String roomDescription) {
		//this.player = player;
		this.roomNum = roomNum;
		this.roomName = roomName;
		this.exitN = exitN;
		this.exitNW = exitNW;
		this.exitNE = exitNE;
		this.exitW = exitW;
		this.exitE = exitE;
		this.exitSW = exitSW;
		this.exitS = exitS;
		this.exitSE = exitSE;
		this.exitUP = exitUP;
		this.exitDN = exitDN;
		this.locked = locked;
		addItemsToRoomFromJsonFile(roomItemsFilepath);
		roomItems = new LinkedList<>();
		this.requiredKey = requiredKey;
		if ( enemyFilepath != null && !enemyFilepath.isEmpty() )
		{
			try {
				enemies = World.loadEnemies(enemyFilepath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.roomDescription = roomDescription;
	}

	@Override
	public String toString() {
		StringBuilder items = new StringBuilder();
		int i = 0;
		
		if ( roomItems != null && !roomItems.isEmpty() && enemies != null && !enemies.isEmpty() )
		{
			for ( Item item : roomItems )
			{
				if ( i++ != roomItems.size())
					items.append(item.getName() + ", ");
				else
					items.append(item.getName());
			}
			
			return "Room{" +
			"roomNum=" + roomNum +
			", roomName='" + roomName + '\'' +
			", exitN=" + exitN +
			", exitNW=" + exitNW +
			", exitNE=" + exitNE +
			", exitW=" + exitW +
			", exitE=" + exitE +
			", exitSW=" + exitSW +
			", exitS=" + exitS +
			", exitSE=" + exitSE +
			", exitUP=" + exitUP +
			", exitDN=" + exitDN +
			", locked=" + locked +
			", " + items + '\'' +
			", " + enemies + '\'' +
			", roomDescription='" + roomDescription + '\'' +
			'}';
		}
		
		else if (roomItems != null && !roomItems.isEmpty() && enemies == null)
		{
			for ( Item item : roomItems )
			{
				if ( i++ != roomItems.size())
					items.append(item.getName() + ", ");
				else
					items.append(item.getName());
			}
			
			return "Room{" +
			"roomNum=" + roomNum +
			", roomName='" + roomName + '\'' +
			", exitN=" + exitN +
			", exitNW=" + exitNW +
			", exitNE=" + exitNE +
			", exitW=" + exitW +
			", exitE=" + exitE +
			", exitSW=" + exitSW +
			", exitS=" + exitS +
			", exitSE=" + exitSE +
			", exitUP=" + exitUP +
			", exitDN=" + exitDN +
			", locked=" + locked +
			", " + items + '\'' +
			", roomDescription='" + roomDescription + '\'' +
			'}';
		}
		
		else if ( roomItems == null && enemies != null )
		{
			return "Room{" +
					"roomNum=" + roomNum +
					", roomName='" + roomName + '\'' +
					", exitN=" + exitN +
					", exitNW=" + exitNW +
					", exitNE=" + exitNE +
					", exitW=" + exitW +
					", exitE=" + exitE +
					", exitSW=" + exitSW +
					", exitS=" + exitS +
					", exitSE=" + exitSE +
					", exitUP=" + exitUP +
					", exitDN=" + exitDN +
					", locked=" + locked +
					", " + enemies + '\'' +
					", roomDescription='" + roomDescription + '\'' +
					'}';
		}
		
		else
		{
			return "Room{" +
					"roomNum=" + roomNum +
					", roomName='" + roomName + '\'' +
					", exitN=" + exitN +
					", exitNW=" + exitNW +
					", exitNE=" + exitNE +
					", exitW=" + exitW +
					", exitE=" + exitE +
					", exitSW=" + exitSW +
					", exitS=" + exitS +
					", exitSE=" + exitSE +
					", exitUP=" + exitUP +
					", exitDN=" + exitDN +
					", locked=" + locked +
					", roomDescription='" + roomDescription + '\'' +
					'}';
		}
		
	}
	
	public void getExits()
	{
		StringBuilder possibleExits = new StringBuilder("");
		StringBuilder possibleExitRoomNames = new StringBuilder("");
		// This is a size of 10 because there are 10 possible directions.
		List<Integer> INDEXES_FOR_EXIT_ROOMS = new LinkedList<Integer>();
		/*
		int[] INDEXES_FOR_EXIT_ROOMS = new int[10];
		// Fill the array with -1 values, this will make sense later.
		for ( int i = 0; i < INDEXES_FOR_EXIT_ROOMS.length; i++ )
			INDEXES_FOR_EXIT_ROOMS[i] = -1;
		*/
		int numberOfPossibleExits = 0, currentIndex = 0;
		
		if( exitN > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitN) )
			{
				possibleExits.append("North (" + ( World.getRoomByNum(World.getRooms(), exitN) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitN) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(0);
				//INDEXES_FOR_EXIT_ROOMS[0] = 0;
			}
			else
			{
				possibleExits.append("North,");
			}
			
			numberOfPossibleExits++;
		}
		
		if( exitS > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitS) )
			{
				possibleExits.append("South (" + ( World.getRoomByNum(World.getRooms(), exitS) ).getRoomName() + "),");
				//System.out.println("Already visited " + ( World.getRoomByNum(World.getRooms(), exitS) ).getRoomName() + " which is to the south of " + roomName + "!");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitS) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(1);
			}
			else
				possibleExits.append("South,");
			
			numberOfPossibleExits++;
		}
		
		if( exitE > 0 ) 
		{
			if ( World.getAlreadyVisitedRooms().contains(exitE) )
			{
				possibleExits.append("East (" + ( World.getRoomByNum(World.getRooms(), exitE) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitE) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(2);
			}
			else
			{
				possibleExits.append("East,");
			}
			
			numberOfPossibleExits++;
		}
		
		if( exitW > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitW) )
			{
				possibleExits.append("West (" + ( World.getRoomByNum(World.getRooms(), exitW) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitW) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(3);
			}
			else
			{
				possibleExits.append("West,");
			}
			
			numberOfPossibleExits++;
		}
		
		if( exitNE > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitNE) )
			{
				possibleExits.append("Northeast (" + ( World.getRoomByNum(World.getRooms(), exitNE) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitNE) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(4);
			}
			else
			{
				possibleExits.append("Northeast,");
			}
			
			numberOfPossibleExits++;
		}
			
		if( exitNW > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitNW) )
			{
				possibleExits.append("Northwest (" + ( World.getRoomByNum(World.getRooms(), exitNW) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitNW) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(5);
			}
			else
			{
				possibleExits.append("Northwest,");
			}
			
			numberOfPossibleExits++;
		}
			
		if( exitSE > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitSE) )
			{
				possibleExits.append("Southeast (" + ( World.getRoomByNum(World.getRooms(), exitSE) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitSE) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(6);
			}
			else
			{
				possibleExits.append("Southeast,");
			}
			
			numberOfPossibleExits++;
		}	
			
		if( exitSW > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitSW) )
			{
				possibleExits.append("Southewest (" + ( World.getRoomByNum(World.getRooms(), exitSW) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitSW) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(7);
			}
			else
			{
				possibleExits.append("Southwest,");
			}
			
			numberOfPossibleExits++;
		}
			
		if( exitUP > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitUP) )
			{
				possibleExits.append("Up (" + ( World.getRoomByNum(World.getRooms(), exitUP) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitUP) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(8);
			}
			else
			{
				possibleExits.append("Up,");
			}
			
			numberOfPossibleExits++;
		}
			
		if( exitDN > 0 )
		{
			if ( World.getAlreadyVisitedRooms().contains(exitDN) )
			{
				possibleExits.append("Down (" + ( World.getRoomByNum(World.getRooms(), exitDN) ).getRoomName() + "),");
				//possibleExitRoomNames.append( "(" + ( World.getRoomByNum(World.getRooms(), exitDN) ).getRoomName() + ")###");
				//INDEXES_FOR_EXIT_ROOMS.add(9);
			}
			else
			{
				possibleExits.append("Down,");
			}
			
			numberOfPossibleExits++;
		}
			
		String[] possibleExitsArray = possibleExits.toString().split(",");
		String[] possibleExitRoomNamesArray = possibleExitRoomNames.toString().split("###");
		/*
		for ( String name : possibleExitRoomNamesArray )
			System.out.println(name);
		for ( int ind : INDEXES_FOR_EXIT_ROOMS )
			System.out.println(ind);
		*/
		
		//Only lists an exit if it is valid
		if ( numberOfPossibleExits == 1 )
		{
			if ( possibleExitRoomNames.length() > 2 )
			{
				System.out.println("\n(The only possible exit is to the " + possibleExits.substring(0, possibleExits.length()-1) + ".) " + possibleExitRoomNames.substring(0, possibleExitRoomNames.length() - 3));
			}
			else
				System.out.println("\n(The only possible exit is to the " + possibleExits.substring(0, possibleExits.length()-1) + ".) ");
		}
			
		else
		{
			System.out.println("\nThe possible room exits are: ");
			
			for ( String exit : possibleExitsArray )
			{
				System.out.println(exit);
			}
			
			
			
			/*
			int index = 0, slowerIndex = 0;
			
			for ( Integer roomNum : INDEXES_FOR_EXIT_ROOMS )
			{
				if ( World.getAlreadyVisitedRooms().contains(roomNum) )
				{
					System.out.println("* " + possibleExitsArray[slowerIndex++]);
				}
				index++;
			}
			
			for ( String exit : possibleExitsArray )
			{
				if ( World.getAlreadyVisitedRooms().contains(1) )
				{
					
				}
				/*
				if ( INDEXES_FOR_EXIT_ROOMS.size() <= index && slowerIndex < possibleExitRoomNamesArray.length)
				{
					System.out.println("rawr?");
					System.out.println("* " + exit + possibleExitRoomNamesArray[slowerIndex++]);
				}
					
				else
					System.out.println("* " + exit);
				
				index++;
				*/
				/*
				if ( index < possibleExitRoomNamesArray.length )
					System.out.println("* " + exit + possibleExitRoomNamesArray[index++]);
				else
					System.out.println("* " + exit);
					
			}
			*/
				
		}
	}

	//Displays the room to the player
	public void display(boolean showDescription) {
		System.out.println("\n" + roomName);
		if ( showDescription )
			System.out.println("- " + roomDescription);
		
		getExits();
		
		if (roomItems!=null && roomItems.size() > 0 )
		{
			if ( roomItems.size() > 1 )
				System.out.print("\nYou find some items in the room! ");
			else
				System.out.println("\nYou find an item in the room! ");
			
			
			int index = 0;
			for ( Item item : roomItems )
			{
				if ( index == 0 )
					System.out.print("Added ");
				if ( roomItems.size() == 1 )
				{
					System.out.print(item.getName() + " in your inventory.");
					player.addItemToInventory(item, item.getAmount());
					break;
				}
				
				if ( ++index == roomItems.size())
					System.out.print("and " + item.getName() + " in your inventory.");
				else
					System.out.print(item.getName() + ", ");
				player.addItemToInventory(item, item.getAmount());
			}
			
			System.out.println("");
			
			roomItems.clear();
		}
		
		if ( enemies != null )
		{
			GameEngine.startFight(player, enemies);
		}
		
	}
	
	public void addItemsToRoomFromJsonFile(String filepath) {
		
		if ( filepath == null || filepath.isEmpty() )
			return;
		
		try (FileReader reader = new FileReader(filepath)) {
            JsonArray itemsArray = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (JsonElement itemElement : itemsArray) {
            	int armorValue = 0, weaponDamage = 0, healingAmount = 0;
            	Item newItem;
                String name = itemElement.getAsJsonObject().get("name").getAsString();
                if ( getRoomName().equals("DUNGEON STORAGE"))
                {
                	
                }
                	
                String description = itemElement.getAsJsonObject().get("description").getAsString();
                Item.ItemType type = Item.ItemType.valueOf(itemElement.getAsJsonObject().get("type").getAsString());
                int amount = itemElement.getAsJsonObject().get("amount").getAsInt();
                if (type.equals(Item.ItemType.ARMOR))
                {
                	armorValue = itemElement.getAsJsonObject().get("armorValue").getAsInt();
                	newItem = new Item(name, description, type, armorValue);
                }
                else if (type.equals(Item.ItemType.WEAPON))
                {
                	weaponDamage = itemElement.getAsJsonObject().get("damage").getAsInt();
                	newItem = new Item(name, description, type, weaponDamage);
                }
                else if (type.equals(Item.ItemType.FOOD) || type.equals(Item.ItemType.POTION))
                {
                	healingAmount = itemElement.getAsJsonObject().get("healingAmount").getAsInt();
                	newItem = new Item(name, description, type, healingAmount);
                }
                else
                {
                	newItem = new Item(name, description, type);
                }
                
                // Create a new item and add it to the player's inventory
                addItemToRoom(newItem, amount);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid amount format for item: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid item type: " + e.getMessage());
        }
    }
	
	 public void addItemToRoom(Item item, int amount) 
	    {
	        if (item == null)
	        	return;

	        
	        if ( roomItems != null )
	        {
	        	// Check if the item already exists in the room
	        	for (Item currentItem : roomItems) 
		        {
		            if (currentItem.getName().equals(item.getName()) && currentItem.getType() == item.getType()) 
		            {
		                // If the item already exists, increment its quantity and return
		                currentItem.changeAmount(amount);
		                return;
		            }
		        }
	        	
	        	item.setAmount(amount);
	        	roomItems.add(item);
	        }
	        else
	        {
	        	roomItems = new ArrayList<>();
	        	// If the room items array doesn't exist, instantiate it. For some fucking godforsaken reason, the roomItems arrayList does not
	        	// instantiate in the constructor like the other variables do, so it must be instantiated at run-time.
		        item.setAmount(amount);
		        roomItems.add(item);
	        }

	        
	    }

	//Getters and Setters
	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getExitN() {
		return exitN;
	}

	public void setExitN(int exitN) {
		this.exitN = exitN;
	}

	public int getExitNW() {
		return exitNW;
	}

	public void setExitNW(int exitNW) {
		this.exitNW = exitNW;
	}

	public int getExitNE() {
		return exitNE;
	}

	public void setExitNE(int exitNE) {
		this.exitNE = exitNE;
	}

	public int getExitW() {
		return exitW;
	}

	public void setExitW(int exitW) {
		this.exitW = exitW;
	}

	public int getExitE() {
		return exitE;
	}

	public void setExitE(int exitE) {
		this.exitE = exitE;
	}

	public int getExitSW() {
		return exitSW;
	}

	public void setExitSW(int exitSW) {
		this.exitSW = exitSW;
	}

	public int getExitS() {
		return exitS;
	}

	public void setExitS(int exitS) {
		this.exitS = exitS;
	}

	public int getExitSE() {
		return exitSE;
	}

	public void setExitSE(int exitSE) {
		this.exitSE = exitSE;
	}

	public int getExitUP() {
		return exitUP;
	}

	public void setExitUP(int exitUP) {
		this.exitUP = exitUP;
	}

	public int getExitDN() {
		return exitDN;
	}

	public void setExitDN(int exitDN) {
		this.exitDN = exitDN;
	}

	public boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean locked, Item requiredKey) {
		this.locked = locked;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}
	
	/**
	 * @return The String value of the required key for the room. That is to say, just the name of the key needed to unlock the room as a String.
	 */
	public String getRequiredKey() {
		return requiredKey;
	}

	public void setRequiredKey(String requiredKey) {
		this.requiredKey = requiredKey;
	}

	public List<Item> getRoomItems() {
		return roomItems;
	}

	public void setRoomItems(List<Item> roomItems) {
		this.roomItems = roomItems;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	
}
