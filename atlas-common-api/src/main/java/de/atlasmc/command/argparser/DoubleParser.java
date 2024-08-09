package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="brigadier:double")
public class DoubleParser implements VarArgParser<Double> {

	public static final int ID = 2;
	public static final NamespacedKey KEY = NamespacedKey.of("brigadier:double");
	
	private final double min;
	private final double max;
	
	public DoubleParser(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	public DoubleParser(ConfigurationSection config) {
		this.min = config.getDouble("min", Double.MIN_VALUE);
		this.max = config.getDouble("max", Double.MAX_VALUE);
	}
	
	@Override
	public Double parse(CommandStringReader reader) {
		int cursor = reader.getCursor();
		double value = reader.readDouble();
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
	public int getID() {
		return ID;
	}

	@Override
	public void write(ByteBuf buf) {
		int index = buf.writerIndex();
		buf.writeByte(0);
		int flags = 0;
		if (min != Integer.MIN_VALUE) {
			flags &= 0x1;
			buf.writeDouble(min);
		}
		if (max != Integer.MAX_VALUE) {
			flags &= 0x2;
			buf.writeDouble(max);
		}
		int next = buf.writerIndex();
		buf.writerIndex(index);
		buf.writeByte(flags);
		buf.writerIndex(next);
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
