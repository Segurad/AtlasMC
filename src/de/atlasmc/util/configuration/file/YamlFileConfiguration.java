package de.atlasmc.util.configuration.file;

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

}
