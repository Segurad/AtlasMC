package de.atlasmc.core.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ContainerLootComponent;

public class CoreContainerLootComponent extends AbstractItemComponent implements ContainerLootComponent {
	
	private NamespacedKey lootTable;
	private long seed;
	
	public CoreContainerLootComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreContainerLootComponent clone() {
		return (CoreContainerLootComponent) super.clone();
	}

	@Override
	public NamespacedKey getLootTable() {
		return lootTable;
	}

	@Override
	public void setLootTable(NamespacedKey key) {
		this.lootTable = key;
	}

	@Override
	public long getLootTableSeed() {
		return seed;
	}

	@Override
	public void setLootTableSeed(long seed) {
		this.seed = seed;
	}

}
