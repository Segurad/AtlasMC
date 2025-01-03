package de.atlasmc.inventory.component.effect;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.factory.BaseSingletonFactory;

public class SingletonComponentEffectFactory extends BaseSingletonFactory<ComponentEffect> implements ComponentEffectFactory, ConfigurationSerializeable {

	public SingletonComponentEffectFactory(Class<?> clazz) {
		super(clazz);
	}
	
	public SingletonComponentEffectFactory(ConfigurationSection config) throws ClassNotFoundException {
		super(Class.forName(config.getString("effect")));
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("effect", configuration);
		return configuration;
	}

	@Override
	public ComponentEffect createEffect() {
		return super.create();
	}

}
