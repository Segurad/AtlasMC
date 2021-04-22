package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUnlockRecipes;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUnlockRecipes extends AbstractPacket implements PacketOutUnlockRecipes {
	
	private int action;
	private boolean craftingOpen, craftingFilter, smeltingOpen, smeltingFilter, 
					blastFurnaceOpen, blastFurnaceFilter, smokerOpen, smokerFilter;
	private String[] recipes1, recipes2;
	
	public CorePacketOutUnlockRecipes() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	/**
	 * 
	 * @param action
	 * @param craftingOpen
	 * @param craftingFilter
	 * @param smeltingOpen
	 * @param smeltingFilter
	 * @param blastFurnaceOpen
	 * @param blastFurnaceFilter
	 * @param smokerOpen
	 * @param smokerFilter
	 * @param recipes1
	 * @param recipes2 will be ignored in case of {@link RecipesAction} ADD and REMOVE
	 */
	public CorePacketOutUnlockRecipes(RecipesAction action, boolean craftingOpen, boolean craftingFilter,
			boolean smeltingOpen, boolean smeltingFilter, boolean blastFurnaceOpen, boolean blastFurnaceFilter,
			boolean smokerOpen, boolean smokerFilter, String[] recipes1, String[] recipes2) {
		this();
		this.action = action.getID();
		this.craftingOpen = craftingOpen;
		this.craftingFilter = craftingFilter;
		this.smeltingOpen = smeltingOpen;
		this.smeltingFilter = smeltingFilter;
		this.blastFurnaceOpen = blastFurnaceOpen;
		this.blastFurnaceFilter = blastFurnaceFilter;
		this.smokerOpen = smokerOpen;
		this.smokerFilter = smokerFilter;
		this.recipes1 = recipes1;
		this.recipes2 = recipes2;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		action = readVarInt(in);
		craftingOpen = in.readBoolean();
		craftingFilter = in.readBoolean();
		smeltingOpen = in.readBoolean();
		smeltingFilter = in.readBoolean();
		blastFurnaceOpen = in.readBoolean();
		blastFurnaceFilter = in.readBoolean();
		smokerOpen = in.readBoolean();
		smokerFilter = in.readBoolean();
		int size = readVarInt(in);
		recipes1 = new String[size];
		for (int i = 0; i < size; i++) {
			recipes1[i] = readString(in);
		}
		if (action == 0) return;
		size = readVarInt(in);
		recipes2 = new String[size];
		for (int i = 0; i < size; i++) {
			recipes2[i] = readString(in);
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(action, out);
		out.writeBoolean(craftingOpen);
		out.writeBoolean(craftingFilter);
		out.writeBoolean(smeltingOpen);
		out.writeBoolean(smeltingFilter);
		out.writeBoolean(blastFurnaceOpen);
		out.writeBoolean(blastFurnaceFilter);
		out.writeBoolean(smokerOpen);
		out.writeBoolean(smokerFilter);
		writeVarInt(recipes1.length, out);
		for (String s : recipes1) {
			writeString(s, out);
		}
		if (action == 0) return;
		writeVarInt(recipes2.length, out);
		for (String s : recipes2) {
			writeString(s, out);
		}
	}

}
