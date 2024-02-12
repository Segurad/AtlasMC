package de.atlascore.test.io.protocol;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

import de.atlascore.io.protocol.CorePacketListenerConfigurationIn;
import de.atlascore.io.protocol.CorePacketListenerLoginIn;
import de.atlascore.io.protocol.CorePacketListenerPlayIn;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketInbound;
import de.atlastest.util.ReflectionUtil;

public class PacketListenerTest {
	
	@Test
	void testConfigurationListener() throws Exception {
		testListenersPresent("de.atlasmc.io.protocol.configuration", CorePacketListenerConfigurationIn.class);
	}
	
	@Test
	void testLoginListener() throws Exception {
		testListenersPresent("de.atlasmc.io.protocol.login", CorePacketListenerLoginIn.class);
	}
	
	@Test
	void testPlayListener() throws Exception {
		testListenersPresent("de.atlasmc.io.protocol.play", CorePacketListenerPlayIn.class);
	}
	
	void testListenersPresent(String packet, Class<?> listenerClass) throws Exception {
		Field handlerField = listenerClass.getDeclaredField("HANDLERS");
		handlerField.setAccessible(true);
		Object handlers = handlerField.get(null);
		LinkedList<AssertionFailedError> checks = new LinkedList<>();
		ReflectionUtil.getClassesInPacket(packet, (clazz) -> {
			if (!PacketInbound.class.isAssignableFrom(clazz))
				return;
			int id = -1;
			try {
				id = Packet.getDefaultPacketID(clazz);
			} catch(Exception e) {}
			if (id == -1)
				return;
			Object handler = null;
			try {
				handler = Array.get(handlers, id);
			} catch (ArrayIndexOutOfBoundsException e) {
				checks.add(new AssertionFailedError("Failed to fetch handler (array index out of bounds) index: " + id + " size: " + Array.getLength(handlers)));
				return;
			}
			if (handler == null) {
				checks.add(new AssertionFailedError("Missing handler for packet: " + clazz.getName() + "(" + id +")"));
			}
		});
		if (!checks.isEmpty())
			throw new MultipleFailuresError("Failled to check all handlers!", checks);
	}

}
