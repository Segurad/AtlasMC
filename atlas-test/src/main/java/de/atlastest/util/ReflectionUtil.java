package de.atlastest.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;

public class ReflectionUtil {
	
	private ReflectionUtil() {}
	
	public static void getClassesInPacket(String packet, Consumer<Class<?>> consumer) throws ClassNotFoundException {
		String packetDir = packet.replace('.', '/');
		InputStream in = ClassLoader.getSystemResourceAsStream(packetDir);
		if (in == null) 
			fail("InputStream is null! (maybe wrong packet name? " + packet + ")");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Stream<String> lins = reader.lines();
		Iterator<String> it = lins.iterator();
		while (it.hasNext()) {
			final String classname = it.next();
			if (!classname.endsWith(".class")) continue;
			Class<?> clazz = Class.forName(packet + "." + classname.substring(0, classname.lastIndexOf('.')));
			consumer.accept(clazz);
		}
	}
	
	public static Method isMethodPresent(Class<?> clazz, String method, Class<?>... parameterTypes) {
		try {
			return clazz.getDeclaredMethod(method, parameterTypes);
		} catch (NoSuchMethodException e) {
			Assertions.fail("Method " + method + " is not present in " + clazz.getName(), e);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void isMethodStatic(Method method) {
		if (!Modifier.isStatic(method.getModifiers()))
			fail("Method " + method.getName() + " is not static in " + method.getDeclaringClass().getName());
	}
	
	public static void isMethodPresentAndStatic(Class<?> clazz, String method, Class<?>... parameterTypes) {
		Method m = isMethodPresent(clazz, method, parameterTypes);
		isMethodStatic(m);
	}

}
