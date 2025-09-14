package de.atlasmc.core.node.inventory.component.effect;

import java.io.IOException;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.effect.ClearAllEffects;
import de.atlasmc.node.inventory.component.effect.ComponentEffectType;
import de.atlasmc.util.annotation.Singleton;
import io.netty.buffer.ByteBuf;

@Singleton
public class CoreClearAllEffects extends CoreAbstractEffect implements ClearAllEffects {
	
	public CoreClearAllEffects(ComponentEffectType type) {
		super(type);
	}

	@Override
	public void apply(Entity target, ItemStack item) {
		if (target instanceof LivingEntity entity)
			entity.removePotionEffects();
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		// not required
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		// not required
	}
	
	@Override
	public CoreClearAllEffects clone() {
		return this;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		return getClass() == obj.getClass();
	}

}
