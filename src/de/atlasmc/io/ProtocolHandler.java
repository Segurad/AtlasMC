package de.atlasmc.io;

import java.util.HashMap;

public class ProtocolHandler {

	private final HashMap<Integer, ProtocolAdapter> wrapper;
	
	public ProtocolHandler() {
		wrapper = new HashMap<Integer, ProtocolAdapter>();
	}
	
	public ProtocolAdapter getProtocol(int id) {
		return wrapper.get(id);
	}
	
	public void setProtocol(int id, ProtocolAdapter protocol) {
		wrapper.put(id, protocol);
	}
}
