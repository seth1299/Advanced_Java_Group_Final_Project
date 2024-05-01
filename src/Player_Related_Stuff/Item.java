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
	private int amount, armorValue, weaponDamage, healingAmount;
    
    // Constructor without armor value and weapon damage
    public Item(String name, String description, ItemType type) 
    {
    		this(name, description, type, 0);
    }

    /**
     * @param name The item's name.
     * @param description The item's description.
     * @param type What type of item it is. This is an enum value that must be ItemType.POTION, ItemType.WEAPON, etc.
     */
    public Item(String name, String description, ItemType type, int integerAmount) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.amount = 1;
        switch ( type )
        {
        	case ARMOR:
        		armorValue = integerAmount;
        		break;
        	case FOOD:
        	case POTION:
        		healingAmount = integerAmount;
        		break;
        	case WEAPON:
        		weaponDamage = integerAmount;
        	default:
        		break;
        }
    }
    
    // Implementing Comparable interface
    @Override
    public int compareTo(Item other) {
        return this.type.compareTo(other.getType());
    }
    
    @Override
    public String toString()
    {
    	switch ( getType() )
    	{
    		case ARMOR:
    			return name + ", " + description + " (Protects against " + armorValue + " damage)";
    		case POTION:
    		case FOOD:
    			return name + ", " + description + " (Heals for " + healingAmount + " health)";
    		case WEAPON:
    			return name + ", " + description + " (dooes " + weaponDamage + " damage)";
    		default:
    			return name + ", " + description;
    	}
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
     * @return How many of this item there are. For example, if the player has 3 health potions, then there will just be one Item object that has name = "Health Potion" 
     * and amount = 3.
     */
    public int getAmount()
    {
    	return amount;
    }

    public int getArmorValue() {
		return armorValue;
	}

	public void setArmorValue(int armorValue) {
		this.armorValue = armorValue;
	}

	public int getWeaponDamage() {
		return weaponDamage;
	}

	public void setWeaponDamage(int weaponDamage) {
		this.weaponDamage = weaponDamage;
	}

	public int getHealingAmount() {
		return healingAmount;
	}

	public void setHealingAmount(int healingAmount) {
		this.healingAmount = healingAmount;
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
