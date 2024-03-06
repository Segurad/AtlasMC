package de.atlasmc.command;

public interface ConsoleCommandSender extends CommandSender {
	
	String readLine();
	
	String readLine(String prompt);
	
	String readLine(String prompt, Character mask);
	
	String readLine(Character mask);

}
