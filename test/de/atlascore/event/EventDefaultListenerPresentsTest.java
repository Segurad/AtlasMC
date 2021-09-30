package de.atlascore.event;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;

class EventDefaultListenerPresentsTest {

	int totalWarnings = 0, totalTested;
	
	boolean printMissingClasses = false;
	
	@Test
	void test() throws Exception {
		testEventPacket("de.atlasmc.event.block");
		testEventPacket("de.atlasmc.event.entity");
		testEventPacket("de.atlasmc.event.inventory");
		testEventPacket("de.atlasmc.event.network");
		testEventPacket("de.atlasmc.event.player");
		testEventPacket("de.atlasmc.event.proxy");
		System.out.println((totalTested-totalWarnings) + "|" + totalTested + "\tDefaultEventExecutors set...");
	}
	
	void testEventPacket(String packet) throws Exception {
		String packetDir = packet.replace('.', '/');
		InputStream in = ClassLoader.getSystemResourceAsStream(packetDir);
		if (in == null) fail("InputStream is null! (maybe wrong packet name?)");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Stream<String> lins = reader.lines();
		Iterator<String> it = lins.iterator();
		int tested = 0, warnings = 0;
		while (it.hasNext()) {
			final String classname = it.next();
			if (!classname.endsWith(".class")) continue;
			Class<?> clazz = Class.forName(packet + "." + classname.substring(0, classname.lastIndexOf('.')));
			if (!Event.class.isAssignableFrom(clazz)) continue;
			for (Method m : clazz.getMethods()) {
				if (!m.getName().equals("getHandlerList")) continue;
				if (!Modifier.isStatic(m.getModifiers())) continue;
				m.setAccessible(true);
				HandlerList handlers = (HandlerList) m.invoke(clazz, null);
				if (handlers.getDefaultExecutor() == null || handlers.getDefaultExecutor() == EventExecutor.NULL_EXECUTOR) {
					warnings++;
					if (printMissingClasses)
						System.out.println("[Warning] Event class does not have a DefaultExecutor: " + packet + "." + classname);
				}
				tested++;
				break;
			}
		}
		System.out.println((tested-warnings) + "|" + tested + "\tDefaultEventExecutors set in " + packet);
		totalTested += tested;
		totalWarnings += warnings;
	}

}
