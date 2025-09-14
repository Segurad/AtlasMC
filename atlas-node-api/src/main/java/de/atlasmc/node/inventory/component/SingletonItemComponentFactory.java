package de.atlasmc.node.inventory.component;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.factory.ClassFactory;

public class SingletonItemComponentFactory extends ClassFactory<ItemComponent> implements ItemComponentFactory, ConfigurationSerializable {

	private ItemComponent instance;
	
	public SingletonItemComponentFactory(ConfigurationSection config) {
		super(getClass(config.getString("component")), ComponentType.class);
	}

	@Override
	public ItemComponent createComponent(ComponentType type) {
		ItemComponent instance = this.instance;
		if (instance.getType() == type)
			return instance;
		// not thread safe but whatever
		this.instance = instance = create(type);
		return instance;
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("component", clazz.getName());
		return configuration;
	}
	
	

}
