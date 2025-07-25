package de.atlascore.inventory.component.effect;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.entity.Entity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.effect.ComponentEffectType;
import de.atlasmc.inventory.component.effect.TeleportRandomly;
import io.netty.buffer.ByteBuf;

public class CoreTeleportRandomly extends CoreAbstractEffect implements TeleportRandomly {
	
	private float diameter = 16.0f;
	
	public CoreTeleportRandomly(ComponentEffectType type) {
		super(type);
	}
	
	@Override
	public void apply(Entity target, ItemStack item) {
		// TODO teleport random
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		diameter = buf.readFloat();
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		buf.writeFloat(diameter);
	}

	@Override
	public float getDiameter() {
		return diameter;
	}

	@Override
	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}
	
	@Override
	public CoreTeleportRandomly clone() {
		return (CoreTeleportRandomly) super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(diameter);
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
		CoreTeleportRandomly other = (CoreTeleportRandomly) obj;
		return Float.floatToIntBits(diameter) == Float.floatToIntBits(other.diameter);
	}

}
