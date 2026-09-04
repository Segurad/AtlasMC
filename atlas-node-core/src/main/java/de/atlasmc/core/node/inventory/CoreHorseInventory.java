package de.atlasmc.core.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.inventory.HorseInventory;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.ItemType;

public class CoreHorseInventory extends CoreAbstractHorseInventory implements HorseInventory {

	public CoreHorseInventory(Chat title, InventoryHolder holder) {
		super(getInvsize(holder), InventoryType.HORSE, title, holder);
	}
	
	private static int getInvsize(InventoryHolder holder) {
		if (!(holder instanceof LivingEntity ent))
			return 2;
		var equip = ent.getEquipment();
		var body = equip.getBodyUnsafe();
		return body != null && body.getType() == ItemType.CHEST.get() ? 17 : 2;
	}
	
	@Override
	public void setArmor(ItemStack armor) {
		setItem(1, armor);
	}

	@Override
	public ItemStack getArmor() {
		return getItem(1);
	}

}
