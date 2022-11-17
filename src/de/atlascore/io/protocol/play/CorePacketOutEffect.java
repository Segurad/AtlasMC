package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Effect;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEffect extends AbstractPacket implements PacketOutEffect {

	private int id, data;
	private long pos;
	private boolean disableRelativVolume;
	
	public CorePacketOutEffect() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEffect(Effect effect, long position, int data, boolean disableRelativVolume) {
		this();
		this.id = effect.getID();
		pos = position;
		this.data = data;
		this.disableRelativVolume = disableRelativVolume;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = in.readInt();
		pos = in.readLong();
		data = in.readInt();
		disableRelativVolume = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(id);
		out.writeLong(pos);
		out.writeInt(data);
		out.writeBoolean(disableRelativVolume);
	}

	@Override
	public Effect getEffect() {
		return Effect.getByID(id);
	}

	@Override
	public long getPosition() {
		return pos;
	}

	@Override
	public int getData() {
		return data;
	}

	@Override
	public boolean getDisableRelativVolume() {
		return disableRelativVolume;
	}

	@Override
	public void setEffect(Effect effect) {
		this.id = effect.getID();
	}

	@Override
	public void setPosition(long pos) {
		this.pos = pos;
	}

	@Override
	public void setData(int data) {
		this.data = data;
	}

	@Override
	public void setDisableRelativeVolume(boolean disable) {
		this.disableRelativVolume = disable;
	}

}
