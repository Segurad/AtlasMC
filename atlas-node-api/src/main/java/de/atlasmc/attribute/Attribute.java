package de.atlasmc.attribute;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;

public class Attribute implements Namespaced {
	
	private static final Map<NamespacedKey, Attribute> BY_NAME;
	
	public static final Attribute
	GENERIC_MAX_HEALTH = new Attribute("generic.max_health"),
	GENERIC_FOLLOW_RANGE = new Attribute("generic.follow_range"),
	GENERIC_KOCKBACK_RESISTANCE = new Attribute("generic.knockback_resistance"),
	GENERIC_MOVEMENT_SPEED = new Attribute("generic.movement_speed"),
	GENERIC_ATTACK_DAMAGE = new Attribute("generic.attack_damage"),
	GENERIC_ARMOR = new Attribute("generic.armor"),
	GENERIC_ARMOR_TOUGHNESS = new Attribute("generic.armor_toughness"),
	GENERIC_ATTACK_KOCKBACK = new Attribute("generic.attack_knockback"),
	GENERIC_ATTACK_SPEED = new Attribute("generic.attack_speed"),
	GENERIC_LUCK = new Attribute("generic.luck"),
	HORSE_JUMP_STRENGTH = new Attribute("horse.jump_strength"),
	GENERIC_FLYING_SPEED = new Attribute("generic.flying_speed"),
	ZOMBIE_SPAWN_REINFORCEMENTS = new Attribute("zombie.spawn_reinforcements");
	
	static {
		BY_NAME = new ConcurrentHashMap<>();
	}
	
	private final NamespacedKey key;
	
	private Attribute(String key) {
		this.key = NamespacedKey.literal(key);
		BY_NAME.put(this.key, this);
	}
	
	public static Collection<Attribute> getValues() {
		return BY_NAME.values();
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public static Attribute getByName(String name) {
		return BY_NAME.get(NamespacedKey.of(name));
	}
	
	public static Attribute getByName(NamespacedKey name) {
		return BY_NAME.get(NamespacedKey.of(name));
	}

	public static Attribute getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
