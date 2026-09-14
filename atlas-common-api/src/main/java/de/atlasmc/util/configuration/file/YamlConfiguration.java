package de.atlasmc.util.configuration.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSettings;

public class YamlConfiguration extends FileConfiguration {

	private Yaml yaml;
	
	public YamlConfiguration() {
		super(null, null);
	}
	
	public YamlConfiguration(ConfigurationSettings settings) {
		super(settings, null);
	}
	
	public YamlConfiguration(ConfigurationSection config) {
		super(null, config);
	}
	
	public YamlConfiguration(ConfigurationSettings settings, ConfigurationSection config) {
		super(settings, config);
	}
	
	public Yaml getYaml() {
		var yaml = this.yaml;
		if (yaml == null)
			this.yaml = yaml = createYaml();
		return yaml;
	}
	
	protected Yaml createYaml() {
		DumperOptions dumpOptions = new DumperOptions();
		dumpOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		dumpOptions.setProcessComments(true);
		YamlRepresenter representer = new YamlRepresenter(dumpOptions);
		representer.setDefaultFlowStyle(FlowStyle.BLOCK);
		return new Yaml(representer, dumpOptions);
	}

	@Override
	public String saveToString() {
		return getYaml().dump(asMap());
	}
	
	@Override
	public void save(Writer writer) throws IOException {
		getYaml().dump(asMap(), writer);
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

}
