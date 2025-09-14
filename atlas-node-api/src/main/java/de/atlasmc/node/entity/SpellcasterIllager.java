package de.atlasmc.node.entity;

import java.util.List;

import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface SpellcasterIllager extends AbstractIllager {
	
	public static final NBTSerializationHandler<SpellcasterIllager>
	NBT_HANDLER = NBTSerializationHandler
					.builder(SpellcasterIllager.class)
					.include(AbstractIllager.NBT_HANDLER)
					.intField("SpellTicks", SpellcasterIllager::getSpellcastTime, SpellcasterIllager::setSpellcastTime, 0)
					.enumStringField("Spell", SpellcasterIllager::getSpell, SpellcasterIllager::setSpell, Spell::getByName, Spell.NONE) // non standard
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
	
	public static enum Spell implements AtlasEnum {
		NONE,
		SUMMON_VEX,
		ATTACK,
		WOLOLO,
		DISAPEAR,
		BLINDNESS;
		
		private static List<Spell> VALUES;
		
		private final String name;
		
		private Spell() {
			this.name = name().toLowerCase();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Spell getByID(int id) {
			return getValues().get(id);
		}
		
		public static Spell getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			final List<Spell> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Spell value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Spell> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
