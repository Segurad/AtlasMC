package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TechnicalPiston;
import de.atlasmc.util.Validate;

public class CoreTechnicalPiston extends CoreDirectional6Faces implements TechnicalPiston {

	private Type type;
	
	public CoreTechnicalPiston(Material material) {
		super(material);
		type = Type.NORMAL;
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
		return getMaterial().getBlockID()+getFaceValue()*2+type.ordinal();
	}

}
