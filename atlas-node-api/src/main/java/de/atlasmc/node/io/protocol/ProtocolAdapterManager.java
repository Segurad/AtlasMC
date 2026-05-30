package de.atlasmc.node.io.protocol;

import java.util.concurrent.ConcurrentHashMap;

public class ProtocolAdapterManager {

	private final ConcurrentHashMap<Integer, ProtocolAdapter> wrapper;
	private volatile ProtocolAdapter defaultProtocol;
	
	public ProtocolAdapterManager() {
		wrapper = new ConcurrentHashMap<>();
	}
	
	public ProtocolAdapter getDefaultProtocol() {
		return defaultProtocol;
	}
	
	public void setDefaultProtocol(ProtocolAdapter defaultProtocol) {
		this.defaultProtocol = defaultProtocol;
	}
	
	public ProtocolAdapter getProtocolOrDefault(int id) {
		var protocol = wrapper.get(id);
		return protocol != null ? protocol : defaultProtocol;
	}
	
	public ProtocolAdapter getProtocol(int id) {
		return wrapper.get(id);
	}
	
	public void setProtocol(ProtocolAdapter protocol) {
		wrapper.put(protocol.getVersion(), protocol);
	}
}
