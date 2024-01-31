package Player_Related_Stuff;

public class Item implements Comparable<Item> {
	
    /**
     * These are the different types an item can be, be it a potion, weapon, armor, key, quest item, or null (only used for the printing inventory method in the Player class)
     */
    public enum ItemType {
        POTION,
        WEAPON,
        ARMOR,
        KEY,
        QUEST_ITEM,
        NULL,
        FOOD
    }

    private String name;
    private String description;
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

    // Getter methods
    public String getName() {
        return name;
    }
    
    public String getDescription()
    {
    	return description;
    }

    public ItemType getType() {
        return type;
    }
    
    public int getAmount()
    {
    	return amount;
    }

    // Setter methods (if needed)
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description)
    {
    	this.description = description;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
    
    public void setAmount(int amount)
    {
    	this.amount = amount;
    }
    
    /**
     * Increases the number of this item that is in the player's inventory.
     * @param amount The amount to change the item by. This is not negative by default, so please input a negative value to subtract from the item's amount.
     */
    public void changeAmount(int amount)
    {
    	this.amount += amount;
    }
    
}
