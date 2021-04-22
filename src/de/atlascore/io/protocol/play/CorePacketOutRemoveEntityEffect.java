package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutRemoveEntityEffect;
import de.atlasmc.potion.PotionEffectType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRemoveEntityEffect extends AbstractPacket implements PacketOutRemoveEntityEffect {

	private int entityID, effectID;
	
	public CorePacketOutRemoveEntityEffect() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutRemoveEntityEffect(int entityID, PotionEffectType effect) {
		this();
		this.entityID = entityID;
		this.effectID = effect.getID();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		effectID = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeByte(effectID);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public int getEffectID() {
		return effectID;
	}

}
