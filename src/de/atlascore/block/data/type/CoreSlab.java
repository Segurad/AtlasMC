package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Slab;
import de.atlasmc.util.Validate;

public class CoreSlab extends CoreBlockData implements Slab {

	private boolean waterlogged;
	private Type type;
	
	public CoreSlab(Material material) {
		super(material);
		type = Type.BOTTOM;
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
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		Validate.notNull(type, "Type can not be null!");
		this.type = type;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(waterlogged?0:1)+
				type.ordinal()*2;
	}

}
