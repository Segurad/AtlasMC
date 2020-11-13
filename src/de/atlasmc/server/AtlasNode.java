package de.atlasmc.server;

public interface AtlasNode {

	public boolean isThis();
	public AtlasNode getMaster();
	public boolean isMaster();
	
}
