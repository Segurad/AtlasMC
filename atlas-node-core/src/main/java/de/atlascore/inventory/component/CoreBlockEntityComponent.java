package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.BlockEntityComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import io.netty.buffer.ByteBuf;

public class CoreBlockEntityComponent extends AbstractItemComponent implements BlockEntityComponent {

	private TileEntity tile;
	
	public CoreBlockEntityComponent(NamespacedKey key) {
		super(key);
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
	
	@Override
	public ComponentType getType() {
		return ComponentType.BLOCK_ENTITY_DATA;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NBTReader reader = new NBTNIOReader(buf, true);
		reader.readNextEntry();
		tile = TileEntity.NBT_HANDLER.deserialize(reader);
		reader.close();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		NBTWriter writer = new NBTNIOWriter(buf, true);
		writer.writeCompoundTag(null);
		if (tile != null) {
			@SuppressWarnings("unchecked")
			NBTSerializationHandler<TileEntity> handler = (NBTSerializationHandler<TileEntity>) tile.getNBTHandler();
			handler.serialize(tile, writer);
		}
		writer.writeEndTag();
		writer.close();
	}

}
