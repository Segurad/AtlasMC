package de.atlasmc.util.configuration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;

import de.atlasmc.util.configuration.ConfigurationSection;

import org.yaml.snakeyaml.Yaml;

public class YamlConfiguration extends FileConfiguration {

	private final Yaml yaml;
	
	public YamlConfiguration() {
		this.yaml = createYaml();
	}
	
	public YamlConfiguration(ConfigurationSection configuration) {
		super(configuration);
		this.yaml = createYaml();
	}
	
	private Yaml createYaml() {
		DumperOptions dumpOptions = new DumperOptions();
		dumpOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		YamlRepresenter representer = new YamlRepresenter(dumpOptions);
		representer.setDefaultFlowStyle(FlowStyle.BLOCK);
		return new Yaml(representer);
	}

	@Override
	public String saveToString() {
		return yaml.dump(getValues());
	}
	
	@Override
	public void save(Writer writer) throws IOException {
		yaml.dump(getValues(), writer);
	}

	@Override
	public void loadFromString(String data) {
		Map<?, ?> input = yaml.load(data);
		mapToSection(input, this);
	}
	
	@Override
	public void load(InputStream in) throws IOException {
		Map<?, ?> input = yaml.load(in);
		mapToSection(input, this);
	}
	
	@Override
	public void load(Reader in) throws IOException {
		Map<?, ?> input = yaml.load(in);
		mapToSection(input, this);
	}
	
	public static YamlConfiguration loadConfiguration(File file) throws FileNotFoundException, IOException {
		YamlConfiguration cfg = new YamlConfiguration();
		cfg.load(file);
		return cfg;
	}
	
	public static YamlConfiguration loadConfiguration(InputStream file) throws IOException {
		YamlConfiguration cfg = new YamlConfiguration();
		cfg.load(file);
		return cfg;
	}

	public static YamlConfiguration loadConfiguration(Reader reader) throws IOException {
		YamlConfiguration cfg = new YamlConfiguration();
		cfg.load(reader);
		return cfg;
	}

}
