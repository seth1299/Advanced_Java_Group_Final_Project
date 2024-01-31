package Player_Related_Stuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Player {
	
	/**
	 * Global variable declarations
	 */
    private int health, mana;
    private final int MAX_HEALTH, MAX_MANA;
    private boolean isDead;
    private String name, gender;
    private LinkedList<Item> inventory;
    private PlayerClass player_class;
    
    // The player's class, either mage, rogue, or warrior.
    public enum PlayerClass {
        MAGE,
        ROGUE,
        WARRIOR
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
	 * Default Player constructor, all values are automatically assigned to garbage values.
	 */
    public Player() {
        health = 1;
        name = "John Doe";
        gender = "M";
        isDead = false;
        inventory = new LinkedList<>();
        player_class = PlayerClass.WARRIOR;
        this.MAX_HEALTH = 100;
        this.MAX_MANA = 0;
        this.setHealth(MAX_HEALTH);
        this.setMana(MAX_MANA);
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
        this.player_class = player_class;
        isDead = false;

        // Switch statement for assigning health based on player_class enum
        switch (this.player_class) {
            case WARRIOR:
            	this.MAX_HEALTH = 100;
            	this.MAX_MANA = 0;
                
                break;
            case ROGUE:
            	this.MAX_HEALTH = 80;
            	this.MAX_MANA = 0;
                break;
            case MAGE:
            	this.MAX_HEALTH = 60;
            	this.MAX_MANA = 10;
                break;
            default:
            	this.MAX_HEALTH = 100;
            	this.MAX_MANA = 0;
                break;
        }
        
        this.setHealth(MAX_HEALTH);
        this.setMana(MAX_MANA);
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


    /**
     * Adds an item to the player's inventory.
     * @param item The item to be added to the inventory. This must be an Item class object that has been properly initialized.
     * @param amount The number of items to add.
     */
    public void addItemToInventory(Item item, int amount) {
    	if ( item == null )
    		return;
        // Check if the item already exists in the inventory
    	
        for (Item currentItem : inventory) {
            if (currentItem.getName().equals(item.getName()) && currentItem.getType() == item.getType()) {
                // If the item already exists, increment its quantity and return
                currentItem.changeAmount(amount);
                return;
            }
        }
        
     // If the item doesn't exist, add it to the inventory, and add the amount specified in the method parameters.
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
