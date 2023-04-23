package de.atlasmc.util.configuration.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;

public abstract class FileConfiguration extends MemoryConfiguration {
	
	public void save(File file) throws IOException {
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		file.mkdirs();
		FileWriter writer = new FileWriter(file, Charset.forName("UTF-8"));
		writer.write(saveToString());
		writer.close();
	}

	public void load(File file) throws IOException, FileNotFoundException {
		FileInputStream in = new FileInputStream(file);
		load(in);
	}
	
	public void load(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		load(reader);
	}
	
	public void load(Reader in) throws IOException {
		BufferedReader reader = (in instanceof BufferedReader) ? (BufferedReader) in : new BufferedReader(in);
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
			builder.append('\n');
		}
		reader.close();
		loadFromString(builder.toString());
	}
	
	public abstract String saveToString();
	
	public abstract void loadFromString(String data);

	protected void mapToSection(Map<?, ?> map, ConfigurationSection section) {
		map.forEach((key, value) -> {
			if (value instanceof Map) {
				mapToSection((Map<?, ?>) value, section.createSection((String) key));
			} else {
				section.set((String) key, value);
			}
		});
	}
	
}
