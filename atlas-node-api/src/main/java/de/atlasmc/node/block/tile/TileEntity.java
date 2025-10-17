package de.atlasmc.node.block.tile;

import org.joml.Vector3i;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.inventory.component.ItemComponentHolder;
import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.World;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.UnsafeAPI;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface TileEntity extends Cloneable, NBTSerializable, ItemComponentHolder {
	
	public static final NBTCodec<TileEntity>
	NBT_HANDLER = NBTCodec
					.builder(TileEntity.class)
					.searchKeyConstructor("id", BlockType.REGISTRY_KEY, BlockType::createTileEntity, TileEntity::getType)
					.intField("x", TileEntity::getX, TileEntity::setX)
					.intField("y", TileEntity::getY, TileEntity::setY)
					.intField("z", TileEntity::getZ, TileEntity::setZ)
					.include(ItemComponentHolder.NBT_HANDLER)
					.build();
	
	@NotNull
	TileEntity clone();
	
	@NotNull
	BlockType getType();
	
	void setType(BlockType material);
	
	@UnsafeAPI
	@NotNull
	Vector3i getLocationUnsafe();
	
	/**
	 * Returns the <u><b>relative</b></u> Location of this Tile in the Chunk
	 * @return location
	 */
	@NotNull
	default Vector3i getLocation() {
		return getLocation(new Vector3i());
	}
	
	@NotNull
	Vector3i getLocation(@NotNull Vector3i vec);
	
	int getX();
	
	int getY();
	
	int getZ();
	
	void setX(int x);
	
	void setY(int y);
	
	void setZ(int z);
	
	Chunk getChunk();
	
	World getWorld();
	
	@InternalAPI
	void setLocation(Chunk chunk, int x, int y, int z);

	int getID();
	
	@Override
	default NBTCodec<? extends TileEntity> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
