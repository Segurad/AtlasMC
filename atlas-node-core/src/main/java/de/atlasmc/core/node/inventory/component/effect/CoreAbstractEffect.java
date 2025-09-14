package de.atlasmc.core.node.inventory.component.effect;

import java.util.Objects;

import de.atlasmc.node.inventory.component.effect.ComponentEffect;
import de.atlasmc.node.inventory.component.effect.ComponentEffectType;

public abstract class CoreAbstractEffect implements ComponentEffect {

	private final ComponentEffectType type;

	public CoreAbstractEffect(ComponentEffectType type) {
		this.type = type;
	}
	
	@Override
	public CoreAbstractEffect clone() {
		try {
			return (CoreAbstractEffect) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}
	
	@Override
	public ComponentEffectType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoreAbstractEffect other = (CoreAbstractEffect) obj;
		return Objects.equals(type, other.type);
	}

}
