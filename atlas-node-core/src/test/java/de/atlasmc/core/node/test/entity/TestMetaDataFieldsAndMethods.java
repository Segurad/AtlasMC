package de.atlasmc.core.node.test.entity;

import static  org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import de.atlasmc.core.node.system.init.ContainerFactoryLoader;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.test.util.ReflectionUtil;

public class TestMetaDataFieldsAndMethods implements Consumer<Class<?>> {

	@Test
	void test() throws ClassNotFoundException {
		ContainerFactoryLoader.loadContainerFactories();
		ReflectionUtil.getClassesInPacket("de.atlascore.entity", this);
	}

	@Override
	public void accept(Class<?> clazz) {
		Field LAST_META_INDEX = null;
		List<Field> META_FIELDS = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers()))
				continue;
			if (field.getName().equals("LAST_META_INDEX")) {
				if (!int.class.isAssignableFrom(field.getType()))
					fail("LAST_META_INDEX not assignable from int: " + clazz.getName());
				LAST_META_INDEX = field;
			} else if (MetaDataField.class.isAssignableFrom(field.getType())) {
				META_FIELDS.add(field);
			} else continue;
			field.setAccessible(true);
		}
		if (LAST_META_INDEX == null && META_FIELDS.isEmpty())
			return;
		if (LAST_META_INDEX != null && META_FIELDS.isEmpty())
			fail("LAST_META_INDEX without MetaDataFields: " + clazz.getName());
		if (LAST_META_INDEX == null && !META_FIELDS.isEmpty())
			fail("LAST_META_INDEX missing but MetaDataFields present: " + clazz.getName());
		try {
			clazz.getDeclaredMethod("initMetaContainer");
		} catch (NoSuchMethodException | SecurityException e) {
			fail("Missing \"initMetaContainer()\" method for entity class: " + clazz.getName());
		}
		try {
			clazz.getDeclaredMethod("getMetaContainerSize");
		} catch (NoSuchMethodException | SecurityException e) {
			fail("Missing \"getMetaContainerSize()\" method for entity class: " + clazz.getName());
		}
		if (Modifier.isAbstract(clazz.getModifiers()))
			return;
		Constructor<?> construct = null;
		try {
			construct = clazz.getConstructor(EntityType.class, UUID.class);
		} catch (NoSuchMethodException | SecurityException e) {
			fail("Entity constructor(EntityType, UUID) missing: " + clazz.getName(), e);
		}
		try {
			construct.newInstance(null, null);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			fail("Fail to create Entity instance: " + clazz.getName(), e);
		}
	}

}
