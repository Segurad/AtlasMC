package de.atlasmc.test.util;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.ReflectionUtils;
import org.junit.jupiter.api.function.Executable;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import de.atlasmc.test.AtlasTest;

public class EnumTestUtil {
	
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> void testRegistryProtocolEnum(Class<E> clazz, String registryFile) throws Exception {
		Class<?> caller = AtlasTest.getCaller();
		JsonReader reader = AtlasTest.getJsonResourceReader(registryFile, caller);
		reader.beginObject();
		final Set<E> types = EnumSet.allOf(clazz);
		final Method getByNameID = ReflectionUtils.tryToGetMethod(clazz, "getByName", String.class).get();
		final Method getID = ReflectionUtils.tryToGetMethod(clazz, "getID").get();
		final Method getByID = ReflectionUtils.tryToGetMethod(clazz, "getByID", int.class).get();
		final Queue<Executable> checks = new ConcurrentLinkedQueue<>();
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
