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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.configuration.ListConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;

public abstract class FileConfiguration extends MemoryConfiguration {
	
	public FileConfiguration() {}
	
	public FileConfiguration(ConfigurationSection configuration) {
		super(configuration);
	}
	
	public void save(File file) throws IOException {
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		file.getParentFile().mkdirs();
		FileWriter writer = new FileWriter(file, Charset.forName("UTF-8"));
		save(writer);
		writer.close();
	}
	
	public void save(OutputStream out) throws IOException {
		save(new OutputStreamWriter(out));
	}
	
	public void save(Writer writer) throws IOException {
		writer.write(saveToString());
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
		if (map == null)
			return;
		final boolean autoSerialize = getSettings().isAutoSerialize();
		mapToSection(true, autoSerialize, map, section);
	}
	
	private Object mapToSection(boolean root, boolean autoSerialize, Map<?, ?> map, ConfigurationSection section) {
		for (Entry<?, ?> entry : map.entrySet()) {
			final String key = (String) entry.getKey();
			final Object value = entry.getValue();
			if (value instanceof Map mapValue) {
				ConfigurationSection s = section.createSection(key);
				section.set(key, mapToSection(false, autoSerialize, mapValue, s));
			} else if (value instanceof List list) {
				ListConfigurationSection listSection = section.createListSection(key);
				remapList(autoSerialize, list, listSection);
			} else {
				section.set(key, value);
			}
		};
		if (root || !autoSerialize)
			return section;
		String clazz = section.getString("==");
		if (clazz == null)
			return section;
		return ConfigurationSerializable.deserialize(section, clazz);
	}
	
	private void remapList(boolean autoSerialize, List<?> list, ListConfigurationSection listSection) {
		for (Object listValue : list) {
			if (listValue instanceof Map mapListValue) {
				ConfigurationSection s = listSection.addSection();
				listSection.set(listSection.size() - 1, mapToSection(false, autoSerialize, mapListValue, s));
			} else if (listValue instanceof List l) {
				remapList(autoSerialize, l, listSection.addListSection());
			} else {
				listSection.add(listValue);
			}
		}
	}
	
}
