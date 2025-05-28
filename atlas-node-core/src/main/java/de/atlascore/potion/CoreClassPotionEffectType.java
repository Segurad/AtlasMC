package de.atlascore.potion;

import java.lang.reflect.Constructor;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.factory.ClassFactory;

public class CoreClassPotionEffectType extends PotionEffectType {

	private final Constructor<? extends PotionEffect> effectConstructor;
	
	public CoreClassPotionEffectType(NamespacedKey key, int id, int color, Class<? extends PotionEffect> effectClass) {
		super(key, id, color);
		this.effectConstructor = ClassFactory.getConstructor(effectClass, PotionEffectType.class, int.class, int.class, boolean.class, boolean.class, boolean.class, UUID.class);
	}
	
	public CoreClassPotionEffectType(ConfigurationSection cfg) {
		super(cfg);
		String rawEffectClass = cfg.getString("effectClass");
		this.effectConstructor = ClassFactory.getConstructor(rawEffectClass, PotionEffectType.class, int.class, int.class, boolean.class, boolean.class, boolean.class, UUID.class);
	}

	@Override
	public PotionEffect createEffect(int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		return ClassFactory.createInstance(effectConstructor, this, amplifier, duration, reducedAmbient, particles, icon, uuid);
	}

}
