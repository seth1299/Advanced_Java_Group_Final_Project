package Other_Stuff;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Dialogues.DialogueLine;
import Player_Related_Stuff.Player;

public class NPC {
	
    private String name;
    private String filepath;
    private List<DialogueLine> dialogue;

    public NPC()
    {
    	this.name = "UNINSTANTIATED NAME";
    	this.filepath = "UNINSTANTIATED FILEPATH";
    }
    
    public NPC(String name) 
    {
        this.name = name;
        this.filepath = "UNINSTANTIATED FILEPATH";
    }
    
    public NPC(String name, String filepath)
    {
    	this.name = name;
    	this.filepath = filepath;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void speakToPlayer(Player player)
	{
		createDialogueFromJsonFile(player);
	}
	
	/**
	 * @param player The player to check for certain variables such as name, gender, class, etc.
	 */
	@SuppressWarnings("deprecation")
	public void createDialogueFromJsonFile(Player player) {
		if ( player.equals(null) )
			return;
		
	    try (FileReader reader = new FileReader(filepath)) {
	        JsonParser parser = new JsonParser();
	        JsonElement jsonElement = parser.parse(reader);
	        JsonArray jsonArray = jsonElement.getAsJsonArray();

	        dialogue = new ArrayList<>();
	        for (JsonElement element : jsonArray) {
	            JsonObject dialogueObject = element.getAsJsonObject();
	            String line = dialogueObject.get("line").getAsString();
	            boolean requiresInput = dialogueObject.has("requiresInput") ? dialogueObject.get("requiresInput").getAsBoolean() : false;
	            
	            if (line.contains("[Player name]")) {
	                line = line.replace("[Player name]", player.getName()); // getPlayerName() method should return the actual player name
	            }
	            dialogue.add(new DialogueLine(line, requiresInput));
	        }
	    } catch (IOException e) {
	        System.err.println("Error reading JSON file: " + e.getMessage());
	    }
	    
	    printDialogue(player);
	}


    public void printDialogue(Player player) {
        if (dialogue != null && !dialogue.isEmpty()) {
            for (DialogueLine line : dialogue) {
                System.out.println(name + ": " + line.getLine());
                if (line.isRequiresInput()) {
                    @SuppressWarnings("unused")
					String response = waitForPlayerInput(player);
                }
            }
        }
    }

    private String waitForPlayerInput(Player player) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        // I am aware that the scanner is not closed, however if it is closed then it for some reason can't be opened
        // again, preventing the player from ever being able to input anything again.
        System.out.print("Your response: ");
        String response = scanner.nextLine();
        return response;
    }
}
