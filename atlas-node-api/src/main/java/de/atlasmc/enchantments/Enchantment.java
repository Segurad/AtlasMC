package de.atlasmc.enchantments;

import de.atlasmc.NamespacedKey.Namespaced;

public abstract class Enchantment implements Namespaced {

	public static Enchantment LUCK;

	public abstract boolean conflictsWith(Enchantment echantment);

	public String getNamespacedName() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Enchantment getEnchantment(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static Enchantment getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
