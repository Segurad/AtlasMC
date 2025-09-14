package de.atlasmc.node.inventory.animation;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.sound.Sound;

abstract class AbstractAnimation implements Animation {

	protected int[] slots;
	protected ItemStack[] items;
	protected int delay; // MC Ticks
	protected Sound sound;

	public AbstractAnimation(int[] slots, ItemStack[] items) {
		this(slots, items, 2);
	}

	public AbstractAnimation(int[] slots, ItemStack[] items, int delay) {
		this(slots, items, delay, null);
	}

	public AbstractAnimation(int[] slots, ItemStack[] items, int delay, Sound sound) {
		if (slots.length > items.length)
			throw new ArrayIndexOutOfBoundsException(
					"slots " + slots.length + " > items " + items.length + " | items.length must be equal or higher!");
		this.slots = slots;
		this.items = items;
		this.delay = delay;
		this.sound = sound;
	}

	@Override
	public void setItems(ItemStack[] items) {
		if (slots.length > items.length)
			throw new ArrayIndexOutOfBoundsException(
					"slots " + slots.length + " > items " + items.length + " | items.length must be equal or higher!");
		this.items = items;
	}
	
	@Override
	public Animation clone() {
		return null;
	}
}
