package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.block.BlockPredicate;

public interface AbstractBlockPredicateComponent extends ItemComponent {
	
	List<BlockPredicate> getPredicates();
	
	boolean hasPredicates();
	
	void addPredicate(BlockPredicate predicate);
	
	void removePredicate(BlockPredicate predicate);
	
	AbstractBlockPredicateComponent clone();

}
