package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.PotionDurationScaleComponent;

public class CorePotionDurationScaleComponent extends AbstractItemComponent implements PotionDurationScaleComponent {

	private float scale;
	
	public CorePotionDurationScaleComponent(ComponentType type) {
		super(type);
	}

	@Override
	public float getScale() {
		return scale;
	}

	@Override
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override
	public CorePotionDurationScaleComponent clone() {
		return (CorePotionDurationScaleComponent) super.clone();
	}

}
