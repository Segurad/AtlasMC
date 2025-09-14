package de.atlasmc.node.io.protocol.common;

import java.util.List;

import de.atlasmc.io.AbstractPacket;

public abstract class AbstractPacketKnownPacks extends AbstractPacket {

	public List<PackInfo> knownPacks;
	
	public static class PackInfo {
		
		public String namespace;
		public String id;
		public String version;
		
	}
	
}
