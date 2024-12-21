package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Wall;

public class CoreWall extends CoreWaterlogged implements Wall {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES,
				BlockDataProperty.CON_NORTH,
				BlockDataProperty.CON_EAST,
				BlockDataProperty.CON_WEST,
				BlockDataProperty.CON_SOUTH,
				BlockDataProperty.UP);
	}
	
	private boolean up;
	private final Height[] heights;
	
	public CoreWall(Material material) {
		super(material);
		heights = new Height[] {
				Height.NONE,
				Height.NONE,
				Height.NONE,
				Height.NONE
		};
		up = true;
	}

	@Override
	public Height getHeight(BlockFace face) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (face.ordinal() > 4) 
			throw new IllegalArgumentException("BlockFace is not valid: " + face.name());
		return heights[face.ordinal()];
	}

	@Override
	public boolean isUp() {
		return up;
	}

	@Override
	public void setHeight(BlockFace face, Height height) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (height == null) 
			throw new IllegalArgumentException("Height can not be null!");
		if (face.ordinal() > 4) 
			throw new IllegalArgumentException("BlockFace is not valid: " + face.name());
		heights[face.ordinal()] = height;
	}

	@Override
	public void setUp(boolean up) {
		this.up = up;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				heights[3].ordinal()+ // WEST
				(isWaterlogged()?0:3)+ 
				(up?0:6)+
				heights[2].ordinal()*12+ // SOUTH
				heights[0].ordinal()*36+ // NORTH
				heights[1].ordinal()*108; // EAST
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
