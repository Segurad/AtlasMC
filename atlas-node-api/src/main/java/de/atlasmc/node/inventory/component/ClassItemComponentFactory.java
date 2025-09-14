package de.atlasmc.node.inventory.component;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.factory.ClassFactory;

public class ClassItemComponentFactory extends ClassFactory<ItemComponent> implements ItemComponentFactory, ConfigurationSerializable {
	
	public ClassItemComponentFactory(Class<? extends ItemComponent> clazz) {
		super(clazz, ComponentType.class);
	}
	
	public ClassItemComponentFactory(ConfigurationSection config) {
		super(getClass(config.getString("component")), ComponentType.class);
	}

	@Override
	public ItemComponent createComponent(ComponentType type) {
		return super.create(type);
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("component", clazz.getName());
		return configuration;
	}

}
