package Player_Related_Stuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import Other_Stuff.Enemy;

public class Player {
	
	/**
	 * Global variable declarations
	 */
    private int health, mana, damage;
    private final int MAX_HEALTH, MAX_MANA;
    private boolean isDead;
    private String name, gender;
    private LinkedList<Item> inventory;
    private LinkedList<Spell> spells;
    private LinkedList<String> playerMovesList = new LinkedList<>();
    private Map<EquipmentSlot, Item> equipment;
    private final LinkedList<Spell> ALL_POSSIBLE_SPELLS = new LinkedList<>();
    private PlayerClass player_class;
    
    /**
	 * Default Player constructor, all values are automatically assigned to garbage values.
	 */
    public Player() {
        health = 10;
        name = "UNINSTANTIATED";
        gender = "UNINSTANTIATED";
        isDead = false;
        inventory = new LinkedList<>();
        spells = new LinkedList<>();
        player_class = PlayerClass.NULL;
        damage = 2;
        playerMovesList.add("ATTACK");
        playerMovesList.add("DEFEND");
        playerMovesList.add("FLEE");
        playerMovesList.add("ITEM");
        playerMovesList.add("MAGIC");
        equipment = new HashMap<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            equipment.put(slot, null);
        }
        this.MAX_HEALTH = 100;
        this.MAX_MANA = 0;
        this.setHealth(MAX_HEALTH);
        this.setMana(MAX_MANA);
        ALL_POSSIBLE_SPELLS.clear();
    }

    /**
     * Full constructor for the Player class.
     * @param name The player's name.
     * @param gender The player's gender.
     * @param player_class The player's character class.
     */
    public Player(String name, String gender, PlayerClass player_class) {
        this.name = name;
        this.gender = gender;
        inventory = new LinkedList<>();
        spells = new LinkedList<>();
        this.player_class = player_class;
        playerMovesList.add("ATTACK");
        playerMovesList.add("DEFEND");
        playerMovesList.add("FLEE");
        playerMovesList.add("ITEM");
        playerMovesList.add("MAGIC");
        isDead = false;

        // Switch statement for assigning health based on player_class enum
        switch (this.player_class) {
            case WARRIOR:
            	this.MAX_HEALTH = 100;
            	this.MAX_MANA = 0;
                setDamage(3);
                ALL_POSSIBLE_SPELLS.clear();
                break;
            case ROGUE:
            	this.MAX_HEALTH = 80;
            	this.MAX_MANA = 0;
            	setDamage(2);
            	ALL_POSSIBLE_SPELLS.clear();
                break;
            case MAGE:
            	this.MAX_HEALTH = 60;
            	this.MAX_MANA = 10;
            	setDamage(1);
                break;
            default:
            	this.MAX_HEALTH = 100;
            	this.MAX_MANA = 0;
            	setDamage(3);
                break;
        }
        
        this.setHealth(MAX_HEALTH);
        this.setMana(MAX_MANA);
        ADD_ALL_SPELLS("spells.json");
        addValidSpells();
    }
    
    public void setNameAndClass()
    {
    	Scanner sc = new Scanner(System.in);
    	String response = "", name = "";
    	
    	do
    	{
    		System.out.println("What is your name?");
    		name = sc.nextLine();
    		
    		System.out.println("Your name is " + name + "? (Y/N)");
    		response  = sc.nextLine();
    	}while(!response.trim().equalsIgnoreCase("Y"));
    	
    	do
    	{
    		System.out.println("What class would you like?");
    		name = sc.nextLine();
    		
    		System.out.println("Your name is " + name + "? (Y/N)");
    		response  = sc.nextLine();
    	}while(!response.trim().equalsIgnoreCase("Y"));
    	
    	
    	
    	sc.close();
    }
    
    public void takeTurn(LinkedList<Enemy> enemies) {
    	Scanner sc = new Scanner(System.in);
        LinkedList<String> playerMoves = getPlayerMovesList();
        Map<String, Integer> nameCountMap = new HashMap<>(); // Map to store counts of each enemy name
        int playerDamage = getDamage();

		// Display available moves
		System.out.println("\n" + getName() + "'s move! What will you do? (Current health: " + getHealth() + ")");
		for (String move : playerMoves)
			System.out.println(move);

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
		if (response.equals("ATTACK")) {
			do {
				System.out.println("\nWho would you like to attack?");
				response = sc.nextLine().trim().toLowerCase(); // Convert player's input to lowercase for comparison
			} while (!nameCountMap.keySet().stream().map(String::toLowerCase).collect(Collectors.toSet())
					.contains(response)); // Convert each key in the map to lowercase before comparison

			System.out.println("You attack the " + response + " for " + playerDamage + " damage!\n");

			// Inflict damage on the enemy
			for (Enemy currentEnemy : enemies) {
				if (currentEnemy.getName().equalsIgnoreCase(response)) {
					currentEnemy.changeEnemyHealth(-playerDamage);
					if (currentEnemy.getEnemyHealth() <= 0)
					{
						System.out.println("You have slain the " + response + "!");
						enemies.remove(currentEnemy);
					}
						
				}

			}
		} else {
			System.out.println(response + " IS NOT IMPLEMENTED YET.");
		}
        
    }
    
    /**
     * The player's class, either mage, rogue, or warrior.
     */
    public enum PlayerClass {
        MAGE,
        ROGUE,
        WARRIOR,
        NULL
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
        // Add more equipment slots as needed
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
    
    /**
     * This is the actual method to add items to the player's spell list, not all the spells in the game at once.
     * @param filepath The file path to read from. This is generally a .txt file, such as "spellsFromChest1.txt"
     */
    public void addSpellsFromFile(String filepath) {
        try (FileReader reader = new FileReader(filepath)) {
            Gson gson = new Gson();
            List<Spell> spellList = gson.fromJson(reader, new TypeToken<List<Spell>>() {}.getType());

            // Add each spell from the list to the player's spell list
            if (spellList != null) {
                spells.addAll(spellList);
            }
        } catch (IOException e) {
            System.out.println("Error reading spells file: " + filepath + "\n" + e.getMessage());
        }
    }
    
    /**
     * This method is only to add all possible spells to the ALL_POSSIBLE_SPELLS final LinkedList<Spell> during the Player class instantiation. It will never be invoked again after the class instantiation.
     */
    final public void ADD_ALL_SPELLS(String filepath) {
        try (FileReader reader = new FileReader(filepath)) {
            Gson gson = new Gson();
            List<Spell> spellList = gson.fromJson(reader, new TypeToken<List<Spell>>() {}.getType());

            // Add each spell from the list to the ALL_POSSIBLE_SPELLS list
            if (spellList != null) {
                ALL_POSSIBLE_SPELLS.addAll(spellList);
            }
        } catch (IOException e) {
            System.out.println("Error reading spells file: " + filepath + "\n" + e.getMessage());
        }
    }
    
   
    
    /**
     * @param spell The spell to add to the player's current spell list.
     */
    public void addSpell(Spell spell)
    {
    	if ( spell != null && spells != null && !spells.contains(spell) )
    		spells.add(spell);
    }
    
    /**
     * Adds any spells with a mana cost of less than or equal to the player's maximum mana into the player's possible spells LinkedList.
     */
    public void addValidSpells()
    {
    	for ( Spell spell : ALL_POSSIBLE_SPELLS )
    	{
    		if ( spell != null && spell.getManaCost() <= MAX_MANA )
    			addSpell(spell);
    	}
    }
    
    public void displaySpellList() {
        if (MAX_MANA == 0 || spells == null)
            return;
        else {
        	
        	System.out.println("SPELLS: \n");
            // Initialize a map to store spells grouped by type
            Map<Spell.SpellType, List<Spell>> groupedSpells = new HashMap<>();

            // Group spells by type
            for (Spell spell : spells) {
                groupedSpells.computeIfAbsent(spell.getSpellType(), k -> new ArrayList<>()).add(spell);
            }

            // Iterate through the grouped spells and print them
            for (Map.Entry<Spell.SpellType, List<Spell>> entry : groupedSpells.entrySet()) {
                // Print spell type
                String spellTypeFormatted = entry.getKey().toString().toLowerCase();
                spellTypeFormatted = spellTypeFormatted.substring(0, 1).toUpperCase() + spellTypeFormatted.substring(1);
                System.out.println(spellTypeFormatted + ":");

                // Sort spells by name within the type
                entry.getValue().sort(Comparator.comparing(Spell::getName));

                // Print spells within the type
                for (Spell spell : entry.getValue()) {
                    System.out.println("* " + spell.getName() + " (" + spell.getManaCost() + ")");
                }

                System.out.println();
            }
        }
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
     * @return The player's class (not to be confused with the .java class of this file).
     */
    public PlayerClass getPlayerClass()
    {
    	return player_class;
    }
    
    /**
     * @param player_class The player's new class.
     */
    public void setPlayerClass(PlayerClass player_class)
    {
    	this.player_class = player_class;
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
    	System.out.println("The hero" + (gender.equals("M") ? "" : "ine") + " falls dead, succumbing to their wounds as the world fades to black around them...");
		System.out.println("GAME OVER...");
		isDead = true;
    }
    
    /**
     * @return The player's current amount of mana.
     */
    public int getMana() {
		return mana;
	}
    
    /**
     * @param mana The amount of mana to set the player's current mana to. If this would increase their mana above their maximum mana, then it will get clamped to their maximum mana.
     */
	public void setMana(int mana) {
		this.mana = mana;
		if ( mana > MAX_MANA )
			mana = MAX_MANA;
		if ( mana < 0 )
			mana = 0;
	}
	
	/**
	 * @param value The amount of mana to change the player's current mana by. If this would increase their mana above their maximum mana, then it will get clamped to their maximum mana.
	 */
	public void changeMana(int value)
	{
		mana += value;
		if ( mana > MAX_MANA )
			mana = MAX_MANA;
		if ( mana < 0 )
			mana = 0;
	}
	
	/**
	 * @return The player's maximum amount of mana.
	 */
	public int getMaxMana()
	{
		return MAX_MANA;
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

	
    
    
    
    /**
     * Allows for a much better formatted player object when it is converted into a String, rather than the default garbage a Java class displays as with ToString().
     */
    @Override
    public String toString() {
        String playerClassFormatted = player_class.toString().toLowerCase();
        playerClassFormatted = playerClassFormatted.substring(0, 1).toUpperCase() + playerClassFormatted.substring(1);
        if ( playerClassFormatted.equals("Mage"))
        	return "Name: " + name + "\nGender: " + gender + "\nPlayer class: " + playerClassFormatted + "\nHealth: " + health + "\nMana: " + mana;
        else
        	return "Name: " + name + "\nGender: " + gender + "\nPlayer class: " + playerClassFormatted + "\nHealth: " + health;
    }
    
    public void addItemsToInventoryFromJsonFile(String filepath) {
        try (FileReader reader = new FileReader(filepath)) {
            JsonArray itemsArray = JsonParser.parseReader(reader).getAsJsonArray();
            
            for (JsonElement itemElement : itemsArray) {
                String name = itemElement.getAsJsonObject().get("name").getAsString();
                String description = itemElement.getAsJsonObject().get("description").getAsString();
                Item.ItemType type = Item.ItemType.valueOf(itemElement.getAsJsonObject().get("type").getAsString());
                int amount = itemElement.getAsJsonObject().get("amount").getAsInt();
                
                // Create a new item and add it to the player's inventory
                Item newItem = new Item(name, description, type);
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
    	if ( item == null )
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
                // If the item already exists, increment its quantity and return
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
    		System.out.println(name + "'s inventory is empty.\n");
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
                // Print the item's name and description
                if (currentItem.getAmount() > 1 && !currentItem.getName().endsWith("s"))
                    System.out.println("* " + currentItem.getName() + "s (" + currentItem.getAmount() + "). " + currentItem.getDescription());
                else if (currentItem.getAmount() > 1 && currentItem.getName().endsWith("s"))
                    System.out.println("* " + currentItem.getName() + " (" +  currentItem.getAmount() + "). " + currentItem.getDescription());
                else
                    System.out.println("* " + currentItem.getName() + ". " + currentItem.getDescription());
            }
        }
        System.out.println();
    }
}
