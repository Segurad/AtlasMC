package de.atlascore.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeIdentifier;
import static de.atlasmc.io.PacketUtil.writeVarInt;

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
		packet.entityID = readVarInt(in);
		final int attributeCount = in.readInt();
		List<AttributeInstance> attributes = new ArrayList<>(attributeCount);
		for (int i = 0; i < attributeCount; i++) {
			int typeID = readVarInt(in);
			Attribute type = Attribute.getByID(typeID);
			double value = in.readDouble();
			AttributeInstance instance = new AttributeInstance(type, 0.0, null);
			instance.setBaseValue(value);
			final int modifierCount = readVarInt(in);
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
		packet.attributes = attributes;
	}

	@Override
	public void write(PacketOutUpdateAttributes packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		List<AttributeInstance> attributes = packet.attributes;
		final int size = attributes.size();
		writeVarInt(size, out);
		for (int i = 0; i < size; i++) {
			AttributeInstance instance = attributes.get(i);
			writeVarInt(instance.getAttribute().getID(), out);
			out.writeDouble(instance.getDefaultValue());
			List<AttributeModifier> modifiers = instance.getModifiers();
			final int modCount = modifiers.size();
			writeVarInt(modCount, out);
			for (int j = 0; j < modCount; j++) {
				AttributeModifier mod = modifiers.get(j);
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
