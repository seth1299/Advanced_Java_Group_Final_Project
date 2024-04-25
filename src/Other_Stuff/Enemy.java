package Other_Stuff;

import Player_Related_Stuff.Player;

public class Enemy extends NPC {
	
	private int enemyHealth, enemyDamage;
    private String enemyDialogue;
	
    public Enemy(String name, int enemyHealth, int enemyDamage) {
		super(name);
		this.enemyHealth = enemyHealth;
		this.enemyDamage = enemyDamage;
	}
    
    public int[] getEnemyStats()
    {
    	return new int[] {enemyHealth, enemyDamage};
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

	public String getEnemyDialogue() {
		return enemyDialogue;
	}

	public void setEnemyDialogue(String enemyDialogue) {
		this.enemyDialogue = enemyDialogue;
	}
	
	@Override
	public String toString()
	{
		return "Enemy name: " + getName() + "\nEnemy health: " + getEnemyHealth() + "\nEnemy damage: " + getEnemyDamage();
	}

	public void takeTurn(Player player)
	{
		System.out.println("The " + getName() + " attacks! You take " + enemyDamage + " damage!");
		player.changeHealth(-enemyDamage);
	}
}
