package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface SpellcasterIllager extends AbstractIllager {
	
	public static final NBTSerializationHandler<SpellcasterIllager>
	NBT_HANDLER = NBTSerializationHandler
					.builder(SpellcasterIllager.class)
					.include(AbstractIllager.NBT_HANDLER)
					.intField("SpellTicks", SpellcasterIllager::getSpellcastTime, SpellcasterIllager::setSpellcastTime, 0)
					.enumStringField("Spell", SpellcasterIllager::getSpell, SpellcasterIllager::setSpell, Spell.class, Spell.NONE) // non standard
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
	default NBTSerializationHandler<? extends SpellcasterIllager> getNBTHandler() {
		return NBT_HANDLER;
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
