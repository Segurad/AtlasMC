package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;

public class JsonConfiguration extends FileConfiguration {

	@Override
	public String saveToString() {
		StringWriter string = new StringWriter();
		try {
			save(string);
		} catch (IOException e) {
			throw new JsonIOException("Error while writing configuration!", e);
		}
		return string.toString();
	}
	
	@Override
	public void save(Writer writer) throws IOException {
		JsonWriter json = new JsonWriter(writer);
		json.beginObject();
		writeSection(this, json);
		json.endObject();
	}
	
	private void writeSection(ConfigurationSection section, JsonWriter writer) throws IOException {
		for (String key : section.getKeys()) {
			Object value = section.get(key);
			writeValue(key, value, writer);
		}
	}
	
	private void writeList(List<Object> list, JsonWriter writer) throws IOException {
		if (list.isEmpty())
			return;
		for (Object o : list) {
			writeValue(null, o, writer);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void writeValue(String key, Object value, JsonWriter writer) throws IOException {
		if (key != null)
			writer.name(key);
		if (value == null) {
			writer.nullValue();
		} else if (value instanceof ConfigurationSection) {
			writer.beginObject();
			writeSection((ConfigurationSection) value, writer);
			writer.endObject();
		} else if (value instanceof List) {
			writer.beginArray();
			writeList((List<Object>) value, writer);
			writer.endArray();
		} else if (value instanceof Number) {
			writer.value((Number) value);
		} else {
			writer.value(value.toString());
		}
	}

	@Override
	public void loadFromString(String data) {
		load(new StringReader(data));
	}
	
	@Override
	public void load(Reader in) {
		JsonReader reader = new JsonReader(in);
		load(reader);
	}
	
	public void load(JsonReader in) {
		try {
			if (in.peek() != JsonToken.BEGIN_OBJECT) {
				throw new JsonIOException("Expected BEGIN_OBJECT but found: " + in.peek());
			}
			in.beginObject();
			loadSection(this, in);
		} catch (IOException e) {
			throw new JsonIOException("Error while loading configuration!", e);
		}
	}
	
	private void loadSection(ConfigurationSection section, JsonReader reader) throws IOException {
		JsonToken token = null;
		while ((token = reader.peek()) != JsonToken.END_OBJECT) {
			String key = reader.nextName();
			token = reader.peek();
			switch(token) {
			case BEGIN_ARRAY:
				ArrayList<Object> list = new ArrayList<>();
				section.set(key, list);
				reader.beginArray();
				loadList(section, reader, list);
				break;
			case BEGIN_OBJECT:
				ConfigurationSection child = section.createSection(key);
				reader.beginObject();
				loadSection(child, reader);
				break;
			case BOOLEAN:
				section.set(key, reader.nextBoolean());
				break;
			case NULL:
				section.set(key, null);
				reader.nextNull();
				break;
			case NUMBER:
			case STRING:
				section.set(key, reader.nextString());
				break;
			case END_DOCUMENT:
				throw new JsonIOException("Unexpected end of document!");
			default:
				break;
			}
		}
		reader.endObject();
	}
	
	private void loadList(ConfigurationSection section, JsonReader reader, List<Object> list) throws IOException {
		JsonToken token = null;
		while ((token = reader.peek()) != JsonToken.END_ARRAY) {
			switch(token) {
			case BEGIN_ARRAY:
				ArrayList<Object> childList = new ArrayList<>();
				list.add(childList);
				reader.beginArray();
				loadList(section, reader, childList);
				break;
			case BEGIN_OBJECT:
				ConfigurationSection child = new MemoryConfiguration(section);
				list.add(child);
				reader.beginObject();
				loadSection(child, reader);
				break;
			case BOOLEAN:
				list.add(reader.nextBoolean());
				break;
			case NUMBER:
			case STRING:
				list.add(reader.nextString());
				break;
			case END_DOCUMENT:
				throw new JsonIOException("Unexpected end of document!");
			default:
				break;
			}
		}
		reader.endArray();
	}
	
	public static JsonConfiguration loadConfiguration(File file) throws FileNotFoundException, IOException {
		JsonConfiguration cfg = new JsonConfiguration();
		cfg.load(file);
		return cfg;
	}

	public static JsonConfiguration loadConfiguration(Reader reader) throws IOException {
		JsonConfiguration cfg = new JsonConfiguration();
		cfg.load(reader);
		return cfg;
	}
	
	public static JsonConfiguration loadConfiguration(JsonReader reader) {
		JsonConfiguration cfg = new JsonConfiguration();
		cfg.load(reader);
		return cfg;
	}

}
