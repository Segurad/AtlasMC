package de.atlasmc.nbt.io;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.tag.ByteArrayTag;
import de.atlasmc.nbt.tag.ByteTag;
import de.atlasmc.nbt.tag.CompoundTag;
import de.atlasmc.nbt.tag.IntArrayTag;
import de.atlasmc.nbt.tag.IntTag;
import de.atlasmc.nbt.tag.ListTag;
import de.atlasmc.nbt.tag.LongArrayTag;
import de.atlasmc.nbt.tag.LongTag;
import de.atlasmc.nbt.tag.NBT;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.LongArrayList;

public class SNBTReader extends NBTObjectReader {
	
	// Raw regex
	// (?:(?<key>(?:(?:(?<!\\)(?:\\{2})*\"(?:(?<!\\)(?:\\{2})*\\\"|[^\"])+(?<!\\)(?:\\{2})*\")|(?:[\w-+/\\]+)))\s*:\s*)?(?:(?<compound>\{)|(?<list>\[(?:[BILbil];)?)|(?<float>[+-]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?[FDfd])|(?<int>[+-]?(?:0|[1-9][0-9]*)[LBSlbs]?)|(?<string>(?:(?<!\\)(?:\\{2})*\"(?:(?<!\\)(?:\\{2})*\\\"|[^\"])+(?<!\\)(?:\\{2})*\")|(?:[\w-+/\\]+)))\s*(?<end>(?:(?:[\]\}]\s*)+,?)|,)?\s*
	private static final Pattern TOKENIZER = Pattern.compile("(?:(?<key>(?:(?:(?<!\\\\)(?:\\\\{2})*\\\"(?:(?<!\\\\)(?:\\\\{2})*\\\\\\\"|[^\\\"])+(?<!\\\\)(?:\\\\{2})*\\\")|(?:[\\w-+/\\\\]+)))\\s*:\\s*)?(?:(?<compound>\\{)|(?<list>\\[(?:[BILbil];)?)|(?<float>[+-]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?[FDfd])|(?<int>[+-]?(?:0|[1-9][0-9]*)[LBSlbs]?)|(?<string>(?:(?<!\\\\)(?:\\\\{2})*\\\"(?:(?<!\\\\)(?:\\\\{2})*\\\\\\\"|[^\\\"])+(?<!\\\\)(?:\\\\{2})*\\\")|(?:[\\w-+/\\\\]+)))\\s*(?<end>(?:(?:[\\]\\}]\\s*)+,?)|,)?\\s*");
	private static final int 
	KEY_TOKEN,
	COMPOUND_TOKEN,
	LIST_TOKEN,
	FLOAT_TOKEN,
	INT_TOKEN,
	STRING_TOKEN,
	END_TOKEN;
	
	private static final int GROUP_COUNT;
	
	static {
		Map<String, Integer> groups = TOKENIZER.namedGroups();
		KEY_TOKEN = groups.get("key");
		COMPOUND_TOKEN = groups.get("compound");
		LIST_TOKEN = groups.get("list");
		FLOAT_TOKEN = groups.get("float");
		INT_TOKEN = groups.get("int");
		STRING_TOKEN = groups.get("string");
		END_TOKEN = groups.get("end");
		GROUP_COUNT = groups.size();
	}
	
	public SNBTReader(CharSequence snbt) {
		super(toObject(snbt));
	}
	
	
	
	private static NBT toObject(CharSequence snbt) {
		List<Token> tokens = buildTokens(snbt);
		ListIterator<Token> iterator = tokens.listIterator();
		NBT nbt = toNBT(iterator);
		if (nbt != null && nbt.getName() == null)
			nbt.setName(""); // true unnamed root only in nbt io stream
		return nbt;
	}
	
	private static NBT toNBT(ListIterator<Token> iterator) {
		String key = null;
		while (iterator.hasNext()) {
			Token next = iterator.next();
			if (COMPOUND_TOKEN == next.tokenType) {
				return toCompoundTag(key, iterator);
			} else if (LIST_TOKEN == next.tokenType) {
				return toArrayTag(key, next.token, iterator);
			} else if (FLOAT_TOKEN == next.tokenType) {
				return toFloatTag(key, next.token);
			} else if (INT_TOKEN == next.tokenType) {
				return toIntTag(key, next.token);
			} else if (KEY_TOKEN == next.tokenType) {
				if (key != null)
					throw new NBTException("Tag may not contain two keys! Token: " + iterator.previousIndex());
				key = unescape(next.token);
				continue;
			} else if (STRING_TOKEN == next.tokenType) {
				return TagType.STRING.createTag(key, unescape(next.token));
			} else if (END_TOKEN == next.tokenType) {
				if (key != null)
					throw new NBTException("EndTag may not have a key! Token: " + iterator.previousIndex());
				return null;
			} else {
				Map<String, Integer> groups = TOKENIZER.namedGroups();
				String group = "Unknown Group";
				for (Entry<String, Integer> entry : groups.entrySet()) {
					if (entry.getValue() != next.tokenType)
						continue;
					group = entry.getKey();
					break;
				}
				throw new NBTException("Unknown token type: " + group + " (" + next.tokenType + ") Token: " + iterator.previousIndex());
			}
		}
		throw new NBTException("Unable to find token!");
	}
	
