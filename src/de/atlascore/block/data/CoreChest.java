package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Chest;
import de.atlasmc.util.Validate;

public class CoreChest extends CoreAbstractDirectional4Faces implements Chest {

	private boolean waterlogged;
	private Type type;
	
	public CoreChest(Material material) {
		super(material);
		type = Type.SINGLE;
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
		return getMaterial().getBlockID()+
				getFaceValue()*6+
				type.ordinal()*2+
				(waterlogged?0:1);
	}
	
	

}
