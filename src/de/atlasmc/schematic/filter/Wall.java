package de.atlasmc.schematic.filter;

import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.schematic.SchematicObject;

public class Wall extends Cuboid {

	protected int walls;
	protected Material inner;

	public Wall(int widhtx, int height, int widhtz, Material boarder, Material inner, int walls) {
		super(widhtx, height, widhtz, boarder);
		this.inner = inner;
		this.walls = walls;
	}

	@Override
	public SchematicObject[][][] apply(SchematicObject[][][] blocks) {
		SchematicObject[][][] new_blocks = apply(blocks, new SimpleLocation(0, 0, 0), new SimpleLocation(widhtx, height, widhtz), type);
		SimpleLocation end = new SimpleLocation(widhtx - walls, height, widhtz - walls);
		return apply(new_blocks, new SimpleLocation(walls, 0, walls), end, inner);
	}

}
