package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.FaceAttachable;
import de.atlasmc.util.Validate;

public class CoreFaceAttachable extends CoreBlockData implements FaceAttachable {

	private AttachedFace face;
	
	public CoreFaceAttachable(Material material) {
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
	public int getStateID() {
		return super.getStateID()+face.ordinal();
	}

}
