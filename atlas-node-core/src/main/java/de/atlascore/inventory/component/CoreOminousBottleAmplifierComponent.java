package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.OminousBottleAmplifierComponent;

public class CoreOminousBottleAmplifierComponent extends AbstractItemComponent implements OminousBottleAmplifierComponent {

	private int amplifier;
	
	public CoreOminousBottleAmplifierComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreOminousBottleAmplifierComponent clone() {
		return (CoreOminousBottleAmplifierComponent) super.clone();
	}

	@Override
	public int getAmplifier() {
		return amplifier;
	}

	@Override
	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}

}
