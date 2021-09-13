package de.atlascore.system.init;

import de.atlascore.factory.CoreGenericInventoryFactory;
import de.atlascore.inventory.CoreBeaconInventory;
import de.atlascore.inventory.CoreBrewingStandInventory;
import de.atlascore.inventory.CoreFurnaceInventory;
import de.atlascore.inventory.CorePlayerInventory;
import de.atlascore.inventory.CoreSmithingInventory;
import de.atlasmc.factory.ClassContainerFactory;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.BrewingStandInventory;
import de.atlasmc.inventory.FurnaceInventory;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.inventory.SmithingInventory;

public class ContainerFactoryLoader {
	
	public static void loadContainerFactories() {
		ContainerFactory.GENERIC_INV_FACTORY = new CoreGenericInventoryFactory();
		ContainerFactory.BEACON_INV_FACTORY = new ClassContainerFactory<BeaconInventory>(CoreBeaconInventory.class);
		ContainerFactory.PLAYER_INV_FACTORY = new ClassContainerFactory<PlayerInventory>(CorePlayerInventory.class);
		ContainerFactory.FURNACE_INV_FACTPRY = new ClassContainerFactory<FurnaceInventory>(CoreFurnaceInventory.class);
		ContainerFactory.BREWING_STAND_INV_FACTORY = new ClassContainerFactory<BrewingStandInventory>(CoreBrewingStandInventory.class);
		ContainerFactory.SMITHING_INV_FACTORY = new ClassContainerFactory<SmithingInventory>(CoreSmithingInventory.class);
	}

}
