package de.atlasmc.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Packet {
	
	public void read(int length, DataInput input) throws IOException;
	public void write(DataOutput output) throws IOException;
	public boolean isCancelled();
	public void setCancelled(boolean cancelled);
	public int getVersion();
	public int getID();
}
