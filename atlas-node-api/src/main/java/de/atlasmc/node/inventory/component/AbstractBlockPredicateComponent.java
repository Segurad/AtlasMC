package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.node.block.BlockPredicate;

public interface AbstractBlockPredicateComponent extends ItemComponent {
	
	public static final StreamCodec<AbstractBlockPredicateComponent>
	STREAM_CODEC = StreamCodec
					.builder(AbstractBlockPredicateComponent.class)
					.listCodec(AbstractBlockPredicateComponent::hasPredicates, AbstractBlockPredicateComponent::getPredicates, BlockPredicate.STREAM_CODEC)
					.build();
	
	List<BlockPredicate> getPredicates();
	
	boolean hasPredicates();
	
	void addPredicate(BlockPredicate predicate);
	
	void removePredicate(BlockPredicate predicate);
	
	AbstractBlockPredicateComponent clone();
	
	@Override
	default StreamCodec<? extends AbstractBlockPredicateComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
