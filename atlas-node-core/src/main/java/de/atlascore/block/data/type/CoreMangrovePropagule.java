package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreAgeable;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.MangrovePropagule;

public class CoreMangrovePropagule extends CoreAgeable implements MangrovePropagule {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAgeable.PROPERTIES, 
				BlockDataProperty.WATERLOGGED,
				BlockDataProperty.HANGING,
				BlockDataProperty.STAGE);
	}
	
	private boolean waterlogged;
	private boolean hanging;
	private int stage;
	
	public CoreMangrovePropagule(Material material) {
		super(material, 3);
	}

	@Override
	public int getMaxStage() {
		return 1;
	}

	@Override
	public int getStage() {
		return stage;
	}

	@Override
	public void setStage(int stage) {
		if (1 < stage || stage < 0) 
			throw new IllegalArgumentException("Stage is not between 0 and 1: " + stage);
		this.stage = stage;
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
	public boolean isHanging() {
		return hanging;
	}

	@Override
	public void setHanging(boolean hanging) {
		this.hanging = hanging;
	}
	
	@Override
	public MangrovePropagule clone() {
		return (MangrovePropagule) super.clone();
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+(waterlogged?0:1)+stage*2+(hanging?0:4)+getAge()*8;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
