package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.configuration.ConfigurationSettings;

@Singleton
@RegistryValue(registry="atlas:factory/file_configuration", key="atlas:yaml")
public class YamlConfigurationFactory implements FileConfigurationFactory {

	public static final YamlConfigurationFactory INSTANCE = new YamlConfigurationFactory();
	
	private YamlConfigurationFactory() {
		// not required
	}
	
	@NotNull
	@Override
	public YamlConfiguration load(File file) throws FileNotFoundException, IOException {
		return load(null, file);
	}
	
	@NotNull
	@Override
	public YamlConfiguration load(InputStream input) throws IOException {
		return load(null, input);
	}
	
	@NotNull
	@Override
	public YamlConfiguration load(Reader reader) throws IOException {
		return load(null , reader);
	}
	
	@NotNull
	@Override
	public YamlConfiguration load(ConfigurationSettings settings, File file) throws FileNotFoundException, IOException {
		YamlConfiguration cfg = new YamlConfiguration(settings);
		cfg.load(file);
		return cfg;
	}
	
	@NotNull
	@Override
	public YamlConfiguration load(ConfigurationSettings settings, InputStream file) throws IOException {
		YamlConfiguration cfg = new YamlConfiguration(settings);
		cfg.load(file);
		return cfg;
	}

	@NotNull
	@Override
	public YamlConfiguration load(ConfigurationSettings settings, Reader reader) throws IOException {
		YamlConfiguration cfg = new YamlConfiguration(settings);
		cfg.load(reader);
		return cfg;
	}

}
