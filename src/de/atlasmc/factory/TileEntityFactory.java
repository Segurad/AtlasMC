package de.atlasmc.factory;

import java.util.HashMap;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.util.Validate;

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
		Validate.notNull(material, "Material can not be null!");
		Validate.isTrue(material.isBlock(), "Material is not a Block!");
		if (tile == null) tilePreConfig.remove(material);
		if (!material.isValidTile(tile))
			throw new IllegalArgumentException("TileEntity is not valid for Material: " + material.getName());
		tilePreConfig.put(material, tile);
	}
	
}
