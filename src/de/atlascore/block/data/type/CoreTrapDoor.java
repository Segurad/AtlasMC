package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TrapDoor;
import de.atlasmc.util.Validate;

public class CoreTrapDoor extends CoreDirectional4Faces implements TrapDoor {
	
	private Half half;
	private boolean open, powered, waterlogged;
	
	public CoreTrapDoor(Material material) {
		super(material);
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		Validate.notNull(half, "Half can not be null!");
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
		return getMaterial().getBlockID()+
				(waterlogged?0:1)+
				(powered?0:2)+
				(open?0:4)+
				half.ordinal()*8+
				getFaceValue()*16;
	}

}
