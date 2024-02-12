package de.atlasmc.io.protocol;

import java.util.concurrent.ConcurrentHashMap;

public class ProtocolAdapterHandler {

	private final ConcurrentHashMap<Integer, ProtocolAdapter> wrapper;
	
	public ProtocolAdapterHandler() {
		wrapper = new ConcurrentHashMap<>();
	}
	
	public ProtocolAdapter getProtocol(int id) {
		return wrapper.get(id);
	}
	
	public void setProtocol(ProtocolAdapter protocol) {
		wrapper.put(protocol.getVersion(), protocol);
	}
}
