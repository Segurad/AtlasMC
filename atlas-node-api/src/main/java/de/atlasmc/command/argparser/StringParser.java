package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="brigadier:string")
public class StringParser implements VarArgParser<String> {

	public static final int ID = 5;
	public static final NamespacedKey KEY = NamespacedKey.of("brigadier:string");
	
	private final StringType type;
	
	public StringParser(ConfigurationSection config) {
		String rawType = config.getString("string-type");
		if (rawType == null) {
			type = StringType.SINGLE_WORD;
		} else {
			type = StringType.valueOf(rawType);
		}
	}
	
	public StringParser(StringType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		this.type = type;
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return KEY;
	}

	@Override
	public void write(ByteBuf buf) {
		AbstractPacket.writeVarInt(type.ordinal(), buf);
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String parse(CommandStringReader reader) {
		if (type == StringType.GREEDY_PHRASE) {
			String result = reader.getRemaining();
			reader.setCursor(reader.getTotalLength());
			return result;
		} else if (type == StringType.SINGLE_WORD) {
			return reader.readUnquotedString();
		} else {
			return reader.readString();
		}
	}

	@Override
	public Collection<String> getExamples() {
		return List.of(); // TODO parser examples
	}
	
	public static enum StringType {
		SINGLE_WORD,
		QUOTABLE_PHRASE,
		GREEDY_PHRASE
	}

}
