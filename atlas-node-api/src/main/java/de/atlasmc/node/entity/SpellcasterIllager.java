package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface SpellcasterIllager extends AbstractIllager {
	
	@NotNull
	public static final NBTCodec<SpellcasterIllager>
	NBT_CODEC = NBTCodec
					.builder(SpellcasterIllager.class)
					.include(AbstractIllager.NBT_CODEC)
					.intField("SpellTicks", SpellcasterIllager::getSpellcastTime, SpellcasterIllager::setSpellcastTime, 0)
					.codec("Spell", SpellcasterIllager::getSpell, SpellcasterIllager::setSpell, EnumUtil.enumStringNBTCodec(Spell.class), Spell.NONE) // non standard
					.build();
	
	Spell getSpell();
	
	void setSpell(Spell spell);
	
	void setSpellcastTime(int time);
	
	/**
	 * Returns the time in ticks this {@link SpellcasterIllager} needs until the current spell is casted
	 * @return ticks or 0 of none
	 */
	int getSpellcastTime();
	
	@Override
	default NBTCodec<? extends SpellcasterIllager> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Spell implements IDHolder, EnumName {
		
		NONE,
		SUMMON_VEX,
		ATTACK,
		WOLOLO,
		DISAPEAR,
		BLINDNESS;
		
		private final String name;
		
		private Spell() {
			this.name = name().toLowerCase();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
