package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.google.gson.stream.JsonReader;

import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.configuration.ConfigurationSettings;

@Singleton
@RegistryValue(registry="atlas:factory/file_configuration", key="atlas:json")
public class JsonConfigurationFactory implements FileConfigurationFactory {

	public static final JsonConfigurationFactory INSTANCE = new JsonConfigurationFactory();
	
	private JsonConfigurationFactory() {
		// not required
	}
	
	@NotNull
	@Override
	public JsonConfiguration load(File file) throws FileNotFoundException, IOException {
		return load(null, file);
	}
	
	@NotNull
	@Override
	public JsonConfiguration load(InputStream input) throws IOException {
		return load(null, input);
	}
	
	@NotNull
	@Override
	public JsonConfiguration load(Reader reader) throws IOException {
		return load(null , reader);
	}
	
	@NotNull
	@Override
	public JsonConfiguration load(ConfigurationSettings settings, File file) throws FileNotFoundException, IOException {
		JsonConfiguration cfg = new JsonConfiguration(settings);
		cfg.load(file);
		return cfg;
	}

	@NotNull
	@Override
	public JsonConfiguration load(ConfigurationSettings settings, Reader reader) throws IOException {
		JsonConfiguration cfg = new JsonConfiguration(settings);
		cfg.load(reader);
		return cfg;
	}
	
	@NotNull
	public JsonConfiguration load(JsonReader reader) {
		return load(null, reader);
	}
	
	@NotNull
	public JsonConfiguration load(ConfigurationSettings settings, JsonReader reader) {
		JsonConfiguration cfg = new JsonConfiguration(settings);
		cfg.load(reader);
		return cfg;
	}

	@NotNull
	@Override
	public JsonConfiguration load(ConfigurationSettings settings, InputStream input) throws IOException {
		JsonConfiguration cfg = new JsonConfiguration(settings);
		cfg.load(input);
		return cfg;
	}

}
