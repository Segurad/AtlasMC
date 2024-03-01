package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="brigadier:long")
public class LongParser implements VarArgParser<Long> {

	public static final int ID = 4;
	public static final NamespacedKey KEY = NamespacedKey.of("brigadier:long");
	
	private final long min;
	private final long max;
	
	public LongParser(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public LongParser(ConfigurationSection config) {
		this.min = config.getLong("min", Long.MIN_VALUE);
		this.max = config.getLong("max", Long.MAX_VALUE);
	}
	
	@Override
	public Long parse(CommandStringReader reader) {
		int cursor = reader.getCursor();
		long value = reader.readLong();
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
			buf.writeLong(min);
		}
		if (max != Integer.MAX_VALUE) {
			flags &= 0x2;
			buf.writeLong(max);
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

}
