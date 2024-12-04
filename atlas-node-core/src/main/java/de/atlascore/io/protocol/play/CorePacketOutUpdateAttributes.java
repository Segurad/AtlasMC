package de.atlascore.io.protocol.play;

import static de.atlasmc.io.AbstractPacket.readIdentifier;
import static de.atlasmc.io.AbstractPacket.readString;
import static de.atlasmc.io.AbstractPacket.readVarInt;
import static de.atlasmc.io.AbstractPacket.writeIdentifier;
import static de.atlasmc.io.AbstractPacket.writeString;
import static de.atlasmc.io.AbstractPacket.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.AttributeModifier.Operation;
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
				NamespacedKey id = readIdentifier(in);
				double amount = in.readDouble();
				Operation op = Operation.getByID(i);
				AttributeModifier modifier = new AttributeModifier(id, amount, op);
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
				writeIdentifier(mod.getID(), out);
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
