package de.atlascore.command;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.command.ConsoleCommandSender;
import de.atlasmc.permission.Permission;

public class CoreConsoleCommandSender implements ConsoleCommandSender {

	private final Console console;
	private final Scanner scanner;
	private volatile boolean colorConsole = true;
	
	public CoreConsoleCommandSender() throws IOException {
		this.console = System.console();
		if (console == null) {
			this.scanner = new Scanner(System.in);
		} else {
			this.scanner = null;
		}
	}
	
	@Override
	public String readLine() {
		return console != null ? console.readLine() : scanner.nextLine();
	}
	
	@Override
	public String readLine(String prompt, Character mask) {
		if (console == null)
			return scanner.nextLine();
		char[] rawPW = console.readPassword(prompt);
		if (rawPW == null)
			return "";
		return new String(rawPW);
	}
	
	@Override
	public String readLine(String prompt) {
		if (console == null)
			return scanner.nextLine();
		return console.readLine(prompt);
	}
	
	@Override
	public String readLine(Character mask) {
		if (console == null)
			return scanner.nextLine();
		char[] rawPW = console.readPassword();
		if (rawPW == null)
			return "";
		return new String(rawPW);
	}
	
	@Override
	public Permission getPermission(CharSequence permission) {
		return null;
	}
	
	@Override
	public boolean hasPermission(CharSequence permission) {
		return true;
	}

	@Override
	public void sendMessage(Chat chat) {
		if (colorConsole) {
			internalSend(ChatUtil.componentToConsole(chat.toComponent()));
		} else {
			internalSend(chat.toRawText());
		}
	}

	@Override
	public void sendMessage(String message) {
		if (colorConsole) {
			sendMessage(ChatUtil.legacyToComponent(message));
		} else {
			internalSend(ChatUtil.legacyToRawText(message));
		}
	}

	@Override
	public void sendMessage(String message, ChatType type, String source, String target) {
		if (colorConsole) {
		sendMessage(ChatUtil.legacyToComponent(message));
		} else {
			internalSend(ChatUtil.legacyToRawText(message));
		}
	}
	
	private void sendMessage(ChatComponent chat) {
		internalSend(ChatUtil.componentToConsole(chat));
	}
	
	private void internalSend(String message) {
		System.out.println(message);
	}
	
	@Override
	public boolean hasColors() {
		return colorConsole;
	}
	
	@Override
	public void setUseColor(boolean colors) {
		this.colorConsole = colors;
	}

	@Override
	public void sendTranslation(String key, Object... values) {
		// TODO Auto-generated method stub
		
	}

}
