package de.atlasmc.command.argparser;

import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.command.CommandStringReader;
import de.atlasmc.io.IOWriteable;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.configuration.ConfigurationSerializeable;

@RegistryHolder(key="atlas:command/var_arg_parser", target=Target.CLASS)
public interface VarArgParser<T> extends Namespaced, IOWriteable, ConfigurationSerializeable {
	
	int getID();
	
	T parse(CommandStringReader reader);
	
	Collection<String> getExamples();

}
