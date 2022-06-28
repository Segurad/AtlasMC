package de.atlascore.inventory.meta;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.TileEntityMeta;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTileEntityMeta extends CoreItemMeta implements TileEntityMeta {

	protected static final CharKey NBT_BLOCK_ENTITY_TAG = CharKey.of("BlockEntityTag");
	
	static {
		NBT_FIELDS.setField(NBT_BLOCK_ENTITY_TAG, (holder, reader) -> {
			if (holder instanceof TileEntityMeta) {
				TileEntityMeta meta = ((TileEntityMeta) holder);
				TileEntity tile = meta.getType().createTileEntity();
				if (tile == null) {
					reader.skipTag();
					return;
				}
				tile.fromNBT(reader);
				meta.setTileEntity(tile);
			} else ((ItemMeta) holder).getCustomTagContainer().addCustomTag(reader.readNBT());
		});
	}
	
	private TileEntity tile;
	private Material type;
	
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
		if (clone == null)
			return null;
		if (hasTileEntity()) 
			clone.setTileEntity(getTileEntity().clone());
		return clone;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasTileEntity()) {
			writer.writeCompoundTag(NBT_BLOCK_ENTITY_TAG);
			tile.toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}
	
	@Override
	public boolean isSimilar(ItemMeta meta, boolean ignoreDamage) {
		if (!super.isSimilar(meta, ignoreDamage))
			return false;
		TileEntityMeta tileMeta = (TileEntityMeta) meta;
		if (hasTileEntity() != tileMeta.hasTileEntity())
			return false;
		if (hasTileEntity() && !getTileEntity().equals(tileMeta.getTileEntity()))
			return false;
		if (!getType().equals(tileMeta.getType()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tile == null) ? 0 : tile.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

}
