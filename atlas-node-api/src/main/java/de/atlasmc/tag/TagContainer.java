package de.atlasmc.tag;

import java.util.HashSet;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import io.netty.buffer.ByteBuf;

/**
 * Contains all elements for this tag
 */
public class TagContainer<T> extends Tag<T> {
	
	private Set<T> taged;
	
	public TagContainer(NamespacedKey type, NamespacedKey identifier) {
		super(type, identifier);
		taged = new HashSet<>();
	}
	
	public boolean isTaged(T element) {
		return taged.contains(element);
	}
	
	public Set<T> getValues() {
		return taged;
	}

	@Override
	public void write(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTaged(int elementID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tag(T element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tag(int elementID) {
		// TODO Auto-generated method stub
		return false;
	}

}
