import Player_Related_Stuff.Player;
import Player_Related_Stuff.Spell;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameEngine 
{
	/**
	 * TODO: Replace the second Player parameter with an "Enemy" parameter once the NPC and Enemy classes (which extends the NPC class) are created
	 * @param player The player in the fight.
	 * @param enemies The enemies in the fight.
	 */
	public void StartFight(Player player, Player[] enemies)
	{
		int max_enemy_speed = 0;
		
		for ( Player enemy : enemies )
		{
			if ( enemy.getSpeed() > max_enemy_speed )
				max_enemy_speed = enemy.getSpeed();
		}
		
		if ( max_enemy_speed > player.getSpeed() )
		{
			
		}
	}

}
