package Dialogues;

public class DialogueLine {
	private String line;
    private boolean requiresInput;

    public DialogueLine(String line, boolean requiresInput) {
        this.line = line;
        this.requiresInput = requiresInput;
    }

    public String getLine() {
        return line;
    }

    public boolean isRequiresInput() {
        return requiresInput;
    }
}