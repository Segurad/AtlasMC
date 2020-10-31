package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInCraftRecipeRequest extends Packet {
	
	public byte WindowID();
	public String Recipe();
	public boolean makeAll();

}
