package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Jukebox;

class HasRecordProperty extends AbstractBooleanProperty {

	public HasRecordProperty() {
		super("has_record");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Jukebox jukebox)
			jukebox.setRecord(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Jukebox jukebox)
			return jukebox.hasRecord();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Jukebox;
	}

}
