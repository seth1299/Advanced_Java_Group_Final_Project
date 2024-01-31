package Player_Related_Stuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Player {
	
    private int health, mana;
    private boolean isDead;
    private String name, gender;
    private LinkedList<Item> inventory;
    private PlayerClass player_class;
    
    // The player's class
    public enum PlayerClass {
        WARRIOR,
        MAGE,
        ROGUE
    }

    // Getters and setters
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }
    
    public PlayerClass getPlayerClass()
    {
    	return player_class;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public void changeHealth(int value)
    {
    	health += value;
    	if ( health <= 0 )
		{
			System.out.println("The hero" + (gender.equals("M") ? "" : "ine") + " falls dead, succumbing to their wounds as the world fades to black around them...");
			System.out.println("GAME OVER...");
			isDead = true;
		}
    }

    public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public boolean getIsDead()
	{
		return isDead;
	}
	
	public void setIsDead(boolean isDead)
	{
		this.isDead = isDead;
	}
	
	public void changeMana(int value)
	{
		mana += value;
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
        this.setHealth(100);
        this.setMana(0);
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
                this.setHealth(100);
                this.setMana(0);
                break;
            case ROGUE:
                this.setHealth(80);
                this.setMana(0);
                break;
            case MAGE:
                this.setHealth(60);
                this.setMana(10);
                break;
            default:
                this.setHealth(100); // Default health value
                this.setMana(0); // Default mana value
                break;
        }
    }


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
     * @param The item to find. This must be an Item class object that has been properly initialized.
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
     * Prints the player's inventory.
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
