package Other_Stuff;

import java.util.List;

import Dialogues.DialogueLine;
import Player_Related_Stuff.Player;

public class Enemy extends NPC {
	
	private int enemyHealth, enemyDamage;
    private List<String> dialogue;
    private String name;
	
    public Enemy(String name, int enemyHealth, int enemyDamage) {
		super(name);
		this.enemyHealth = enemyHealth;
		this.enemyDamage = enemyDamage;
	}
    
    public void changeEnemyHealth(int value)
    {
    	enemyHealth += value;
    }

    public int getEnemyHealth() {
        return enemyHealth;
    }

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public int getEnemyDamage() {
        return enemyDamage;
    }

    public void setEnemyDamage(int enemyDamage) {
        this.enemyDamage = enemyDamage;
    }
	
	public boolean getIsDead()
	{
		if ( enemyHealth <= 0 )
			return true;
		return false;
	}

	public List<String> getDialogue() {
		return dialogue;
	}

	public void setDialogue(List<String> dialogue) {
		this.dialogue = dialogue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void takeTurn(Player player)
	{
		System.out.println("The " + getName() + " attacks! You take " + enemyDamage + " damage!");
		player.changeHealth(-enemyDamage);
	}
}
