package de.atlasmc.test.io;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.io.protocol.configuration.PacketConfiguration;
import de.atlasmc.io.protocol.login.PacketLogin;
import de.atlasmc.io.protocol.play.PacketPlay;
import de.atlasmc.io.protocol.status.PacketStatus;
import de.atlastest.util.PacketIOUtils;
import de.atlastest.util.ReflectionUtil;

public class PacketPresentTest {

	@Test
	public void testProtocolPlay() throws Exception {
		testIDs(PacketPlay.class, "de.atlasmc.io.protocol.play");
	}
	
	@Test
	public void testProtocolStatus() throws Exception {
		testIDs(PacketStatus.class, "de.atlasmc.io.protocol.status");
	}
	
	@Test
	public void testProtocolLogin() throws Exception {
		testIDs(PacketLogin.class, "de.atlasmc.io.protocol.login");
	}
	
	@Test
	public void testProtocolConfiguration() throws Exception {
		testIDs(PacketConfiguration.class, "de.atlasmc.io.protocol.configuration");
	}
	
	public void testIDs(Class<?> clazz, String pathToPacketPackage) throws Exception {
		HashMap<Integer, String>[] ids = PacketIOUtils.getPacketIDs(clazz);
		HashMap<Integer, String> packetIDsIn = ids[0];
		HashMap<Integer, String> packetIDsOut = ids[1];
		ReflectionUtil.getClassesInPacket(pathToPacketPackage, (packetClass) -> {
			if (!clazz.isAssignableFrom(packetClass))
				return;
			int id = -1;
			try {
				id = Packet.getDefaultPacketID(packetClass);
			} catch (IllegalArgumentException e) {}
			if (id == -1)
				return;
			if (PacketInbound.class.isAssignableFrom(packetClass)) {
				packetIDsIn.remove(id);
			} else if (PacketOutbound.class.isAssignableFrom(packetClass)) {
				packetIDsOut.remove(id);
			}
		});
		LinkedList<Throwable> fails = new LinkedList<>();
		packetIDsIn.forEach((k, v) -> {
			fails.add(new AssertionError("No packet IN found for " + v + " with ID " + k));
		});
		packetIDsOut.forEach((k, v) -> {
			fails.add(new AssertionError("No packet OUT found for " + v + " with ID " + k));
		});
		if (fails.isEmpty())
			return;
		int missing = packetIDsIn.size() + packetIDsOut.size();
		throw new MultipleFailuresError("Failed to find all Packets for " + clazz.getSimpleName() + " in package " + pathToPacketPackage + " (" + missing + " missing)", fails);
	}
	
}
