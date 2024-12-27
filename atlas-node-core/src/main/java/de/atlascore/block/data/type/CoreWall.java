package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreHightConnectable;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Wall;

public class CoreWall extends CoreHightConnectable implements Wall {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreHightConnectable.PROPERTIES,
				BlockDataProperty.WATERLOGGED,
				BlockDataProperty.UP);
	}
	
	protected boolean waterlogged;
	protected boolean up;
	
	public CoreWall(Material material) {
		super(material);
		up = true;
	}
	
	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public boolean isUp() {
		return up;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}

	@Override
	public void setUp(boolean up) {
		this.up = up;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				heights[3].ordinal()+ // WEST
				(isWaterlogged()?0:3)+ 
				(up?0:6)+
				heights[2].ordinal()*12+ // SOUTH
				heights[0].ordinal()*36+ // NORTH
				heights[1].ordinal()*108; // EAST
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
