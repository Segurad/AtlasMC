package de.atlascore.system.init;

import de.atlascore.factory.CoreGenericInventoryFactory;
import de.atlascore.inventory.CoreAnvilInventory;
import de.atlascore.inventory.CoreBeaconInventory;
import de.atlascore.inventory.CoreBlastFurnaceInventory;
import de.atlascore.inventory.CoreBrewingStandInventory;
import de.atlascore.inventory.CoreCartographyInventory;
import de.atlascore.inventory.CoreCraftingInventory;
import de.atlascore.inventory.CoreEnchantingInventory;
import de.atlascore.inventory.CoreFurnaceInventory;
import de.atlascore.inventory.CoreGrindstoneInventory;
import de.atlascore.inventory.CoreHorseInventory;
import de.atlascore.inventory.CoreLecternInventory;
import de.atlascore.inventory.CoreLlamaInventory;
import de.atlascore.inventory.CoreLoomInventory;
import de.atlascore.inventory.CoreMerchantInventory;
import de.atlascore.inventory.CorePlayerInventory;
import de.atlascore.inventory.CoreSmithingInventory;
import de.atlascore.inventory.CoreSmokerInventory;
import de.atlascore.inventory.CoreStonecutterInventory;
import de.atlascore.inventory.CoreWorkbenchInventory;
import de.atlasmc.factory.ClassContainerFactory;
import de.atlasmc.factory.ContainerFactory;

public class ContainerFactoryLoader {
	
	public static void loadContainerFactories() {
		ContainerFactory.GENERIC_INV_FACTORY = new CoreGenericInventoryFactory();
		ContainerFactory.BEACON_INV_FACTORY = new ClassContainerFactory<>(CoreBeaconInventory.class);
		ContainerFactory.PLAYER_INV_FACTORY = new ClassContainerFactory<>(CorePlayerInventory.class);
		ContainerFactory.FURNACE_INV_FACTPRY = new ClassContainerFactory<>(CoreFurnaceInventory.class);
		ContainerFactory.BREWING_INV_FACTORY = new ClassContainerFactory<>(CoreBrewingStandInventory.class);
		ContainerFactory.SMITHING_INV_FACTORY = new ClassContainerFactory<>(CoreSmithingInventory.class);
		ContainerFactory.CRAFTING_INV_FACTORY = new ClassContainerFactory<>(CoreCraftingInventory.class);
		ContainerFactory.ANVIL_INV_FACTORY = new ClassContainerFactory<>(CoreAnvilInventory.class);
		ContainerFactory.BLAST_FURNACE_INV_FACTORY = new ClassContainerFactory<>(CoreBlastFurnaceInventory.class);
		ContainerFactory.CARTOGRAPHY_INV_FACTORY = new ClassContainerFactory<>(CoreCartographyInventory.class);
		ContainerFactory.ENCHANTING_INV_FACTORY = new ClassContainerFactory<>(CoreEnchantingInventory.class);
		ContainerFactory.GRINDSTONE_INV_FACTORY = new ClassContainerFactory<>(CoreGrindstoneInventory.class);
		ContainerFactory.HORSE_INV_FACTORY = new ClassContainerFactory<>(CoreHorseInventory.class);
		ContainerFactory.LECTERN_INV_FACTORY = new ClassContainerFactory<>(CoreLecternInventory.class);
		ContainerFactory.LLAMA_INV_FACTORY = new ClassContainerFactory<>(CoreLlamaInventory.class);
		ContainerFactory.MERCHANT_INV_FACTORY = new ClassContainerFactory<>(CoreMerchantInventory.class);
		ContainerFactory.SMOKER_INV_FACTORY = new ClassContainerFactory<>(CoreSmokerInventory.class);
		ContainerFactory.STONECUTTER_INV_FACTORY = new ClassContainerFactory<>(CoreStonecutterInventory.class);
		ContainerFactory.WORKBENCH_INV_FACTORY = new ClassContainerFactory<>(CoreWorkbenchInventory.class);
		ContainerFactory.LOOM_INV_FACTORY = new ClassContainerFactory<>(CoreLoomInventory.class);
	}

}
