package de.atlasmc.inventory.component.effect;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.factory.BaseClassFactory;

public class ClassComponentEffectFactory extends BaseClassFactory<ComponentEffect> implements ComponentEffectFactory, ConfigurationSerializeable {
	
	public ClassComponentEffectFactory(Class<? extends ComponentEffect>  clazz) {
		super(clazz);
	}
	
	public ClassComponentEffectFactory(ConfigurationSection config) throws ClassNotFoundException {
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
