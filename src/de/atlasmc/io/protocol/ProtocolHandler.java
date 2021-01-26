package de.atlasmc.io.protocol;

import java.util.HashMap;

public class ProtocolHandler {

	private final HashMap<Integer, ProtocolAdapter> wrapper;
	private ProtocolAdapter defaultAdapter;
	
	public ProtocolAdapter getDefaultAdapter() {
		return defaultAdapter;
	}
	
	public void setDefaultAdapter(ProtocolAdapter defaultAdapter) {
		this.defaultAdapter = defaultAdapter;
	}
	
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
