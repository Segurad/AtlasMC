package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.RarityComponent;

public class CoreRarityComponent extends AbstractItemComponent implements RarityComponent {

	private Rarity rarity;
	
	public CoreRarityComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreRarityComponent clone() {
		return (CoreRarityComponent) super.clone();
	}

	@Override
	public Rarity getRarity() {
		return rarity;
	}

	@Override
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

}
