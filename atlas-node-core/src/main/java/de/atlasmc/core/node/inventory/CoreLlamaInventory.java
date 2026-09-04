package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.entity.component.EntityComponentTypes;
import de.atlasmc.node.entity.component.LlamaMetaContainer;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.LlamaInventory;

public class CoreLlamaInventory extends CoreAbstractHorseInventory implements LlamaInventory {

	public CoreLlamaInventory(Chat title, InventoryHolder holder) {
		super(getInvsize(holder), InventoryType.LLAMA, title, holder);
	}
	
	private static int getInvsize(InventoryHolder holder) {
		if (!(holder instanceof LivingEntity ent))
			return 2;
		LlamaMetaContainer meta = ent.getComponent(EntityComponentTypes.LLAMA_META.get());
		return meta != null ? meta.getStrength() * 3 : 2;
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
