package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Chest;
import de.atlasmc.util.Validate;

public class CoreChest extends CoreDirectional4Faces implements Chest {

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
