package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.nbt.tag.NBT;

@DefaultPacketID(PacketPlay.OUT_NBT_QUERY_RESPONSE)
public class PacketOutNBTQueryResponse extends AbstractPacket implements PacketPlayOut {
	
	private int transactionID;
	private NBT nbt;
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public NBT getNBT() {
		return nbt;
	}
	
	public void setNBT(NBT nbt) {
		this.nbt = nbt;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_NBT_QUERY_RESPONSE;
	}

}
