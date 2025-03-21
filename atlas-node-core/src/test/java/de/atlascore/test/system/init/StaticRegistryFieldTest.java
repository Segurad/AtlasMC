package de.atlascore.test.system.init;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.atlascore.system.init.ContainerFactoryLoader;
import de.atlasmc.inventory.ContainerFactory;

public class StaticRegistryFieldTest {
	
	@Test
	void testContainerFactoryLoader() throws IllegalArgumentException, IllegalAccessException {
		ContainerFactoryLoader.loadContainerFactories();
		StaticRegistryFieldTest.testNotNull(ContainerFactory.class, ContainerFactory.class);
	}
	
	public static void testNotNull(Class<?> registry, Class<?> fieldType) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = registry.getFields();
		for (Field field : fields) {
			if (field.getType() != fieldType || field.get(null) != null) continue;
			Assertions.fail(registry.getName() + " field not set!: " + field.getName());
		}
	}

}
