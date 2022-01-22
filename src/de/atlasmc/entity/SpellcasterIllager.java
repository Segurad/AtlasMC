package de.atlasmc.entity;

import java.util.List;

public interface SpellcasterIllager extends AbstractIllager {
	
	public Spell getSpell();
	
	public void setSpell(Spell spell);
	
	public static enum Spell {
		NONE,
		SUMMON_VEX,
		ATTACK,
		WOLOLO,
		DISAPEAR,
		BLINDNESS;
		
		private static List<Spell> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Spell getByID(int id) {
			return getValues().get(id);
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
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
