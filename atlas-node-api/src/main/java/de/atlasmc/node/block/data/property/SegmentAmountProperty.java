package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.LeafLitter;

public class SegmentAmountProperty extends AbstractIntProperty {

	public SegmentAmountProperty() {
		super("segment_amount");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof LeafLitter segmentable)
			segmentable.setSegmentAmount(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof LeafLitter segmentable)
			return segmentable.getSegmentAmount();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof LeafLitter;
	}

}
