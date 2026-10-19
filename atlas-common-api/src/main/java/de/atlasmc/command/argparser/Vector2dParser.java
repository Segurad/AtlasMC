package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="minecraft:vec2")
public class Vector2dParser implements VarArgParser<ArgumentVector2d> {

	public static final int ID = 11;
	@NotNull
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
		final ArgumentVector2d vec = new ArgumentVector2d();
		final CoordinateReader coords = new CoordinateReader(reader, 2, "double");
		vec.x = coords.readDouble();
		vec.relativeX = coords.isRelative();
		vec.y = coords.readDouble();
		vec.relativeY = coords.isRelative();
		vec.targetCentric = coords.isLocal();
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
