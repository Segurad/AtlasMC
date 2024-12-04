package de.atlasmc.inventory.itempredicate;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.tag.Tag;

public class MaterialTagItemPredicate implements ItemPredicate {
	
	private List<Tag<Material>> tags;
	
	public MaterialTagItemPredicate() {
		tags = new ArrayList<>(5);
	}
	
	public List<Tag<Material>> getTags() {
		return tags;
	}
	
	public void addTag(Tag<Material> tag) {
		tags.add(tag);
	}
	
	public void removeTag(Tag<Material> tag) {
		tags.remove(tag);
	}

	@Override
	public boolean match(ItemStack item) {
		return match(item.getType());
	}

	@Override
	public boolean match(Material material) {
		final int size = tags.size();
		for (int i = 0; i < size; i++) {
			Tag<Material> tag = tags.get(i);
			if (tag.isTaged(material))
				return true;
		}
		return false;
	}

}
