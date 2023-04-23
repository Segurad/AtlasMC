package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlFileConfiguration extends FileConfiguration {

	private final Yaml yaml;
	
	public YamlFileConfiguration() {
		this.yaml = new Yaml();
	}

	@Override
	public String saveToString() {
		return yaml.dump(getValues());
	}

	@Override
	public void loadFromString(String data) {
		Map<?, ?> input = yaml.load(data);
		mapToSection(input, this);
	}
	
	public static YamlFileConfiguration loadConfiguration(File file) throws FileNotFoundException, IOException {
		YamlFileConfiguration cfg = new YamlFileConfiguration();
		cfg.load(file);
		return cfg;
	}

	public static YamlFileConfiguration loadConfiguration(Reader reader) throws IOException {
		YamlFileConfiguration cfg = new YamlFileConfiguration();
		cfg.load(reader);
		return cfg;
	}

}
