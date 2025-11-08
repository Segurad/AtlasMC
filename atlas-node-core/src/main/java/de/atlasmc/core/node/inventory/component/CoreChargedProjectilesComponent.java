package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ChargedProjectilesComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreChargedProjectilesComponent extends AbstractItemComponent implements ChargedProjectilesComponent {
	
	private List<ItemStack> projectiles;
	
	public CoreChargedProjectilesComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreChargedProjectilesComponent clone() {
		return (CoreChargedProjectilesComponent) super.clone();
	}

	@Override
	public List<ItemStack> getProjectiles() {
		if (projectiles == null)
			projectiles = new ArrayList<>();
		return projectiles;
	}

	@Override
	public boolean hasProjectiles() {
		return projectiles != null && !projectiles.isEmpty();
	}

	@Override
	public void addProjectile(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getProjectiles().add(item);
	}

	@Override
	public void removeProjectile(ItemStack item) {
		if (projectiles == null)
			return;
		projectiles.remove(item);
	}

}
