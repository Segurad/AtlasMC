package de.atlasmc.util.configuration.file;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

import de.atlasmc.util.configuration.ConfigurationSection;

public class YamlRepresenter extends Representer {
	
	public YamlRepresenter(DumperOptions options) {
		super(options);
		super.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
	}
	
	private class RepresentConfigurationSection extends RepresentMap {
		
		@Override
		public Node representData(Object data) {
			return super.representData(((ConfigurationSection)data).getValues());
		}
		
	}

}
