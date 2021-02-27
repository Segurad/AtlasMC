package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Grindstone;
import de.atlasmc.util.Validate;

public class CoreGrindstone extends CoreDirectional4Faces implements Grindstone {

	private AttachedFace face;
	
	public CoreGrindstone(Material material) {
		super(material);
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
	public int getStateID() {
		return super.getStateID()+face.ordinal()*4;
	}

}
