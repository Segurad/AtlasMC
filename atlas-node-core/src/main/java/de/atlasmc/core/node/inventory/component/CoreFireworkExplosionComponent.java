package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.FireworkExplosion;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.FireworkExplosionComponent;

public class CoreFireworkExplosionComponent extends AbstractItemComponent implements FireworkExplosionComponent {

	private FireworkExplosion explosion;
	
	public CoreFireworkExplosionComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreFireworkExplosionComponent clone() {
		CoreFireworkExplosionComponent clone = (CoreFireworkExplosionComponent) super.clone();
		if (explosion != null)
			clone.explosion = explosion.clone();
		return clone;
	}

	@Override
	public FireworkExplosion getExplosion() {
		return explosion;
	}

	@Override
	public void setExplosion(FireworkExplosion explosion) {
		this.explosion = explosion;
	}

}
