package de.atlascore.test.system.init;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.atlascore.registry.CoreRegistryHandler;
import de.atlascore.system.init.ContainerFactoryLoader;
import de.atlascore.system.init.EntityTypeLoader;
import de.atlascore.system.init.MaterialLoader;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockDataFactory;
import de.atlasmc.block.tile.TileEntityFactory;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.registry.Registries;

public class StaticRegistryFieldTest {
	
	@Test
	void testContainerFactoryLoader() throws IllegalArgumentException, IllegalAccessException {
		ContainerFactoryLoader.loadContainerFactories();
		StaticRegistryFieldTest.testNotNull(ContainerFactory.class, ContainerFactory.class);
	}
	
	@Test
	void testMaterialLoader() throws Exception {
		try {
			Registries.init(new CoreRegistryHandler());
		} catch (IllegalStateException e) {}
		Registries.createInstanceRegistry(Material.class);
		Registries.createInstanceRegistry(BlockDataFactory.class);
		Registries.createInstanceRegistry(TileEntityFactory.class);
		MaterialLoader.loadMaterial();
		StaticRegistryFieldTest.testNotNull(Material.class, Material.class);
	}
	
	@Test
	void testEntityTypeLoader() throws Exception {
		try {
			Registries.init(new CoreRegistryHandler());
		} catch (IllegalStateException e) {}
		Registries.createInstanceRegistry(EntityType.class);
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
