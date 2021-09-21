package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.Waterlogged;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreWaterlogged extends CoreBlockData implements Waterlogged {

	private boolean waterlogged;
	
	protected static final String WATERLOGGED = "waterlogged";
	
	static {
		NBT_FIELDS.setField(WATERLOGGED, (holder, reader) -> {
			if (Waterlogged.class.isInstance(holder)) {
				((Waterlogged) holder).setWaterlogged(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreWaterlogged(Material material) {
		super(material);
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(waterlogged?0:1);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeByteTag(WATERLOGGED, waterlogged);
	}
	
}
