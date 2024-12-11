package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.PacketUtil.*;

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
		writeVarInt(type.ordinal(), buf);
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String parse(CommandStringReader reader) {
		String result = null;
		if (type == StringType.GREEDY_PHRASE) {
			result = reader.getRemaining();
			reader.setCursor(reader.getTotalLength());
		} else if (type == StringType.SINGLE_WORD) {
			result = reader.readUnquotedString();
		} else {
			result = reader.readString();
		}
		if (result.length() == 0)
			return null;
		return result;
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

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		if (type != StringType.SINGLE_WORD)
			configuration.set("string-type", type.name());
		return configuration;
	}

}
