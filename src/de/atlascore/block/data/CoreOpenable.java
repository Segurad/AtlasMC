package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Openable;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreOpenable extends CoreBlockData implements Openable {

	private boolean open;
	
	protected static final String OPEN = "open";
	
	static {
		NBT_FIELDS.setField(OPEN, (holder, reader) -> {
			if (Openable.class.isInstance(holder)) {
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
		writer.writeByteTag(OPEN, open);
	}
}
