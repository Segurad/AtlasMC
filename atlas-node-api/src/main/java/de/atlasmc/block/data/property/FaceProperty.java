package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.FaceAttachable;
import de.atlasmc.block.data.FaceAttachable.AttachedFace;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class FaceProperty extends AbstractEnumProperty<AttachedFace> {

	public FaceProperty() {
		super("face");
	}

	@Override
	public AttachedFace fromNBT(NBTReader reader) throws IOException {
		return AttachedFace.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(AttachedFace value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, AttachedFace value) {
		if (data instanceof FaceAttachable face)
			face.setAttachedFace(value);
	}

	@Override
	public AttachedFace get(BlockData data) {
		if (data instanceof FaceAttachable face)
			return face.getAttachedFace();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof FaceAttachable;
	}

}
