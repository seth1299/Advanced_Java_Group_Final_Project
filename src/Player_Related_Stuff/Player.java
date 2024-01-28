package Player_Related_Stuff;

public class Player 
{
	private int health;
	private String name, gender;
	
	
	/** Getters and setters **/
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
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
	
	public Player()
	{
		health = 1;
		name = "John Doe";
		gender = "M";
	}
	
	public Player(int health)
	{
		this.health = health;
		name = "John Doe";
		gender = "M";
	}
	
	public Player(int health, String name)
	{
		this.health = health;
		this.name = name;
		gender = "M";
	}
	
	public Player(String name, String gender, int health)
	{
		this.health = health;
		this.name = name;
		this.gender = gender;
	}
	
	public Player (String name) throws Exception
	{
		throw new Exception("Player cannot be instantiated with only a String value.");
	}
	
	public Player (String name, String gender)
	{
		this.health = 1;
		this.name = name;
		this.gender = gender;
	}
	
	@Override
	public String toString()
	{
		return name + "\n" + gender + "\n" + health;
	}

}
