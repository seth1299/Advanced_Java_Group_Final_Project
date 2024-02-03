package Unit_Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Player_Related_Stuff.Item;
import Player_Related_Stuff.Item.ItemType;
import Player_Related_Stuff.Player;
import Player_Related_Stuff.Player.PlayerClass;

public class TestPlayer {
	
	/**
	 * Tests the creation of a Player object with a default constructor.
	 */
	@Test
	public void testPlayerCreation()
	{
		Player player_default = new Player();
		assertEquals("Name: John Doe" + "\n" + "Gender: M" + "\n" + "Player class: Warrior" + "\n" + "Health: " + 100, player_default.toString());		
	}
	
	/**
	 * Tests some of the methods of the Player class by uusing a Player object to access them.
	 */
	@Test
	public void testPlayerMethods()
	{
		Player the_Thing = new Player("Ben Grim", "F", PlayerClass.WARRIOR);
		the_Thing.setGender("M");
		the_Thing.setHealth(20);
		the_Thing.setName("Ben Grimm");
		assertEquals("Name: Ben Grimm" + "\n" + "Gender: M" + "\n" + "Player class: Warrior" + "\n" + "Health: " + 20, the_Thing.toString());
	}
	
	/**
	 * Speecifically tests the player's inventory.
	 */
	@Test
	public void testPlayerInventory()
	{
		Player Sally_McKnight = new Player("Sally McKnight", "F", PlayerClass.MAGE);
		Item health_potion = new Item("Health Potion", "A healing potion typical of high fantasy worlds, capable of healing wounds instantly.", ItemType.POTION);
		Item felix_felicis = new Item("Felix Felicis", "A vial of liquid luck, drinking this will make your attacks hit more often in combat. Any resemblance to potions real or fictional is purely coincidential.", ItemType.POTION);
		Item wooden_sword = new Item("Wooden Sword", "A short wooden sword, capable of cutting even the mightiest of small bushes.", ItemType.WEAPON);
		Item rounded_buckler = new Item("Rounded Buckler", "A small round buckler, capable of protecting one from fatal blows from anything ranging from small house cats to medium-sized domesticated dogs.", ItemType.ARMOR);
		Item leather_armor = new Item("Leather Armor", "A classic set of leather armor, complete with gauntlets and boots. Stylish, yet functional.", ItemType.ARMOR);
		Item front_door_key = new Item("Front Door Key", "The key to the front door of your house. Can't leave home without it. Or I guess you could, if you want your house to be robbed.", ItemType.KEY);
		Item back_door_key = new Item("Odd Key", "What is this key for? You have no idea where you got it from, but you refuse to part with it because it could come in handy some day.", ItemType.KEY);
		Item macguffin = new Item("Macguffin", "This item will defeat the boss of the game and make you rich, but only after finishing your quest. Why? Because I said so.", ItemType.QUEST_ITEM);
		Item iron_armor = new Item("Iron Armor", "Armor made from iron, stronger than leather but weaker than steel.", ItemType.ARMOR);
		Item apple = new Item("Apple", "A delicious red apple, free from any sort of sophoric poisons as far as you know.", ItemType.FOOD);
		Item wine = new Item("Wine", "A fine aged wine. You can't wait for the day to end so you can drink it.", ItemType.FOOD);
		Item banana = new Item("Banana", "A plain banana. It seems to be getting overly ripe more quickly than you would expect.", ItemType.FOOD);
		Sally_McKnight.addItemToInventory(health_potion, 1);
		Sally_McKnight.addItemToInventory(felix_felicis, 2);
		Sally_McKnight.addItemToInventory(wooden_sword, 1);
		Sally_McKnight.addItemToInventory(rounded_buckler, 1);
		Sally_McKnight.addItemToInventory(leather_armor, 1);
		Sally_McKnight.addItemToInventory(iron_armor, 2);
		Sally_McKnight.addItemToInventory(front_door_key, 1);
		Sally_McKnight.addItemToInventory(back_door_key, 1);
		Sally_McKnight.addItemToInventory(macguffin, 1);
		Sally_McKnight.addItemToInventory(banana,  3);
		Sally_McKnight.addItemToInventory(wine,  2);
		Sally_McKnight.addItemToInventory(apple,  4);
		
		Item returned_item = Sally_McKnight.getItemFromInventory(health_potion);
		assertEquals("Health Potion, " + "A healing potion typical of high fantasy worlds, capable of healing wounds instantly." + ", " + Item.ItemType.POTION + ", " + 1, returned_item.toString());
		Sally_McKnight.addItemToInventory(health_potion, 1);
		returned_item = Sally_McKnight.getItemFromInventory(health_potion);
		assertEquals("Health Potion, " + "A healing potion typical of high fantasy worlds, capable of healing wounds instantly." + ", " + Item.ItemType.POTION + ", " + 2, returned_item.toString());
		returned_item = Sally_McKnight.getItemFromInventory(felix_felicis);
		assertEquals("Felix Felicis, " + "A vial of liquid luck, drinking this will make your attacks hit more often in combat. Any resemblance to potions real or fictional is purely coincidential." + ", " + Item.ItemType.POTION + ", " + 2, returned_item.toString());
		returned_item = Sally_McKnight.getItemFromInventory(wooden_sword);
		assertEquals("Wooden Sword, " + "A short wooden sword, capable of cutting even the mightiest of small bushes." + ", " + Item.ItemType.WEAPON + ", " + 1, returned_item.toString());
		System.out.println(Sally_McKnight.toString());
		Sally_McKnight.printInventory();
	}
	
	/**
	 * Specifically tests the player's spell list.
	 */
	@Test
	public void testPlayerSpells()
	{
		Player Merlin = new Player("Merlin", "M", PlayerClass.MAGE);
		Merlin.displaySpellList();
		Player Arthur = new Player("Arthur", "M", PlayerClass.WARRIOR);
		Arthur.displaySpellList(); // Should not display anything
	}
}
