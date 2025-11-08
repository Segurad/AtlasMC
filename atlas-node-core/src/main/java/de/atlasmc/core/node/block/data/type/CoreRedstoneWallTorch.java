package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.RedstoneWallTorch;

public class CoreRedstoneWallTorch extends CoreDirectional4Faces implements RedstoneWallTorch {
	
	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, PropertyType.LIT);
	}
	
	private boolean lit;
	
	public CoreRedstoneWallTorch(BlockType type) {
		super(type);
		lit = true;
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+
				(lit?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
