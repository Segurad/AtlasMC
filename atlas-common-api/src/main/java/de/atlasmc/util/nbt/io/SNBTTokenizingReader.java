package de.atlasmc.util.nbt.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.NBT;

public class SNBTTokenizingReader extends NBTObjectReader {
	
	// Raw regex
	// (?:(?<key>(?:(?:(?<!\\)(?:\\{2})*\"(?:(?<!\\)(?:\\{2})*\\\"|[^\"])+(?<!\\)(?:\\{2})*\")|(?:[\w-+/\\]+)))\s*:\s*)?(?:(?<compound>\{)|(?<list>\[(?:[BILbil];)?)|(?<float>[+-]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?[FDfd])|(?<int>[+-]?(?:0|[1-9][0-9]*)[LBSlbs]?)|(?<string>(?:(?<!\\)(?:\\{2})*\"(?:(?<!\\)(?:\\{2})*\\\"|[^\"])+(?<!\\)(?:\\{2})*\")|(?:[\w-+/\\]+)))\s*(?<end>(?:(?:[\]\}]\s*)+,?)|,)?\s*
	private static final Pattern TOKENIZER = Pattern.compile("(?:(?<key>(?:(?:(?<!\\\\)(?:\\\\{2})*\\\"(?:(?<!\\\\)(?:\\\\{2})*\\\\\\\"|[^\\\"])+(?<!\\\\)(?:\\\\{2})*\\\")|(?:[\\w-+/\\\\]+)))\\s*:\\s*)?(?:(?<compound>\\{)|(?<list>\\[(?:[BILbil];)?)|(?<float>[+-]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?[FDfd])|(?<int>[+-]?(?:0|[1-9][0-9]*)[LBSlbs]?)|(?<string>(?:(?<!\\\\)(?:\\\\{2})*\\\"(?:(?<!\\\\)(?:\\\\{2})*\\\\\\\"|[^\\\"])+(?<!\\\\)(?:\\\\{2})*\\\")|(?:[\\w-+/\\\\]+)))\\s*(?<end>(?:(?:[\\]\\}]\\s*)+,?)|,)?\\s*");

	public SNBTTokenizingReader(String snbt) {
		super(toObject(snbt));
	}
	
	private static NBT toObject(String snbt) {
		Matcher matcher = TOKENIZER.matcher(snbt);
		NBT tag = null;
		while (matcher.find()) {
			String key = matcher.group(1); // key
			String value = null;
			if (matcher.group(2) != null) { // component
				tag = TagType.COMPOUND.createTag(key, null);
			} else if (matcher.group(3) != null) { // list or array
				tag = TagType.COMPOUND.createTag(key, null);
			} else if ((value = matcher.group(4)) != null) { // float
				tag = readFloatTag(key, value);
			} if ((value = matcher.group(5)) != null) { // int
				tag = readIntTag(key, value);
			} else if ((value = matcher.group(6)) != null) { // string
				tag = TagType.STRING.createTag(key, unescape(value));
			}
			if ((value = matcher.group(7)) != null) { // end
				final int length = value.length();
				for (int i = 0; i < length; i++) {
					char c = value.charAt(0);
					if (Character.isWhitespace(c))
						continue;
					switch (c) {
					case '}':
					case ']':
					case ',':
					default:
						throw new IllegalArgumentException("Unknown char in end token: " + c + " <" + value + ">");
					}
				}
			}
		}
		return null;
	}
	
	private static NBT readFloatTag(String key, String rawValue) {
		final int index = rawValue.length() - 1;
		final char last = rawValue.charAt(index);
		String truncated = rawValue.substring(0, index);
		switch (last) {
		case 'D', 'd':
			return TagType.DOUBLE.createTag(key, Double.parseDouble(truncated));
		case 'F', 'f':
			return TagType.FLOAT.createTag(key, Float.parseFloat(truncated));
		default:
			throw new NBTException("Unknown suffix for float token: " + last);
		}
	}
	
	private static NBT readIntTag(String key, String rawValue) {
		final int index = rawValue.length() - 1;
		final char last = rawValue.charAt(index);
		if (Character.isDigit(last)) {
			return TagType.INT.createTag(key, Integer.parseInt(rawValue));
		}
		String truncated = rawValue.substring(0, index);
		switch (last) {
		case 'L', 'd':
			return TagType.LONG.createTag(key, Double.parseDouble(truncated));
		case 'B', 'b':
			return TagType.BYTE.createTag(key, Float.parseFloat(truncated));
		case 'S', 's':
			return TagType.SHORT.createTag(key, Float.parseFloat(truncated));
		default:
			throw new NBTException("Unknown suffix for int token: " + last);
		}
	}
	
	private static String unescape(String rawValue) {
		final int length = rawValue.length();
		if (length == 0)
			return rawValue;
		char first = rawValue.charAt(0);
		if (first != '"' && first != '\'')
			return rawValue;
		if (length == 2)
			return "";
		StringBuilder builder = new StringBuilder(length - 2);
		boolean escaped = false;
		for (int i = 1; i < length - 1; i++) {
			char c = rawValue.charAt(i);
			if (escaped) {
				switch (c) {
				case '\\', '"', '\'':
					break;
				case 'n':
					c = '\n';
					break;
				case 't':
					c = '\t';
					break;
				case 'b':
					c = '\b';
					break;
				case 'r':
					c = '\r';
					break;
				case 'f':
					c = '\f';
					break;
				default:
					throw new NBTException("Unknown Escaped char (" + c + ") in value: " + rawValue);
				}
				builder.append(c);
				escaped = false;
				continue;
			}
			if (c == '\\') { // next char is escaped char
				escaped = true;
				continue;
			}
			builder.append(c); // add read char
		}
		return builder.toString();
	}
	
	private static NBT readArrayTag(String rawValue) {
		return null;
	}

}
