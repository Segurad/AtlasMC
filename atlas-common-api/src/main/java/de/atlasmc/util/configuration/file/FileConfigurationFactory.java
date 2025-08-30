package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.annotation.NotNull;

@RegistryHolder(key="atlas:factory/file_configuration")
public interface FileConfigurationFactory {
	
	@NotNull
	FileConfiguration load(File file) throws FileNotFoundException, IOException;
	
	@NotNull
	FileConfiguration load(InputStream input) throws IOException;
	
	@NotNull
	FileConfiguration load(Reader reader) throws IOException;

}
