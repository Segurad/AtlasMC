package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.entity.Llama;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.LlamaInventory;

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
