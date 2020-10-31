package de.atlasmc.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Packet {
	
	public void read(int length, DataInputStream input) throws IOException;
	public void write(DataOutputStream output) throws IOException;
	public boolean isCancelled();
	public void setCancelled(boolean cancelled);
	public int getVersion();
	public int getID();
}
