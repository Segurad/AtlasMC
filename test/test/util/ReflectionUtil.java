package test.util;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ReflectionUtil {
	
	private ReflectionUtil() {}
	
	public static void getClassesInPacket(String packet, Consumer<Class<?>> consumer) throws ClassNotFoundException {
		String packetDir = packet.replace('.', '/');
		InputStream in = ClassLoader.getSystemResourceAsStream(packetDir);
		if (in == null) fail("InputStream is null! (maybe wrong packet name?)");
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

}
