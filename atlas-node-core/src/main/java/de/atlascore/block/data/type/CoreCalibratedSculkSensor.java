package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAnaloguePowerable;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.CalibratedSculkSensor;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCalibratedSculkSensor extends CoreDirectional4Faces implements CalibratedSculkSensor {

	private boolean waterlogged;
	private Phase phase;
	private int power;
	
	public CoreCalibratedSculkSensor(Material material) {
		super(material);
		phase = Phase.INACTIVE;
	}
	
	@Override
	public CoreCalibratedSculkSensor clone() {
		return (CoreCalibratedSculkSensor) super.clone();
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
	public void setPhase(Phase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Phase can not be null!");
		this.phase = phase;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (waterlogged?0:1) + phase.getID()*2 + getPower()*6 + getFaceValue()*96;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (waterlogged)
			writer.writeByteTag(CoreWaterlogged.NBT_WATERLOGGED, true);
		if (phase != Phase.INACTIVE)
			writer.writeStringTag(CoreSculkSensor.NBT_SCULK_SENSOR_PHASE, phase.getName());
		if (power > 0)
			writer.writeIntTag(CoreAnaloguePowerable.NBT_POWER, power);
	}
	
	@Override
	public Phase getPhase() {
		return phase;
	}

	@Override
	public int getMaxPower() {
		return 15;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public void setPower(int level) {
		if (level > 15 || level < 0) 
			throw new IllegalArgumentException("Power is not between 0 and 15: " + level);
		this.power = level;
	}

}
