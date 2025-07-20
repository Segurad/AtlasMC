package de.atlasmc.block.tile;

import de.atlasmc.SimpleLocation;
import de.atlasmc.block.BlockType;
import de.atlasmc.inventory.component.ItemComponent;
import de.atlasmc.inventory.component.ItemComponentHolder;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public interface TileEntity extends Cloneable, NBTSerializable, ItemComponentHolder {

	public static final CharKey NBT_ID = CharKey.literal("id");
	
	public static final NBTSerializationHandler<TileEntity>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TileEntity.class)
					.searchKeyConstructor("id", Registries.getRegistry(BlockType.class), BlockType::createTileEntity, TileEntity::getType)
					.intField("x", TileEntity::getX, TileEntity::setX)
					.intField("y", TileEntity::getY, TileEntity::setY)
					.intField("z", TileEntity::getZ, TileEntity::setZ)
					.compoundMapNamespacedType("components", TileEntity::hasComponents, TileEntity::getComponents, ItemComponent.NBT_HANDLER)
					.build();
	
	TileEntity clone();
	
	BlockType getType();
	
	void setType(BlockType material);
	
	/**
	 * Returns the <u><b>relative</b></u> Location of this Tile in the Chunk
	 * @return location
	 */
	SimpleLocation getLocation();
	
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
	default NBTSerializationHandler<? extends TileEntity> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
