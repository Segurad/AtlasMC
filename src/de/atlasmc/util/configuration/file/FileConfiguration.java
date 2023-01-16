package de.atlasmc.util.configuration.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;

public abstract class FileConfiguration extends MemoryConfiguration {
	
	public void save(File file) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(saveToString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public void load(File file) {
		BufferedReader in = null;
		String data = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
				builder.append('\n');
			}
			data = builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		loadFromString(data);
	}
	
	public abstract String saveToString();
	
	public abstract void loadFromString(String data);

	protected void mapToSection(Map<?, ?> map, ConfigurationSection section) {
		map.forEach((key, value) -> {
			if (value instanceof Map)
				mapToSection(map, section.createSection((String) key));
			else
				section.set((String) key, value);
		});
	}
	
}
