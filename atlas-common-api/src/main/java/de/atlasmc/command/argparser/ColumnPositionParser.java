package de.atlasmc.command.argparser;

import static de.atlasmc.command.argparser.Vector3dParser.parseRelative;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="minecraft:column_pos")
public class ColumnPositionParser implements VarArgParser<ArgumentVector2i> {

	public static final int ID = 9;
	public static final NamespacedKey KEY = NamespacedKey.of("minecraft:column_pos");
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return KEY;
	}

	@Override
	public void write(ByteBuf buf) {}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public ArgumentVector2i parse(CommandStringReader reader) {
		final int start = reader.getCursor();
		final ArgumentVector2i vec = new ArgumentVector2i();
		boolean targetCentric = false; 
		if (reader.canRead() && reader.peek() == '^') {
			targetCentric = vec.targetCentric = true;
			reader.skip();
		}
		// parse x
		vec.relativeX = parseRelative(reader, targetCentric);
		vec.x = reader.readInt();
		if (!reader.canRead() || reader.peek() != ' ')
			throw new CommandSyntaxException(reader.getCommand(), start, "Expected 3 int arguments");
		reader.skip();
		// parse y 
		vec.relativeY = parseRelative(reader, targetCentric);
		vec.y = reader.readInt();
		if (!reader.canRead() || reader.peek() != ' ')
			throw new CommandSyntaxException(reader.getCommand(), start, "Expected 3 int arguments");
		return vec;
	}

	@Override
	public Collection<String> getExamples() {
		return List.of(); // TODO parser examples
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		return configuration;
	}

}
