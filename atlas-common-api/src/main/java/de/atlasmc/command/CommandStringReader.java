package de.atlasmc.command;

import de.atlasmc.util.annotation.NotNull;

public class CommandStringReader {
	
	private final String command;
	private int cursor;
	
	public CommandStringReader(String command) {
		if (command == null)
			throw new IllegalArgumentException("Command can not be null!");
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
	
	public int getCursor() {
		return cursor;
	}
	
	public void setCursor(int cursor) {
		if (cursor < 0 || cursor > command.length())
			throw new IllegalArgumentException("Cursor must be between 0 and " + (command.length()-1) + ": " + cursor);
		this.cursor = cursor;
	}
	
	public int getTotalLength() {
		return command.length();
	}
	
	public boolean canRead() {
		return canRead(1);
	}
	
	public boolean canRead(int length) {
		return cursor + length <= command.length();
	}
	
	public boolean isAllowedNumber(char c) {
		return c >= '0' && c <= '9' || c == '.' || c == '-';
	}
	
	public void skip() {
		cursor++;
	}
	
	public void skip(int count) {
		cursor += count;
	}
	
	public void skipWhitespaces() {
		while (canRead() && Character.isWhitespace(peek())) {
			skip();
		}
	}

	public char peek() {
		return command.charAt(cursor);
	}
	
	public char read() {
		return command.charAt(cursor++);
	}

	public int readInt() {
		int start = cursor;
		while (canRead() && isAllowedNumber(peek())) {
			skip();
		}
		String rawNumber = command.substring(start, cursor);
		if (rawNumber.isEmpty())
			throw new CommandSyntaxException(command, start, "Exspected number but found nothing");
		int value = 0;
		try {
			value = Integer.parseInt(rawNumber);
		} catch(NumberFormatException e) {
			throw new CommandSyntaxException(command, start, "Unable to parse int", e);
		}
		return value;
	}
	
	public long readLong() {
		int start = cursor;
		while (canRead() && isAllowedNumber(peek())) {
			skip();
		}
		String rawNumber = command.substring(start, cursor);
		if (rawNumber.isEmpty())
			throw new CommandSyntaxException(command, start, "Exspected number but found nothing");
		long value = 0;
		try {
			value = Long.parseLong(rawNumber);
		} catch(NumberFormatException e) {
			throw new CommandSyntaxException(command, start, "Unable to parse long", e);
		}
		return value;
	}
	
	public float readFloat() {
		int start = cursor;
		while (canRead() && isAllowedNumber(peek())) {
			skip();
		}
		String rawNumber = command.substring(start, cursor);
		if (rawNumber.isEmpty())
			throw new CommandSyntaxException(command, start, "Exspected number but found nothing");
		float value = 0;
		try {
			value = Float.parseFloat(rawNumber);
		} catch(NumberFormatException e) {
			throw new CommandSyntaxException(command, start, "Unable to parse float", e);
		}
		return value;
	}
	
	public double readDouble() {
		int start = cursor;
		while (canRead() && isAllowedNumber(peek())) {
			skip();
		}
		String rawNumber = command.substring(start, cursor);
		if (rawNumber.isEmpty())
			throw new CommandSyntaxException(command, start, "Exspected number but found nothing");
		double value = 0;
		try {
			value = Double.parseDouble(rawNumber);
		} catch(NumberFormatException e) {
			throw new CommandSyntaxException(command, start, "Unable to parse double", e);
		}
		return value;
	}
	
	public boolean isQuoteChar(char c) {
		return c == '"' || c == '\'';
	}
	
	public boolean isValidUnquotedChar(char c) {
		return Character.isUpperCase(c) ||
				Character.isLowerCase(c) ||
				Character.isDigit(c) ||
				c == '.' || c == '_' ||
				c == '+' || c == '-' ||
				c == ':';
	}
	
	@NotNull
	public String readString() {
		if (!canRead()) {
			return "";
		}
		char next = peek();
		if (isQuoteChar(next)) {
			skip();
			return readStringUntil(next);
		}
		return readUnquotedString();
	}
	
	@NotNull
	public String readStringUntil(char terminator) {
		final StringBuilder builder = new StringBuilder();
		boolean escaped = false;
		while (canRead()) {
			char next = read();
			if (escaped) {
				if (next == terminator || next == '\\') {
					builder.append(next);
					escaped = true;
				} else {
					throw new CommandSyntaxException(command, cursor-1, "Invaid escaped char: " + next);
				}
			} else if (next == '\\') {
				escaped = true;
			} else if (next == terminator) {
				return builder.toString();
			} else {
				builder.append(next);
			}
		}
		return builder.toString();
	}
	
	@NotNull
	public String readQuotedString() {
		if (!canRead()) {
			return "";
		}
		char next = peek();
		if (!isQuoteChar(next)) {
			throw new CommandSyntaxException(command, cursor, "Expected start of quoted string!");
		}
		skip();
		return readStringUntil(next);
	}
	
	@NotNull
	public String readUnquotedString() {
		final int start = cursor;
		while (canRead() && isValidUnquotedChar(peek())) {
			skip();
		}
		if (canRead() && !Character.isWhitespace(peek())) {
			throw new CommandSyntaxException(command, cursor, "Expected whitespace in at end of unquoted String but found: " + peek());
		}
		return command.substring(start, cursor);
	}
	
	public boolean readBoolean() {
		final int start = cursor;
		String rawBool = readString();
		if (rawBool.isEmpty()) {
			throw new CommandSyntaxException(command, start, "Expected boolean but found nothing!");
		}
		if (rawBool.equals("true")) {
			return true;
		}
		if (rawBool.equals("false")) {
			return false;
		}
		throw new CommandSyntaxException(command, start, "Expected boolean but found: " + rawBool);
	}

	public String getRemaining() {
		return command.substring(cursor);
	}

}
