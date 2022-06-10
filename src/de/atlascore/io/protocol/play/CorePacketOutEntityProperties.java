package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityProperties;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CorePacketOutEntityProperties extends AbstractPacket implements PacketOutEntityProperties {

	private int entityID;
	private ByteBuf buf;
	
	public CorePacketOutEntityProperties() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityProperties(int entityID, Collection<AttributeInstance> attributes) {
		this();
		this.entityID = entityID;
		setAttributes(attributes);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		buf = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeBytes(buf);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public List<AttributeInstance> getAttributes() {
		// TODO return attributes
		return null;
	}

	@Override
	public void setEntity(int id) {
		this.entityID = id;
	}

	@Override
	public void setAttributes(Collection<AttributeInstance> attributes) {
		if (buf == null)
			buf = Unpooled.buffer();
		else
			buf.clear();
		writeVarInt(attributes.size(), buf);
		for (AttributeInstance i : attributes) {
			writeString(i.getAttribute().getName(), buf);
			buf.writeDouble(i.getDefaultValue());
			writeVarInt(i.getModifierCount(), buf);
			for (AttributeModifier mod : i.getModifiers()) {
				buf.writeLong(mod.getUUID().getMostSignificantBits());
				buf.writeLong(mod.getUUID().getLeastSignificantBits());
				buf.writeDouble(mod.getAmount());
				buf.writeByte(mod.getOperation().ordinal());
			}
		}
	}
	
	

}
