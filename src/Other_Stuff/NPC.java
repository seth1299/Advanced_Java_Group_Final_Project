package Other_Stuff;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Dialogues.DialogueLine;
import Player_Related_Stuff.Player;

public class NPC {
    private String name;
    private List<DialogueLine> dialogue;

    public NPC(String name) {
        this.name = name;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void createDialogueFromJsonFile(String filepath, Player player) {
	    try (FileReader reader = new FileReader(filepath)) {
	        JsonParser parser = new JsonParser();
	        JsonElement jsonElement = parser.parse(reader);
	        JsonArray jsonArray = jsonElement.getAsJsonArray();

	        dialogue = new ArrayList<>();
	        Gson gson = new Gson();

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
	}


    public void printDialogue(Player player) {
        if (dialogue != null && !dialogue.isEmpty()) {
            for (DialogueLine line : dialogue) {
                System.out.println(name + "- " + line.getLine());
                if (line.isRequiresInput()) {
                    String response = waitForPlayerInput(player);
                }
            }
        }
    }

    private String waitForPlayerInput(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your response: ");
        String response = scanner.nextLine();
        return response;
        // Handle player response
    }
}
