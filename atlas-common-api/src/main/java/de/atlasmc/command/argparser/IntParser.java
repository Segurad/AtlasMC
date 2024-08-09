package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="brigadier:integer")
public class IntParser implements VarArgParser<Integer> {

	public static final int ID = 3;
	public static final NamespacedKey KEY = NamespacedKey.of("brigadier:integer");
	
	private final int min;
	private final int max;
	
	public IntParser(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public IntParser(ConfigurationSection config) {
		this.min = config.getInt("min", Integer.MIN_VALUE);
		this.max = config.getInt("max", Integer.MAX_VALUE);
	}
	
	@Override
	public Integer parse(CommandStringReader reader) {
		int cursor = reader.getCursor();
		int value = reader.readInt();
		if (value < min) {
			throw new CommandSyntaxException(reader.getCommand(), cursor, "Value can not be lower than " + min + ": " + value);
		} else if (value > max) {
			throw new CommandSyntaxException(reader.getCommand(), cursor, "Value can not be higher than " + max + ": " + value);
		}
		return value;
	}

	@Override
	public Collection<String> getExamples() {
		return List.of(); // TODO examples
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return KEY;
	}

	@Override
	public void write(ByteBuf buf) {
		int index = buf.writerIndex();
		buf.writeByte(0);
		int flags = 0;
		if (min != Integer.MIN_VALUE) {
			flags &= 0x1;
			buf.writeInt(min);
		}
		if (max != Integer.MAX_VALUE) {
			flags &= 0x2;
			buf.writeInt(max);
		}
		int next = buf.writerIndex();
		buf.writerIndex(index);
		buf.writeByte(flags);
		buf.writerIndex(next);
	}

	@Override
	public int getID() {
		return ID;
	}
	
	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		if (min != Double.MIN_VALUE)
			configuration.set("min", min);
		if (max != Double.MAX_VALUE)
			configuration.set("max", max);
		return configuration;
	} 

}
