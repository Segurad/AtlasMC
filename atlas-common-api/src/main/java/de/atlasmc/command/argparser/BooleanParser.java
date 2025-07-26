package de.atlasmc.command.argparser;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import io.netty.buffer.ByteBuf;

@RegistryValue(registry="atlas:command/var_arg_parser", key="brigadier:bool")
public class BooleanParser implements VarArgParser<Boolean> {
	
	public static final int ID = 0;
	public static final NamespacedKey KEY = NamespacedKey.of("brigadier:bool");
	public static final Collection<String> EXAMPLES = List.of("true", "false");
	
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
	public Boolean parse(CommandStringReader reader) {
		return reader.readBoolean();
	}

	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		return configuration;
	}

}
