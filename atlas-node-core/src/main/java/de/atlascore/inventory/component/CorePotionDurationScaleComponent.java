package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.PotionDurationScaleComponent;

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
