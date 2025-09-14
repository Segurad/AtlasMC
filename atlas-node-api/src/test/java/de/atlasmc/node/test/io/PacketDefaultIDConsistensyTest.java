package de.atlasmc.node.test.io;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import de.atlasmc.io.Packet;
import de.atlasmc.test.util.ReflectionUtil;

public class PacketDefaultIDConsistensyTest {
	
	@Test
	public void testProtocolPlay() throws Exception {
		testIDs("de.atlasmc.io.protocol.play");
	}
	
	@Test
	public void testProtocolStatus() throws Exception {
		testIDs("de.atlasmc.io.protocol.status");
	}
	
	@Test
	public void testProtocolLogin() throws Exception {
		testIDs("de.atlasmc.io.protocol.login");
	}
	
	@Test
	public void testProtocolConfiguration() throws Exception {
		testIDs("de.atlasmc.io.protocol.configuration");
	}
	
	public void testIDs(String pathToPacketPackage) throws Exception {
		LinkedList<Executable> checks = new LinkedList<>();
		ReflectionUtil.getClassesInPacket(pathToPacketPackage, (clazz) -> {
			int id = -1;
			try {
				id = Packet.getDefaultPacketID(clazz);
			} catch(IllegalArgumentException e) {}
			if (id == -1)
				return;
			Packet packet = null;
			try {
				packet = (Packet) clazz.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new AssertionError("Exception while instanciating Packet class", e);
			}
			if (id != packet.getDefaultID()) {
				checks.add(() -> Assertions.fail(clazz.getName() + " default ids do not match!"));
			}
		});
		Assertions.assertAll("Inconsistent Packet ids in package: " + pathToPacketPackage + " (" + checks.size() + " inconsistensies)", checks);
	}

}
