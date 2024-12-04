package de.atlastest.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.commons.util.ReflectionUtils;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import de.atlastest.AtlasTest;

/**
 * Test enum classes
 */
public class EnumTest {
	
	private static Class<?> ENUM_ID_INTERFACE;
	private static Class<?> ENUM_NAME_INTERFACE;
	private static Class<?> ENUM_VALUE_CACHE_INTERFACE;
	
	private EnumTest() {}
	
	public static interface EnumTestCases {
		
		void testCacheMethods();
		
		void testIDMethods();
		
		void testNameMethods();
		
	}
	
	/**
	 * Test a enum class if a static getValues() and freeValues() method is present
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	public static void testCacheMethods(Class<? extends Enum<?>> clazz) {
		if (ENUM_VALUE_CACHE_INTERFACE == null)
			try {
				ENUM_VALUE_CACHE_INTERFACE = Class.forName("de.atlasmc.util.EnumValueCache");
			} catch (ClassNotFoundException e) {
				Assertions.fail(e);
			}
		Assertions.assertTrue(ENUM_VALUE_CACHE_INTERFACE.isAssignableFrom(clazz), "Enum does not implement EnumValueCache interface");
		Method getValues = ReflectionUtil.isMethodPresentAndStatic(clazz, "getValues");
		Method freeValues = ReflectionUtil.isMethodPresentAndStatic(clazz, "freeValues");
		Enum<?>[] values = clazz.getEnumConstants();
		List<?> valueList = List.of(values);
		List<?> cacheValues = null;
		try {
			cacheValues = (List<?>) getValues.invoke(null);
		} catch (IllegalAccessException | InvocationTargetException e) {
			Assertions.fail("Error while fetching values!", e);
		}
		Assertions.assertEquals(valueList, cacheValues, "getValues does not match values!");
		List<?> newCacheValues = null;
		try {
			newCacheValues = (List<?>) getValues.invoke(null);
		} catch (IllegalAccessException | InvocationTargetException e) {
			Assertions.fail("Error while fetching values!", e);
		}
		Assertions.assertSame(cacheValues, newCacheValues, "getValues returned other collection as last time!");
		try {
			freeValues.invoke(null);
		} catch (IllegalAccessException | InvocationTargetException e) {
			Assertions.fail("Error while calling freeValues!", e);
		}
		try {
			newCacheValues = (List<Enum<?>>) getValues.invoke(null);
		} catch (IllegalAccessException | InvocationTargetException e) {
			Assertions.fail("Error while fetching values!", e);
		}
		Assertions.assertNotSame(cacheValues, newCacheValues, "getValues returned same object after calling freeValues!");
	}
	
	/**
	 * Test a enum class if a getID() and static getByID(int) method is present
	 * @param clazz
	 */
	public static void testIDMethods(Class<? extends Enum<?>> clazz) {
		if (ENUM_ID_INTERFACE == null)
			try {
				ENUM_ID_INTERFACE = Class.forName("de.atlasmc.util.EnumID");
			} catch (ClassNotFoundException e) {
				Assertions.fail(e);
			}
		Assertions.assertTrue(ENUM_ID_INTERFACE.isAssignableFrom(clazz), "Enum does not implement EnumID interface");
		Method getID = ReflectionUtil.isMethodPresent(clazz, "getID");
		Method getByID = ReflectionUtil.isMethodPresentAndStatic(clazz, "getByID", int.class);
		Enum<?>[] values = clazz.getEnumConstants();
		for (Enum<?> value : values) {
			int id = 0;
			try {
				id = (int) getID.invoke(value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				Assertions.fail("Error while fetching id!", e);
			}
			Enum<?> other = null;
			try {
				other = (Enum<?>) getByID.invoke(null, id);
			} catch (IllegalAccessException | InvocationTargetException e) {
				Assertions.fail("Error while fetching enum by id!", e);
			}
			Assertions.assertNotNull(other, "getByID returned null!");
			int otherID = 0;
			try {
				otherID = (int) getID.invoke(other);
			} catch (IllegalAccessException | InvocationTargetException e) {
				Assertions.fail("Error while fetching other id!", e);
			}
			Assertions.assertSame(value, other, "getByID value mismatch: " + value + "(" + id + ") does not match " + other + "(" + otherID + ")");
		}
	}
	
	public static void testNameMethods(Class<? extends Enum<?>> clazz) {
		if (ENUM_NAME_INTERFACE == null)
			try {
				ENUM_NAME_INTERFACE = Class.forName("de.atlasmc.util.EnumName");
			} catch (ClassNotFoundException e) {
				Assertions.fail(e);
			}
		Assertions.assertTrue(ENUM_NAME_INTERFACE.isAssignableFrom(clazz), "Enum does not implement EnumName interface");
		Method getName = ReflectionUtil.isMethodPresent(clazz, "getName");
		Method getByName = ReflectionUtil.isMethodPresentAndStatic(clazz, "getByName", String.class);
		Enum<?>[] values = clazz.getEnumConstants();
		for (Enum<?> value : values) {
			String name = null;
			try {
				name = (String) getName.invoke(value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				Assertions.fail("Error while fetching name!", e);
			}
			Assertions.assertSame(name, name.intern(), "Name(" + name + ") is not interned!");
			Enum<?> other = null;
			try {
				other = (Enum<?>) getByName.invoke(null, name);
			} catch (IllegalAccessException | InvocationTargetException e) {
				Assertions.fail("Error while fetching enum by name!", e);
			}
			Assertions.assertNotNull(other, "getByName returned null!");
			String otherName = null;
			try {
				otherName = (String) getName.invoke(other);
			} catch (IllegalAccessException | InvocationTargetException e) {
				Assertions.fail("Error while fetching other name!", e);
			}
			Assertions.assertSame(value, other, "getByName value mismatch: " + value + "(" + name + ") does not match " + other + "(" + otherName + ")");
		}
		// invalid name
		Enum<?> value = null;
		try {
			value = (Enum<?>) getByName.invoke(null, AtlasTest.RANDOM_TEST_STRING);
		} catch (IllegalAccessException | InvocationTargetException e) {
			Assertions.fail("Error while fetching enum by name!", e);
		}
		Assertions.assertNull(value, "getByName returned non null value with random test string: " + AtlasTest.RANDOM_TEST_STRING);
		try {
			getByName.invoke(null, new Object[]{ null });
			Assertions.fail("getByName did not throw IllegalArgumentException on null parameter!");
		} catch (InvocationTargetException e) {
			Assertions.assertInstanceOf(IllegalArgumentException.class, e.getCause(), "getByName did not throw IllegalArgumentException on null parameter!");
		} catch (IllegalAccessException e) {
			Assertions.fail("Error while fetching enum by name!", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> void testRegistryProtocolEnum(Class<E> clazz, String registryFile) throws Exception {
		Class<?> caller = AtlasTest.getCaller();
		JsonReader reader = AtlasTest.getJsonResourceReader(registryFile, caller);
		reader.beginObject();
		final Set<E> types = EnumSet.allOf(clazz);
		final Method getByNameID = ReflectionUtils.tryToGetMethod(clazz, "getByName", String.class).get();
		final Method getID = ReflectionUtils.tryToGetMethod(clazz, "getID").get();
		final Method getByID = ReflectionUtils.tryToGetMethod(clazz, "getByID", int.class).get();
		final LinkedList<Executable> checks = new LinkedList<>();
		while (reader.peek() == JsonToken.NAME) {
			String name = reader.nextName();
			reader.beginObject();
			reader.nextName();
			int protocolID = reader.nextInt();
			reader.endObject();
			checks.add(() -> {
				E type = null;
				try {
					type = (E) getByNameID.invoke(null, name);
				} catch (Exception e) {}
				if (type == null) {
					Assertions.fail("Missing " + clazz.getSimpleName() + ": " + name + " with ID: " + protocolID);
				} else {
					types.remove(type);
					int typeID = (int) getID.invoke(type);
					if (typeID != protocolID) {
						Assertions.fail("ID missmatch for " + clazz.getSimpleName() + ":" + type.name() + "(" + name + ") was " + typeID + " expected " + protocolID);
					}
					try {
						E typeByID = (E) getByID.invoke(null, protocolID);
						if (type != typeByID) {
							Assertions.fail("byName/byID missmatch for " + clazz.getSimpleName() + ": expected " + type.name() + "(" + getID.invoke(type) + ") was " + typeByID.name() + "(" + getID.invoke(typeByID) + ")");
						}
					} catch(Exception e) {
						Assertions.fail("Error while getByID: " + e.getClass().getName() +  ": " + e.getMessage());
					}
				}
			});
		}
		checks.add(() -> {
		if (!types.isEmpty()) {
			for (E type : types) {
				checks.add(() -> {
					Assertions.fail("Unused " + clazz.getSimpleName() +  " present: " + type.name());
				});
			}
		}});
		reader.close();
		Assertions.assertAll("Failed to match all " + clazz.getName() + " to registry: " + registryFile, checks);
	}

}
