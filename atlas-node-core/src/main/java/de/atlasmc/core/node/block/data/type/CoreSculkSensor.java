package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreAnaloguePowerable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.SculkSensor;

public class CoreSculkSensor extends CoreAnaloguePowerable implements SculkSensor {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAnaloguePowerable.PROPERTIES, 
				BlockDataProperty.WATERLOGGED,
				BlockDataProperty.SCULK_SENSOR_PHASE);
	}
	
	private boolean waterlogged;
	private Phase phase;
	
	public CoreSculkSensor(BlockType type) {
		super(type);
		phase = Phase.INACTIVE;
	}
	
	@Override
	public CoreSculkSensor clone() {
		return (CoreSculkSensor) super.clone();
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
		return getType().getBlockStateID() + (waterlogged?0:1) + phase.getID()*2 + getPower()*6;
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
	@Override
	public Phase getPhase() {
		return phase;
	}

}
