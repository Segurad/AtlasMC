package de.atlasmc.util.configuration.file;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.configuration.MemoryConfiguration;

public class YamlRepresenter extends Representer {
	
	public YamlRepresenter(DumperOptions options) {
		super(options);
		super.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
		super.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializeable());
	}
	
	private class RepresentConfigurationSection extends RepresentMap {
		
		@Override
		public Node representData(Object data) {
			return super.representData(((ConfigurationSection)data).getValues());
		}
		
	}
	
	private class RepresentConfigurationSerializeable extends RepresentMap {
		
		@Override
		public Node representData(Object data) {
			ConfigurationSerializable value = (ConfigurationSerializable) data;
			Configuration cfg = new MemoryConfiguration();
			cfg.set("type", value.getClass().getName());
			value.toConfiguration(cfg.createSection("configuration"));
			return super.representData(cfg.getValues());
		}
		
	}

}
