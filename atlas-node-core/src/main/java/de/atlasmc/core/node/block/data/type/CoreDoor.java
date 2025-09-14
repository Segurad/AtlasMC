package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.Door;

public class CoreDoor extends CoreDirectional4Faces implements Door {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				BlockDataProperty.HALF,
				BlockDataProperty.HINGE,
				BlockDataProperty.OPEN,
				BlockDataProperty.POWERED);
	}
	
	private Half half;
	private Hinge hinge;
	private boolean open;
	private boolean powered;
	
	public CoreDoor(BlockType type) {
		super(type);
		half = Half.LOWER;
		hinge = Hinge.LEFT;
	}
	
	@Override
	public CoreDoor clone() {
		return (CoreDoor) super.clone();
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		if (half == null) 
			throw new IllegalArgumentException("Half can not be null!");
		if (half == Half.TOP || half == Half.BOTTOM)
			throw new IllegalArgumentException("Invalid half: " + half.name());
		this.half = half;
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public Hinge getHinge() {
		return hinge;
	}

	@Override
	public void setHinge(Hinge hinge) {
		if (hinge == null) 
			throw new IllegalArgumentException("Hinge can not be null!");
		this.hinge = hinge;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+
				(powered?0:1)+
				(open?0:2)+
				hinge.ordinal()*4+
				half.getID()*8+
				getFaceValue()*16;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
