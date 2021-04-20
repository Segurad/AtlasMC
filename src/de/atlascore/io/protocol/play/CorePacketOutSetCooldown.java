package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSetCooldown;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetCooldown extends AbstractPacket implements PacketOutSetCooldown {

	private int itemID, cooldown;
	
	public CorePacketOutSetCooldown() {
		super(0x16, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSetCooldown(int itemID, int cooldown) {
		this();
		this.itemID = itemID;
		this.cooldown = cooldown;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		itemID = readVarInt(in);
		cooldown = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(itemID, out);
		writeVarInt(cooldown, out);
	}

	@Override
	public int getItemID() {
		return itemID;
	}

	@Override
	public int getCooldown() {
		return cooldown;
	}

}
