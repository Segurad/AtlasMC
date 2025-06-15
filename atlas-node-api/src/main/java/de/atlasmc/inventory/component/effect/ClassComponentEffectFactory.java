package de.atlasmc.inventory.component.effect;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.factory.ClassFactory;

public class ClassComponentEffectFactory extends ClassFactory<ComponentEffect> implements ComponentEffectFactory, ConfigurationSerializable {
	
	public ClassComponentEffectFactory(Class<? extends ComponentEffect>  clazz) {
		super(clazz);
	}
	
	public ClassComponentEffectFactory(ConfigurationSection config) {
		this(getClass(config.getString("effect")));
	}
	
	@Override
	public ComponentEffect createEffect() {
		return super.create();
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T configuration) {
		configuration.set("effect", clazz.getName());
		return configuration;
	}

}
