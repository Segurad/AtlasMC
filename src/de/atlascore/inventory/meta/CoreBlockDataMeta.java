package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.BlockDataMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBlockDataMeta extends CoreItemMeta implements BlockDataMeta {

	private BlockData data;
	private Material material;
	
	protected static final String
			BLOCK_STATE_TAG = "BlockStateTag";
	
	static {
		NBT_FIELDS.setField(BLOCK_STATE_TAG, (holder, reader) -> {
			if (holder instanceof BlockDataMeta) {
				((BlockDataMeta) holder).getBlockData().fromNBT(reader);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	public CoreBlockDataMeta(Material material) {
		this(material, null);
	}
	
	public CoreBlockDataMeta(Material material, BlockData data) {
		super(material);
		this.material = material;
		this.data = data;
	}

	@Override
	public BlockData getBlockData() {
		if (data == null) data = material.createBlockData();
		return data;
	}

	@Override
	public boolean hasBlockData() {
		return data != null;
	}

	@Override
	public void setBlockData(BlockData data) {
		this.data = data;
	}
	
	@Override
	public CoreBlockDataMeta clone() {
		CoreBlockDataMeta clone = (CoreBlockDataMeta) super.clone();
		clone.data = data.clone();
		return clone;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeCompoundTag(BLOCK_STATE_TAG);
		data.toNBT(writer, systemData);
		writer.writeEndTag();
	}

}
