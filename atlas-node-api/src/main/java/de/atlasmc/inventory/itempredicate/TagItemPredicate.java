package de.atlasmc.inventory.itempredicate;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.tag.Tag;

public class TagItemPredicate implements ItemPredicate {
	
	private List<Tag<ItemType>> tags;
	
	public TagItemPredicate() {
		tags = new ArrayList<>(5);
	}
	
	public List<Tag<ItemType>> getTags() {
		return tags;
	}
	
	public void addTag(Tag<ItemType> tag) {
		tags.add(tag);
	}
	
	public void removeTag(Tag<ItemType> tag) {
		tags.remove(tag);
	}

	@Override
	public boolean match(ItemStack item) {
		return match(item.getType());
	}

	@Override
	public boolean match(ItemType type) {
		final int size = tags.size();
		for (int i = 0; i < size; i++) {
			Tag<ItemType> tag = tags.get(i);
			if (tag.isTaged(type))
				return true;
		}
		return false;
	}

}
