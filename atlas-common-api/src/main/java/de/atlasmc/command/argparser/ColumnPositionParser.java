package de.atlasmc.command.argparser;


import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
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
		final ArgumentVector2i vec = new ArgumentVector2i();
		final CoordinateReader coords = new CoordinateReader(reader, 2, "int");
		vec.x = coords.readInt();
		vec.relativeX = coords.isRelative();
		vec.y = coords.readInt();
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
