package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.Entity.Animation;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityAnimation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityAnimation extends AbstractPacket implements PacketOutEntityAnimation {

	private int id, animation;
	
	public CorePacketOutEntityAnimation() {
		super(0x05, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityAnimation(Entity entity, Animation animation) {
		this();
		id = entity.getID();
		this.animation = animation.ordinal();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = readVarInt(in);
		animation = in.readUnsignedByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(id, out);
		out.writeByte(animation);
	}

	@Override
	public int getEntityID() {
		return id;
	}

	@Override
	public Animation getAnimation() {
		return Animation.getByID(animation);
	}

}
