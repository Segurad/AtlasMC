package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="minecraft:vec3")
public class Vector3dParser implements VarArgParser<ArgumentVector3d> {

	public static final int ID = 10;
	public static final NamespacedKey KEY = NamespacedKey.of("minecraft:vec3");
	
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
	public ArgumentVector3d parse(CommandStringReader reader) {
		final ArgumentVector3d vec = new ArgumentVector3d();
		final CoordinateReader coords = new CoordinateReader(reader, 3, "double");
		vec.x = coords.readDouble();
		vec.relativeX = coords.isRelative();
		vec.y = coords.readDouble();
		vec.relativeY = coords.isRelative();
		vec.z = coords.readDouble();
		vec.relativeZ = coords.isRelative();
		vec.targetCentric = coords.isLocal();
		return vec;
	}
	
	/**
	 * @deprecated use {@link CoordinateReader} which also handles local coordinates per component
	 */
	@Deprecated
	public static boolean parseRelative(CommandStringReader reader, boolean targetCentric) {
		if (reader.canRead() && reader.peek() == '~' ) {
			if (targetCentric)
				throw new CommandSyntaxException(reader.getCommand(), reader.getCursor(), "Can not mix realative with target centric!");
			reader.skip();
			return true;
		}
		return false;
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
