package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Openable;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreOpenable extends CoreBlockData implements Openable {

	private boolean open;
	
	public static final CharKey
	NBT_OPEN = CharKey.literal("open");
	
	static {
		NBT_FIELDS.setField(NBT_OPEN, (holder, reader) -> {
			if (holder instanceof Openable) {
				((Openable) holder).setOpen(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreOpenable(Material material) {
		super(material);
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(open?0:1);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(NBT_OPEN, open);
	}
}
