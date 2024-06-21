package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.command.argparser.Vector3dParser.parseRelative;

@RegistryValue(registry="atlas:command/var_arg_parser", key="minecraft:block_pos")
public class BlockPositionParser implements VarArgParser<ArgumentVector3i> {

	public static final int ID = 8;
	public static final NamespacedKey KEY = NamespacedKey.of("minecraft:block_pos");
	
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
	public ArgumentVector3i parse(CommandStringReader reader) {
		final int start = reader.getCursor();
		final ArgumentVector3i vec = new ArgumentVector3i();
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
		reader.skip();
		// parse z
		vec.relativeZ = parseRelative(reader, targetCentric);
		vec.z = reader.readInt();
		if (!reader.canRead() || reader.peek() != ' ')
			throw new CommandSyntaxException(reader.getCommand(), start, "Expected 3 int arguments");
		return vec;
	}

	@Override
	public Collection<String> getExamples() {
		return List.of(); // TODO parser example
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		return configuration;
	}

}
