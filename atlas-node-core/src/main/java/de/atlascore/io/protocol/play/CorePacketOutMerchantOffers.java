package de.atlascore.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.io.protocol.ProtocolUtil.readSlot;
import static de.atlasmc.io.protocol.ProtocolUtil.writeSlot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.entity.Merchant.MerchantRecipe;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutMerchantOffers;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMerchantOffers implements PacketIO<PacketOutMerchantOffers> {

	@Override
	public void read(PacketOutMerchantOffers packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = readVarInt(in);
		final int size = readVarInt(in);
		if (size > 0) {
			List<MerchantRecipe> recipes = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				ItemStack in1 = readSlot(in);
				ItemStack out = readSlot(in);
				ItemStack in2 = readSlot(in);
				int trades = in.readInt();
				int maxtrades = in.readInt();
				int xp = in.readInt();
				int specialPrice = in.readInt();
				float priceMulti = in.readFloat();
				int demand = in.readInt();
				
				MerchantRecipe t = new MerchantRecipe(in1, out);
				t.setInputItem2(in2);
				t.setTrades(trades);
				t.setMaxTrades(maxtrades);
				t.setXP(xp);
				t.setSpecialPrice(specialPrice);
				t.setPriceMultiplier(priceMulti);
				t.setDemand(demand);
				
				recipes.add(t);
			}
			packet.trades = recipes;
		} else {
			packet.trades = List.of();
		}
		packet.level = readVarInt(in);
		packet.experience = readVarInt(in);
		packet.regular = in.readBoolean();
		packet.canRestock = in.readBoolean();
	}

	@Override
	public void write(PacketOutMerchantOffers packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.windowID, out);
		List<MerchantRecipe> trades = packet.trades;
		final int size = trades.size();
		writeVarInt(size, out);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				MerchantRecipe t = trades.get(i);
				writeSlot(t.getInputItem1(), out);
				writeSlot(t.getOutputItem(), out);
				writeSlot(t.getInputItem2(), out);
				out.writeBoolean(t.isDisabled());
				out.writeInt(t.getTrades());
				out.writeInt(t.getMaxTrades());
				out.writeInt(t.getXP());
				out.writeInt(t.getSpecialPrice());
				out.writeFloat(t.getPriceMultiplier());
				out.writeInt(t.getDemand());
			}
		}
		writeVarInt(packet.level, out);
		writeVarInt(packet.experience, out);
		out.writeBoolean(packet.regular);
		out.writeBoolean(packet.canRestock);
	}
	
	@Override
	public PacketOutMerchantOffers createPacketData() {
		return new PacketOutMerchantOffers();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutMerchantOffers.class);
	}

}
