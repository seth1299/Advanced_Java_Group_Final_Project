package Other_Stuff;

import java.io.FileReader;
import java.io.IOException;
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
	private Enemy enemy;
	private List<Enemy> enemies;
	private String roomDescription;
	private Item requiredKey;
	private List<Item> roomItems;
	private Player player;

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
		this.enemy = null;
		this.roomDescription = "";
	}
	
	/**
	 * @param player The player of the game.
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
	 * @param enemy The enemy that is in the room, if any.
	 * @param roomDescription The description of the room.
	 */
	public Room(Player player, int roomNum, String roomName, int exitN, int exitNW, int exitNE, int exitW, int exitE, int exitSW, int exitS, int exitSE, int exitUP, int exitDN, boolean locked, String requiredKey, String roomItemsFilepath, String enemyFilepath, String roomDescription) {
		this.player = player;
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
		this.setRequiredKey(player.getItemFromInventoryByName(requiredKey));
		if ( !enemyFilepath.isEmpty() )
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
				", enemy='" + enemy + '\'' +
				", roomDescription='" + roomDescription + '\'' +
				'}';
	}

	//Displays the room to the player
	public void display() {
		System.out.println(roomName);
		System.out.println(roomDescription);
		//Only lists an exit if it is valid
		System.out.print("The exit(s) are to the: ");
		if(exitN>0){
			System.out.print("N ");
		}
		if(exitNW>0){
			System.out.print("NW ");
		}
		if(exitNE>0){
			System.out.print("NE ");
		}
		if(exitW>0){
			System.out.print("W ");
		}
		if(exitE>0) {
			System.out.print("E ");
		}
		if(exitSW>0){
			System.out.print("SW ");
		}
		if(exitS>0){
			System.out.print("S ");
		}
		if(exitSE>0){
			System.out.print("SE ");
		}
		if(exitUP>0){
			System.out.print("UP ");
		}
		if(exitDN>0){
			System.out.print("DN");
		}
		
		if ( roomItems.size() > 0 )
		{
			System.out.print("The room contains these items: ");
			for ( Item item : roomItems )
			{
				System.out.println(item);
			}
		}
		
		if ( enemy != null )
		{
			GameEngine.startFight(player, enemy);
		}
		
		if ( enemies != null )
		{
			GameEngine.startFight(player, enemies);
		}
		
	}
	
	public void addItemsToRoomFromJsonFile(String filepath) {
		if ( filepath.isEmpty() )
			return;
		
        try (FileReader reader = new FileReader(filepath)) {
            JsonArray itemsArray = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (JsonElement itemElement : itemsArray) {
                String name = itemElement.getAsJsonObject().get("name").getAsString();
                String description = itemElement.getAsJsonObject().get("description").getAsString();
                Item.ItemType type = Item.ItemType.valueOf(itemElement.getAsJsonObject().get("type").getAsString());
                int amount = itemElement.getAsJsonObject().get("amount").getAsInt();
                
                // Create a new item and add it to the player's inventory
                Item newItem = new Item(name, description, type);
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

	        // If the item doesn't exist, add it to the room
	        item.setAmount(amount);
	        roomItems.add(item);
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

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
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

	public Item getRequiredKey() {
		return requiredKey;
	}

	public void setRequiredKey(Item requiredKey) {
		this.requiredKey = requiredKey;
	}

	public List<Item> getRoomItems() {
		return roomItems;
	}

	public void setRoomItems(List<Item> roomItems) {
		this.roomItems = roomItems;
	}

	
}
