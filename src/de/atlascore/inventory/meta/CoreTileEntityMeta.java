package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.TileEntityMeta;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTileEntityMeta extends CoreItemMeta implements TileEntityMeta {

	private TileEntity tile;
	protected static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
	private Material type;
	
	static {
		NBT_FIELDS.setField(BLOCK_ENTITY_TAG, (holder, reader) -> {
			if (TileEntityMeta.class.isInstance(holder)) {
				TileEntityMeta meta = ((TileEntityMeta) holder);
				TileEntity tile = meta.getType().createTileEntity();
				tile.fromNBT(reader);
				meta.setTileEntity(tile);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	public CoreTileEntityMeta(Material material) {
		super(material);
		this.type = material;
	}

	@Override
	public void setTileEntity(TileEntity tile) {
		this.tile = tile;
		this.type = tile.getType();
	}

	@Override
	public TileEntity getTileEntity() {
		if (tile == null) tile = type.createTileEntity();
		return tile;
	}

	@Override
	public boolean hasTileEntity() {
		return tile != null;
	}

	@Override
	public Material getType() {
		return type;
	}
	
	@Override
	public CoreTileEntityMeta clone() {
		CoreTileEntityMeta clone = (CoreTileEntityMeta) super.clone();
		if (hasTileEntity()) clone.setTileEntity(getTileEntity().clone());
		return clone;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasTileEntity()) {
			writer.writeCompoundTag(BLOCK_ENTITY_TAG);
			tile.toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}

}
