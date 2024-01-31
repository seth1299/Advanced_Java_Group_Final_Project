package Player_Related_Stuff;

public class Item implements Comparable<Item> {
	
    /**
     * These are the different types an item can be, be it a potion, weapon, armor, key, quest item, or null (only used for the printing inventory method in the Player class)
     */
    public enum ItemType {
    	ARMOR,
    	FOOD,
    	KEY,
    	NULL,
    	POTION,
    	QUEST_ITEM,
    	WEAPON
    }
    
    /**
     * Global variable declarations
     */
    private String name, description;
    private ItemType type;
    private int amount;

    /**
     * @param name The item's name.
     * @param description The item's description.
     * @param type What type of item it is. This is an enum value that must be ItemType.POTION, ItemType.WEAPON, etc.
     */
    public Item(String name, String description, ItemType type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.amount = 1;
    }
    
    // Implementing Comparable interface
    @Override
    public int compareTo(Item other) {
        return this.type.compareTo(other.getType());
    }
    
    @Override
    public String toString()
    {
    	return name + ", " + description + ", " + type + ", " + amount;
    }

    /**
     * @return The item's name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return The item's description.
     */
    public String getDescription()
    {
    	return description;
    }
    
    /**
     * @return The item's type (armor, food, potion, weapon, etc.).
     */
    public ItemType getType() {
        return type;
    }
    
    /**
     * @return How many of this item the player has in their inventory.
     */
    public int getAmount()
    {
    	return amount;
    }

    /**
     * @param name The item's new name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param description The item's new description.
     */
    public void setDescription(String description)
    {
    	this.description = description;
    }
    
    /**
     * @param type The item's new type.
     */
    public void setType(ItemType type) {
        this.type = type;
    }
    
    /**
     * @param amount The new number of how many of this item the player has in their inventory.
     */
    public void setAmount(int amount)
    {
    	this.amount = amount;
    }
    
    /**
     * Increases the number of this item that is in the player's inventory.
     * @param amount The amount to change the item by. This is not negative by default, so please input a negative value if you want to subtract from the item's amount, rather than add.
     */
    public void changeAmount(int amount)
    {
    	this.amount += amount;
    }
    
}
