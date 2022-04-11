package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Llama;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.LlamaInventory;

public class CoreLlamaInventory extends CoreAbstractHorseInventory implements LlamaInventory {

	public CoreLlamaInventory(Chat title, InventoryHolder holder) {
		super(((holder instanceof Llama) ? 2 + ((Llama) holder).getStrength() * 3 : 2), InventoryType.LLAMA, title, holder);
	}

	@Override
	public ItemStack getDecor() {
		return getItem(1);
	}

	@Override
	public void setDecor(ItemStack decor) {
		setItem(1, decor);
	}

}
