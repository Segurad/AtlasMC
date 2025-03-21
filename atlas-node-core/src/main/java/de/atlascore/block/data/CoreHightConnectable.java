package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.HightConnectable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreHightConnectable extends CoreBlockData implements HightConnectable {

	private static final Set<BlockFace> ALLOWED_FACES;

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES,
				BlockDataProperty.CON_NORTH,
				BlockDataProperty.CON_EAST,
				BlockDataProperty.CON_WEST,
				BlockDataProperty.CON_SOUTH);
		ALLOWED_FACES = EnumSet.range(BlockFace.NORTH, BlockFace.WEST);
	}
	
	protected final Height[] heights;
	
	public CoreHightConnectable(BlockType type) {
		super(type);
		heights = new Height[] {
				Height.NONE,
				Height.NONE,
				Height.NONE,
				Height.NONE
		};
	}
	
	@Override
	public Set<BlockFace> getAllowedFaces() {
		return ALLOWED_FACES;
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
	public int getStateID() {
		return type.getBlockStateID()+
				heights[3].ordinal()+ // WEST
				heights[2].ordinal()*3+ // SOUTH
				heights[0].ordinal()*9+ // NORTH
				heights[1].ordinal()*27; // EAST
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
