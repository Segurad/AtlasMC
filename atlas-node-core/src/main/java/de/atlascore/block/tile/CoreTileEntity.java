package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.Nameable;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public class CoreTileEntity extends AbstractNBTBase implements TileEntity {
	
	protected static final NBTFieldSet<CoreTileEntity> NBT_FIELDS;
	
	protected static final CharKey
	NBT_KEEP_PACKED = CharKey.literal("keepPacked"),
	NBT_X = CharKey.literal("x"),
	NBT_Y = CharKey.literal("y"),
	NBT_Z = CharKey.literal("z"),
	NBT_CUSTOM_NAME = CharKey.literal("CustomName");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_ID, (holder, reader) -> {
			holder.setType(Material.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_X, (holder, reader) -> {
			holder.x = reader.readIntTag();
		});
		NBT_FIELDS.setField(NBT_Y, (holder, reader) -> {
			holder.y = reader.readIntTag();
		});
		NBT_FIELDS.setField(NBT_Z, (holder, reader) -> {
			holder.z = reader.readIntTag();
		});
		NBT_FIELDS.setField(NBT_CUSTOM_NAME, (holder, reader) -> {
			if (holder instanceof Nameable)
			((Nameable) holder).setCustomName(ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_KEEP_PACKED, NBTField.skip()); // TODO Field skipped due to unknown behavior
	}
	
	private Material type;
	private int x;
	private int y;
	private int z;
	private Chunk chunk;
	
	public CoreTileEntity(Material type) {
		this.type = type;
	}

	@Override
	public Material getType() {
		return type;
	}

	@Override
	public void setType(Material material) {
		if (!material.isValidTile(this))
			throw new IllegalArgumentException("Tile ist not compatible with this Material: " + material.getNamespacedKeyRaw());
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
	
	public TileEntity clone() {
		try {
			return (TileEntity) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

	@Override
	protected NBTFieldSet<? extends CoreTileEntity> getFieldSetRoot() {
		return NBT_FIELDS;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (!systemData) { 
			writer.writeStringTag(NBT_ID, getType().getClientKey().toString());
			return;
		}
		writer.writeStringTag(NBT_ID, getType().getNamespacedKeyRaw());
		writer.writeByteTag(NBT_KEEP_PACKED, 0);
		writer.writeIntTag(NBT_X, getX());
		writer.writeIntTag(NBT_Y, getY());
		writer.writeIntTag(NBT_Z, getZ());
	}

	@Override
	public SimpleLocation getLocation() {
		return new SimpleLocation(x, y, z);
	}

	@Override
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public void setLocation(Chunk chunk, int x, int y, int z) {
		this.chunk = chunk;
		this.x = x & 0xF;
		this.y = y & 0xF;
		this.z = z & 0xF;
	}

	@Override
	public World getWorld() {
		return chunk != null ? chunk.getWorld() : null;
	}

	@Override
	public int getID() {
		return type.getTileID();
	}

}
