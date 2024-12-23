package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutRecipeBookSettings;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRecipeBookSettings implements PacketIO<PacketOutRecipeBookSettings> {

	@Override
	public void read(PacketOutRecipeBookSettings packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.craftingOpen = in.readBoolean();
		packet.craftingFilter = in.readBoolean();
		packet.smeltingOpen = in.readBoolean();
		packet.smeltingFilter = in.readBoolean();
		packet.blastFurnaceOpen = in.readBoolean();
		packet.blastFurnaceFilter = in.readBoolean();
		packet.smokerOpen = in.readBoolean();
		packet.smokerFilter = in.readBoolean();
	}

	@Override
	public void write(PacketOutRecipeBookSettings packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeBoolean(packet.craftingOpen);
		out.writeBoolean(packet.craftingFilter);
		out.writeBoolean(packet.smeltingOpen);
		out.writeBoolean(packet.smeltingFilter);
		out.writeBoolean(packet.blastFurnaceOpen);
		out.writeBoolean(packet.blastFurnaceFilter);
		out.writeBoolean(packet.smokerOpen);
		out.writeBoolean(packet.smokerFilter);
	}

	@Override
	public PacketOutRecipeBookSettings createPacketData() {
		return new PacketOutRecipeBookSettings();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRecipeBookSettings.class);
	}

}
