package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.enchantments.Enchantment;
import de.atlasmc.node.inventory.component.AbstractEnchantmentComponent;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class CoreAbstractEnchantmentComponent extends AbstractItemComponent implements AbstractEnchantmentComponent {
	
	private Object2IntMap<Enchantment> enchantments;
	
	public CoreAbstractEnchantmentComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreAbstractEnchantmentComponent clone() {
		CoreAbstractEnchantmentComponent clone = (CoreAbstractEnchantmentComponent) super.clone();
		if (enchantments != null)
			clone.enchantments = new Object2IntOpenHashMap<>(enchantments);
		return clone;
	}

	@Override
	public void addEnchant(Enchantment ench, int level) {
		if (ench == null)
			throw new IllegalArgumentException("Enchantment can not be null!");
		getStoredEnchants().put(ench, level);
	}

	@Override
	public int getEnchantLevel(Enchantment ench) {
		if (enchantments == null)
			return 0;
		return enchantments.getInt(ench);
	}

	@Override
	public Object2IntMap<Enchantment> getStoredEnchants() {
		if (enchantments == null)
			enchantments = new Object2IntOpenHashMap<>();
		return enchantments;
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench) {
		// TODO conflicts
		return false;
	}

	@Override
	public boolean hasEnchants() {
		return enchantments != null && !enchantments.isEmpty();
	}

	@Override
	public boolean hasEnchant(Enchantment ench) {
		return enchantments != null ? enchantments.containsKey(ench) : false;
	}

	@Override
	public void removeEnchant(Enchantment ench) {
		if (enchantments == null)
			return;
		enchantments.removeInt(ench);
	}

}
