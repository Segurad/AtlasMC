package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.TrapDoor;

public class CoreTrapDoor extends CoreDirectional4Faces implements TrapDoor {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				BlockDataProperty.WATERLOGGED,
				BlockDataProperty.HALF,
				BlockDataProperty.OPEN,
				BlockDataProperty.POWERED);
	}
	
	private Half half;
	private boolean open;
	private boolean powered;
	private boolean waterlogged;
	
	public CoreTrapDoor(BlockType type) {
		super(type);
		half = Half.BOTTOM;
	}
	
	@Override
	public CoreTrapDoor clone() {
		return (CoreTrapDoor) super.clone();
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		if (half == null) 
			throw new IllegalArgumentException("Half can not be null!");
		if (half == Half.UPPER || half == Half.LOWER)
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
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				(waterlogged?0:1)+
				(powered?0:2)+
				(open?0:4)+
				half.getID()*8+
				getFaceValue()*16;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
