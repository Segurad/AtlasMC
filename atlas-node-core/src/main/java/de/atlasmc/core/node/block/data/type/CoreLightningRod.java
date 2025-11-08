package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional6Faces;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.LightningRod;

public class CoreLightningRod extends CoreDirectional6Faces implements LightningRod {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, 
				PropertyType.WATERLOGGED,
				PropertyType.POWERED);
	}
	
	private boolean waterlogged;
	private boolean powered;
	
	public CoreLightningRod(BlockType type) {
		super(type, BlockFace.UP);
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
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID() + (waterlogged?0:1) + (powered?0:2) + getFaceValue()*4;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
