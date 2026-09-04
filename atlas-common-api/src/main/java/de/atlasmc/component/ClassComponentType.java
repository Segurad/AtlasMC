package de.atlasmc.component;

import java.lang.reflect.Constructor;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.factory.ClassFactory;

public class ClassComponentType extends ComponentType {

	private final Constructor<Component> constructor;
	
	protected ClassComponentType(ConfigurationSection cfg) {
		super(cfg);
		this.constructor = ClassFactory.getConstructor(cfg.getString("class"), ComponentType.class);
	}

	@Override
	public <T extends ConfigurationSection> T toConfiguration(T config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component createComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getComponentType() {
		return null;
	}

}
