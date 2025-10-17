package de.atlasmc.core.node.inventory.component;

import java.io.IOException;

import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BlockEntityComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreBlockEntityComponent extends AbstractItemComponent implements BlockEntityComponent {

	private TileEntity tile;
	
	public CoreBlockEntityComponent(ComponentType type) {
		super(type);
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
			NBTCodec<TileEntity> handler = (NBTCodec<TileEntity>) tile.getNBTCodec();
			handler.serialize(tile, writer);
		}
		writer.writeEndTag();
		writer.close();
	}

}
