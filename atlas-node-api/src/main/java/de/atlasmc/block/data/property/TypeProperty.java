package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Chest;
import de.atlasmc.block.data.type.Slab;
import de.atlasmc.block.data.type.TechnicalPiston;

class TypeProperty extends AbstractMultiEnumProperty {
	
	public TypeProperty() {
		super("type",
				de.atlasmc.block.data.type.Chest.Type.class, 
				de.atlasmc.block.data.type.Slab.Type.class, 
				de.atlasmc.block.data.type.TechnicalPiston.Type.class);
	}

	@Override
	public void set(BlockData data, Enum<?> value) {
		if (data instanceof Chest chest) {
			chest.setChestType((de.atlasmc.block.data.type.Chest.Type) value);
		} else if (data instanceof Slab slab) {
			slab.setSlabType((de.atlasmc.block.data.type.Slab.Type) value);
		} else if (data instanceof TechnicalPiston piston) {
			piston.setPistonType((de.atlasmc.block.data.type.TechnicalPiston.Type) value);
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
