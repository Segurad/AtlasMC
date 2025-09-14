package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.factory.ClassFactory;

public class SingletonComponentEffectFactory extends ClassFactory<ComponentEffect> implements ComponentEffectFactory, ConfigurationSerializable {

	private ComponentEffect instance;
	
	public SingletonComponentEffectFactory(Class<? extends ComponentEffect> clazz) {
		super(clazz);
	}
	
	public SingletonComponentEffectFactory(ConfigurationSection config) throws ClassNotFoundException {
		super(getClass(config.getString("effect")), ComponentEffectType.class);
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("effect", configuration);
		return configuration;
	}

	@Override
	public ComponentEffect createEffect(ComponentEffectType type) {
		ComponentEffect instance = this.instance;
		if (instance.getType() == type)
			return instance;
		this.instance = instance = create(type);
		return instance;
	}

}
