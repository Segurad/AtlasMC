package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import de.atlasmc.registry.RegistryHolder;

@RegistryHolder(key="atlas:factory/file_configuration")
public interface FileConfigurationFactory {
	
	FileConfiguration load(File file) throws FileNotFoundException, IOException;
	
	FileConfiguration load(InputStream input) throws IOException;
	
	FileConfiguration load(Reader reader) throws IOException;

}
