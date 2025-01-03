package de.atlascore.potion;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.factory.FactoryException;

public class CoreClassPotionEffectType extends PotionEffectType {

	private Constructor<? extends PotionEffect> effectConstructor;
	
	public CoreClassPotionEffectType(NamespacedKey key, int id, int color, Class<? extends PotionEffect> effectClass) {
		super(key, id, color);
		if (effectClass == null)
			throw new IllegalArgumentException("EffectClass can not be null!");
		try {
			this.effectConstructor = effectClass.getConstructor(PotionEffectType.class, int.class, int.class, boolean.class, boolean.class, boolean.class, UUID.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("Unable to find constructor for potion effect: " + effectClass.getName());
		}
	}

	@Override
	public PotionEffect createEffect(int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		try {
			return effectConstructor.newInstance(this, amplifier, duration, reducedAmbient, particles, icon, uuid);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new FactoryException("Error while creating potion effect!", e);
		}
	}

}
