package de.atlasmc.util.configuration.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;
import de.atlasmc.util.configuration.MemoryConfigurationSection;

public abstract class FileConfiguration extends MemoryConfiguration {
	
	public void save(File file) throws IOException {
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		file.mkdirs();
		FileWriter writer = new FileWriter(file, Charset.forName("UTF-8"));
		save(writer);
		writer.close();
	}
	
	public void save(OutputStream out) throws IOException {
		save(new OutputStreamWriter(out));
		out.close();
	}
	
	public void save(Writer writer) throws IOException {
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
				if (value instanceof List) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) value;
					final int size = list.size();
					for (int i = 0; i < size; i++) {
						Object listValue = list.get(i);
						if (listValue == null)
							continue;
						if (!(listValue instanceof LinkedHashMap))
							continue;
						ConfigurationSection listSection = new MemoryConfigurationSection(section);
						list.set(i, listSection);
						mapToSection(map, listSection);
					}
				}
				section.set((String) key, value);
			}
		});
	}
	
}
