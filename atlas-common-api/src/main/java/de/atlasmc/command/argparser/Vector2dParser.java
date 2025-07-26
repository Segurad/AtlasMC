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

@RegistryValue(registry="atlas:command/var_arg_parser", key="minecraft:vec2")
public class Vector2dParser implements VarArgParser<ArgumentVector2d> {

	public static final int ID = 11;
	public static final NamespacedKey KEY = NamespacedKey.of("minecraft:vec2");
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return KEY;
	}

	@Override
	public void write(ByteBuf buf) {
		// not required
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public ArgumentVector2d parse(CommandStringReader reader) {
		final int start = reader.getCursor();
		final ArgumentVector2d vec = new ArgumentVector2d();
		boolean targetCentric = false; 
		if (reader.canRead() && reader.peek() == '^') {
			targetCentric = vec.targetCentric = true;
			reader.skip();
		}
		// parse x
		vec.relativeX = parseRelative(reader, targetCentric);
		vec.x = reader.readDouble();
		if (!reader.canRead() || reader.peek() != ' ')
			throw new CommandSyntaxException(reader.getCommand(), start, "Expected 3 double arguments");
		reader.skip();
		// parse y 
		vec.relativeY = parseRelative(reader, targetCentric);
		vec.y = reader.readDouble();
		if (!reader.canRead() || reader.peek() != ' ')
			throw new CommandSyntaxException(reader.getCommand(), start, "Expected 3 double arguments");
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
