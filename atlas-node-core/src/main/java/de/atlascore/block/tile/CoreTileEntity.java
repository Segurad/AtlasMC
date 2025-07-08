package de.atlascore.block.tile;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.inventory.component.ItemComponent;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public class CoreTileEntity implements TileEntity {
	
	private BlockType type;
	private int x;
	private int y;
	private int z;
	private Chunk chunk;
	private Map<NamespacedKey, ItemComponent> components;
	
	public CoreTileEntity(BlockType type) {
		this.type = type;
	}

	@Override
	public BlockType getType() {
		return type;
	}

	@Override
	public void setType(BlockType material) {
		if (!material.isValidTile(this))
			throw new IllegalArgumentException("Tile ist not compatible with this type: " + material.getNamespacedKeyRaw());
		this.type = material;
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getZ() {
		return z;
	}
	
	@Override
	public void setZ(int z) {
		this.z = z;
	}
	
	public TileEntity clone() {
		try {
			return (TileEntity) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
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

	@Override
	public Map<NamespacedKey, ItemComponent> getComponents() {
		if (components == null)
			components = new HashMap<>();
		return components;
	}

	@Override
	public boolean hasComponents() {
		return components != null && !components.isEmpty();
	}

}
