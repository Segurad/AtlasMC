package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Comparator;
import de.atlasmc.node.block.data.type.StructureBlock;

class ModeProperty extends AbstractMultiEnumProperty {

	public ModeProperty() {
		super("mode", Comparator.Mode.class, StructureBlock.Mode.class);
	}

	@Override
	public void set(BlockData data, Enum<?> value) {
		if (data instanceof Comparator  comp) {
			comp.setMode((Comparator.Mode) value);
		} else if (data instanceof StructureBlock struc) {
			struc.setMode((StructureBlock.Mode) value);
		}
	}

	@Override
	public Enum<?> get(BlockData data) {
		if (data instanceof Comparator  comp) {
			return comp.getMode();
		} else if (data instanceof StructureBlock struc) {
			return struc.getMode();
		}
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Comparator || data instanceof StructureBlock;
	}

}
