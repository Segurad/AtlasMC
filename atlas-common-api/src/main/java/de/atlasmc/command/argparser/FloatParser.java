package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="brigadier:float")
public class FloatParser implements VarArgParser<Float> {

	public static final int ID = 1;
	public static final NamespacedKey KEY = NamespacedKey.of("brigadier:float");
	public static final Collection<String> EXAMPLES = List.of("0", "1.2", ".5", "-1", "-.5", "-1234.56");
	
	private final float min;
	private final float max;
	
	public FloatParser(float min, float max) {
		if (min > max)
	        throw new IllegalArgumentException("Min can not be greater than max: " + min + " > " + max);
		this.min = min;
		this.max = max;
	}
	
	public FloatParser(ConfigurationSection config) {
		this(config.getFloat("min", -Float.MAX_VALUE),
				config.getFloat("max", Float.MAX_VALUE));
	}
	
	@Override
	public Float parse(CommandStringReader reader) {
		int cursor = reader.getCursor();
		float value = reader.readFloat();
		if (value < min) {
			throw new CommandSyntaxException(reader.getCommand(), cursor, "Value can not be lower than " + min + ": " + value);
		} else if (value > max) {
			throw new CommandSyntaxException(reader.getCommand(), cursor, "Value can not be higher than " + max + ": " + value);
		}
		return value;
	}

	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
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
		if (min != -Float.MAX_VALUE) {
			flags |= 0x1;
			buf.writeFloat(min);
		}
		if (max != Float.MAX_VALUE) {
			flags |= 0x2;
			buf.writeFloat(max);
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
		if (min != -Float.MAX_VALUE)
			configuration.set("min", min);
		if (max != Float.MAX_VALUE)
			configuration.set("max", max);
		return configuration;
	} 

}
