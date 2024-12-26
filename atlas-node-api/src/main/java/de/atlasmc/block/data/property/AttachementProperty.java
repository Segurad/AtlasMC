package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Bell;
import de.atlasmc.block.data.type.Bell.Attachment;

class AttachementProperty extends AbstractEnumProperty<Attachment> {

	public AttachementProperty() {
		super("attachement", Attachment.class);
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
