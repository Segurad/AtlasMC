package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBisected;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreOpenable;
import de.atlascore.block.data.CorePowerable;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TrapDoor;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreTrapDoor extends CoreDirectional4Faces implements TrapDoor {
	
	private Half half;
	private boolean open, powered, waterlogged;
	
	public CoreTrapDoor(Material material) {
		super(material);
		half = Half.BOTTOM;
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
		return getMaterial().getBlockStateID()+
				(waterlogged?0:1)+
				(powered?0:2)+
				(open?0:4)+
				half.getID()*8+
				getFaceValue()*16;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getHalf() != Half.BOTTOM) 
			writer.writeStringTag(CoreBisected.NBT_HALF, half.getNameID());
		if (isOpen()) 
			writer.writeByteTag(CoreOpenable.NBT_OPEN, true);
		if (isPowered()) 
			writer.writeByteTag(CorePowerable.NBT_POWERED, true);
		if (isWaterlogged()) 
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
	}

}
