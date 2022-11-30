package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutUnlockRecipes;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUnlockRecipes extends AbstractPacket implements PacketOutUnlockRecipes {
	
	private int action;
	private boolean craftingOpen, craftingFilter, smeltingOpen, smeltingFilter, 
					blastFurnaceOpen, blastFurnaceFilter, smokerOpen, smokerFilter;
	private List<NamespacedKey> tagged, untagged;
	
	public CorePacketOutUnlockRecipes() {
		super(CoreProtocolAdapter.VERSION);
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
		tagged = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			tagged.add(i, new NamespacedKey(readString(in)));
		}
		if (action == 0) return;
		size = readVarInt(in);
		untagged = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			untagged.add(i, new NamespacedKey(readString(in)));
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
		if (tagged == null) {
			writeVarInt(0, out);
		} else {
			writeVarInt(tagged.size(), out);
			for (NamespacedKey key : tagged) {
				writeString(key.toString(), out);
			}
		}
		if (action == 0) 
			return;
		if (untagged == null) {
			writeVarInt(0, out);
			return;
		}
		writeVarInt(untagged.size(), out);
		for (NamespacedKey key : untagged) {
			writeString(key.toString(), out);
		}
	}

	@Override
	public RecipesAction getAction() {
		return RecipesAction.getByID(action);
	}

	@Override
	public List<NamespacedKey> getTagged() {
		return tagged;
	}

	@Override
	public List<NamespacedKey> getUntagged() {
		return untagged;
	}

	@Override
	public void setTagged(List<NamespacedKey> tagged) {
		this.tagged = tagged;
	}

	@Override
	public void setUntagged(List<NamespacedKey> untagged) {
		this.untagged = untagged;
	}

	@Override
	public void setAction(RecipesAction action) {
		this.action = action.getID();
	}

	@Override
	public boolean isCraftingBookOpen() {
		return craftingOpen;
	}

	@Override
	public boolean isCraftingBookFiltered() {
		return craftingFilter;
	}

	@Override
	public boolean isSmeltingBookOpen() {
		return smeltingOpen;
	}

	@Override
	public boolean isSmeltingBookFiltered() {
		return smeltingFilter;
	}

	@Override
	public boolean isBlastingBookOpen() {
		return blastFurnaceOpen;
	}
	
	@Override
	public boolean isBlastingBookFiltered() {
		return blastFurnaceFilter;
	}

	@Override
	public boolean isSmokingBookOpen() {
		return smokerOpen;
	}

	@Override
	public boolean isSmokingBookFilered() {
		return smokerFilter;
	}

	@Override
	public void setCraftingBookOpen(boolean open) {
		this.craftingOpen = open;
	}

	@Override
	public void setCraftingBookFiltered(boolean filtered) {
		this.craftingFilter = filtered;
	}

	@Override
	public void setSmeltingBookOpen(boolean open) {
		this.smeltingOpen = open;
	}

	@Override
	public void setSmeltingBookFiltered(boolean filtered) {
		this.smeltingFilter = filtered;
	}

	@Override
	public void setBlastingBookOpen(boolean open) {
		this.blastFurnaceOpen = open;
	}

	@Override
	public void setBlastingBookFiltered(boolean filtered) {
		this.blastFurnaceFilter = filtered;
	}

	@Override
	public void setSmokingBookOpen(boolean open) {
		this.smokerOpen = open;
	}

	@Override
	public void setSmokingBookFiltered(boolean filtered) {
		this.smokerFilter = filtered;
	}

}
