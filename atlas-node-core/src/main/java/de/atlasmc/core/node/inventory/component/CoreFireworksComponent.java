package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.FireworkExplosion;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.FireworksComponent;

public class CoreFireworksComponent extends AbstractItemComponent implements FireworksComponent {
	
	private List<FireworkExplosion> explosions; 
	private int flightDuration;
	
	public CoreFireworksComponent(ComponentType type) {
		super(type);
		flightDuration = 1;
	}
	
	@Override
	public CoreFireworksComponent clone() {
		CoreFireworksComponent clone = (CoreFireworksComponent) super.clone();
		if (explosions != null) {
			clone.explosions = new ArrayList<>();
			final int size = explosions.size();
			for (int i = 0; i < size; i++) {
				FireworkExplosion explosion = explosions.get(i);
				clone.explosions.add(explosion.clone());
			}
		}
		return clone;
	}

	@Override
	public List<FireworkExplosion> getExplosions() {
		if (explosions == null)
			explosions = new ArrayList<>();
		return explosions;
	}

	@Override
	public boolean hasExplosions() {
		return explosions != null && !explosions.isEmpty();
	}

	@Override
	public void addExplosion(FireworkExplosion explosion) {
		getExplosions().add(explosion);
	}

	@Override
	public void removeExplosions(FireworkExplosion explosion) {
		if (explosions != null)
			explosions.remove(explosion);
	}

	@Override
	public int getFlightDuration() {
		return flightDuration;
	}

	@Override
	public void setFlightDuration(int duration) {
		this.flightDuration = duration;
	}

}
