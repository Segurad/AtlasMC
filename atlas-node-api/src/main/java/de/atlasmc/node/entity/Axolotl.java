package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Axolotl extends Fish, AgeableMob {
	
	public static final NBTSerializationHandler<Axolotl>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Axolotl.class)
					.include(AgeableMob.NBT_HANDLER)
					.include(Fish.NBT_HANDLER)
					.enumIntField("Variant", Axolotl::getVariant, Axolotl::setVariant, Variant.class, Variant.LUCY)
					.build();
	
	Variant getVariant();
	
	void setVariant(Variant variant);

	@Override
	default NBTSerializationHandler<? extends Axolotl> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Variant implements IDHolder {
		
		LUCY,
		WILD,
		GOLD,
		CYAN,
		BLUE;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
