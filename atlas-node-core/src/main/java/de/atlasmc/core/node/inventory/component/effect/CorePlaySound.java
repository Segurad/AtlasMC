package de.atlasmc.core.node.inventory.component.effect;

import java.util.Objects;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.effect.ComponentEffectType;
import de.atlasmc.node.inventory.component.effect.PlaySound;
import de.atlasmc.node.sound.Sound;

public class CorePlaySound extends CoreAbstractEffect implements PlaySound {

	private Sound sound;
	
	public CorePlaySound(ComponentEffectType type) {
		super(type);
	}
	
	@Override
	public void apply(Entity target, ItemStack item) {
		target.causeSound(sound);
	}

	@Override
	public Sound getSound() {
		return sound;
	}

	@Override
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	@Override
	public CorePlaySound clone() {
		return (CorePlaySound) super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(sound);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorePlaySound other = (CorePlaySound) obj;
		return Objects.equals(sound, other.sound);
	}

}
