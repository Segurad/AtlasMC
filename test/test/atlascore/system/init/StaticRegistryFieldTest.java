package test.atlascore.system.init;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.atlascore.system.init.ContainerFactoryLoader;
import de.atlascore.system.init.EntityTypeLoader;
import de.atlascore.system.init.MaterialLoader;
import de.atlasmc.Material;
import de.atlasmc.entity.EntityType;
import de.atlasmc.factory.ContainerFactory;

public class StaticRegistryFieldTest {
	
	@Test
	void testContainerFactoryLoader() throws IllegalArgumentException, IllegalAccessException {
		ContainerFactoryLoader.loadContainerFactories();
		StaticRegistryFieldTest.testNotNull(ContainerFactory.class, ContainerFactory.class);
	}
	
	@Test
	void testMaterialLoader() throws IllegalArgumentException, IllegalAccessException {
		MaterialLoader.loadMaterial();
		StaticRegistryFieldTest.testNotNull(Material.class, Material.class);
	}
	
	@Test
	void testEntityTypeLoader() throws IllegalArgumentException, IllegalAccessException {
		EntityTypeLoader.loadEntityTypes();
		StaticRegistryFieldTest.testNotNull(EntityType.class, EntityType.class);
	}
	
	public static void testNotNull(Class<?> registry, Class<?> fieldType) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = registry.getFields();
		for (Field field : fields) {
			if (field.getType() != fieldType || field.get(null) != null) continue;
			Assertions.fail(registry.getName() + " field not set!: " + field.getName());
		}
	}

}
