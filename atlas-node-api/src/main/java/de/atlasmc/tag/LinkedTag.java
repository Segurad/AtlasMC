package de.atlasmc.tag;

import java.util.Set;

import de.atlasmc.NamespacedKey;
import io.netty.buffer.ByteBuf;

/**
 * Links {@link TagContainer} together
 */
public class LinkedTag<T> extends Tag<T> {
	
	private Set<Tag<T>> tagcontainer;
	
	public LinkedTag(NamespacedKey type, NamespacedKey identifier) {
		super(type, identifier);
	}

	public void addTag(Tag<T> tag) {
		tagcontainer.add(tag);
	}
	
	public void removeTag(Tag<T> tag) {
		tagcontainer.remove(tag);
	}
	
	@Override
	public boolean isTaged(T element) {
		for (Tag<T> t : tagcontainer) {
			if (t.isTaged(element)) return true;
		}
		return false;
	}

	@Override
	public Set<T> getValues() {
		Set<T> s = Set.of();
		for (Tag<T> t : tagcontainer) {
			s.addAll(t.getValues());
		}
		return s;
	}
	
	public Set<Tag<T>> getTags() {
		return tagcontainer;
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
