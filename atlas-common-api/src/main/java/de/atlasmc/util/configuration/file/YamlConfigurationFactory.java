package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import de.atlasmc.registry.RegistryValue;

@RegistryValue(registry="atlas:factory/file_configuration", key="atlas:factory/yaml_configuration")
public class YamlConfigurationFactory implements FileConfigurationFactory {

	public static final YamlConfigurationFactory INSTANCE = new YamlConfigurationFactory();
	
	@Override
	public FileConfiguration load(File file) throws FileNotFoundException, IOException {
		return YamlConfiguration.loadConfiguration(file);
	}

	@Override
	public FileConfiguration load(InputStream input) throws IOException {
		return YamlConfiguration.loadConfiguration(input);
	}

	@Override
	public FileConfiguration load(Reader reader) throws IOException {
		return YamlConfiguration.loadConfiguration(reader);
	}

}
