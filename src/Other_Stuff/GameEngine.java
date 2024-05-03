package Other_Stuff;
import Player_Related_Stuff.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GameEngine 
{
	
	public static void startFight(Player player, List<Enemy> enemies) {
		System.out.println();
		
        // Check if the player or enemies are null
        if (player == null || enemies == null || enemies.isEmpty()) {
            System.out.println("Invalid player or enemy list provided.");
            return;
        }
        
        System.out.println("There " + ( enemies.size() > 1  ? "are enemies in this room!" : "is an enemy in this room!"));
        
        for (Enemy enemy : enemies)
        {
        	if ( enemy.getEnemyDialogue() != null )
        	{
        		System.out.println("\n" + enemy.getName() + ": " + enemy.getEnemyDialogue());
        	}
        }

        // Start the fight
        while (!player.getIsDead() && !enemies.isEmpty() && !player.getHasFled()) {
            // Player's turn
            player.takeTurn(enemies);

            // Check if player died after their turn
            if (player.getIsDead()) {
                System.out.println("You have been defeated!");
                return;
            }

            // Enemies' turns
            for (Enemy enemy : enemies) {
                if (!enemy.getIsDead() && !player.getHasFled()) 
                {
                    enemy.takeTurn(player);
                }
                else if ( player.getHasFled() ) 
                {
                	break;
                }
            }
            
        }

        // Check if enemies defeated or player defeated all enemies
        if (enemies.isEmpty()) {
            System.out.println("Congratulations! You have defeated all enemies!");
        }
    }
	
	public static void startFight(Player player, Enemy enemy)
	{
		System.out.println();
		
		if ( player == null || enemy == null )
		{
			System.out.println("Invalid player or enemy provided.");
			return;
		}
		
		if ( enemy.getEnemyDialogue() != null )
		{
			enemy.createDialogueFromJsonFile(player);
	    	enemy.printDialogue(player);
		}
		 
    	
    	// Start the fight
        System.out.println("The battle begins!");
        
        while (!player.getIsDead() && !player.getHasFled()) 
        {
            // Player's turn
            player.takeTurn(enemy);
            if ( enemy.getIsDead() || player.getHasFled())
            	break;
            enemy.takeTurn(player);
        }
	}
	
}
