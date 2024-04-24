package Other_Stuff;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Player_Related_Stuff.Player;
import Other_Stuff.GameEngine;

public class World {
	
	private static List<Enemy> enemies;
	
	public static List<Enemy> loadEnemies(String filename) throws IOException {
        Gson gson = new GsonBuilder().create();
        Type enemyListType = new TypeToken<List<Enemy>>(){}.getType();

        try (Reader reader = new FileReader(filename)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            List<Enemy> enemies = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                Enemy enemy = gson.fromJson(jsonObject, Enemy.class);
                if (jsonObject.has("dialogue")) {
                    enemy.setEnemyDialogue(jsonObject.get("dialogue").getAsString());
                }
                enemies.add(enemy);
            }

            return enemies;
        }
    }
	
	public static Enemy getEnemyByName(List<Enemy> enemies, String name) {
        for (Enemy enemy : enemies) {
            if (enemy.getName().equals(name)) {
                return enemy;
            }
        }
        return null; // Enemy not found
    }

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
            enemies = loadEnemies("src/Other_Stuff/enemies.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		String response = "", name = "", gender = "";
		
		do
		{
			System.out.println("Hello! What is your name?");
			name = sc.nextLine();
			
			System.out.println("Your name is " + name + "? ('Y' for yes, 'N' for no)");
			response = sc.nextLine();
		} while(!response.trim().equalsIgnoreCase("Y") && !response.trim().equalsIgnoreCase("YES"));
		
		response = "";
		
		do
		{
			System.out.println("And are you (M)ale, (F)emale, or (N)onbinary?");
			gender = sc.nextLine().trim().toUpperCase();
			
			switch(gender)
			{
				case "M":
			    case "MALE":
			    	gender = "M";
			        System.out.println("So you're a man? (Y/N)");
			        break;
			    case "F":
			    case "FEMALE":
			    	gender = "F";
			        System.out.println("So you're a woman? (Y/N)");
			        break;
			    case "N":
			    case "NONBINARY":
			    	gender = "NB";
			        System.out.println("So you're nonbinary? (Y/N)");
			        break;
			    default:
			        System.out.println("Sorry, I didn't recognize that. Please enter 'M', 'F', or 'N'.");
			        continue;
			}
			
			response = sc.nextLine().trim().toUpperCase();

		} while(!response.trim().equalsIgnoreCase("Y") && !response.trim().equalsIgnoreCase("YES"));
		
		response = "";
		Player.PlayerClass playerClass = Player.PlayerClass.NULL;
		
		do
		{
			System.out.println("And are you a (M)age, (R)ogue, or (W)arrior?");
			response = sc.nextLine().trim().toUpperCase();
			
			switch(response)
			{
				case "M":
			    case "MAGE":
			        System.out.println("So you're a mage? (Y/N)");
			        playerClass = Player.PlayerClass.MAGE;
			        break;
			    case "R":
			    case "ROGUE":
			        System.out.println("So you're a rogue? (Y/N)");
			        playerClass = Player.PlayerClass.ROGUE;
			        break;
			    case "W":
			    case "WARRIOR":
			        System.out.println("So you're a warrior? (Y/N)");
			        playerClass = Player.PlayerClass.WARRIOR;
			        break;
			    default:
			        System.out.println("Sorry, I didn't recognize that. Please enter 'M', 'R', or 'W'.");
			        continue;
			}
			
			response = sc.nextLine().trim().toUpperCase();

		} while(!response.trim().equalsIgnoreCase("Y") && !response.trim().equalsIgnoreCase("YES"));
		
		Player player = new Player(name, gender, playerClass);
		Enemy bandit = getEnemyByName(enemies, "Bandit");
		Enemy bat1 = getEnemyByName(enemies, "Bat"), bat2 = getEnemyByName(enemies, "Bat");
		
		LinkedList<Enemy> enemies = new LinkedList<>();
	    enemies.add(bat1);
	    enemies.add(bandit);
		
		//Other_Stuff.GameEngine.startFight(player, enemies);
		//Other_Stuff.GameEngine.startFight(player, bat2);
	    
	    NPC steve = new NPC("Steve", "src/Dialogues/Tutorial_NPC_dialogue.json");
	    steve.speakToPlayer(player);
		
		sc.close();
	}

}
