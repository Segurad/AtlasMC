package de.atlasmc.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.ItemComponent;
import de.atlasmc.inventory.component.ItemComponentHolder;
import de.atlasmc.inventory.component.predicate.ItemComponentPredicate;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import de.atlasmc.util.predicate.IntRange;

public class ItemPredicate implements NBTSerializable, ItemComponentHolder, Predicate<ItemStack> {
	
	public static final NBTSerializationHandler<ItemPredicate>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemPredicate.class)
					.dataSetField("items", ItemPredicate::getItems, ItemPredicate::setItems, ItemType.getRegistry())
					.intNullableField("count", ItemPredicate::getCount, ItemPredicate::setCount, null)
					.typeCompoundField("count", ItemPredicate::getRange, ItemPredicate::setRange, IntRange.NBT_HANDLER)
					.include(ItemComponentHolder.NBT_HANDLER)
					.compoundMapNamespacedType("predicates", ItemPredicate::hasPredicates, ItemPredicate::getPredicates, ItemComponentPredicate.NBT_HANDLER)
					.build();

	private DataSet<ItemType> items;
	private Integer count;
	private IntRange range;
	private Map<NamespacedKey, ItemComponent> components;
	private Map<NamespacedKey, ItemComponentPredicate> predicates;
	
	public DataSet<ItemType> getItems() {
		return items;
	}
	
	public void setItems(DataSet<ItemType> items) {
		this.items = items;
	}
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public IntRange getRange() {
		return range;
	}
	
	public void setRange(IntRange range) {
		this.range = range;
	}
	
	public Map<NamespacedKey, ItemComponentPredicate> getPredicates() {
		return predicates;
	}
	
	public boolean hasPredicates() {
		return predicates != null && !predicates.isEmpty();
	}
	
	@Override
	public Map<NamespacedKey, ItemComponent> getComponents() {
		if (components == null)
			components = new HashMap<>();
		return components;
	}

	@Override
	public boolean hasComponents() {
		return components != null && !components.isEmpty();
	}

	@Override
	public NBTSerializationHandler<? extends ItemPredicate> getNBTHandler() {
		return NBT_HANDLER;
	}

	@Override
	public boolean test(ItemStack t) {
		if (t == null)
			return false;
		if (items != null && !items.contains(t.getType()))
			return false;
		if (count != null && t.getAmount() != count)
			return false;
		if (range != null && range.test(t.getAmount()))
			return false;
		if (hasComponents()) {
			if (!t.hasComponents())
				return false;
			final Map<NamespacedKey, ItemComponent> comps = t.getComponents();
			if (!components.equals(comps))
				return false;
		}
		if (hasPredicates()) {
			if (!t.hasComponents())
				return false;
			final Map<NamespacedKey, ItemComponent> comps = t.getComponents();
			for (Entry<NamespacedKey, ItemComponentPredicate> entry : predicates.entrySet()) {
				final ItemComponent comp = comps.get(entry.getKey());
				if (!entry.getValue().test(comp))
					return false;
			}
		}
		return true;
	}
	
}
