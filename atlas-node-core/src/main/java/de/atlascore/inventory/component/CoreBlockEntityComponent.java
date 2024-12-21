package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BlockEntityComponent;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBlockEntityComponent extends AbstractItemComponent implements BlockEntityComponent {

	private TileEntity tile;
	
	public CoreBlockEntityComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (tile == null)
			return;
		writer.writeCompoundTag(getNamespacedKeyRaw());
		tile.toNBT(writer, systemData);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		tile = TileEntity.getFromNBT(reader);
	}

	@Override
	public TileEntity getTileEntity() {
		return tile;
	}

	@Override
	public void setTileEntity(TileEntity tile) {
		this.tile = tile;
	}
	
	@Override
	public CoreBlockEntityComponent clone() {
		CoreBlockEntityComponent clone = (CoreBlockEntityComponent) super.clone();
		if (clone == null)
			return null;
		if (tile != null) {
			clone.tile = tile.clone();
		}
		return clone;
	}

}
