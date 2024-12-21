package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Bell;
import de.atlasmc.block.data.type.Bell.Attachment;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class AttachementProperty extends AbstractEnumProperty<Attachment> {

	public AttachementProperty() {
		super("attachement");
	}

	@Override
	public Attachment fromNBT(NBTReader reader) throws IOException {
		return Attachment.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Attachment value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
	}

	@Override
	public void set(BlockData data, Attachment value) {
		if (data instanceof Bell bell)
			bell.setAttachment(value);
	}

	@Override
	public Attachment get(BlockData data) {
		if (data instanceof Bell bell)
			return bell.getAttachment();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Bell;
	}

}
