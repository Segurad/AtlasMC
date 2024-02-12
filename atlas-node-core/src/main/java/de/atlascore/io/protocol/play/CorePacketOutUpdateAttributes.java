package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateAttributes;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateAttributes implements PacketIO<PacketOutUpdateAttributes> {

	@Override
	public void read(PacketOutUpdateAttributes packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntity(readVarInt(in));
		int attributeCount = in.readInt();
		List<AttributeInstance> attributes = new ArrayList<>(attributeCount);
		for (int i = 0; i < attributeCount; i++) {
			String typeID = readString(in);
			Attribute type = Attribute.getByName(typeID);
			double value = in.readDouble();
			AttributeInstance instance = new AttributeInstance(type, 0.0, null);
			instance.setBaseValue(value);
			int modifierCount = readVarInt(in);
			List<AttributeModifier> modifiers = new ArrayList<>(attributeCount);
			for (int j = 0; j < modifierCount; j++) {
				long most = in.readLong();
				long least = in.readLong();
				double amount = in.readDouble();
				Operation op = Operation.getByID(i);
				UUID uuid = new UUID(most, least);
				AttributeModifier modifier = new AttributeModifier(uuid, null, amount, op);
				modifiers.add(modifier);
			}
			instance.setModifiers(modifiers);
			attributes.add(instance);
		}
	}

	@Override
	public void write(PacketOutUpdateAttributes packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		List<AttributeInstance> attributes = packet.getAttributes();
		writeVarInt(attributes.size(), out);
		for (AttributeInstance i : attributes) {
			writeString(i.getAttribute().getName(), out);
			out.writeDouble(i.getDefaultValue());
			writeVarInt(i.getModifierCount(), out);
			for (AttributeModifier mod : i.getModifiers()) {
				out.writeLong(mod.getUUID().getMostSignificantBits());
				out.writeLong(mod.getUUID().getLeastSignificantBits());
				out.writeDouble(mod.getAmount());
				out.writeByte(mod.getOperation().getID());
			}
		}
	}

	@Override
	public PacketOutUpdateAttributes createPacketData() {
		return new PacketOutUpdateAttributes();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateAttributes.class);
	}

}
