package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreMultipleFacing4;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.GlassPane;

public class CoreGlassPane extends CoreMultipleFacing4 implements GlassPane {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreMultipleFacing4.PROPERTIES, BlockDataProperty.WATERLOGGED);
	}
	
	private boolean waterlogged;
	
	public CoreGlassPane(BlockType material) {
		super(material);
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
				(hasFace(BlockFace.WEST)?0:1)+
				(waterlogged?0:2)+
				(hasFace(BlockFace.SOUTH)?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
