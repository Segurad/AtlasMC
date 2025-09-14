package de.atlasmc.core.node.test.io.protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

import de.atlasmc.core.node.io.protocol.CoreProtocolConfiguration;
import de.atlasmc.core.node.io.protocol.CoreProtocolLogin;
import de.atlasmc.core.node.io.protocol.CoreProtocolPlay;
import de.atlasmc.core.node.io.protocol.CoreProtocolStatus;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.Protocol;
import de.atlasmc.node.io.protocol.configuration.PacketConfiguration;
import de.atlasmc.node.io.protocol.login.PacketLogin;
import de.atlasmc.node.io.protocol.play.PacketPlay;
import de.atlasmc.node.io.protocol.status.PacketStatus;
import de.atlasmc.test.util.PacketIOUtils;

public class ProtocolTest {
	
	@Test
	public void testProtocolStatusPacketPresent() throws Exception {
		testProtocol(new CoreProtocolStatus(), PacketStatus.class);
	}
	
	@Test
	public void testProtocolLoginPacketPresent() throws Exception {
		testProtocol(new CoreProtocolLogin(), PacketLogin.class);
	}
	
	@Test
	public void testProtocolConfigurationPacketPresent() throws Exception {
		testProtocol(new CoreProtocolConfiguration(), PacketConfiguration.class);
	}
	
	@Test
	public void testProtocolPlayPacketPresent() throws Exception {
		testProtocol(new CoreProtocolPlay(), PacketPlay.class);
	}
	
	private void testProtocol(Protocol protocol, Class<?> protocolClass) throws Exception {
		HashMap<Integer,String>[] io = PacketIOUtils.getPacketIDs(protocolClass);
		HashMap<Integer, String> packetsIn = io[0];
		HashMap<Integer, String> packetsOut = io[1];
		
		final int packetCountIn = packetsIn.size();
		for (int i = 0; i < packetCountIn; i++) {
			PacketIO<?> handler = null;
			try {
				handler = protocol.getHandlerIn(i);
			} catch(Exception e) {}
			if (handler == null)
				continue;
			packetsIn.remove(handler.getPacketID());
		}
		final int packetCountOut = packetsOut.size();
		for (int i = 0; i < packetCountOut; i++) {
			PacketIO<?> handler = null;
			try {
				handler = protocol.getHandlerOut(i);
			} catch(Exception e) {}
			if (handler == null)
				continue;
			packetsOut.remove(handler.getPacketID());
		}
		if (packetsIn.isEmpty() && packetsOut.isEmpty())
			return;
		List<Throwable> fails = new LinkedList<>();
		packetsIn.forEach((k, v) -> {
			fails.add(new AssertionFailedError("No packet io IN found for " + v + " with ID " + k));
		});
		packetsOut.forEach((k, v) -> {
			fails.add(new AssertionFailedError("No packet io OUT found for " + v + " with ID " + k));
		});
		int missing = fails.size();
		throw new MultipleFailuresError("Failed to find all PacketIO for " + protocolClass.getSimpleName() + " (" + missing + " missing)", fails);
	}

}
