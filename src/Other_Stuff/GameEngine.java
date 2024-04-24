package Other_Stuff;
import Player_Related_Stuff.Player;
import Player_Related_Stuff.Spell;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GameEngine 
{
	
	public static void startFight(Player player, LinkedList<Enemy> enemies) {
        // Check if the player or enemies are null
        if (player == null || enemies == null || enemies.isEmpty()) {
            System.out.println("Invalid player or enemy list provided.");
            return;
        }

        // Start the fight
        System.out.println("The battle begins!");

        while (!player.getIsDead() && !enemies.isEmpty()) {
            // Player's turn
            player.takeTurn(enemies);

            // Check if player died after their turn
            if (player.getIsDead()) {
                System.out.println("You have been defeated!");
                return;
            }

            // Enemies' turns
            for (Enemy enemy : enemies) {
                if (!enemy.getIsDead()) {
                    enemy.takeTurn(player);
                }
            }

            // Remove defeated enemies
            enemies.removeIf(Enemy::getIsDead);
        }

        // Check if enemies defeated or player defeated all enemies
        if (enemies.isEmpty()) {
            System.out.println("Congratulations! You have defeated all enemies!");
        }
    }
	
}
