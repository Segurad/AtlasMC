package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.CalibratedSculkSensor;

public class CoreCalibratedSculkSensor extends CoreWaterloggedDirectional4Faces implements CalibratedSculkSensor {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, 
				BlockDataProperty.SCULK_SENSOR_PHASE,
				BlockDataProperty.POWER);
	}
	
	private Phase phase;
	private int power;
	
	public CoreCalibratedSculkSensor(BlockType type) {
		super(type);
		phase = Phase.INACTIVE;
	}
	
	@Override
	public CoreCalibratedSculkSensor clone() {
		return (CoreCalibratedSculkSensor) super.clone();
	}

	@Override
	public void setPhase(Phase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Phase can not be null!");
		this.phase = phase;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID() + (waterlogged?0:1) + phase.getID()*2 + power*6 + getFaceValue()*96;
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
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
