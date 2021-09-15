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
import de.atlasmc.inventory.AnvilInventory;
import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.inventory.BlastFurnaceInventory;
import de.atlasmc.inventory.BrewingInventory;
import de.atlasmc.inventory.CartographyInventory;
import de.atlasmc.inventory.CraftingInventory;
import de.atlasmc.inventory.EnchantingInventory;
import de.atlasmc.inventory.FurnaceInventory;
import de.atlasmc.inventory.GrindstoneInventory;
import de.atlasmc.inventory.HorseInventory;
import de.atlasmc.inventory.LecternInventory;
import de.atlasmc.inventory.LlamaInventory;
import de.atlasmc.inventory.LoomInventory;
import de.atlasmc.inventory.MerchantInventory;
import de.atlasmc.inventory.PlayerInventory;
import de.atlasmc.inventory.SmithingInventory;
import de.atlasmc.inventory.SmokerInventory;
import de.atlasmc.inventory.StonecutterInventory;
import de.atlasmc.inventory.WorkbenchInventory;

public class ContainerFactoryLoader {
	
	public static void loadContainerFactories() {
		ContainerFactory.GENERIC_INV_FACTORY = new CoreGenericInventoryFactory();
		ContainerFactory.BEACON_INV_FACTORY = new ClassContainerFactory<BeaconInventory>(CoreBeaconInventory.class);
		ContainerFactory.PLAYER_INV_FACTORY = new ClassContainerFactory<PlayerInventory>(CorePlayerInventory.class);
		ContainerFactory.FURNACE_INV_FACTPRY = new ClassContainerFactory<FurnaceInventory>(CoreFurnaceInventory.class);
		ContainerFactory.BREWING_INV_FACTORY = new ClassContainerFactory<BrewingInventory>(CoreBrewingStandInventory.class);
		ContainerFactory.SMITHING_INV_FACTORY = new ClassContainerFactory<SmithingInventory>(CoreSmithingInventory.class);
		ContainerFactory.CRAFTING_INV_FACTORY = new ClassContainerFactory<CraftingInventory>(CoreCraftingInventory.class);
		ContainerFactory.ANVIL_INV_FACTORY = new ClassContainerFactory<AnvilInventory>(CoreAnvilInventory.class);
		ContainerFactory.BLAST_FURNACE_INV_FACTORY = new ClassContainerFactory<BlastFurnaceInventory>(CoreBlastFurnaceInventory.class);
		ContainerFactory.CARTOGRAPHY_INV_FACTORY = new ClassContainerFactory<CartographyInventory>(CoreCartographyInventory.class);
		ContainerFactory.ENCHANTING_INV_FACTORY = new ClassContainerFactory<EnchantingInventory>(CoreEnchantingInventory.class);
		ContainerFactory.GRINDSTONE_INV_FACTORY = new ClassContainerFactory<GrindstoneInventory>(CoreGrindstoneInventory.class);
		ContainerFactory.HORSE_INV_FACTORY = new ClassContainerFactory<HorseInventory>(CoreHorseInventory.class);
		ContainerFactory.LECTERN_INV_FACTORY = new ClassContainerFactory<LecternInventory>(CoreLecternInventory.class);
		ContainerFactory.LLAMA_INV_FACTORY = new ClassContainerFactory<LlamaInventory>(CoreLlamaInventory.class);
		ContainerFactory.MERCHANT_INV_FACTORY = new ClassContainerFactory<MerchantInventory>(CoreMerchantInventory.class);
		ContainerFactory.SMOKER_INV_FACTORY = new ClassContainerFactory<SmokerInventory>(CoreSmokerInventory.class);
		ContainerFactory.STONECUTTER_INV_FACTORY = new ClassContainerFactory<StonecutterInventory>(CoreStonecutterInventory.class);
		ContainerFactory.WORKBENCH_INV_FACTORY = new ClassContainerFactory<WorkbenchInventory>(CoreWorkbenchInventory.class);
		ContainerFactory.LOOM_INV_FACTORY = new ClassContainerFactory<LoomInventory>(CoreLoomInventory.class);
	}

}
