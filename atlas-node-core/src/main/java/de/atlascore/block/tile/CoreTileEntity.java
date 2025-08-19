package de.atlascore.block.tile;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3i;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ItemComponent;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public class CoreTileEntity implements TileEntity {
	
	private BlockType type;
	private final Vector3i pos;
	private Chunk chunk;
	private Map<ComponentType, ItemComponent> components;
	
	public CoreTileEntity(BlockType type) {
		this.type = type;
		this.pos = new Vector3i();
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
		return pos.x;
	}
	
	@Override
	public void setX(int x) {
		this.pos.x = x;
	}

	@Override
	public int getY() {
		return pos.y;
	}
	
	@Override
	public void setY(int y) {
		this.pos.y = y;
	}

	@Override
	public int getZ() {
		return pos.z;
	}
	
	@Override
	public void setZ(int z) {
		this.pos.z = z;
	}
	
	public TileEntity clone() {
		try {
			return (TileEntity) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

	@Override
	public Vector3i getLocationUnsafe() {
		return pos;
	}
	
	@Override
	public Vector3i getLocation(Vector3i vec) {
		return vec.set(pos);
	}

	@Override
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public void setLocation(Chunk chunk, int x, int y, int z) {
		this.chunk = chunk;
		this.pos.x = x & 0xF;
		this.pos.y = y & 0xF;
		this.pos.z = z & 0xF;
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
	public Map<ComponentType, ItemComponent> getComponents() {
		if (components == null)
			components = new HashMap<>();
		return components;
	}

	@Override
	public boolean hasComponents() {
		return components != null && !components.isEmpty();
	}

}
