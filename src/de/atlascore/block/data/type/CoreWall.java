package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.Wall;
import de.atlasmc.util.Validate;

public class CoreWall extends CoreBlockData implements Wall {

	private boolean up, waterlogged;
	private final Height[] heights;
	
	public CoreWall(Material material) {
		super(material);
		heights = new Height[] {
				Height.NONE,
				Height.NONE,
				Height.NONE,
				Height.NONE
		};
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}

	@Override
	public Height getHeight(BlockFace face) {
		Validate.notNull(face, "BlockFace can not be null!");
		Validate.isTrue(face.ordinal() < 4, "BlockFace is not valid: " + face.name());
		return heights[face.ordinal()];
	}

	@Override
	public boolean isUp() {
		return up;
	}

	@Override
	public void setHeight(BlockFace face, Height height) {
		Validate.notNull(face, "BlockFace can not be null!");
		Validate.notNull(height, "Height can not be null!");
		Validate.isTrue(face.ordinal() < 4, "BlockFace is not valid: " + face.name());
		heights[face.ordinal()] = height;
	}

	@Override
	public void setUp(boolean up) {
		this.up = up;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				heights[3].ordinal()+
				(waterlogged?0:3)+ // WEST
				(up?0:6)+
				heights[2].ordinal()*12+ // SOUTH
				heights[0].ordinal()*36+ // NORTH
				heights[1].ordinal()*108; // EAST
	}

}
