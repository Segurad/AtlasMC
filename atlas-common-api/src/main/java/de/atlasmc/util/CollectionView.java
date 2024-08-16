package de.atlasmc.util;

import java.util.Collection;

public class CollectionView<E> extends AbstractCollectionView<E, Collection<E>> {

	public CollectionView(Collection<E> collection) {
		super(collection);
	}

}
