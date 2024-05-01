package Player_Related_Stuff;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import Other_Stuff.Enemy;
import Player_Related_Stuff.Item.ItemType;

public class Player {
	
	/**
	 * Global variable declarations
	 */
    private int health, mana, damage;
    private final int MAX_HEALTH;
    private boolean isDead, remindMe = true;
    private String name, gender;
    private LinkedList<Item> inventory;
    private LinkedList<String> playerMovesList = new LinkedList<>();
    private LinkedList<String> outOfCombatActions = new LinkedList<>();
    private Map<EquipmentSlot, Item> equipment;
    private static Scanner sc;
    
    /**
	 * Default Player constructor, all values are automatically assigned to garbage values.
	 */
    public Player() {
    	sc = new Scanner (System.in);
        health = 100;
        name = "UNINSTANTIATED";
        gender = "UNINSTANTIATED";
        isDead = false;
        inventory = new LinkedList<>();
        damage = 2;
        playerMovesList.add("ATTACK");
        playerMovesList.add("DEFEND");
        playerMovesList.add("FLEE");
        playerMovesList.add("ITEM");
        outOfCombatActions.add("ZORK");
        outOfCombatActions.add("HELP");
        outOfCombatActions.add("H");
        outOfCombatActions.add("MOVE");
        outOfCombatActions.add("M");
        outOfCombatActions.add("SUPERBRIEF");
        outOfCombatActions.add("S");
        outOfCombatActions.add("VERBOSE");
        outOfCombatActions.add("V");
        outOfCombatActions.add("NOTHING");
        outOfCombatActions.add("LOOK");
        outOfCombatActions.add("L");
        outOfCombatActions.add("INVENTORY");
        outOfCombatActions.add("I");
        outOfCombatActions.add("USE");
        outOfCombatActions.add("U");
        outOfCombatActions.add("DONTREMINDME");
        outOfCombatActions.add("D");
        outOfCombatActions.add("REMINDME");
        outOfCombatActions.add("R");
        outOfCombatActions.add("CLEAR");
        outOfCombatActions.add("CLS");
        outOfCombatActions.add("JOKE");
        outOfCombatActions.add("J");
        outOfCombatActions.add("TIME");
        outOfCombatActions.add("T");
        equipment = new HashMap<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            equipment.put(slot, null);
        }
        this.MAX_HEALTH = 100;
        this.setHealth(MAX_HEALTH);
    }

    /**
     * Full constructor for the Player class.
     * @param name The player's name.
     * @param gender The player's gender.
     */
    public Player(String name, String gender) {
    	if ( name == null || gender == null )
    		System.exit(-1);
    	sc = new Scanner (System.in);
        this.name = name;
        this.gender = gender;
        inventory = new LinkedList<>();
        playerMovesList.add("ATTACK");
        playerMovesList.add("DEFEND");
        playerMovesList.add("FLEE");
        playerMovesList.add("ITEM");
        outOfCombatActions.add("ZORK");
        outOfCombatActions.add("HELP");
        outOfCombatActions.add("H");
        outOfCombatActions.add("MOVE");
        outOfCombatActions.add("M");
        outOfCombatActions.add("SUPERBRIEF");
        outOfCombatActions.add("S");
        outOfCombatActions.add("VERBOSE");
        outOfCombatActions.add("V");
        outOfCombatActions.add("NOTHING");
        outOfCombatActions.add("LOOK");
        outOfCombatActions.add("L");
        outOfCombatActions.add("INVENTORY");
        outOfCombatActions.add("I");
        outOfCombatActions.add("USE");
        outOfCombatActions.add("U");
        outOfCombatActions.add("DONTREMINDME");
        outOfCombatActions.add("D");
        outOfCombatActions.add("REMINDME");
        outOfCombatActions.add("R");
        outOfCombatActions.add("CLEAR");
        outOfCombatActions.add("CLS");
        outOfCombatActions.add("JOKE");
        outOfCombatActions.add("J");
        outOfCombatActions.add("TIME");
        outOfCombatActions.add("T");
        equipment = new HashMap<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            equipment.put(slot, null);
        }
        isDead = false;
        this.MAX_HEALTH = 100;
        this.setHealth(MAX_HEALTH);
    }
    
    public void displayHelp()
    {
    	System.out.println();
    	for ( String action : outOfCombatActions)
    	{
    		if ( action.equals("HELP"))
    			System.out.println("* HELP - displays this help menu.");
    		if ( action.equals("MOVE"))
    			System.out.println("* MOVE - allows the player to move from room to room. After typing in move, you must then input an ordinal direction (N, S, E, W, NE, NW, SE, SW). Valid directions will be displayed underneath the current room's description.");
    		if ( action.equals("SUPERBRIEF"))
    			System.out.println("* SUPERBRIEF - this will skip the description for each room and only display the name of the room and valid directions to go from there.");
    		if ( action.equals("VERBOSE"))
    			System.out.println("* VERBOSE - this is the opposite of SUPERBRIEF, this will show room descriptions again (default).");
    		if ( action.equals("LOOK"))
    			System.out.println("* LOOK - This will repeat the name and description of the current room, as if you had just walked back into the room.");
    		if ( action.equals("INVENTORY"))
    			System.out.println("* INVENTORY - Displays your current inventory, along with a description of each item.");
    		if ( action.equals("USE"))
    			System.out.println("* USE - Allows you to use an item from your inventory. You must specify which item after you type in USE, or say CANCEL.");
    		if ( action.equals("DONTREMINDME"))
    			System.out.println("* DONTREMINDME - Prevents the entire inventory from being displayed every time that you use an item.");
    		if ( action.equals("REMINDME"))
    			System.out.println("* REMINDME - Displays the entire inventory every time that you use an item (default).");
    		if ( action.equals("TIME"))
    			System.out.println("* TIME - Displays how long you've been playing for (in seconds).");
    		
    	}
    	System.out.println("\nAny command can be entered by only putting in the first letter of the command as well.");
    	// The "magic number" here is the amount of commands shown in the Help screen, minus the "NOTHING" command which does nothing. I suppose it technically counts?
    	System.out.println("There are also " + ( outOfCombatActions.size() - 22 ) + " secret commands, see if you can find them all! ;)");
    }
    
    public boolean getRemindMe()
    {
    	return remindMe;
    }
    
    public void remindMe(boolean remindMe)
    {
    	this.remindMe = remindMe;
    }
    
    public void useItem()
    {
    	String nameOfItem = "";
    	
    	if ( remindMe )
    	{
    		printInventory();
    		System.out.println("\n---------------------\n");
    	}
    	
		System.out.println("Use what item?");
		nameOfItem = sc.nextLine().trim();
    	
    	String[] funnyCoffeeResponses = {"The coffee does look delicious, but you're saving it for later.", "You shouldn't drink the coffee now, what if someone sneaks up behind you?",
    			"Hmm, it just doesn't feel like the right time to drink the coffee. Maybe later.", "You should save the coffee for once you get outside!"};
    	
    	String[] funnyGenericResponses = {("Hmm, use a " + nameOfItem + "? I don't think so."), ("Maybe you can use the " + nameOfItem + " later, but I'm not sure how it would help now."),
    			("Alright, now you're just messing with me. Who would even try to use a " + nameOfItem + " in this situation?")};
    	
    	if ( nameOfItem == null || nameOfItem.isBlank())
    		return;
    	
    	if ( nameOfItem.equals("Coffee"))
    	{
    		Random rand = new Random();
    		int randNum = rand.nextInt(0, funnyCoffeeResponses.length);
    		System.out.println(funnyCoffeeResponses[randNum]);
    	}
    	
    	else
    	{
    		Item item = getItemFromInventoryByName(nameOfItem);
    		
    		if ( item == null )
    		{
    			System.out.println("You don't have a " + nameOfItem + " in your inventory.");
    			return;
    		}
    		
    		else if ( item.getType() == Item.ItemType.FOOD || item.getType() == Item.ItemType.POTION )
    		{
    			
    		}
    		
    		else
    		{
    			Random rand = new Random();
        		int randNum = rand.nextInt(0, funnyGenericResponses.length);
        		System.out.println(funnyGenericResponses[randNum]);
    		}
    	}
    		
    }
    
    public void takeTurn(List<Enemy> enemies) {
    	
    	if ( enemies == null )
    		return;
    	
        LinkedList<String> playerMoves = getPlayerMovesList();
        Map<String, Integer> nameCountMap = new HashMap<>(); // Map to store counts of each enemy name
        int playerDamage = getDamage();

		// Display available moves
		System.out.println("\n" + getName() + "'s move! What will you do? (Current health: " + getHealth() + ")");
		for (String move : playerMoves)
			System.out.println("* " + move);

		// Create enemyNames
		for (Enemy enemy : enemies) {
			String enemyName = enemy.getName();
			int count = nameCountMap.getOrDefault(enemyName, 0); // Get the count of the current enemy name
			nameCountMap.put(enemyName, count + 1); // Increment the count and update the map
		}

		// Display enemy names along with counts
		System.out.println("\nEnemy Names:");
		for (Map.Entry<String, Integer> entry : nameCountMap.entrySet()) {
			String enemyName = entry.getKey();
			int count = entry.getValue();

			if (count > 1) {
				System.out.println("* " + enemyName + " (" + count + ")");
			} else {
				System.out.println("* " + enemyName);
			}
		}

		// Player chooses action
		String response = "";
		do {
			System.out.println("\nWhat would you like to do?");
			response = sc.nextLine().toUpperCase().trim();
		} while (!playerMoves.contains(response));

		// Player performs action
		if (response.equals("ATTACK") || response.equals("A")) {
			do {
				System.out.println("\nWho would you like to attack?");
				response = sc.nextLine().trim().toLowerCase(); // Convert player's input to lowercase for comparison
			} while (!nameCountMap.keySet().stream().map(String::toLowerCase).collect(Collectors.toSet())
					.contains(response)); // Convert each key in the map to lowercase before comparison

			System.out.println("You attack the " + response + " for " + playerDamage + " damage!\n");
			
			
			// Inflict damage on the enemy
		    Iterator<Enemy> iterator = enemies.iterator();
		    while (iterator.hasNext()) {
		        Enemy currentEnemy = iterator.next();
		        if (currentEnemy.getName().equalsIgnoreCase(response)) {
		            currentEnemy.changeEnemyHealth(-playerDamage);
		            if (currentEnemy.getEnemyHealth() <= 0) {
		                System.out.println("You have slain the " + response + "!");
		                iterator.remove(); // Remove the current enemy using the iterator
		                break;
		            }
		            System.out.println("The " + response + " now has " + currentEnemy.getEnemyHealth() + " health!");
		            break;
		        }
		    }
		} else {
			System.out.println(response + " IS NOT IMPLEMENTED YET.");
		}
        
    }
    
	public void takeTurn(Enemy enemy) {
		if ( isDead || enemy == null )
			return;
		
		LinkedList<String> playerMoves = getPlayerMovesList();
		int playerDamage = getDamage();
		String enemyName = enemy.getName();

		System.out.println("\n" + getName() + "'s move! What will you do? (Current health: " + getHealth() + ")");
		
		for (String move : playerMoves)
			System.out.println(move);

		System.out.println("\nEnemy: \n* " + enemyName);

		// Player chooses action
		String response = "";
		do {
			System.out.println("\nWhat would you like to do?");
			response = sc.nextLine().toUpperCase().trim();
		} while (!playerMoves.contains(response));

		// Player performs action
		if (response.equals("ATTACK") || response.equals("A")) 
		{

			System.out.println("You attack the " + enemyName + " for " + playerDamage + " damage!\n");
			
			enemy.changeEnemyHealth(-playerDamage);
			
			if (enemy.getEnemyHealth() <= 0) 
			{
				System.out.println("You have slain the " + enemyName + "!");
				return;
			}

		} 
		else
		{
			System.out.println(response + " IS NOT IMPLEMENTED YET. RESTARTING PLAYER TURN.");
			takeTurn(enemy);
		}
			

	}
    
    /**
     * The possible moves that the player can take during combat.
     */
    public enum PlayerMoves {
    	ATTACK,
    	DEFEND,
    	FLEE,
    	ITEM,
    	MAGIC
    }
    
    public enum EquipmentSlot {
        HEAD,
        CHEST,
        LEGS,
        HANDS,
        FEET,
        WEAPON,
        OFF_HAND
    }
    
	/**
	 * Equip an item to a specified equipment slot.
	 * 
	 * @param item The item to equip.
	 * @param slot The equipment slot to equip the item to.
	 */
	public void equipItem(Item item, EquipmentSlot slot) {
		// Check if the item is valid and the slot is valid
		if (item == null || slot == null) {
			return;
		}

		// Check if the item is already equipped in another slot
		for (Map.Entry<EquipmentSlot, Item> entry : equipment.entrySet()) {
			if (entry.getValue() == item) {
				unequipItem(entry.getKey());
				break;
			}
		}

		// Equip the item to the specified slot
		equipment.put(slot, item);
	}
	
	/**
     * Unequip an item from a specified equipment slot.
     * @param slot The equipment slot to unequip the item from.
     */
    public void unequipItem(EquipmentSlot slot) {
        if (slot != null) {
            equipment.put(slot, null);
        }
    }
    
    /**
     * Get the item equipped in a specified equipment slot.
     * @param slot The equipment slot to get the equipped item from.
     * @return The item equipped in the specified slot, or null if no item is equipped.
     */
    public Item getEquippedItem(EquipmentSlot slot) {
        if (slot != null) {
            return equipment.get(slot);
        }
        return null;
    }

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public LinkedList<String> getPlayerMovesList() {
		return playerMovesList;
	}

	public void setPlayerMovesList(LinkedList<String> playerMovesList) {
		this.playerMovesList = playerMovesList;
	}

	/**
     * @return The player's gender.
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * @param gender The player's new gender.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * @return The player's name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name The player's new name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return The player's current health.
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * @param health The player's new health. If set to above the value for their maximum health, then their health gets clamped down to their maximum health. If their health reaches 0 or below, they die.
     */
    public void setHealth(int health) {
        this.health = health;
        if ( health > MAX_HEALTH )
        	health = MAX_HEALTH;
        if ( health <= 0 )
        	die();
    }
    
    /**
     * @param value The amount to change the player's health by. If this would exceed their maximum health, then their health gets clamped down to their maximum health. If their health reaches 0 or below, they die.
     */
    public void changeHealth(int value)
    {
    	health += value;
    	if ( health > MAX_HEALTH )
    		health = MAX_HEALTH;
    	if ( health <= 0 )
    		die();
    }
    
    /**
     * Method to kill the player. Somehow, this should allow the player to easily restart the game (unimplemented so far).
     */
    public void die()
    {
    	System.out.println("The hero" + (gender.equals("M") || gender.equals("NB") ? "" : "ine") + " falls dead, succumbing to their wounds as the world fades to black around them...");
		System.out.println("GAME OVER...");
		isDead = true;
		sc.close();
    }
    
    /**
     * @return The player's current amount of mana.
     */
    public int getMana() {
		return mana;
	}
	
	/**
	 * @return If the player is currently dead or not.
	 */
	public boolean getIsDead()
	{
		return isDead;
	}
	
	/**
	 * @param isDead Either true or false if the player is currently dead or not.
	 */
	public void setIsDead(boolean isDead)
	{
		this.isDead = isDead;
	}

	
    
    
    
    public LinkedList<String> getOutOfCombatActions() {
		return outOfCombatActions;
	}

	/**
     * Allows for a much better formatted player object when it is converted into a String, rather than the default garbage a Java class displays as with ToString().
     */
    @Override
    public String toString() {
        	return "Name: " + name + "\nGender: " + gender + "\nHealth: " + health;
    }
    
    public void addItemsToInventoryFromJsonFile(String filepath) {
    	
    	if ( filepath == null || filepath.isEmpty() )
    		return;
    	
        try (FileReader reader = new FileReader(filepath)) {
            JsonArray itemsArray = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (JsonElement itemElement : itemsArray) {
            	int armorValue = 0, weaponDamage = 0, healingAmount = 0;
            	Item newItem;
                String name = itemElement.getAsJsonObject().get("name").getAsString();
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
                
                addItemToInventory(newItem, amount);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid amount format for item: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid item type: " + e.getMessage());
        }
    }

    public void addItemToInventory(Item item, int amount) 
    {
        if (item == null)
            return;

        // Check if the item already exists in the inventory
        for (Item currentItem : inventory) 
        {
            if (currentItem.getName().equals(item.getName()) && currentItem.getType() == item.getType()) 
            {
                // If the item already exists, increment its quantity and return
                currentItem.changeAmount(amount);
                return;
            }
        }

        // If the item doesn't exist, add it to the inventory
        item.setAmount(amount);
        inventory.add(item);
    }


    /**
     * Method to remove an item (or several) from the inventory
     * @param item The item to be removed from the inventory. This must be an Item class object that has been properly initialized.
     * @param amount The number of items to remove. This number is automatically made negative in the method, so do not put a negative number in the function call.
     */
    public void removeItemFromInventory(Item item, int amount) {
    	if ( item == null || amount <= 0 )
    		return;
    	for (Item currentItem : inventory) {
            if (currentItem.getName().equals(item.getName()) && currentItem.getType() == item.getType()) {
                // If the item already exists, increment its quantity and return
                currentItem.changeAmount(-amount);
                if ( currentItem.getAmount() == 0 )
                	inventory.remove(currentItem);
                return;
            }
        }
    }
    
    /**
     * Finds and returns an item from the player's inventory.
     * @param item The item to find. This must be an Item class object that has been properly initialized.
     * @return The item from the player's inventory, or null if the player does not have that item.
     */
    public Item getItemFromInventory(Item item)
    {
    	if ( item == null )
    		return null;
    	for (Item currentItem : inventory) {
            if (currentItem.getName().equals(item.getName()) && currentItem.getType() == item.getType()) {
                return currentItem;
            }
        }
    	
    	return null;
    }
    
    /**
     * Finds and returns an item from the player's inventory, or null if there is no item with that name.
     * @param name The name of the item to find. This is just a String.
     * @return The item from the player's inventory, or null if the player does not have that item.
     */
    public Item getItemFromInventoryByName(String name)
    {
    	if ( name == null )
    		return null;
    		
    	if ( name.isEmpty() )
    		return null;
    	for (Item currentItem : inventory) {
            if (currentItem.getName().equalsIgnoreCase(name)) {
                return currentItem;
            }
        }
    	
    	return null;
    }
    
    /**
     * Prints and automatically sorts the player's inventory. Dynamically resizes and formats the inventory list based on the current items in the inventory. Automatically hides entire item categories if the
     * player does not have any items of that category. Displays items alphabetically first by the type of items (e.g. Armor displays first, then food, then keys, etc.), then by the names of the items themselves.
     */
    public void printInventory() {
    	if ( inventory.size() == 0 )
    	{
    		System.out.println("Your inventory is empty.\n");
    		return;
    	}
    		
        System.out.println("\nINVENTORY:");

        // Initialize a map to store items grouped by type
        Map<Item.ItemType, List<Item>> groupedItems = new HashMap<Item.ItemType, List<Item>>();

        // Group items by type
        for (Item currentItem : inventory) {
            groupedItems.computeIfAbsent(currentItem.getType(), k -> new ArrayList<>()).add(currentItem);
        }

        // Sort keys (item types) alphabetically
        List<Item.ItemType> sortedTypes = new ArrayList<>(groupedItems.keySet());
        Collections.sort(sortedTypes, Comparator.comparing(Enum::name));

        // Iterate through the sorted item types and print items
        for (Item.ItemType currentType : sortedTypes) {
            // Print type label
            String currentTypeString = currentType.toString().toLowerCase();
            currentTypeString = currentTypeString.substring(0, 1).toUpperCase() + currentTypeString.substring(1) + "s";
            currentTypeString = currentTypeString.replaceAll("_", " ");
            System.out.println("\n" + currentTypeString + ": ");

            // Get items of the current type
            List<Item> itemsOfType = groupedItems.get(currentType);

            // Sort items of the current type alphabetically by name
            Collections.sort(itemsOfType, Comparator.comparing(Item::getName));

            // Print items within the group
            for (Item currentItem : itemsOfType) {
            	ItemType currentItemType = currentItem.getType();
            	
                // Print the item's name and description
                if (currentItem.getAmount() > 1 && !currentItem.getName().endsWith("s"))
                {
                	switch ( currentItemType )
                	{
                		case ARMOR:
                			System.out.println("* " + currentItem.getName() + "s (" + currentItem.getAmount() + "). " + currentItem.getDescription() 
                			+ " (Protects against " + currentItem.getArmorValue() + " damage)");
                			break;
                			
                		case FOOD:
                		case POTION:
                			System.out.println("* " + currentItem.getName() + "s (" + currentItem.getAmount() + "). " + currentItem.getDescription() 
                			+ " (Heals for " + currentItem.getHealingAmount() + " health)");
                			break;
                			
                		case WEAPON:
                			System.out.println("* " + currentItem.getName() + "s (" + currentItem.getAmount() + "). " + currentItem.getDescription() 
                			+ " (Deals " + currentItem.getWeaponDamage() + " damage)");
                			break;
                	
                		default:
                			System.out.println("* " + currentItem.getName() + ". " + currentItem.getDescription());
                			break;
                	}
                	
                	
                }
                    //System.out.println("* " + currentItem.getName() + "s (" + currentItem.getAmount() + "). " + currentItem.getDescription() 
                    //+ ( ( currentItem.getType().equals(Item.ItemType.ARMOR) ? currentItem.getArmorValue() ) : );
                else if (currentItem.getAmount() > 1 && currentItem.getName().endsWith("s"))
                {
                	switch ( currentItemType )
                	{
                		case ARMOR:
                			System.out.println("* " + currentItem.getName() + " (" +  currentItem.getAmount() + "). " + currentItem.getDescription() 
                			+ " (Protects against " + currentItem.getArmorValue() + " damage)");
                			break;
                			
                		case FOOD:
                		case POTION:
                			System.out.println("* " + currentItem.getName() + " (" +  currentItem.getAmount() + "). " + currentItem.getDescription() 
                			+ " (Heals for " + currentItem.getHealingAmount() + " health)");
                			break;
                			
                		case WEAPON:
                			System.out.println("* " + currentItem.getName() + " (" +  currentItem.getAmount() + "). " + currentItem.getDescription() 
                			+ " (Deals " + currentItem.getWeaponDamage() + " damage)");
                			break;
                	
                		default:
                			System.out.println("* " + currentItem.getName() + ". " + currentItem.getDescription());
                			break;
                	}
                }
                
                else if ( currentItem.getAmount() == 1)
                {
                	switch ( currentItemType )
                	{
                		case ARMOR:
                			System.out.println("* " + currentItem.getName() + ". " + currentItem.getDescription() 
                			+ " (Protects against " + currentItem.getArmorValue() + " damage)");
                			break;
                			
                		case FOOD:
                		case POTION:
                			System.out.println("* " + currentItem.getName() + ". " + currentItem.getDescription() 
                			+ " (Heals for " + currentItem.getHealingAmount() + " health)");
                			break;
                			
                		case WEAPON:
                			System.out.println("* " + currentItem.getName() + ". " + currentItem.getDescription() 
                			+ " (Deals " + currentItem.getWeaponDamage() + " damage)");
                			break;
                	
                		default:
                			System.out.println("* " + currentItem.getName() + ". " + currentItem.getDescription());
                			break;
                	}
                }
                
                
                else
                {
                	System.out.println("THIS LINE SHOULD NOT BE REACHED.");
                	System.exit(-1);
                }
                
                    
            }
        }
    }
}