	private static List<Token> buildTokens(CharSequence snbt) {
		Matcher matcher = TOKENIZER.matcher(snbt);
		List<Token> tokens = new ArrayList<>();
		int match = 0;
		while (matcher.find()) {
			match++;
			for (int i = 1; i <= GROUP_COUNT; i++) {
				String value = matcher.group(i);
				if (value == null)
					continue;
				if (i == END_TOKEN) {
					final int length = value.length();
					for (int j = 0; j < length; j++) {
						char c = value.charAt(j);
						if (c == ',' || Character.isWhitespace(c))
							continue;
						switch (c) {
						case '}':
							tokens.add(new Token(i, "}"));
							break;
						case ']':
							tokens.add(new Token(i, "]"));
							break;
						default:
							throw new IllegalArgumentException("Unknown char in end token: " + c + " <" + value + "> match: "+ match + " index: " + tokens.size());
						}
					}
				} else {
					tokens.add(new Token(i, value));
				}
			}
		}
		return tokens;
	}
	
	private static NBT toFloatTag(String key, String rawValue) {
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
	
	private static NBT toIntTag(String key, String rawValue) {
		final int index = rawValue.length() - 1;
		final char last = rawValue.charAt(index);
		if (Character.isDigit(last)) {
			return TagType.INT.createTag(key, Integer.parseInt(rawValue));
		}
		String truncated = rawValue.substring(0, index);
		switch (last) {
		case 'L', 'd':
			return TagType.LONG.createTag(key, Long.parseLong(truncated));
		case 'B', 'b':
			return TagType.BYTE.createTag(key, Byte.parseByte(truncated));
		case 'S', 's':
			return TagType.SHORT.createTag(key, Short.parseShort(truncated));
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
	
	private static NBT toCompoundTag(String key, ListIterator<Token> iterator) {
		CompoundTag compound = new CompoundTag(key);
		boolean end = false;
		while (iterator.hasNext()) {
			int nextIndex = iterator.nextIndex();
			NBT tag = toNBT(iterator);
			if (tag != null) {
				if (tag.getName() == null)
					throw new NBTException("CompoundTag element may not have null key! Token: " + nextIndex);
				compound.addTag(tag);
				continue;
			}
			nextIndex = iterator.previousIndex();
			Token endToken = iterator.previous();
			if (!endToken.token.equals("}"))
				throw new NBTException("Invalid end token(" + endToken.token + "): " + nextIndex);
			iterator.next();
			end = true;
			break;
		}
		if (!end)
			throw new NBTException("No end token found!");
		return compound;
	}
	
	private static NBT toArrayTag(String key, String token, ListIterator<Token> iterator) {
		if (token.length() > 1) { // array
			TagType arrayType = null;
			TagType elementType = null;
			Object values = null;
			boolean end = false;
			switch (token.charAt(1)) {
			case 'I', 'i':
				arrayType = TagType.INT_ARRAY;
				elementType = TagType.INT;
				values = new IntArrayList();
				break;
			case 'B', 'b':
				arrayType = TagType.BYTE_ARRAY;
				elementType = TagType.BYTE;
				values = new ByteArrayList();
				break;
			case 'L', 'l':
				arrayType = TagType.LONG_ARRAY;
				elementType = TagType.LONG;
				values = new LongArrayList();
				break;
			default:
				throw new NBTException("Unknown list token(" + token + "): " + iterator.previousIndex());
			}
			while (iterator.hasNext()) {
				int nextIndex = iterator.nextIndex();
				NBT tag = toNBT(iterator);
				if (tag != null) {
					if (tag.getName() != null)
						throw new NBTException("Array element may not have key! Token: " + nextIndex);
					if (tag.getType() != elementType)
						throw new NBTException(arrayType + " may not contain elements of type: " + tag.getType() + "! Token: " + nextIndex);
					switch (elementType) {
					case INT:
						IntArrayList intList = (IntArrayList) values;
						intList.add(((IntTag)tag).asInteger());
						break;
					case BYTE:
						ByteArrayList byteList = (ByteArrayList) values;
						byteList.add(((ByteTag)tag).asByte());
						break;
					case LONG:
						LongArrayList longList = (LongArrayList) values;
						longList.add(((LongTag)tag).asLong());
						break;
					default:
						throw new NBTException("Unexpected element type: " + elementType);
					}
					continue;
				}
				nextIndex = iterator.previousIndex();
				Token endToken = iterator.previous();
				if (!endToken.token.equals("]"))
					throw new NBTException("Invalid end token(" + endToken.token + "): " + nextIndex);
				iterator.next();
				end = true;
				break;
			}
			if (!end)
				throw new NBTException("No end token found!");
			switch (arrayType) {
			case BYTE_ARRAY:
				return new ByteArrayTag(key, ((ByteArrayList) values).toByteArray());
			case INT_ARRAY:
				return new IntArrayTag(key, ((IntArrayList) values).toIntArray());
			case LONG_ARRAY:
				return new LongArrayTag(key, ((LongArrayList) values).toLongArray());
			default:
				throw new NBTException("Unexpected array type: " + arrayType);
			}
		} else { // list
			ListTag list = new ListTag();
			list.setName(key);
			boolean end = false;
			while (iterator.hasNext()) {
				int nextIndex = iterator.nextIndex();
				NBT tag = toNBT(iterator);
				if (tag != null) {
					if (tag.getName() != null)
						throw new NBTException("ListTag element may not have key! Token: " + nextIndex);
					list.addTag(tag);
					continue;
				}
				nextIndex = iterator.previousIndex();
				Token endToken = iterator.previous();
				if (!endToken.token.equals("]"))
					throw new NBTException("Invalid end token(" + endToken.token + "): " + nextIndex);
				iterator.next();
				end = true;
				break;
			}
			if (!end)
				throw new NBTException("No end token found!");
			if (list.getPayloadSize() == 0)
				return new ListTag(key, TagType.TAG_END);
			return list;
		}
	}
	
	private static class Token {
		
		public final int tokenType;
		public final String token;
		
		public Token(int tokenType, String token) {
			this.tokenType = tokenType;
			this.token = token;
		}
		
	}

}
