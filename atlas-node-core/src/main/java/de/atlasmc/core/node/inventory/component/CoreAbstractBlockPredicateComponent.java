package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;
import de.atlasmc.node.block.BlockPredicate;
import de.atlasmc.node.inventory.component.AbstractBlockPredicateComponent;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreAbstractBlockPredicateComponent extends AbstractItemComponent implements AbstractBlockPredicateComponent {
	
	private List<BlockPredicate> predicates;

	public CoreAbstractBlockPredicateComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreAbstractBlockPredicateComponent clone() {
		var clone = (CoreAbstractBlockPredicateComponent) super.clone();
		if (predicates != null && !predicates.isEmpty()) {
			clone.predicates = new ArrayList<>(predicates);
		}
		return clone;
	}

	@Override
	public List<BlockPredicate> getPredicates() {
		if (predicates == null)
			predicates = new ArrayList<>();
		return null;
	}

	@Override
	public boolean hasPredicates() {
		return predicates != null && !predicates.isEmpty();
	}

	@Override
	public void addPredicate(BlockPredicate predicate) {
		getPredicates().add(predicate);
	}

	@Override
	public void removePredicate(BlockPredicate predicate) {
		if (predicates == null)
			return;
		predicates.remove(predicate);
	}

}
