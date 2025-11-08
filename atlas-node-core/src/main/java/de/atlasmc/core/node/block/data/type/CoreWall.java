package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreHightConnectable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Wall;

public class CoreWall extends CoreHightConnectable implements Wall {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreHightConnectable.PROPERTIES,
				PropertyType.WATERLOGGED,
				PropertyType.UP);
	}
	
	protected boolean waterlogged;
	protected boolean up;
	
	public CoreWall(BlockType type) {
		super(type);
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
		return getType().getBlockStateID()+
				heights[3].ordinal()+ // WEST
				(isWaterlogged()?0:3)+ 
				(up?0:6)+
				heights[2].ordinal()*12+ // SOUTH
				heights[0].ordinal()*36+ // NORTH
				heights[1].ordinal()*108; // EAST
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
