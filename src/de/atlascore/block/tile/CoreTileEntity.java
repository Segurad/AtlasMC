package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.Nameable;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public class CoreTileEntity extends AbstractNBTBase implements TileEntity {
	
	protected static final NBTFieldContainer NBT_FIELDS;
	protected static final String
	ID = "id",
	KEEP_PACKED = "keepPacked",
	X = "x",
	Y = "y",
	Z = "z",
	NBT_CUSTOM_NAME = "CustomName";
	
	static {
		NBT_FIELDS = new NBTFieldContainer();
		NBT_FIELDS.setField(ID, (holder, reader) -> {
			if (holder instanceof CoreTileEntity)
			((TileEntity) holder).setType(Material.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(X, (holder, reader) -> {
			if (holder instanceof TileEntity) 
			((TileEntity) holder).setX(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(Y, (holder, reader) -> {
			if (holder instanceof TileEntity) 
			((TileEntity) holder).setY(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(Z, (holder, reader) -> {
			if (holder instanceof TileEntity) 
			((TileEntity) holder).setZ(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_CUSTOM_NAME, (holder, reader) -> {
			if (holder instanceof Nameable)
			((Nameable) holder).setCustomName(ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(KEEP_PACKED, NBTField.SKIP); // TODO Field skipped due to unknown behavior
	}
	
	private Material type;
	private int x, y, z;
	private Chunk chunk;
	
	public CoreTileEntity(Material type, Chunk chunk, int x, int y, int z) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public Material getType() {
		return type;
	}

	@Override
	public void setType(Material material) {
		if (!material.isValidTile(this))
			throw new IllegalArgumentException("Tile ist not compatible with this Material: " + material.getNamespacedName());
		this.type = material;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZ() {
		return z;
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setZ(int z) {
		this.z = z;
	}
	
	public TileEntity clone() {
		try {
			return (TileEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (!systemData) return;
		writer.writeStringTag(ID, getType().getNamespacedName());
		writer.writeByteTag(KEEP_PACKED, 0);
		writer.writeIntTag(X, getX());
		writer.writeIntTag(Y, getY());
		writer.writeIntTag(Z, getZ());
	}

	@Override
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public Location getLocation() {
		return new Location(chunk.getWorld(), x, y, z);
	}

	@Override
	public World getWorld() {
		return chunk.getWorld();
	}

}
