package de.atlasmc.core.node.block.data;

import java.util.List;
import java.util.Set;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.property.BlockDataProperty;

public abstract class CoreAbstractDirectional extends CoreBlockData implements Directional {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.FACING);
	}
	
	private BlockFace face;
	
	public CoreAbstractDirectional(BlockType type) {
		this(type, BlockFace.NORTH);
	}
	
	public CoreAbstractDirectional(BlockType type, BlockFace face) {
		super(type);
		this.face = face;
	}

	@Override
	public abstract Set<BlockFace> getFaces();

	@Override
	public BlockFace getFacing() {
		return face;
	}

	@Override
	public void setFacing(BlockFace face) {
		if (face == null) 
			throw new IllegalArgumentException("BlockFace can not be null!");
		if (getFaceValue(face) == -1) 
			throw new IllegalArgumentException("BlockFace is not valid: " + face.name());
		this.face = face;
	}
	
	@Override
	public abstract int getStateID();
	
	/**
	 * Returns the value of this BlockFace or -1 if invalid
	 * @param face the BlockFace
	 * @return a value or -1
	 */
	protected abstract int getFaceValue(BlockFace face);
	
	/**
	 * Returns the value of {@link #getFacing()}
	 * @return value
	 */
	protected int getFaceValue() {
		return getFaceValue(face);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
