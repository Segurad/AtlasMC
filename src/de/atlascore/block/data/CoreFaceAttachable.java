package de.atlascore.block.data;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.data.FaceAttachable;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFaceAttachable extends CoreBlockData implements FaceAttachable {

	public static final String
	FACE = "face";
	
	static {
		NBT_FIELDS.setField(FACE, (holder, reader) -> {
			if (holder instanceof FaceAttachable)
			((FaceAttachable) holder).setAttachedFace(AttachedFace.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
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
		if (face == null) throw new IllegalArgumentException("AttachedFace can not be null!");
		this.face = face;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+face.ordinal();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(FACE, getAttachedFace().name().toLowerCase());
	}
	
}
