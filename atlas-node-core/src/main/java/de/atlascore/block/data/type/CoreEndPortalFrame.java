package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.EndPortalFrame;

public class CoreEndPortalFrame extends CoreDirectional4Faces implements EndPortalFrame {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, BlockDataProperty.EYE);
	}
	
	private boolean eye;
	
	public CoreEndPortalFrame(BlockType type) {
		super(type);
	}

	@Override
	public boolean hasEye() {
		return eye;
	}

	@Override
	public void setEye(boolean eye) {
		this.eye = eye;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				getFaceValue()+
				(eye?0:4);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
