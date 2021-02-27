package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Switch;
import de.atlasmc.util.Validate;

public class CoreSwitch extends CoreDirectional4Faces implements Switch {

	private AttachedFace face;
	private boolean powered;
	
	public CoreSwitch(Material material) {
		super(material);
		face = AttachedFace.WALL;
	}

	@Override
	public AttachedFace getAttachedFace() {
		return face;
	}

	@Override
	public void setAttachedFace(AttachedFace face) {
		Validate.notNull(face, "AttachedFace can not be null!");
		this.face = face;
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
		return getMaterial().getBlockID()+
				(powered?0:1)+
				getFaceValue()*2+
				face.ordinal()*8;
	}

}
