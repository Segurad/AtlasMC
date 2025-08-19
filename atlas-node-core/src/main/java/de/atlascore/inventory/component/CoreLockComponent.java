package de.atlascore.inventory.component;

import de.atlasmc.inventory.ItemPredicate;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.LockComponent;

public class CoreLockComponent extends AbstractItemComponent implements LockComponent {

	private ItemPredicate predicate;
	
	public CoreLockComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreLockComponent clone() {
		return (CoreLockComponent) super.clone();
	}

	@Override
	public ItemPredicate getPredicate() {
		return predicate;
	}

	@Override
	public void setPredicate(ItemPredicate predicate) {
		this.predicate = predicate;
	}

}
