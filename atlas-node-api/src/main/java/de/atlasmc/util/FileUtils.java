package de.atlasmc.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileUtils {
	
	public static File extractConfiguration(File destDir, String filename, String resource, boolean override, Class<?> caller) throws IOException {
		File file = new File(destDir, filename);
		if (file.exists()) {
			if (override)
				file.delete();
			else
				return file;
		}
		InputStream resourceStream = caller.getResourceAsStream(resource);
		Files.copy(resourceStream, file.toPath());
		return file;
	}

}
