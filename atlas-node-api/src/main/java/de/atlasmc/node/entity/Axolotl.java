package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Axolotl extends Fish, AgeableMob {
	
	public static final NBTCodec<Axolotl>
	NBT_HANDLER = NBTCodec
					.builder(Axolotl.class)
					.include(AgeableMob.NBT_HANDLER)
					.include(Fish.NBT_HANDLER)
					.enumIntField("Variant", Axolotl::getVariant, Axolotl::setVariant, Variant.class, Variant.LUCY)
					.build();
	
	Variant getVariant();
	
	void setVariant(Variant variant);

	@Override
	default NBTCodec<? extends Axolotl> getNBTCodec() {
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
