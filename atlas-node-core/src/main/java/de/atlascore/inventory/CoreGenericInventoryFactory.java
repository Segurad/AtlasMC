package de.atlascore.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.inventory.InventoryType;

public class CoreGenericInventoryFactory extends ContainerFactory<Inventory> {

	@Override
	public Inventory create(InventoryType type, Chat title, InventoryHolder holder) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		int size = 0;
		switch (type) {
		case GENERIC_9X3:
		case BARREL:
		case CHEST:
		case SHULKER_BOX:
		case ENDER_CHEST:
			size = 27;
			break;
		case GENERIC_9X6:
		case DOUBLE_CHEST:
			size = 54;
			break;
		case GENERIC_3X3:
		case DISPENSER:
		case DROPPER:
		case GENERIC_9X1:
			size = 9;
			break;
		case GENERIC_9X2:
			size = 18;
			break;
		case GENERIC_9X4:
			size = 36;
			break;
		case GENERIC_9X5:
			size = 45;
			break;
		case HOPPER:
			size = 5;
		default:
			throw new IllegalArgumentException("Invalid InventoryType: " + type.name());
		}
		return new CoreInventory(size, type, title, holder);
	}

}
