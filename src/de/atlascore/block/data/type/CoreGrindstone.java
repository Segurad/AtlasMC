package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreFaceAttachable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Grindstone;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreGrindstone extends CoreDirectional4Faces implements Grindstone {

	private AttachedFace face;
	
	public CoreGrindstone(Material material) {
		super(material);
		face = AttachedFace.WALL;
	}

	@Override
	public AttachedFace getAttachedFace() {
		return face;
	}

	@Override
	public void setAttachedFace(AttachedFace face) {
		if (face == null) throw new IllegalArgumentException("AttachedFace can not be null!");
		this.face = face;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+face.ordinal()*4;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getAttachedFace() != AttachedFace.WALL) writer.writeStringTag(CoreFaceAttachable.FACE, getAttachedFace().name().toLowerCase());
	}

}
