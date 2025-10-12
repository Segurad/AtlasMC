package de.atlasmc.core.node.test.io.protocol;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketServerbound;
import de.atlasmc.io.PacketClientbound;
import de.atlasmc.node.io.protocol.configuration.PacketConfiguration;
import de.atlasmc.node.io.protocol.login.PacketLogin;
import de.atlasmc.node.io.protocol.play.PacketPlay;
import de.atlasmc.node.io.protocol.status.PacketStatus;
import de.atlasmc.test.util.PacketIOUtils;
import de.atlasmc.test.util.ReflectionUtil;

public class PacketIOPresentTest {

	@Test
	public void testProtocolPlay() throws Exception {
		testIDs(PacketPlay.class, "de.atlascore.io.protocol.play");
	}
	
	@Test
	public void testProtocolStatus() throws Exception {
		testIDs(PacketStatus.class, "de.atlascore.io.protocol.status");
	}
	
	@Test
	public void testProtocolLogin() throws Exception {
		testIDs(PacketLogin.class, "de.atlascore.io.protocol.login");
	}
	
	@Test
	public void testProtocolConfiguration() throws Exception {
		testIDs(PacketConfiguration.class, "de.atlascore.io.protocol.configuration");
	}
	
	public void testIDs(Class<?> clazz, String pathToPacketPackage) throws Exception {
		HashMap<Integer, String>[] ids = PacketIOUtils.getPacketIDs(clazz);
		HashMap<Integer, String> packetIDsIn = ids[0];
		HashMap<Integer, String> packetIDsOut = ids[1];
		ReflectionUtil.getClassesInPacket(pathToPacketPackage, (ioClass) -> {
			if (!PacketIO.class.isAssignableFrom(ioClass))
				return;
			Method m = null;
			try {
				m = ioClass.getDeclaredMethod("createPacketData");
			} catch (NoSuchMethodException | SecurityException e) {
				return;
			}
			Class<?> packetClass = m.getReturnType();
			int id = -1;
			try {
				id = Packet.getDefaultPacketID(packetClass);
			} catch (IllegalArgumentException e) {}
			if (id == -1)
				return;
			if (PacketServerbound.class.isAssignableFrom(packetClass)) {
				packetIDsIn.remove(id);
			} else if (PacketClientbound.class.isAssignableFrom(packetClass)) {
				packetIDsOut.remove(id);
			}
		});
		LinkedList<Throwable> fails = new LinkedList<>();
		packetIDsIn.forEach((k, v) -> {
			fails.add(new AssertionError("No packet io IN found for " + v + " with ID " + k));
		});
		packetIDsOut.forEach((k, v) -> {
			fails.add(new AssertionError("No packet io OUT found for " + v + " with ID " + k));
		});
		if (fails.isEmpty())
			return;
		int missing = packetIDsIn.size() + packetIDsOut.size();
		throw new MultipleFailuresError("Failed to find all PacketIO for " + clazz.getSimpleName() + " in package " + pathToPacketPackage + " (" + missing + " missing)", fails);
	}
	
}