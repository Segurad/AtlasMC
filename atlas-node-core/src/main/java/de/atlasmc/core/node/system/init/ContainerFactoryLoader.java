package de.atlasmc.core.node.system.init;

import de.atlasmc.core.node.inventory.CoreAnvilInventory;
import de.atlasmc.core.node.inventory.CoreBeaconInventory;
import de.atlasmc.core.node.inventory.CoreBlastFurnaceInventory;
import de.atlasmc.core.node.inventory.CoreBrewingStandInventory;
import de.atlasmc.core.node.inventory.CoreCartographyInventory;
import de.atlasmc.core.node.inventory.CoreCraftingInventory;
import de.atlasmc.core.node.inventory.CoreEnchantingInventory;
import de.atlasmc.core.node.inventory.CoreFurnaceInventory;
import de.atlasmc.core.node.inventory.CoreGenericInventoryFactory;
import de.atlasmc.core.node.inventory.CoreGrindstoneInventory;
import de.atlasmc.core.node.inventory.CoreHorseInventory;
import de.atlasmc.core.node.inventory.CoreLecternInventory;
import de.atlasmc.core.node.inventory.CoreLlamaInventory;
import de.atlasmc.core.node.inventory.CoreLoomInventory;
import de.atlasmc.core.node.inventory.CoreMerchantInventory;
import de.atlasmc.core.node.inventory.CorePlayerInventory;
import de.atlasmc.core.node.inventory.CoreSmithingInventory;
import de.atlasmc.core.node.inventory.CoreSmokerInventory;
import de.atlasmc.core.node.inventory.CoreStonecutterInventory;
import de.atlasmc.core.node.inventory.CoreWorkbenchInventory;
import de.atlasmc.node.inventory.ClassContainerFactory;
import de.atlasmc.node.inventory.ContainerFactory;

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
