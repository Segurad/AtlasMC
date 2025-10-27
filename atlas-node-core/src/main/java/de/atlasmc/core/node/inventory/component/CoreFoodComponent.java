package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.FoodComponent;

public class CoreFoodComponent extends AbstractItemComponent implements FoodComponent  {
	
	private int nutrition;
	private float saturation;
	private boolean canAlwaysEat;
	
	public CoreFoodComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreFoodComponent clone() {
		return (CoreFoodComponent) super.clone();
	}

	@Override
	public int getNutrition() {
		return nutrition;
	}

	@Override
	public void setNutrition(int nutrition) {
		this.nutrition = nutrition;
	}

	@Override
	public float getSaturation() {
		return saturation;
	}

	@Override
	public void setSaturation(float saturation) {
		this.saturation = saturation;
	}

	@Override
	public boolean isAlwaysEatable() {
		return canAlwaysEat;
	}

	@Override
	public void setAlwaysEatable(boolean eatable) {
		this.canAlwaysEat = eatable;
	}

}
