package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="minecraft:block_pos")
public class BlockPositionParser implements VarArgParser<ArgumentVector3i> {

	public static final int ID = 8;
	public static final NamespacedKey KEY = NamespacedKey.of("minecraft:block_pos");
	
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
	public ArgumentVector3i parse(CommandStringReader reader) {
		final ArgumentVector3i vec = new ArgumentVector3i();
		final CoordinateReader coords = new CoordinateReader(reader, 3, "int");
		vec.x = coords.readInt();
		vec.relativeX = coords.isRelative();
		vec.y = coords.readInt();
		vec.relativeY = coords.isRelative();
		vec.z = coords.readInt();
		vec.relativeZ = coords.isRelative();
		vec.targetCentric = coords.isLocal();
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
