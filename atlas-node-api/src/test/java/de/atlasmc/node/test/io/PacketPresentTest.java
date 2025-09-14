package de.atlasmc.node.test.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

import com.google.gson.stream.JsonReader;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketInbound;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.node.io.protocol.configuration.PacketConfiguration;
import de.atlasmc.node.io.protocol.login.PacketLogin;
import de.atlasmc.node.io.protocol.play.PacketPlay;
import de.atlasmc.node.io.protocol.status.PacketStatus;
import de.atlasmc.test.AtlasTest;
import de.atlasmc.test.util.PacketIOUtils;
import de.atlasmc.test.util.ReflectionUtil;

public class PacketPresentTest {

	@Test
	public void testProtocolPlayConsistensy() throws Exception {
		testConsistensy(PacketPlay.class, "de.atlasmc.io.protocol.play");
	}
	
	@Test
	public void testProtocolStatusConsistensy() throws Exception {
		testConsistensy(PacketStatus.class, "de.atlasmc.io.protocol.status");
	}
	
	@Test
	public void testProtocolLoginConsistensy() throws Exception {
		testConsistensy(PacketLogin.class, "de.atlasmc.io.protocol.login");
	}
	
	@Test
	public void testProtocolConfigurationConsistensy() throws Exception {
		testConsistensy(PacketConfiguration.class, "de.atlasmc.io.protocol.configuration");
	}
	
	@Test
	public void testProtocolPlayPresent() throws Exception {
		testPresent("de.atlasmc.io.protocol.play", "/minecraft/protocol/play_serverbound.json", "/minecraft/protocol/play_clientbound.json");
	}
	
	@Test
	public void testProtocolStatusPresent() throws Exception {
		testPresent("de.atlasmc.io.protocol.status", "/minecraft/protocol/status_serverbound.json", "/minecraft/protocol/status_clientbound.json");
	}
	
	@Test
	public void testProtocolLoginPresent() throws Exception {
		testPresent("de.atlasmc.io.protocol.login", "/minecraft/protocol/login_serverbound.json", "/minecraft/protocol/login_clientbound.json");
	}
	
	@Test
	public void testProtocolConfigurationPresent() throws Exception {
		testPresent("de.atlasmc.io.protocol.configuration", "/minecraft/protocol/configuration_serverbound.json", "/minecraft/protocol/configuration_clientbound.json");
	}
	
	private void testPresent(String pathToPacketPackage, String inDefinition, String outDefinition) throws Exception {
		Map<String, Integer> in = readPacketDefinitons(inDefinition);
		Map<String, Integer> out = readPacketDefinitons(outDefinition);
		LinkedList<Throwable> errors = new LinkedList<>();
		ReflectionUtil.getClassesInPacket(pathToPacketPackage, (packetClass) -> {
			DefaultPacketID annotation = packetClass.getAnnotation(DefaultPacketID.class);
			if (annotation == null)
				return;
			String definition = annotation.definition();
			int id = annotation.packetID();
			if (PacketInbound.class.isAssignableFrom(packetClass)) {
				Integer shouldID = in.get(definition);
				if (shouldID == null) {
					errors.add(new AssertionError("Packet class (" + packetClass.getName() + ") with unknown definition: " + definition));
					return;
				}
				in.remove(definition);
				if (id == shouldID)
					return;
				errors.add(new AssertionError("Expected packet id (" + shouldID + ") but was (" + id + ") in packet: " + definition + " class: " + packetClass.getName()));
			} else if (PacketOutbound.class.isAssignableFrom(packetClass)) {
				Integer shouldID = out.get(definition);
				if (shouldID == null) {
					errors.add(new AssertionError("Packet class (" + packetClass.getName() + ") with unknown definition: " + definition));
					return;
				}
				out.remove(definition);
				if (id == shouldID)
					return;
				errors.add(new AssertionError("Expected packet id (" + shouldID + ") but was (" + id + ") in packet: " + definition + " class: " + packetClass.getName()));
			}
		});
		in.forEach((k, v) -> {
			errors.add(new AssertionError("Missing packet in " + k + ":" + v));
		});
		out.forEach((k, v) -> {
			errors.add(new AssertionError("Missing packet out " + k + ":" + v));
		});
		if (errors.isEmpty())
			return;
		throw new MultipleFailuresError("Failed to verify all packets in package " + pathToPacketPackage, errors);
	}
	
	private Map<String, Integer> readPacketDefinitons(String path) throws IOException {
		Map<String, Integer> definitions = new HashMap<>();
		JsonReader reader = AtlasTest.getJsonResourceReader(path);
		reader.beginObject();
		while (reader.hasNext()) {
			String key = reader.nextName();
			int id = reader.nextInt();
			definitions.put(key, id);
		}
		reader.endObject();
		reader.close();
		return definitions;
	}
	
	private void testConsistensy(Class<?> clazz, String pathToPacketPackage) throws Exception {
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
