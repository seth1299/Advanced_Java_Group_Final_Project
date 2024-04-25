package Player_Related_Stuff;

public class Spell 
{
	private String name;
	private int manaCost;
	private SpellType spellType;

	public enum SpellType
	{
		BUFF,
		DEBUFF,
		DAMAGE
	}
	
	public SpellType getSpellType() {
		return spellType;
	}

	public void setSpellType(SpellType spellType) {
		this.spellType = spellType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public Spell()
	{
		this.name = "TEST SPELL";
		this.manaCost = 0;
		this.spellType = SpellType.BUFF;
	}
	
	public Spell(String name, int manaCost, SpellType spellType)
	{
		this.name = name;
		this.manaCost = manaCost;
		this.spellType = spellType;
	}
	
	@Override
	public String toString()
	{
		return name + " (" + manaCost + ")";
	}

}
