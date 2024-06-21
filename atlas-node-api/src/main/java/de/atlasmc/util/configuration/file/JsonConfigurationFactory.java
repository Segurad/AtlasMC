package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:factory/file_configuration", key="atlas:factory/json_configuration")
public class JsonConfigurationFactory implements FileConfigurationFactory {

	public static final JsonConfigurationFactory INSTANCE = new JsonConfigurationFactory();
	
	@Override
	public FileConfiguration load(File file) throws FileNotFoundException, IOException {
		return JsonConfiguration.loadConfiguration(file);
	}

	@Override
	public FileConfiguration load(InputStream input) throws IOException {
		return JsonConfiguration.loadConfiguration(input);
	}

	@Override
	public FileConfiguration load(Reader reader) throws IOException {
		return JsonConfiguration.loadConfiguration(reader);
	}

}
