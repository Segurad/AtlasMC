package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.ConfigurationSettings;

@RegistryHolder(key="atlas:factory/file_configuration")
public interface FileConfigurationFactory {
	
	@NotNull
	default FileConfiguration load(File file) throws FileNotFoundException, IOException {
		return load(null, file);
	}
	
	@NotNull
	default FileConfiguration load(InputStream input) throws IOException {
		return load(null, input);
	}
	
	@NotNull
	default FileConfiguration load(Reader reader) throws IOException {
		return load(null , reader);
	}
	
	@NotNull
	FileConfiguration load(ConfigurationSettings settings, File file) throws FileNotFoundException, IOException;
	
	@NotNull
	FileConfiguration load(ConfigurationSettings settings, InputStream input) throws IOException;
	
	@NotNull
	FileConfiguration load(ConfigurationSettings settings, Reader reader) throws IOException;

}
