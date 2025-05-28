package de.atlasmc.inventory.component;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.factory.ClassFactory;

public class ClassItemComponentFactory extends ClassFactory<ItemComponent> implements ItemComponentFactory, ConfigurationSerializeable {

	public ClassItemComponentFactory(Class<? extends ItemComponent> clazz) {
		super(clazz);
	}
	
	public ClassItemComponentFactory(ConfigurationSection config) {
		super(getClass(config.getString("component")));
	}

	@Override
	public ItemComponent createComponent() {
		return super.create();
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("component", clazz.getName());
		return configuration;
	}

}
