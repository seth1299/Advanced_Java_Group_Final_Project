package Other_Stuff;

import java.util.Scanner;
import Player_Related_Stuff.Player;

public class World {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
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
		
		System.out.println("\n" + player);
		
		sc.close();
	}

}
