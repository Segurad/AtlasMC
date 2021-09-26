package de.atlasmc.factory;

import java.util.HashMap;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;

/**
 * Factory for creating {@link TileEntity}
 */
public abstract class TileEntityFactory {
	
	private static final HashMap<Material, TileEntity> tilePreConfig = new HashMap<>();
	
	public abstract boolean isValidTile(TileEntity tile);
	
	public TileEntity createTile(Material material) {
		return createTile(material, true);
	}
	
	public abstract TileEntity createTile(Material material, boolean preConfig);
	
	public static TileEntity getPreConfig(Material material) {
		return tilePreConfig.get(material);
	}
	
	public static void setPreConfig(Material material, TileEntity tile) {
		if (material == null) throw new IllegalArgumentException("Material can not be null!");
		if (!material.isBlock()) throw new IllegalArgumentException("Material is not a Block!");
		if (tile == null) tilePreConfig.remove(material);
		if (!material.isValidTile(tile))
			throw new IllegalArgumentException("TileEntity is not valid for Material: " + material.getName());
		tilePreConfig.put(material, tile);
	}
	
}
