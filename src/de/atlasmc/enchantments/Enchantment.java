package de.atlasmc.enchantments;

public abstract class Enchantment {

	public static Enchantment LUCK;

	public abstract boolean conflictsWith(Enchantment echantment);

}
