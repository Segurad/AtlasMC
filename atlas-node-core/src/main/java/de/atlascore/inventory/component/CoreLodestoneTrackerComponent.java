package de.atlascore.inventory.component;

import org.joml.Vector3i;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.LodestoneTrackerComponent;

public class CoreLodestoneTrackerComponent extends AbstractItemComponent implements LodestoneTrackerComponent {

	private Vector3i target;
	private NamespacedKey dimension;
	private boolean tracked = true;
	
	public CoreLodestoneTrackerComponent(ComponentType type) {
		super(type);
	}

	@Override
	public boolean hasTarget() {
		return target != null;
	}

	@Override
	public Vector3i getLocation() {
		return target;
	}

	@Override
	public void setLocation(Vector3i location) {
		this.target = location;
	}

	@Override
	public NamespacedKey getDimension() {
		return dimension;
	}

	@Override
	public void setDimension(NamespacedKey dimension) {
		this.dimension = dimension;
	}

	@Override
	public boolean isTracked() {
		return tracked;
	}

	@Override
	public void setTracked(boolean tracked) {
		this.tracked = tracked;
	}
	
	@Override
	public CoreLodestoneTrackerComponent clone() {
		return (CoreLodestoneTrackerComponent) super.clone();
	}

}
