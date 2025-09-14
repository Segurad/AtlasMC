package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Chest;
import de.atlasmc.node.block.data.type.Slab;
import de.atlasmc.node.block.data.type.TechnicalPiston;

class TypeProperty extends AbstractMultiEnumProperty {
	
	public TypeProperty() {
		super("type", Chest.Type.class, Slab.Type.class, TechnicalPiston.Type.class);
	}

	@Override
	public void set(BlockData data, Enum<?> value) {
		if (data instanceof Chest chest) {
			chest.setChestType((Chest.Type) value);
		} else if (data instanceof Slab slab) {
			slab.setSlabType((Slab.Type) value);
		} else if (data instanceof TechnicalPiston piston) {
			piston.setPistonType((TechnicalPiston.Type) value);
		}
	}

	@Override
	public Enum<?> get(BlockData data) {
		if (data instanceof Chest chest) {
			return chest.getChestType();
		} else if (data instanceof Slab slab) {
			return slab.getSlabType();
		} else if (data instanceof TechnicalPiston piston) {
			return piston.getPistonType();
		}
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Chest || data instanceof Slab || data instanceof TechnicalPiston;
	}

}
