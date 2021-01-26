package de.atlasmc.entity;

public interface SpellcasterIllager extends AbstractIllager {
	
	public Spell getSpell();
	
	public static enum Spell {
		NONE,
		SUMMON_VEX,
		ATTACK,
		WOLOLO,
		DISAPEAR,
		BLINDNESS
	}

}
