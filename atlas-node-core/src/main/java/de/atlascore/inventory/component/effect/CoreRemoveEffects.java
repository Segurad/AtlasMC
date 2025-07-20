package de.atlascore.inventory.component.effect;

import static de.atlasmc.io.PacketUtil.readDataSet;
import static de.atlasmc.io.PacketUtil.writeDataSet;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.effect.ComponentEffectType;
import de.atlasmc.inventory.component.effect.RemoveEffects;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.dataset.DataSet;
import io.netty.buffer.ByteBuf;

public class CoreRemoveEffects extends CoreAbstractEffect implements RemoveEffects {

	private DataSet<PotionEffectType> effects;
	
	public CoreRemoveEffects(ComponentEffectType type) {
		super(type);
		effects = DataSet.of();
	}
	
	@Override
	public void apply(Entity target, ItemStack item) {
		if (target instanceof LivingEntity entity && hasEffects()) {
			for (PotionEffectType type : effects.values()) {
				entity.removePotionEffect(type);
			}
		}
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		effects = readDataSet(Registries.getRegistry(PotionEffectType.class), buf);
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		writeDataSet(effects, Registries.getRegistry(PotionEffectType.class), buf);
	}

	@Override
	public DataSet<PotionEffectType> getEffects() {
		return effects;
	}

	@Override
	public void setEffects(DataSet<PotionEffectType> effects) {
		if (effects == null)
			this.effects = DataSet.of();
		else
			this.effects = effects;
	}

	@Override
	public boolean hasEffects() {
		return !effects.isEmpty();
	}
	
	@Override
	public CoreRemoveEffects clone() {
		return (CoreRemoveEffects) super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(effects);
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
		CoreRemoveEffects other = (CoreRemoveEffects) obj;
		return Objects.equals(effects, other.effects);
	}

}
