package de.atlasmc.schematic;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;

public class SchematicBlock implements SchematicObject {

	private Material material = Material.AIR;
	private BlockData data;

	public SchematicBlock(Material material) {
		this.material = material;
		data = material.createBlockData();
	}

	public SchematicBlock(Block block) {
		this.material = block.getType();
		this.data = block.getBlockData();
	}

	public Material getType() {
		return material;
	}

	public void setType(Material material) {
		this.material = material;
		BlockData new_data = material.createBlockData();
		if (!data.matches(new_data)) {
			data = new_data;
		}
	}

	public void place(Location loc) {
		Block b = loc.getBlock();
		b.setType(material);
		if (data != null) {
			b.setBlockData(data);
		}
	}

	public BlockData getBlockData() {
		return data;
	}

	public void setBlockData(BlockData data) {
		this.data = data;
		this.material = data.getMaterial();
	}

	public boolean hasBlockData() {
		return (data == null ? false : true);
	}

	@Override
	public boolean isAir() {
		return material == Material.AIR ? true : false;
	}
}
