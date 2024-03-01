package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.command.CommandSyntaxException;
import de.atlasmc.registry.RegistryValue;
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
		final int start = reader.getCursor();
		final ArgumentVector3d vec = new ArgumentVector3d();
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
		reader.skip();
		// parse z
		vec.relativeZ = parseRelative(reader, targetCentric);
		vec.z = reader.readDouble();
		if (!reader.canRead() || reader.peek() != ' ')
			throw new CommandSyntaxException(reader.getCommand(), start, "Expected 3 double arguments");
		return vec;
	}
	
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

}
