package de.atlascore.system.init;

import static de.atlasmc.Material.*;

import de.atlascore.block.data.CoreAgeable;
import de.atlascore.block.data.CoreAnaloguePowerable;
import de.atlascore.block.data.CoreBisected;
import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlascore.block.data.CoreLevelled;
import de.atlascore.block.data.CoreLightable;
import de.atlascore.block.data.CoreMultipleFacing5;
import de.atlascore.block.data.CoreMultipleFacing6;
import de.atlascore.block.data.CoreOrientable;
import de.atlascore.block.data.CorePowerable;
import de.atlascore.block.data.CoreRail;
import de.atlascore.block.data.CoreRotatable;
import de.atlascore.block.data.CoreSnowable;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlascore.block.data.type.CoreBamboo;
import de.atlascore.block.data.type.CoreBarrel;
import de.atlascore.block.data.type.CoreBed;
import de.atlascore.block.data.type.CoreBeehive;
import de.atlascore.block.data.type.CoreBell;
import de.atlascore.block.data.type.CoreBrewingStand;
import de.atlascore.block.data.type.CoreBubbleColumn;
import de.atlascore.block.data.type.CoreCake;
import de.atlascore.block.data.type.CoreCampfire;
import de.atlascore.block.data.type.CoreChain;
import de.atlascore.block.data.type.CoreChest;
import de.atlascore.block.data.type.CoreCocoa;
import de.atlascore.block.data.type.CoreCommandBlock;
import de.atlascore.block.data.type.CoreComparator;
import de.atlascore.block.data.type.CoreCoralWallFan;
import de.atlascore.block.data.type.CoreDaylightDetector;
import de.atlascore.block.data.type.CoreDispenser;
import de.atlascore.block.data.type.CoreDoor;
import de.atlascore.block.data.type.CoreEndPortalFrame;
import de.atlascore.block.data.type.CoreEnderChest;
import de.atlascore.block.data.type.CoreFarmland;
import de.atlascore.block.data.type.CoreFence;
import de.atlascore.block.data.type.CoreFire;
import de.atlascore.block.data.type.CoreFurnace;
import de.atlascore.block.data.type.CoreGate;
import de.atlascore.block.data.type.CoreGlassPane;
import de.atlascore.block.data.type.CoreGrindstone;
import de.atlascore.block.data.type.CoreHopper;
import de.atlascore.block.data.type.CoreJigsaw;
import de.atlascore.block.data.type.CoreJukebox;
import de.atlascore.block.data.type.CoreLadder;
import de.atlascore.block.data.type.CoreLantern;
import de.atlascore.block.data.type.CoreLeaves;
import de.atlascore.block.data.type.CoreLectern;
import de.atlascore.block.data.type.CoreNoteBlock;
import de.atlascore.block.data.type.CoreObserver;
import de.atlascore.block.data.type.CorePiston;
import de.atlascore.block.data.type.CorePistonHead;
import de.atlascore.block.data.type.CoreRedstoneRail;
import de.atlascore.block.data.type.CoreRedstoneWallTorch;
import de.atlascore.block.data.type.CoreRedstoneWire;
import de.atlascore.block.data.type.CoreRepeater;
import de.atlascore.block.data.type.CoreRespawnAnchor;
import de.atlascore.block.data.type.CoreSapling;
import de.atlascore.block.data.type.CoreScaffolding;
import de.atlascore.block.data.type.CoreSeaPickle;
import de.atlascore.block.data.type.CoreSign;
import de.atlascore.block.data.type.CoreSlab;
import de.atlascore.block.data.type.CoreSnow;
import de.atlascore.block.data.type.CoreStairs;
import de.atlascore.block.data.type.CoreStructureBlock;
import de.atlascore.block.data.type.CoreSwitch;
import de.atlascore.block.data.type.CoreTNT;
import de.atlascore.block.data.type.CoreTechnicalPiston;
import de.atlascore.block.data.type.CoreTrapDoor;
import de.atlascore.block.data.type.CoreTripwire;
import de.atlascore.block.data.type.CoreTripwireHook;
import de.atlascore.block.data.type.CoreTurtleEgg;
import de.atlascore.block.data.type.CoreWall;
import de.atlascore.block.data.type.CoreWallSign;
import de.atlascore.block.tile.CoreBanner;
import de.atlascore.block.tile.CoreBeacon;
import de.atlascore.block.tile.CoreDropper;
import de.atlascore.block.tile.CoreEnchantingTable;
import de.atlascore.block.tile.CoreEndGateway;
import de.atlascore.block.tile.CoreMobSpawner;
import de.atlascore.block.tile.CoreShulkerBox;
import de.atlascore.block.tile.CoreSkull;
import de.atlascore.inventory.meta.CoreBannerMeta;
import de.atlascore.inventory.meta.CoreCompassMeta;
import de.atlascore.inventory.meta.CoreCrossbowMeta;
import de.atlascore.inventory.meta.CoreDamageableMeta;
import de.atlascore.inventory.meta.CoreEnchantmentStorageMeta;
import de.atlascore.inventory.meta.CoreFireworkEffectMeta;
import de.atlascore.inventory.meta.CoreFireworkMeta;
import de.atlascore.inventory.meta.CoreItemMeta;
import de.atlascore.inventory.meta.CoreKnowledgeBookMeta;
import de.atlascore.inventory.meta.CoreLeatherArmorMeta;
import de.atlascore.inventory.meta.CoreMapMeta;
import de.atlascore.inventory.meta.CorePotionMeta;
import de.atlascore.inventory.meta.CoreSkullMeta;
import de.atlascore.inventory.meta.CoreSuspiciousStewMeta;
import de.atlascore.inventory.meta.CoreTileEntityMeta;
import de.atlascore.inventory.meta.CoreTropicalFishBucketMeta;
import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.block.data.Ageable;
import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.block.data.Bisected;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Levelled;
import de.atlasmc.block.data.Lightable;
import de.atlasmc.block.data.MultipleFacing;
import de.atlasmc.block.data.Orientable;
import de.atlasmc.block.data.Powerable;
import de.atlasmc.block.data.Rail;
import de.atlasmc.block.data.Rotatable;
import de.atlasmc.block.data.Snowable;
import de.atlasmc.block.data.Waterlogged;
import de.atlasmc.block.data.type.Bamboo;
import de.atlasmc.block.data.type.Barrel;
import de.atlasmc.block.data.type.Bed;
import de.atlasmc.block.data.type.Beehive;
import de.atlasmc.block.data.type.Bell;
import de.atlasmc.block.data.type.BrewingStand;
import de.atlasmc.block.data.type.BubbleColumn;
import de.atlasmc.block.data.type.Cake;
import de.atlasmc.block.data.type.Campfire;
import de.atlasmc.block.data.type.Chain;
import de.atlasmc.block.data.type.Chest;
import de.atlasmc.block.data.type.Cocoa;
import de.atlasmc.block.data.type.CommandBlock;
import de.atlasmc.block.data.type.Comparator;
import de.atlasmc.block.data.type.CoralWallFan;
import de.atlasmc.block.data.type.DaylightDetectore;
import de.atlasmc.block.data.type.Dispenser;
import de.atlasmc.block.data.type.Door;
import de.atlasmc.block.data.type.EndPortalFrame;
import de.atlasmc.block.data.type.EnderChest;
import de.atlasmc.block.data.type.Farmland;
import de.atlasmc.block.data.type.Fence;
import de.atlasmc.block.data.type.Fire;
import de.atlasmc.block.data.type.Furnace;
import de.atlasmc.block.data.type.Gate;
import de.atlasmc.block.data.type.GlassPane;
import de.atlasmc.block.data.type.Grindstone;
import de.atlasmc.block.data.type.Hopper;
import de.atlasmc.block.data.type.Jigsaw;
import de.atlasmc.block.data.type.Jukebox;
import de.atlasmc.block.data.type.Ladder;
import de.atlasmc.block.data.type.Lantern;
import de.atlasmc.block.data.type.Leaves;
import de.atlasmc.block.data.type.Lectern;
import de.atlasmc.block.data.type.NoteBlock;
import de.atlasmc.block.data.type.Observer;
import de.atlasmc.block.data.type.Piston;
import de.atlasmc.block.data.type.PistonHead;
import de.atlasmc.block.data.type.RedstoneRail;
import de.atlasmc.block.data.type.RedstoneWallTorch;
import de.atlasmc.block.data.type.RedstoneWire;
import de.atlasmc.block.data.type.Repeater;
import de.atlasmc.block.data.type.RespawnAnchor;
import de.atlasmc.block.data.type.Sapling;
import de.atlasmc.block.data.type.Scaffolding;
import de.atlasmc.block.data.type.SeaPickle;
import de.atlasmc.block.data.type.Sign;
import de.atlasmc.block.data.type.Slab;
import de.atlasmc.block.data.type.Snow;
import de.atlasmc.block.data.type.Stairs;
import de.atlasmc.block.data.type.StructureBlock;
import de.atlasmc.block.data.type.Switch;
import de.atlasmc.block.data.type.TechnicalPiston;
import de.atlasmc.block.data.type.TrapDoor;
import de.atlasmc.block.data.type.Tripwire;
import de.atlasmc.block.data.type.TripwireHook;
import de.atlasmc.block.data.type.TurtleEgg;
import de.atlasmc.block.data.type.Wall;
import de.atlasmc.block.data.type.WallSign;
import de.atlasmc.block.tile.Banner;
import de.atlasmc.block.tile.Beacon;
import de.atlasmc.block.tile.Dropper;
import de.atlasmc.block.tile.EnchantingTable;
import de.atlasmc.block.tile.EndGateway;
import de.atlasmc.block.tile.MobSpawner;
import de.atlasmc.block.tile.ShulkerBox;
import de.atlasmc.block.tile.Skull;
import de.atlasmc.factory.AgeableClassMetaDataFactory;
import de.atlasmc.factory.ClassMetaDataFactory;
import de.atlasmc.factory.ClassTileEntityFactory;
import de.atlasmc.factory.MetaDataFactory;
import de.atlasmc.factory.TileEntityFactory;
import de.atlasmc.inventory.meta.BannerMeta;
import de.atlasmc.inventory.meta.CompassMeta;
import de.atlasmc.inventory.meta.CrossbowMeta;
import de.atlasmc.inventory.meta.DamageableMeta;
import de.atlasmc.inventory.meta.EnchantmentStorageMeta;
import de.atlasmc.inventory.meta.FireworkEffectMeta;
import de.atlasmc.inventory.meta.FireworkMeta;
import de.atlasmc.inventory.meta.ItemMeta;
import de.atlasmc.inventory.meta.KnowledgeBookMeta;
import de.atlasmc.inventory.meta.LeatherArmorMeta;
import de.atlasmc.inventory.meta.MapMeta;
import de.atlasmc.inventory.meta.PotionMeta;
import de.atlasmc.inventory.meta.SkullMeta;
import de.atlasmc.inventory.meta.SuspiciousStewMeta;
import de.atlasmc.inventory.meta.TileEntityMeta;
import de.atlasmc.inventory.meta.TropicalFishBucketMeta;

public class MaterialLoader {

	public static void loadMaterial() {
		
		Material.DEFAULT_MDF = new ClassMetaDataFactory(ItemMeta.class, CoreItemMeta.class, BlockData.class, CoreBlockData.class);
		
		MetaDataFactory AIR_MDF = new ClassMetaDataFactory(null, null, BlockData.class, CoreBlockData.class),
				SNOWABLE_MDF = new ClassMetaDataFactory(Snowable.class, CoreSnowable.class),
				SAPLING_MDF = new ClassMetaDataFactory(Sapling.class, CoreSapling.class),
				LEVELLED_MDF = new ClassMetaDataFactory(Levelled.class, CoreLevelled.class),
				ORIENTABLE_MDF = new ClassMetaDataFactory(Orientable.class, CoreOrientable.class),
				LEAVES_MDF = new ClassMetaDataFactory(Leaves.class, CoreLeaves.class),
				DISPENSER_MDF = new ClassMetaDataFactory(Dispenser.class, CoreDispenser.class),
				NOTE_BLOCK_MDF = new ClassMetaDataFactory(NoteBlock.class, CoreNoteBlock.class),
				BED_MDF = new ClassMetaDataFactory(Bed.class, CoreBed.class),
				REDSTONE_RAIL_MDF = new ClassMetaDataFactory(RedstoneRail.class, CoreRedstoneRail.class),
				PISTON_MDF = new ClassMetaDataFactory(Piston.class, CorePiston.class),
				BISECTED_MDF = new ClassMetaDataFactory(Bisected.class, CoreBisected.class),
				PISTON_HEAD_MDF = new ClassMetaDataFactory(null, null, PistonHead.class, CorePistonHead.class),
				TECHNICAL_PISTON_MDF = new ClassMetaDataFactory(null, null, TechnicalPiston.class, CoreTechnicalPiston.class),
				TNT_MDF = new ClassMetaDataFactory(de.atlasmc.block.data.type.TNT.class, CoreTNT.class),
				DIRECTIONAL4F_MDF = new ClassMetaDataFactory(Directional.class, CoreDirectional4Faces.class),
				DIRECTIONAL6F_MDF = new ClassMetaDataFactory(Directional.class, CoreDirectional6Faces.class),
				FIRE_MDF = new ClassMetaDataFactory(null, null, Fire.class, CoreFire.class),
				STAIRS_MDF = new ClassMetaDataFactory(Stairs.class, CoreStairs.class),
				CHEST_MDF = new ClassMetaDataFactory(TileEntityMeta.class, CoreTileEntityMeta.class, Chest.class, CoreChest.class),
				REDSTONE_WIRE_MDF = new ClassMetaDataFactory(null, null, RedstoneWire.class, CoreRedstoneWire.class),
				AGEABLE15_MDF = new AgeableClassMetaDataFactory(Ageable.class, CoreAgeable.class, 15),
				AGEABLE25_MDF = new AgeableClassMetaDataFactory(Ageable.class, CoreAgeable.class, 25),
				AGEABLE5_MDF = new AgeableClassMetaDataFactory(Ageable.class, CoreAgeable.class, 5),
				AGEABLE3_MDF = new AgeableClassMetaDataFactory(Ageable.class, CoreAgeable.class, 3),
				AGEABLE7_MDF = new AgeableClassMetaDataFactory(Ageable.class, CoreAgeable.class, 7),
				FARMLAND_MDF = new ClassMetaDataFactory(Farmland.class, CoreFarmland.class),
				FURNACE_MDF = new ClassMetaDataFactory(TileEntityMeta.class, CoreTileEntityMeta.class, Furnace.class, CoreFurnace.class),
				SIGN_MDF = new ClassMetaDataFactory(Sign.class, CoreSign.class),
				DOOR_MDF = new ClassMetaDataFactory(Door.class, CoreDoor.class),
				LADDER_MDF = new ClassMetaDataFactory(Ladder.class, CoreLadder.class),
				RAIL_MDF = new ClassMetaDataFactory(Rail.class, CoreRail.class),
				WALL_SIGN_MDF = new ClassMetaDataFactory(WallSign.class, CoreWallSign.class),
				SWITCH_MDF = new ClassMetaDataFactory(Switch.class, CoreSwitch.class),
				POWERABLE_MDF = new ClassMetaDataFactory(Powerable.class, CorePowerable.class),
				LIGHTABLE_MDF = new ClassMetaDataFactory(Lightable.class, CoreLightable.class),
				RED_WALL_TORCH_MDF = new ClassMetaDataFactory(RedstoneWallTorch.class, CoreRedstoneWallTorch.class),
				SNOW_MDF = new ClassMetaDataFactory(Snow.class, CoreSnow.class),
				JUKEBOX_MDF = new ClassMetaDataFactory(Jukebox.class, CoreJukebox.class),
				FENCE_MDF = new ClassMetaDataFactory(Fence.class, CoreFence.class),
				CAKE_MDF = new ClassMetaDataFactory(Cake.class, CoreCake.class),
				REPEATER_MDF = new ClassMetaDataFactory(Repeater.class, CoreRepeater.class),
				TRAP_DOOR_MDF = new ClassMetaDataFactory(TrapDoor.class, CoreTrapDoor.class),
				MULTIPLE_FACING6_MDF = new ClassMetaDataFactory(MultipleFacing.class, CoreMultipleFacing6.class),
				MULTIPLE_FACING5_MDF = new ClassMetaDataFactory(MultipleFacing.class, CoreMultipleFacing5.class),
				CHAIN_MDF = new ClassMetaDataFactory(Chain.class, CoreChain.class),
				GATE_MDF = new ClassMetaDataFactory(Gate.class, CoreGate.class),
				BREWING_STAND_MDF = new ClassMetaDataFactory(BrewingStand.class, CoreBrewingStand.class),
				END_PORTAL_FRAME_MDF = new ClassMetaDataFactory(EndPortalFrame.class, CoreEndPortalFrame.class),
				COCOA_MDF = new ClassMetaDataFactory(Cocoa.class, CoreCocoa.class),
				ENDER_CHEST_MDF = new ClassMetaDataFactory(EnderChest.class, CoreEnderChest.class),
				TRIPWIRE_HOOK_MDF = new ClassMetaDataFactory(TripwireHook.class, CoreTripwireHook.class),
				TRIPWIRE_MDF = new ClassMetaDataFactory(Tripwire.class, CoreTripwire.class),
				COMMAND_BLOCK_MDF = new ClassMetaDataFactory(CommandBlock.class, CoreCommandBlock.class),
				WALL_MDF = new ClassMetaDataFactory(Wall.class, CoreWall.class),
				ROTATABLE_MDF = new ClassMetaDataFactory(Rotatable.class, CoreRotatable.class),
				PLAYER_HEAD_MDF = new ClassMetaDataFactory(SkullMeta.class, CoreSkullMeta.class, Rotatable.class, CoreRotatable.class),
				BANNER_MDF = new ClassMetaDataFactory(BannerMeta.class, CoreBannerMeta.class, Rotatable.class, CoreRotatable.class),
				ANALOGUE_POWERABLE_MDF = new ClassMetaDataFactory(AnaloguePowerable.class, CoreAnaloguePowerable.class),
				COMPARATOR_MDF = new ClassMetaDataFactory(Comparator.class, CoreComparator.class),
				DAYLIGHT_DETECTORE_MDF = new ClassMetaDataFactory(DaylightDetectore.class, CoreDaylightDetector.class),
				HOPPER_MDF = new ClassMetaDataFactory(TileEntityMeta.class, CoreTileEntityMeta.class, Hopper.class, CoreHopper.class),
				GLASS_PANE_MDF = new ClassMetaDataFactory(GlassPane.class, CoreGlassPane.class),
				SLAB_MDF = new ClassMetaDataFactory(Slab.class, CoreSlab.class),
				OBSERVER_MDF = new ClassMetaDataFactory(Observer.class, CoreObserver.class),
				TURTLE_EGG_MDF = new ClassMetaDataFactory(TurtleEgg.class, CoreTurtleEgg.class),
				WATERLOGGED_MDF = new ClassMetaDataFactory(Waterlogged.class, CoreWaterlogged.class),
				CORAL_WALL_FAN_MDF = new ClassMetaDataFactory(CoralWallFan.class, CoreCoralWallFan.class),
				SEA_PICKLE_MDF = new ClassMetaDataFactory(SeaPickle.class, CoreSeaPickle.class),
				BAMBOO_MDF = new ClassMetaDataFactory(Bamboo.class, CoreBamboo.class),
				BUBBLE_COLUMN_MDF = new ClassMetaDataFactory(BubbleColumn.class, CoreBubbleColumn.class),
				SCAFFOLDING_MDF = new ClassMetaDataFactory(Scaffolding.class, CoreScaffolding.class),
				BARREL_MDF = new ClassMetaDataFactory(TileEntityMeta.class, CoreTileEntityMeta.class, Barrel.class, CoreBarrel.class),
				GRINDSTONE_MDF = new ClassMetaDataFactory(Grindstone.class, CoreGrindstone.class),
				LECTERN_MDF = new ClassMetaDataFactory(Lectern.class, CoreLectern.class),
				BELL_MDF = new ClassMetaDataFactory(Bell.class, CoreBell.class),
				LANTERN_MDF = new ClassMetaDataFactory(Lantern.class, CoreLantern.class),
				CAMPFIRE_MDF = new ClassMetaDataFactory(Campfire.class, CoreCampfire.class),
				STRUCTURE_BLOCK_MDF = new ClassMetaDataFactory(StructureBlock.class, CoreStructureBlock.class),
				JIGSAW_MDF = new ClassMetaDataFactory(Jigsaw.class, CoreJigsaw.class),
				BEEHIVE_MDF = new ClassMetaDataFactory(TileEntityMeta.class, CoreTileEntityMeta.class, Beehive.class, CoreBeehive.class),
				RESPAWN_ANCHOR_MDF = new ClassMetaDataFactory(RespawnAnchor.class, CoreRespawnAnchor.class),
				// --- Items ---
				DAMAGEABLE_MDF = new ClassMetaDataFactory(DamageableMeta.class, CoreDamageableMeta.class, null, null),
				LEATHER_ARMOR_MDF = new ClassMetaDataFactory(LeatherArmorMeta.class, CoreLeatherArmorMeta.class, null, null),
				MAP_MDF = new ClassMetaDataFactory(MapMeta.class, CoreMapMeta.class, null, null),
				POTION_MDF = new ClassMetaDataFactory(PotionMeta.class, CorePotionMeta.class, null, null),
				FIREWORK_MDF = new ClassMetaDataFactory(FireworkMeta.class, CoreFireworkMeta.class, null, null),
				FIREWORK_EFFECT_MDF = new ClassMetaDataFactory(FireworkEffectMeta.class, CoreFireworkEffectMeta.class, null, null),
				COMPASS_MDF = new ClassMetaDataFactory(CompassMeta.class, CoreCompassMeta.class, null, null),
				ENCHANTMENT_STORAGE_MDF = new ClassMetaDataFactory(EnchantmentStorageMeta.class, CoreEnchantmentStorageMeta.class, null, null),
				TROPICAL_BUCKET_MDF = new ClassMetaDataFactory(TropicalFishBucketMeta.class, CoreTropicalFishBucketMeta.class, null, null),
				KNOWLEDGE_BOOK_MDF = new ClassMetaDataFactory(KnowledgeBookMeta.class, CoreKnowledgeBookMeta.class, null, null),
				CROSSBOW_MDF = new ClassMetaDataFactory(CrossbowMeta.class, CoreCrossbowMeta.class, null, null),
				SUSPICIOUS_STEW_MDF = new ClassMetaDataFactory(SuspiciousStewMeta.class, CoreSuspiciousStewMeta.class, null, null);

		AIR = c("AIR", 0, AIR_MDF);
		VOID_AIR = new Material(NamespacedKey.MINECRAFT, "VOID_AIR", false, (short) 9669, (byte) 1, 0, 0, AIR_MDF);
		CAVE_AIR = new Material(NamespacedKey.MINECRAFT, "CAVE_AIR", false, (short) 9670, (byte) 1, 0, 0, AIR_MDF);
		BUBBLE_COLUMN = new Material(NamespacedKey.MINECRAFT, "BUBBLE_COLUMN", false, (short) 9571, (byte) 1, 0, 0, BUBBLE_COLUMN_MDF);
		STONE = c("STONE", 1);
		GRANITE = c("GRANITE", 2);
		POLISHED_GRANITE = c("POLISHED_GRANITE", 3);
		DIORITE = c("DIORITE", 4);
		POLISHED_DIORITE = c("POLISHED_DIORITE", 5);
		ANDESITE = c("ANDESITE", 6);
		POLISHED_ANDESITE = c("POLISHED_ANDESITE", 7);
		GRASS_BLOCK = c("GRASS_BLOCK", 8, SNOWABLE_MDF);
		DIRT = c("DIRT", 10);
		COARSE_DIRT = c("COARSE_DIRT", 11);
		PODZOL = c("PODZOL", 12, SNOWABLE_MDF);
		CRIMSON_NYLIUM = c("CRIMSON_NYLIUM", 14995);
		WARPED_NYLIUM = c("WARPED_NYLIUM", 14978);
		COBBLESTONE = c("COBBLESTONE", 14);
		OAK_PLANKS = c("OAK_PLANKS", 15);
		SPRUCE_PLANKS = c("SPRUCE_PLANKS", 16);
		BIRCH_PLANKS = c("BIRCH_PLANKS", 17);
		JUNGLE_PLANKS = c("JUNGLE_PLANKS", 18);
		ACACIA_PLANKS = c("ACACIA_PLANKS", 19);
		DARK_OAK_PLANKS = c("DARK_OAK_PLANKS", 20);
		CRIMSON_PLANKS = c("CRIMSON_PLANKS", 15053);
		WARPED_PLANKS = c("WARPED_PLANKS", 15054);
		OAK_SAPLING = c("OAK_SAPLING", 21, SAPLING_MDF);
		SPRUCE_SAPLING = c("SPRUCE_SAPLING", 23, SAPLING_MDF);
		BIRCH_SAPLING = c("BIRCH_SAPLING", 25, SAPLING_MDF);
		JUNGLE_SAPLING = c("JUNGLE_SAPLING", 27, SAPLING_MDF);
		ACACIA_SAPLING = c("ACACIA_SAPLING", 29, SAPLING_MDF);
		DARK_OAK_SAPLING = c("DARK_OAK_SAPLING", 31, SAPLING_MDF);
		BEDROCK = c("BEDROCK", 33);
		WATER = new Material(NamespacedKey.MINECRAFT, "WATER", false, (short) 34, (byte) 1, 0, 0, LEVELLED_MDF);
		LAVA = new Material(NamespacedKey.MINECRAFT, "LAVA", false, (short) 50, (byte) 1, 0, 0, LEVELLED_MDF);
		SAND = c("SAND", 66);
		RED_SAND = c("RED_SAND", 67);
		GRAVEL = c("GRAVEL", 68);
		GOLD_ORE = c("GOLD_ORE", 69);
		IRON_ORE = c("IRON_ORE", 70);
		COAL_ORE = c("COAL_ORE", 71);
		NETHER_GOLD_ORE = c("NETHER_GOLD_ORE", 72);
		OAK_LOG = c("OAK_LOG", 73, ORIENTABLE_MDF);
		SPRUCE_LOG = c("SPRUCE_LOG", 76, ORIENTABLE_MDF);
		BIRCH_LOG = c("BIRCH_LOG", 79, ORIENTABLE_MDF);
		JUNGLE_LOG = c("JUNGLE_LOG", 82, ORIENTABLE_MDF);
		ACACIA_LOG = c("ACACIA_LOG", 85, ORIENTABLE_MDF);
		DARK_OAK_LOG = c("DARK_OAK_LOG", 88, ORIENTABLE_MDF);
		CRIMSON_STEM = c("CRIMSON_STEM", 14983, ORIENTABLE_MDF);
		WARPED_STEM = c("WARPED_STEM", 14966, ORIENTABLE_MDF);
		STRIPPED_OAK_LOG = c("STRIPPED_OAK_LOG", 106, ORIENTABLE_MDF);
		STRIPPED_SPRUCE_LOG = c("STRIPPED_SPRUCE_LOG", 91, ORIENTABLE_MDF);
		STRIPPED_BIRCH_LOG = c("STRIPPED_BIRCH_LOG", 94, ORIENTABLE_MDF);
		STRIPPED_JUNGLE_LOG = c("STRIPPED_JUNGLE_LOG", 97, ORIENTABLE_MDF);
		STRIPPED_ACACIA_LOG = c("STRIPPED_ACACIA_LOG", 100, ORIENTABLE_MDF);
		STRIPPED_DARK_OAK_LOG = c("STRIPPED_DARK_OAK_LOG", 103, ORIENTABLE_MDF);
		STRIPPED_CRIMSON_STEM = c("STRIPPED_CRIMSON_STEM", 14986, ORIENTABLE_MDF);
		STRIPPED_WARPED_STEM = c("STRIPPED_WARPED_STEM", 14969, ORIENTABLE_MDF);
		STRIPPED_OAK_WOOD = c("STRIPPED_OAK_WOOD", 127, ORIENTABLE_MDF);
		STRIPPED_SPRUCE_WOOD = c("STRIPPED_SPRUCE_WOOD", 130, ORIENTABLE_MDF);
		STRIPPED_BIRCH_WOOD = c("STRIPPED_BIRCH_WOOD", 133, ORIENTABLE_MDF);
		STRIPPED_JUNGLE_WOOD = c("STRIPPED_JUNGLE_WOOD", 136, ORIENTABLE_MDF);
		STRIPPED_ACACIA_WOOD = c("STRIPPED_ACACIA_WOOD", 139, ORIENTABLE_MDF);
		STRIPPED_DARK_OAK_WOOD = c("STRIPPED_DARK_OAK_WOOD", 142, ORIENTABLE_MDF);
		STRIPPED_CRIMSON_HYPHAE = c("STRIPPED_CRIMSON_HYPHAE", 14993, ORIENTABLE_MDF);
		STRIPPED_WARPED_HYPHAE = c("STRIPPED_WARPED_HYPHAE", 14975, ORIENTABLE_MDF);
		OAK_WOOD = c("OAK_WOOD", 109, ORIENTABLE_MDF);
		SPRUCE_WOOD = c("SPRUCE_WOOD", 112, ORIENTABLE_MDF);
		BIRCH_WOOD = c("BIRCH_WOOD", 115, ORIENTABLE_MDF);
		JUNGLE_WOOD = c("JUNGLE_WOOD", 118, ORIENTABLE_MDF);
		ACACIA_WOOD = c("ACACIA_WOOD", 121, ORIENTABLE_MDF);
		DARK_OAK_WOOD = c("DARK_OAK_WOOD", 124, ORIENTABLE_MDF);
		CRIMSON_HYPHAE = c("CRIMSON_HYPHAE", 14989, ORIENTABLE_MDF);
		WARPED_HYPHAE = c("WARPED_HYPHAE", 14972, ORIENTABLE_MDF);
		OAK_LEAVES = c("OAK_LEAVES", 145, LEAVES_MDF);
		SPRUCE_LEAVES = c("SPRUCE_LEAVES", 159, LEAVES_MDF);
		BIRCH_LEAVES = c("BIRCH_LEAVES", 173, LEAVES_MDF);
		JUNGLE_LEAVES = c("JUNGLE_LEAVES", 187, LEAVES_MDF);
		ACACIA_LEAVES = c("ACACIA_LEAVES", 201, LEAVES_MDF);
		DARK_OAK_LEAVES = c("DARK_OAK_LEAVES", 215, LEAVES_MDF);
		SPONGE = c("SPONGE", 229);
		WET_SPONGE = c("WET_SPONGE", 230);
		GLASS = c("GLASS", 231);
		LAPIS_ORE = c("LAPIS_ORE", 232);
		LAPIS_BLOCK = c("LAPIS_BLOCK", 233);
		DISPENSER = c("DISPENSER", 234, DISPENSER_MDF);
		SANDSTONE = c("SANDSTONE", 246);
		CHISELED_SANDSTONE = c("CHISELED_SANDSTONE", 247);
		CUT_SANDSTONE = c("CUT_SANDSTONE", 248);
		NOTE_BLOCK = c("NOTE_BLOCK", 249, NOTE_BLOCK_MDF);
		POWERED_RAIL = c("POWERED_RAIL", 1305, REDSTONE_RAIL_MDF);
		DETECTOR_RAIL = c("DETECTOR_RAIL", 1317, REDSTONE_RAIL_MDF);
		STICKY_PISTON = c("STICKY_PISTON", 1329, PISTON_MDF);
		COBWEB = c("COBWEB", 1341);
		GRASS = c("GRASS", 1342);
		FERN = c("FERB", 1343);
		DEAD_BUSH = c("DEAD_BUSH", 1344);
		SEAGRASS = c("SEAGRASS", 1345);
		TALL_SEAGRASS = new Material(NamespacedKey.MINECRAFT, "TALL_SEAGRASS", false, (short) 1346, (byte) 1, 0, 0, BISECTED_MDF);
		SEA_PICKLE = c("SEA_PICKLE", 9644, SEA_PICKLE_MDF);
		PISTON = c("PISTON", 1348, PISTON_MDF);
		PISTON_HEAD = new Material(NamespacedKey.MINECRAFT, "PISTON_HEAD", false, (short) 1360, (byte) 1, 0, 0, PISTON_HEAD_MDF);
		MOVING_PISTON = new Material(NamespacedKey.MINECRAFT, "MOVING_PISTON", false, (short) 1400, (byte) 1, 0, 0, TECHNICAL_PISTON_MDF);
		WHITE_WOOL = c("WHITE_WOOL", 1384);
		ORANGE_WOOL = c("ORANGE_WOOL", 1385);
		MAGENTA_WOOL = c("MAGENTA_WOOL", 1386);
		LIGHT_BLUE_WOOL = c("LIGHT_BLUE_WOOL", 1387);
		YELLOW_WOOL = c("YELLOW_WOOL", 1388);
		LIME_WOOL = c("LIME_WOOL", 1389);
		PINK_WOOL = c("PINK_WOOL", 1390);
		GRAY_WOOL = c("GRAY_WOOL", 1391);
		LIGHT_GRAY_WOOL = c("LIGHT_GRAY", 1392);
		CYAN_WOOL = c("CYAN_WOOL", 1393);
		PURPLE_WOOL = c("PURPLE_WOOL", 1394);
		BLUE_WOOL = c("BLUE_WOOL", 1395);
		BROWN_WOOL = c("BROWN_WOOL", 1396);
		GREEN_WOOL = c("GREEN_WOOL", 1397);
		RED_WOOL = c("RED_WOOL", 1398);
		BLACK_WOOL = c("BLACK_WOOL", 1399);
		DANDELION = c("DANDELION", 1412);
		POPPY = c("POPPY", 1413);
		BLUE_ORCHID = c("BLUE_ORCHID", 1414);
		ALLIUM = c("ALLIUM", 1415);
		AZURE_BLUET = c("AZURE_BLUET", 1416);
		RED_TULIP = c("RED_TULIP", 1417);
		ORANGE_TULIP = c("ORANGE_TULIP", 1418);
		WHITE_TULIP = c("WHITE_TULIP", 1419);
		PINK_TULIP = c("PINK_TULIP", 1420);
		OXEYE_DAISY = c("OXEYE_DAISY", 1421);
		CORNFLOWER = c("CORNFLOWER", 1422);
		LILY_OF_THE_VALLEY = c("LILY_OF_THE_VALLEY", 1424);
		WITHER_ROSE = c("WITHER_ROSE", 1423);
		BROWN_MUSHROOM = c("BROWN_MUSHROOM", 1425);
		RED_MUSHROOM = c("RED_MUSHROOM", 1426);
		CRIMSON_FUNGUS = c("CRIMSON_FUNGUS", 14997);
		WARPED_FUNGUS = c("WARPED_FUNGUS", 14979);
		CRIMSON_ROOTS = c("CRIMSON_ROOTS", 15052);
		WARPED_ROOTS = c("WARPED_ROOTS", 14981);
		NETHER_SPROUTS = c("NETHER_SPROUTS", 14982);
		WEEPING_VINES = c("WEEPING_VINES", 14998, AGEABLE25_MDF);
		WEEPING_VINES_PLANT = new Material(NamespacedKey.MINECRAFT, "WEEPING_VINES_PLANT", false, (short) 15024, (byte) 1, 0, 0);
		TWISTING_VINES = c("TWISTING_VINES", 15025, AGEABLE25_MDF);
		TWISTING_VINES_PLANT = new Material(NamespacedKey.MINECRAFT, "TWISTING_VINES_PLANT", false, (short) 15051, (byte) 1, 0, 0);
		SUGAR_CANE = c("SUGAR_CANE", 3948, AGEABLE15_MDF);
		KELP = c("KELP", 9474, AGEABLE25_MDF);
		KELP_PLANT = new Material(NamespacedKey.MINECRAFT, "KELP_PLANT", false, (short) 9500, (byte) 1, 0, 0);
		BAMBOO = c("BAMBOO", 9656, BAMBOO_MDF);
		BAMBOO_SAPLING = new Material(NamespacedKey.MINECRAFT, "BAMBOO_SAPLING", false, (short) 9655, (byte) 1, 0, 0);
		GOLD_BLOCK = c("GOLD_BLOCK", 1427);
		IRON_BLOCK = c("IRON_BLOCK", 1428);
		OAK_SLAB = c("OAK_SLAB", 8304, SLAB_MDF);
		SPRUCE_SLAB = c("SPRUCE_SLAB", 8310, SLAB_MDF);
		BIRCH_SLAB = c("BIRCH_SLAB", 8316, SLAB_MDF);
		JUNGLE_SLAB = c("JUNGLE_SLAB", 8322, SLAB_MDF);
		ACACIA_SLAB = c("ACACIA_SLAB", 8328, SLAB_MDF);
		DARK_OAK_SLAB = c("DARK_OAK_SLAB", 8334, SLAB_MDF);
		CRIMSON_SLAB = c("CRIMSON_SLAB", 15055, SLAB_MDF);
		WARPED_SLAB = c("WARPED_SLAB", 15061, SLAB_MDF);
		STONE_SLAB = c("STONE_SLAB", 8340, SLAB_MDF);
		SMOOTH_STONE_SLAB = c("SMOOTH_STONE_SLAB", 8346, SLAB_MDF);
		SANDSTONE_SLAB = c("SANDSTONE_SLAB", 8352, SLAB_MDF);
		CUT_SANDSTONE_SLAB = c("CUT_STANDSTONE_SLAB", 8358, SLAB_MDF);
		PETRIFIED_OAK_SLAB = c("PETRIFIED_OAK_SLAB", 8364, SLAB_MDF);
		COBBLESTONE_SLAB = c("COBBLESTONE_SLAB", 8370, SLAB_MDF);
		BRICK_SLAB = c("BRICK_SLAB", 8376, SLAB_MDF);
		STONE_BRICK_SLAB = c("STONE_BRICK_SLAB", 8381, SLAB_MDF);
		NETHER_BRICK_SLAB = c("NETHER_BRICK_SLAB", 8388, SLAB_MDF);
		QUARTZ_SLAB = c("QUARTZ_SLAB", 8394, SLAB_MDF);
		RED_SANDSTONE_SLAB = c("RED_SANDSTONE_SLAB", 8400, SLAB_MDF);
		CUT_RED_SANDSTONE_SLAB = c("CUT_RED_SANDSTONE_SLAB", 8406, SLAB_MDF);
		PURPUR_SLAB = c("PURPUR_SLAB", 8412, SLAB_MDF);
		PRISMARINE_SLAB = c("PRISMARINE_SLAB", 7848, SLAB_MDF);
		PRISMARINE_BRICK_SLAB = c("PRISMARINE_BRICK_SLAB", 7854, SLAB_MDF);
		DARK_PRISMARINE_SLAB = c("DARK_PRISMARINE_SLAB", 7860, SLAB_MDF);
		SMOOTH_QUARTZ = c("SMOOTH_QUARTZ", 8420);
		SMOOTH_RED_SANDSTONE = c("SMOOTH_RED_SANDSTONE", 8421);
		SMOOTH_SANDSTONE = c("SMOOTH_SANDSTONE", 8419);
		SMOOTH_STONE = c("SMOOTH_STONE", 8418);
		BRICKS = c("BRICKS", 1429);
		TNT = c("TNT", 1430, TNT_MDF);
		BOOKSHELF = c("BOOKSHELF", 1432);
		MOSSY_COBBLESTONE = c("MOSSY_COBBLESTONE", 1433);
		OBSIDIAN = c("OBSIDIAN", 1433);
		TORCH = c("TORCH", 1435);
		WALL_TORCH = new Material(NamespacedKey.MINECRAFT, "WALL_TORCH", false, (short) 1436, (byte) 1, 0, 0, DIRECTIONAL4F_MDF);
		FIRE = new Material(NamespacedKey.MINECRAFT, "FIRE", false, (short) 1440, (byte) 1, 0, 0, FIRE_MDF);
		SOUL_FIRE = new Material(NamespacedKey.MINECRAFT, "SOUL_FIRE", false, (short) 1952, (byte) 1, 0, 0);
		END_ROD = c("END_ROD", 9062, DIRECTIONAL6F_MDF);
		CHORUS_PLANT = c("CHORUS_PLANT", 9068, MULTIPLE_FACING6_MDF);
		CHORUS_FLOWER = c("CHORUS_FLOWER", 9132, AGEABLE5_MDF);
		PURPUR_BLOCK = c("PURPUR_BLOCK", 9138);
		PURPUR_PILLAR = c("PURPUR_PILLAR", 9139, ORIENTABLE_MDF);
		PURPUR_STAIRS = c("PURPUR_STAIRS", 9142, STAIRS_MDF);
		SPAWNER = c("SPAWNER", 1953);
		OAK_STAIRS = c("OAK_STAIRS", 1954, STAIRS_MDF);
		CHEST = c("CHEST", 2034, CHEST_MDF);
		REDSTONE_WIRE = c("RESTONE_WIRE", 2058, REDSTONE_WIRE_MDF);
		DIAMOND_ORE = c("DIAMOND_ORE", 3354);
		DIAMOND_BLOCK = c("DIAMOND_BLOCK", 3355);
		CRAFTING_TABLE = c("CRAFTING_TABLE", 3356);
		FARMLAND = c("FARMLAND", 3365, FARMLAND_MDF);
		FURNACE = c("FURNACE", 3373, FURNACE_MDF);
		LADDER = c("LADDER", 3637, LADDER_MDF);
		RAIL = c("RAIL", 3645, RAIL_MDF);
		COBBLESTONE_STAIRS = c("COBBLESTONE_STAIRS", 3655, STAIRS_MDF);
		LEVER = c("LEVER", 3783, SWITCH_MDF);
		STONE_PRESSURE_PLATE = c("STONE_PRESSURE_PLATE", 3807, POWERABLE_MDF);
		OAK_PRESSURE_PLATE = c("OAK_PRESSURE_PLATE", 3873, POWERABLE_MDF);
		SPRUCE_PRESSURE_PLATE = c("SPRUCE_PRESSURE_PLATE", 3875, POWERABLE_MDF);
		BIRCH_PRESSURE_PLATE = c("BIRCH_PRESSURE_PLATE", 3877, POWERABLE_MDF);
		JUNGLE_PRESSURE_PLATE = c("JUNGLE_PRESSURE_PLATE", 3879, POWERABLE_MDF);
		ACACIA_PRESSURE_PLATE = c("ACACIA_PRESSURE_PLATE", 3881, POWERABLE_MDF);
		DARK_OAK_PRESSURE_PLATE = c("DARK_OAK_PRESSURE_PLATE", 3883, POWERABLE_MDF);
		CRIMSON_PRESSURE_PLATE = c("CRIMSON_PRESSURE_PLATE", 15067, POWERABLE_MDF);
		WARPED_PRESSURE_PLATE = c("WARPED_PRESSURE_PLATE", 15069, POWERABLE_MDF);
		POLISHED_BLACKSTONE_PRESSURE_PLATE = c("POLISHED_BLACKSTONE_PRESSURE_PLATE", 16760, POWERABLE_MDF);
		REDSTONE_ORE = c("REDSTONE_ORE", 3885, LIGHTABLE_MDF);
		REDSTONE_TORCH = c("REDSTONE_TORCH", 3887, LIGHTABLE_MDF);
		REDSTONE_WALL_TORCH = c("REDSTONE_WALL_TORCH", 3889, RED_WALL_TORCH_MDF);
		SNOW = c("SNOW", 3921, SNOW_MDF);
		ICE = c("ICE", 3929);
		FROSTED_ICE = new Material(NamespacedKey.MINECRAFT, "FROSTED_ICE", false, (short) 9253, (byte) 1, 0, 0, AGEABLE3_MDF);
		SNOW_BLOCK = c("SNOW_BLOCK", 3930);
		CACTUS = c("CACTUS", 3931, AGEABLE15_MDF);
		CLAY = c("CLAY", 3947);
		JUKEBOX = c("JUKEBOX", 3964, JUKEBOX_MDF);
		OAK_FENCE = c("OAK_FENCE", 3966, FENCE_MDF);
		SPRUCE_FENCE = c("SPRUCE_FENCE", 8582, FENCE_MDF);
		BIRCH_FENCE = c("BIRCH_FENCE", 8614, FENCE_MDF);
		JUNGLE_FENCE = c("JUNGLE_FENCE", 8646, FENCE_MDF);
		ACACIA_FENCE = c("ACACIA_FENCE", 8678, FENCE_MDF);
		DARK_OAK_FENCE = c("DARK_OAK_FENCE", 8710, FENCE_MDF);
		CRIMSON_FENCE = c("CRIMSON_FENCE", 15071, FENCE_MDF);
		WARPED_FENCE = c("WARPED_FENCE", 15103, FENCE_MDF);
		PUMPKIN = c("PUMPKIN", 3998);
		CARVED_PUMPKIN = c("CARVED_PUMPKIN", 4016, DIRECTIONAL4F_MDF);
		NETHERRACK = c("NETHERRACK", 3999);
		SOUL_SAND = c("SOUL_SAND", 4000);
		SOUL_SOIL = c("SOUL_SOIL", 4001);
		BASALT = c("BASALT", 4002, ORIENTABLE_MDF);
		POLISHED_BASALT = c("POLISHED_BASALT", 4005, ORIENTABLE_MDF);
		SOUL_TORCH = c("SOUL_TORCH", 4008);
		SOUL_WALL_TORCH = new Material(NamespacedKey.MINECRAFT, "SOUL_WALL_TORCH", false, (short) 4009, (byte) 1, 0, 0, DIRECTIONAL4F_MDF);
		GLOWSTONE = c("GLOWSTONE", 4013);
		NETHER_PORTAL = new Material(NamespacedKey.MINECRAFT, "NETHER_PORTAL", false, (short) 4014, (byte) 1, 0, 0, ORIENTABLE_MDF);
		JACK_O_LANTERN = c("JACK_O_LANTERN", 4020, DIRECTIONAL4F_MDF);
		OAK_TRAPDOOR = c("OAK_TRAPDOOR", 4111, TRAP_DOOR_MDF);
		SPRUCE_TRAPDOOR = c("SPRUCE_TRAPDOOR", 4175, TRAP_DOOR_MDF);
		BIRCH_TRAPDOOR = c("BIRCH_TRAPDOOR", 4239, TRAP_DOOR_MDF);
		JUNGLE_TRAPDOOR = c("JUNGLE_TRAPDOOR", 4303, TRAP_DOOR_MDF);
		ACACIA_TRAPDOOR = c("ACACIA_TRAPDOOR", 4367, TRAP_DOOR_MDF);
		DARK_OAK_TRAPDOOR = c("DARK_OAK_TRAPDOOR", 4431, TRAP_DOOR_MDF);
		CRIMSON_TRAPDOOR = c("CRIMSON_TRAPDOOR", 15135, TRAP_DOOR_MDF);
		WARPED_TRAPDOOR = c("WARPED_TRAPDOOR", 15199, TRAP_DOOR_MDF);
		INFESTED_STONE = c("INFESTED_STONE", 4499);
		INFESTED_COBBLESTONE = c("INFESTED_COBBLESTONE", 4500);
		INFESTED_STONE_BRICKS = c("INFESTED_STONE_BRICKS", 4501);
		INFESTED_MOSSY_STONE_BRICKS = c("INFESTED_MOSSY_STONE_BRICKS", 4502);
		INFESTED_CRACKED_STONE_BRICKS = c("INFESTED_CRACKED_STONE_BRICKS", 4503);
		INFESTED_CHISELED_STONE_BRICKS = c("INFESTED_CHISELED_STONE_BRICKS", 4504);
		STONE_BRICKS = c("STONE_BRICKS", 4495);
		MOSSY_STONE_BRICKS = c("MOSSY_STONE_BRICKS", 4496);
		CRACKED_STONE_BRICKS = c("CRACKED_STONE_BRICKS", 4497);
		CHISELED_STONE_BRICKS = c("CHISELED_STONE_BRICKS", 4498);
		BROWN_MUSHROOM_BLOCK = c("BROWN_MUSHROOM_BLOCK", 4505, MULTIPLE_FACING6_MDF);
		RED_MUSHROOM_BLOCK = c("RED_MUSHROOM_BLOCK", 4569, MULTIPLE_FACING6_MDF);
		MUSHROOM_STEM = c("MUSHROOM_STEM", 4633, MULTIPLE_FACING6_MDF);
		IRON_BARS = c("IRON_BARS", 4697, FENCE_MDF);
		CHAIN = c("CHAIN", 4729, CHAIN_MDF);
		GLASS_PANE = c("GLASS_PANE", 4735, FENCE_MDF);
		MELON = c("MELON", 4767);
		ATTACHED_MELON_STEM = new Material(NamespacedKey.MINECRAFT, "ATTACHED_MELON_STEM", false, (short) 4768, (byte) 1, 0, 0, DIRECTIONAL4F_MDF);
		ATTACHED_PUMPKIN_STEM = new Material(NamespacedKey.MINECRAFT, "ATTACHED_PUMKIN_STEM", false, (short) 4772, (byte) 1, 0, 0, DIRECTIONAL4F_MDF);
		MELON_STEM = new Material(NamespacedKey.MINECRAFT, "MELON_STEM", false, (short) 4784, (byte) 1, 0, 0, AGEABLE7_MDF);
		PUMPKIN_STEM = new Material(NamespacedKey.MINECRAFT, "PUMPKIN_STEM", false, (short) 4776, (byte) 1, 0, 0, AGEABLE7_MDF);
		VINE = c("VINE", 4792, MULTIPLE_FACING5_MDF);
		OAK_FENCE_GATE = c("OAK_FENCE_GATE", 4825, GATE_MDF);
		SPRUCE_FENCE_GATE = c("SPRUCE_FENCE_GATE", 8422, GATE_MDF);
		BIRCH_FENCE_GATE = c("BIRCH_FENCE_GATE", 8454, GATE_MDF);
		JUNGLE_FENCE_GATE = c("JUNGLE_FENCE_GATE", 8486, GATE_MDF);
		ACACIA_FENCE_GATE = c("ACACIA_FENCE_GATE", 8518, GATE_MDF);
		DARK_OAK_FENCE_GATE = c("DARK_OAK_FENCE_GATE", 8550, GATE_MDF);
		CRIMSON_FENCE_GATE = c("CRIMSON_FENCE_GATE", 15263, GATE_MDF);
		WARPED_FENCE_GATE = c("WARPED_FENCE_GATE", 15295, GATE_MDF);
		BRICK_STAIRS = c("BRICK_STAIRS", 4856, STAIRS_MDF);
		STONE_BRICK_STAIRS = c("STONE_BRICK_STAIRS", 4936, STAIRS_MDF);
		MYCELIUM = c("MYCELIUM", 5016, SNOWABLE_MDF);
		LILY_PAD = c("LILY_PAD", 5018);
		NETHER_BRICKS = c("NETHER_BRICKS", 5019);
		CRACKED_NETHER_BRICKS = c("CRACKED_NETHER_BRICKS", 17110);
		CHISELED_NETHER_BRICKS = c("CHISELED_NETHER_BRICKS", 17109);
		NETHER_BRICK_FENCE = c("NETHER_BRICK_FENCE", 5020, FENCE_MDF);
		NETHER_BRICK_STAIRS = c("NETHER_BRICK_STAIRS", 5052, STAIRS_MDF);
		ENCHANTING_TABLE = c("ENCHANTING_TABLE", 5136);
		END_PORTAL_FRAME = c("END_PORTAL_FRAME", 5150, END_PORTAL_FRAME_MDF);
		END_PORTAL = c("END_PORTAL", 5149);
		END_GATEWAY = new Material(NamespacedKey.MINECRAFT, "END_GATEWAY", false, (short) 9228, (byte) 1, 0, 0);
		END_STONE = c("END_STONE", 5158);
		END_STONE_BRICKS = c("END_STONE_BRICKS", 9222);
		DRAGON_EGG = c("DRAGON_EGG", 5159);
		REDSTONE_LAMP = c("REDSTONE_LAMP", 5160);
		COCOA = new Material(NamespacedKey.MINECRAFT, "COCOA", false, (short) 5162, (byte) 1, 0, 0, COCOA_MDF);
		SANDSTONE_STAIRS = c("SANDSTONE_STAIRS", 5174, STAIRS_MDF);
		EMERALD_ORE = c("EMERALD_ORE", 5254);
		ENDER_CHEST = c("ENDER_CHEST", 5255, ENDER_CHEST_MDF);
		TRIPWIRE_HOOK = c("TRIPWIRE_HOOK", 5263, TRIPWIRE_HOOK_MDF);
		TRIPWIRE = new Material(NamespacedKey.MINECRAFT, "TRIPWIRE", false, (short) 5279, (byte) 1, 0, 0, TRIPWIRE_MDF);
		EMERALD_BLOCK = c("EMERALD_BLOCK", 5407);
		SPRUCE_STAIRS = c("SPRUCE_STAIRS", 5408, STAIRS_MDF);
		BIRCH_STAIRS = c("BIRCH_STAIRS", 5488, STAIRS_MDF);
		JUNGLE_STAIRS = c("JUNGLE_STAIRS", 5568, STAIRS_MDF);
		CRIMSON_STAIRS = c("CRIMSON_STAIRS", 15327, STAIRS_MDF);
		WARPED_STAIRS = c("WARPED_STAIRS", 15407, STAIRS_MDF);
		COMMAND_BLOCK = c("COMMAND_BLOCK", 5648, COMMAND_BLOCK_MDF);
		BEACON = c("BEACON", 5660);
		COBBLESTONE_WALL = c("COBBLESTONE_WALL", 5661, WALL_MDF);
		MOSSY_COBBLESTONE_WALL = c("MOSSY_COBBLESTONE_WALL", 5985, WALL_MDF);
		BRICK_WALL = c("BRICK_WALL", 10871, WALL_MDF);
		PRISMARINE_WALL = c("PRISMARINE_WALL", 11195, WALL_MDF);
		RED_SANDSTONE_WALL = c("RED_SANDSTONE_WALL", 11519, WALL_MDF);
		MOSSY_STONE_BRICK_WALL = c("MOSSY_STONE_BRICK_WALL", 11843, WALL_MDF);
		GRANITE_WALL = c("GRANITE_WALL", 12167, WALL_MDF);
		STONE_BRICK_WALL = c("STONE_BRICK_WALL", 12491, WALL_MDF);
		NETHER_BRICK_WALL = c("NETHER_BRICK_WALL", 12815, WALL_MDF);
		ANDESITE_WALL = c("ANDESITE_WALL", 13139, WALL_MDF);
		RED_NETHER_BRICK_WALL = c("RED_NETHER_BRICK_WALL", 13463, WALL_MDF);
		SANDSTONE_WALL = c("SANDSTONE_WALL", 13787, WALL_MDF);
		END_STONE_BRICK_WALL = c("END_STONE_BRICK_WALL", 14111, WALL_MDF);
		DIORITE_WALL = c("DIORITE_WALL", 14435, WALL_MDF);
		BLACKSTONE_WALL = c("BLACKSTONE_WALL", 15928, WALL_MDF);
		POLISHED_BLACKSTONE_WALL = c("POLISHED_BLACKSTONE_WALL", 16785, WALL_MDF);
		POLISHED_BLACKSTONE_BRICK_WALL = c("POLISHED_BLACKSTONE_BRICK_WALL", 16348, WALL_MDF);
		STONE_BUTTON = c("STONE_BUTTON", 3897, SWITCH_MDF);
		OAK_BUTTON = c("OAK_BUTTON", 6350, SWITCH_MDF);
		SPRUCE_BUTTON = c("SPRUCE_BUTTON", 6374, SWITCH_MDF);
		BIRCH_BUTTON = c("BIRCH_BUTTON", 6398, SWITCH_MDF);
		JUNGLE_BUTTON = c("JUNGLE_BUTTON", 6422, SWITCH_MDF);
		ACACIA_BUTTON = c("ACACIA_BUTTON", 6446, SWITCH_MDF);
		DARK_OAK_BUTTON = c("DARK_OAK_BUTTON", 6470, SWITCH_MDF);
		CRIMSON_BUTTON = c("CRIMSON_BUTTON", 15487, SWITCH_MDF);
		WARPED_BUTTON = c("WARPED_BUTTON", 15511, SWITCH_MDF);
		POLISHED_BLACKSTONE_BUTTON = c("POLISHED_BLACKSTONE_BUTTON", 16761, SWITCH_MDF);
		ANVIL = c("ANVIL", 6614, DIRECTIONAL4F_MDF);
		CHIPPED_ANVIL = c("CHIPPED_ANVIL", 6618, DIRECTIONAL4F_MDF);
		DAMAGED_ANVIL = c("DAMAGED_ANVIL", 6622, DIRECTIONAL4F_MDF);
		TRAPPED_CHEST = c("TRAPPED_CHEST", 6626, CHEST_MDF);
		LIGHT_WEIGHTED_PRESSURE_PLATE = c("LIGHT_WEIGHTED_PRESSURE_PLATE", 6650, ANALOGUE_POWERABLE_MDF);
		HEAVY_WEIGHTED_PRESSURE_PLATE = c("HEAVY_WEIGHTED_PRESSURE_PLATE", 6666, ANALOGUE_POWERABLE_MDF);
		DAYLIGHT_DETECTOR = c("DAYLIGHT_DETECTORE", 6698, DAYLIGHT_DETECTORE_MDF);
		REDSTONE_BLOCK = c("REDSTONE_BLOCK", 6730);
		NETHER_QUARTZ_ORE = c("NETHER_QUARTZ_ORE", 6731);
		HOPPER = c("HOPPER", 6732, HOPPER_MDF);
		CHISELED_QUARTZ_BLOCK = c("CHISELED_QUARTZ_BLOCK", 6743);
		QUARTZ_BLOCK = c("QUARTZ_BLOCK", 6742);
		QUARTZ_BRICKS = c("QUARTZ_BRICKS", 17111);
		QUARTZ_PILLAR = c("QUARTZ_PILLAR", 6744, ORIENTABLE_MDF);
		QUARTZ_STAIRS = c("QUARTZ_STAIRS", 6747, STAIRS_MDF);
		ACTIVATOR_RAIL = c("ACTIVATOR_RAIL", 6827, REDSTONE_RAIL_MDF);
		DROPPER = c("DROPPER", 6839, DISPENSER_MDF);
		WHITE_TERRACOTTA = c("WHITE_TERRACOTTA", 6851);
		ORANGE_TERRACOTTA = c("ORANGE_TERRACOTTA", 6852);
		MAGENTA_TERRACOTTA = c("MAGENTA_TERRACOTTA", 6853);
		LIGHT_BLUE_TERRACOTTA = c("LIGHT_BLUE_TERRACOTTA", 6854);
		YELLOW_TERRACOTTA = c("YELLOW_TERRACOTTA", 6855);
		LIME_TERRACOTTA = c("LIME_TERRACOTTA", 6856);
		PINK_TERRACOTTA = c("PINK_TERRACOTTA", 6857);
		GRAY_TERRACOTTA = c("GRAY_TERRACOTTA", 6858);
		LIGHT_GRAY_TERRACOTTA = c("LIGHT_GRAY_TERRACOTTA", 6859);
		CYAN_TERRACOTTA = c("CYAN_TERRACOTTA", 6860);
		PURPLE_TERRACOTTA = c("PURPLE_TERRACOTTA", 6861);
		BLUE_TERRACOTTA = c("BLUE_TERRACOTTA", 6862);
		BROWN_TERRACOTTA = c("BROWN_TERRACOTTA", 6863);
		GREEN_TERRACOTTA = c("GREEN_TERRACOTTA", 6864);
		RED_TERRACOTTA = c("RED_TERRACOTTA", 6865);
		BLACK_TERRACOTTA = c("BLACK_TERRACOTTA", 6866);
		BARRIER = c("BARRIER", 7540);
		IRON_TRAPDOOR = c("IRON_TRAPDOOR", 7541, TRAP_DOOR_MDF);
		HAY_BLOCK = c("HAY_BLOCK", 7867, ORIENTABLE_MDF);
		WHITE_CARPET = c("WHITE_CARPET", 7870);
		ORANGE_CARPET = c("ORANGE_CARPET", 7871);
		MAGENTA_CARPET = c("MAGENTA_CARPET", 7872);
		LIGHT_BLUE_CARPET = c("LIGHT_BLUE_CARPET", 7873);
		YELLOW_CARPET = c("YELLOW_CARPET", 7874);
		LIME_CARPET = c("LIME_CARPET", 7875);
		PINK_CARPET = c("PINK_CARPET", 7876);
		GRAY_CARPET = c("GRAY_CARPET", 7877);
		LIGHT_GRAY_CARPET = c("LIGHT_GRAY_CARPET", 7878);
		CYAN_CARPET = c("CYAN_CARPET", 7879);
		PURPLE_CARPET = c("PURPLE_CARPET", 7880);
		BLUE_CARPET = c("BLUE_CARPET", 7881);
		BROWN_CARPET = c("BROWN_CARPET", 7882);
		GREEN_CARPET = c("GREEN_CARPET", 7883);
		RED_CARPET = c("RED_CARPET", 7884);
		BLACK_CARPET = c("BLACK_CARPET", 7885);
		TERRACOTTA = c("TERRACOTTA", 7886);
		COAL_BLOCK = c("COAL_BLOCK", 7887);
		PACKED_ICE = c("PACKED_ICE", 7888);
		ACACIA_STAIRS = c("ACACIA_STAIRS", 7379, STAIRS_MDF);
		DARK_OAK_STAIRS = c("DARK_OAK_STAIRS", 7459, STAIRS_MDF);
		SLIME_BLOCK = c("SLIME_BLOCK", 7539);
		GRASS_PATH = c("GRASS_PATH", 9227);
		SUNFLOWER = c("SUNFLOWER", 7889, BISECTED_MDF);
		LILAC = c("LILAC", 7891, BISECTED_MDF);
		ROSE_BUSH = c("ROSE_BUSH", 7893, BISECTED_MDF);
		PEONY = c("PEONY", 7895, BISECTED_MDF);
		TALL_GRASS = c("TALL_GRASS", 7897, BISECTED_MDF);
		LARGE_FERN = c("LARGE_FERN", 7899, BISECTED_MDF);
		WHITE_STAINED_GLASS = c("WHITE_STAINED_GLASS", 4095);
		ORANGE_STAINED_GLASS = c("ORANGE_STAINED_GLASS", 4096);
		MAGENTA_STAINED_GLASS = c("MAGENTA_STAINED_GLASS", 4097);
		LIGHT_BLUE_STAINED_GLASS = c("LIGH_BLUE_STAINED_GLASS", 4098);
		YELLOW_STAINED_GLASS = c("YELLOW_STAINED_GLASS", 4099);
		LIME_STAINED_GLASS = c("LIME_STAINED_GLASS", 4100);
		PINK_STAINED_GLASS = c("PINK_STAINED_GLASS", 4101);
		GRAY_STAINED_GLASS = c("GRAY_STAINED_GLASS", 4102);
		LIGHT_GRAY_STAINED_GLASS = c("LIGHT_GRAY_STAINED_GLASS", 4103);
		CYAN_STAINED_GLASS = c("CYAN_STAINED_GLASS", 4104);
		PURPLE_STAINED_GLASS = c("PURPLE_STAINED_GLASS", 4105);
		BLUE_STAINED_GLASS = c("BLUE_STAINED_GLASS", 4106);
		BROWN_STAINED_GLASS = c("BROWN_STAINED_GLASS", 4107);
		GREEN_STAINED_GLASS = c("GREEN_STAINED_GLASS", 4108);
		RED_STAINED_GLASS = c("RED_STAINED_GLASS", 4109);
		BLACK_STAINED_GLASS = c("BLACK_STAINED_GLASS", 4110);
		WHITE_STAINED_GLASS_PANE = c("WHITE_STAINED_GLASS_PANE", 6867, GLASS_PANE_MDF);
		ORANGE_STAINED_GLASS_PANE = c("ORANGE_STAINED_GLASS_PANE", 6900, GLASS_PANE_MDF);
		MAGENTA_STAINED_GLASS_PANE = c("MAGENTA_STAINED_GLASS_PANE", 6931, GLASS_PANE_MDF);
		LIGHT_BLUE_STAINED_GLASS_PANE = c("LIGHT_BLUE_STAINED_GLASS_PANE", 6963, GLASS_PANE_MDF);
		YELLOW_STAINED_GLASS_PANE = c("YELLOW_STAINED_GLASS_PANE", 6995, GLASS_PANE_MDF);
		LIME_STAINED_GLASS_PANE = c("LIME_STAINED_GLASS_PANE", 7027, GLASS_PANE_MDF);
		PINK_STAINED_GLASS_PANE = c("PINK_STAINED_GLASS_PANE", 7059, GLASS_PANE_MDF);
		GRAY_STAINED_GLASS_PANE = c("GRAY_STAINED_GLASS_PANE", 7091, GLASS_PANE_MDF);
		LIGHT_GRAY_STAINED_GLASS_PANE = c("LIGHT_GRAY_STAINED_GLASS_PANE", 7123, GLASS_PANE_MDF);
		CYAN_STAINED_GLASS_PANE = c("CYAN_STAINED_GLASS_PANE", 7155, GLASS_PANE_MDF);
		PURPLE_STAINED_GLASS_PANE = c("PURPLE_STAINED_GLASS_PANE", 7187, GLASS_PANE_MDF);
		BLUE_STAINED_GLASS_PANE = c("BLUE_STAINED_GLASS_PANE", 7219, GLASS_PANE_MDF);
		BROWN_STAINED_GLASS_PANE = c("BROWN_STAINED_GLASS_PANE", 7251, GLASS_PANE_MDF);
		GREEN_STAINED_GLASS_PANE = c("GREEN_STAINED_GLASS_PANE", 7283, GLASS_PANE_MDF);
		RED_STAINED_GLASS_PANE = c("RED_STAINED_GLASS_PANE", 7315, GLASS_PANE_MDF);
		BLACK_STAINED_GLASS_PANE = c("BLACK_STAINED_GLASS_PANE", 7347, GLASS_PANE_MDF);
		PRISMARINE = c("PRISMARINE", 7605);
		PRISMARINE_BRICKS = c("PRISMARINE_BRICKS", 7606);
		DARK_PRISMARINE = c("DARK_PRISMARINE", 7607);
		PRISMARINE_STAIRS = c("PRISMARINE_STAIRS", 7608, STAIRS_MDF);
		PRISMARINE_BRICK_STAIRS = c("PRISMARINE_BRICK_STAIRS", 7688, STAIRS_MDF);
		DARK_PRISMARINE_STAIRS = c("DARK_PRISMARINE_STAIRS", 7768);
		SEA_LANTERN = c("SEA_LANTERN", 7866);
		RED_SANDSTONE = c("RED_SANDSTONE", 8221);
		CHISELLED_RED_SANDSTONE = c("CHISELLED_RED_SANDSTONE", 8222);
		CUT_RED_SANDSTONE = c("CUT_RED_SANDSTONE", 8223);
		RED_SANDSTONE_STAIRS = c("RED_SANDSTONE_STAIRS", 8224, STAIRS_MDF);
		REPEATING_COMMAND_BLOCK = c("REPEATING_COMMAND_BLOCK", 9229, COMMAND_BLOCK_MDF);
		CHAIN_COMMAND_BLOCK = c("CHAIN_COMMAND_BLOCK", 9241, COMMAND_BLOCK_MDF);
		MAGMA_BLOCK = c("MAGMA_BLOCK", 9257);
		NETHER_WART_BLOCK = c("NETHER_WART_BLOCK", 9258);
		WARPED_WART_BLOCK = c("WARPED_WART_BLOCK", 14980);
		RED_NETHER_BRICKS = c("RED_NETHER_BRICKS", 9259);
		BONE_BLOCK = c("BONE_BLOCK", 9260, ORIENTABLE_MDF);
		STRUCTURE_VOID = c("STRUCTURE_VOID", 9263);
		OBSERVER = c("OBSERVER", 9264, OBSERVER_MDF);
		SHULKER_BOX = c("SHULKER_BOX", 9276, DIRECTIONAL6F_MDF);
		WHITE_SHULKER_BOX = c("WHITE_SHULKER_BOX", 9282, DIRECTIONAL6F_MDF);
		ORANGE_SHULKER_BOX = c("ORANGE_SHULKER", 9288, DIRECTIONAL6F_MDF);
		MAGENTA_SHULKER_BOX = c("MAGENTA_SHULKER_BOX", 9294, DIRECTIONAL6F_MDF);
		LIGHT_BLUE_SHULKER_BOX = c("LIGHT_BLUE_SHULKER_BOX", 9300, DIRECTIONAL6F_MDF);
		YELLOW_SHULKER_BOX = c("YELLOW_SHULKER_BOX", 9306);
		LIME_SHULKER_BOX = c("LIME_SHULKER_BOX", 9312, DIRECTIONAL6F_MDF);
		PINK_SHULKER_BOX = c("PINK_SHULKER_BOX", 9318, DIRECTIONAL6F_MDF);
		GRAY_SHULKER_BOX = c("GRAY_SHULKER_BOX", 9324, DIRECTIONAL6F_MDF);
		LIGHT_GRAY_SHULKER_BOX = c("LIGHT_GRAY_SHULKER_BOX", 9330, DIRECTIONAL6F_MDF);
		CYAN_SHULKER_BOX = c("CYAN_SHULKER_BOX", 9336, DIRECTIONAL6F_MDF);
		PURPLE_SHULKER_BOX = c("PURPLE_SHULKER_BOX", 9342, DIRECTIONAL6F_MDF);
		BLUE_SHULKER_BOX = c("BLUE_SHULKER_BOX", 9348, DIRECTIONAL6F_MDF);
		BROWN_SHULKER_BOX = c("BROWN_SHULKER", 9354, DIRECTIONAL6F_MDF);
		GREEN_SHULKER_BOX = c("GREEN_SHULKER_BOX", 9360, DIRECTIONAL6F_MDF);
		RED_SHULKER_BOX = c("RED_SHULKER_BOX", 9366, DIRECTIONAL6F_MDF);
		BLACK_SHULKER_BOX = c("BLACK_SHULKER_BOX", 9372, DIRECTIONAL6F_MDF);
		WHITE_GLAZED_TERRACOTTA = c("WHITE_GLAZED_TERRACOTTA", 9378, DIRECTIONAL4F_MDF);
		ORANGE_GLAZED_TERRACOTTA = c("ORANGE_GLAZED_TERRACOTTA", 9382, DIRECTIONAL4F_MDF);
		MAGENTA_GLAZED_TERRACOTTA = c("MAGENTA_GLAZED_TERRACOTTA", 9386, DIRECTIONAL4F_MDF);
		LIGHT_BLUE_GLAZED_TERRACOTTA = c("LIGHT_BLUE_GLAZED_TERRACOTTA", 9390, DIRECTIONAL4F_MDF);
		YELLOW_GLAZED_TERRACOTTA = c("YELLOW_GLAZED_TERRACOTTA", 9394, DIRECTIONAL4F_MDF);
		LIME_GLAZED_TERRACOTTA = c("LIME_GLAZED_TERRACOTTA", 9398, DIRECTIONAL4F_MDF);
		PINK_GLAZED_TERRACOTTA = c("PINK_GLAZED_TERRACOTTA", 94020, DIRECTIONAL4F_MDF);
		GRAY_GLAZED_TERRACOTTA = c("GRAY_GLAZED_TERRACOTTA", 9406, DIRECTIONAL4F_MDF);
		LIGHT_GRAY_GLAZED_TERRACOTTA = c("LIGHT_GRAY_GLAZED_TERRACOTTA", 9410, DIRECTIONAL4F_MDF);
		CYAN_GLAZED_TERRACOTTA = c("CYAN_GLAZED_TERRACOTTA", 9414, DIRECTIONAL4F_MDF);
		PURPLE_GLAZED_TERRACOTTA = c("PURPLE_GLAZED_TERRACOTTA", 9418, DIRECTIONAL4F_MDF);
		BLUE_GLAZED_TERRACOTTA = c("BLUE_GLAZED_TERRACOTTA", 9422, DIRECTIONAL4F_MDF);
		BROWN_GLAZED_TERRACOTTA = c("BROWN_GLAZED_TERRACOTTA", 9426, DIRECTIONAL4F_MDF);
		GREEN_GLAZED_TERRACOTTA = c("GREEN_GLAZED_TERRACOTTA", 9430, DIRECTIONAL4F_MDF);
		RED_GLAZED_TERRACOTTA = c("RED_GLAZED_TERRACOTTA", 9434, DIRECTIONAL4F_MDF);
		BLACK_GLAZED_TERRACOTTA = c("BLACK_GLAZED_TERRACOTTA", 9438, DIRECTIONAL4F_MDF);
		WHITE_CONCRETE = c("WHITE_CONCRETE", 9442);
		ORANGE_CONCRETE = c("ORANGE_CONCRETE", 9443);
		MAGENTA_CONCRETE = c("MAGENTA_CONCRETE", 9444);
		LIGHT_BLUE_CONCRETE = c("LIGHT_BLUE_CONCRETE", 9445);
		YELLOW_CONCRETE = c("YELLOW_CONCRETE", 9446);
		LIME_CONCRETE = c("LIME_CONCRETE", 9447);
		PINK_CONCRETE = c("PINK_CONCRETE", 9448);
		GRAY_CONCRETE = c("GRAY_CONCRETE", 9449);
		LIGHT_GRAY_CONCRETE = c("LIGHT_GRAY_CONCRETE", 9450);
		CYAN_CONCRETE = c("CYAN_CONCRETE", 9451);
		PURPLE_CONCRETE = c("PURPLE_CONCRETE", 9452);
		BLUE_CONCRETE = c("BLUE_CONCRETE", 9453);
		BROWN_CONCRETE = c("BROWN_CONCRETE", 9454);
		GREEN_CONCRETE = c("GREEN_CONCRETE", 9456);
		RED_CONCRETE = c("RED_CONCRETE", 9457);
		BLACK_CONCRETE = c("BLACK_CONCRETE", 9457);
		WHITE_CONCRETE_POWDER = c("WHITE_CONCRETE_POWDER", 9458);
		ORANGE_CONCRETE_POWDER = c("ORANGE_CONCRETE_POWDER", 9459);
		MAGENTA_CONCRETE_POWDER = c("MAGENTA_CONCRETE_POWDER", 9460);
		LIGHT_BLUE_CONCRETE_POWDER = c("LIGHT_BLUE_CONCRETE_POWDER", 9461);
		YELLOW_CONCRETE_POWDER = c("YELLOW_CONCRETE_POWDER", 9462);
		LIME_CONCRETE_POWDER = c("LIME_CONCRETE_POWDER", 9463);
		PINK_CONCRETE_POWDER = c("PINK_CONCRETE_POWDER", 9464);
		GRAY_CONCRETE_POWDER = c("GRAY_CONCRETE_POWDER", 9465);
		LIGHT_GRAY_CONCRETE_POWDER = c("LIGHT_GRAY_CONCRETE_POWDER", 9466);
		CYAN_CONCRETE_POWDER = c("CYAN_CONCRETE_POWDER", 9467);
		PURPLE_CONCRETE_POWDER = c("PURPLE_CONCRETE_POWDER", 9468);
		BLUE_CONCRETE_POWDER = c("BLUE_CONCRETE_POWDER", 9469);
		BROWN_CONCRETE_POWDER = c("BROWN_CONCRETE_POWDER", 9470);
		GREEN_CONCRETE_POWDER = c("GREEN_CONCRETE_POWDER", 9471);
		RED_CONCRETE_POWDER = c("RED_CONCRETE_POWDER", 9472);
		BLACK_CONCRETE_POWDER = c("BLACK_CONCRETE_POWDER", 9473);
		TURTLE_EGG = c("TURTLE_EGG", 9502, TURTLE_EGG_MDF);
		DEAD_TUBE_CORAL_BLOCK = c("DEAD_TUBE_CORAL_BLOCK", 9514);
		DEAD_BRAIN_CORAL_BLOCK = c("DEAD_BRAIN_CORAL_BLOCK", 9515);
		DEAD_BUBBLE_CORAL_BLOCK = c("DEAD_BUBBLE_CORAL_BLOCK", 9516);
		DEAD_FIRE_CORAL_BLOCK = c("DEAD_FIRE_CORAL_BLOCK", 9517);
		DEAD_HORN_CORAL_BLOCK = c("DEAD_HORN_CORAL_BLOCK", 9518);
		TUBE_CORAL_BLOCK = c("TUBE_CORAL_BLOCK", 9519);
		BRAIN_CORAL_BLOCK = c("BRAIN_CORAL_BLOCK", 9520);
		BUBBLE_CORAL_BLOCK = c("BUBBLE_CORAL_BLOCK", 9521);
		FIRE_CORAL_BLOCK = c("FIRE_CORAL_BLOCK", 9522);
		HORN_CORAL_BLOCK = c("HORN_CORAL_BLOCK", 9523);
		TUBE_CORAL = c("TUBE_CORAL", 9534, WATERLOGGED_MDF);
		BRAIN_CORAL = c("BRAIN_CORAL", 9536, WATERLOGGED_MDF);
		BUBBLE_CORAL = c("BUBBLE_CORAL", 9538, WATERLOGGED_MDF);
		FIRE_CORAL = c("FIRE_CORAL", 9540, WATERLOGGED_MDF);
		HORN_CORAL = c("HORN_CORAL", 9542, WATERLOGGED_MDF);
		DEAD_BRAIN_CORAL = c("DEAD_BRAIN_CORAL", 9526, WATERLOGGED_MDF);
		DEAD_BUBBLE_CORAL = c("DEAD_BUBBLE_CORAL", 9528, WATERLOGGED_MDF);
		DEAD_FIRE_CORAL = c("DEAD_FIRE_CORAL", 9530, WATERLOGGED_MDF);
		DEAD_HORN_CORAL = c("DEAD_HORN_CORAL", 9532, WATERLOGGED_MDF);
		DEAD_TUBE_CORAL = c("DEAD_TUBE_CORAL", 9524, WATERLOGGED_MDF);
		TUBE_CORAL_FAN = c("TUBE_CORAL_FAN", 9554, WATERLOGGED_MDF);
		BRAIN_CORAL_FAN = c("BRAIN_CORAL_FAN", 9556, WATERLOGGED_MDF);
		BUBBLE_CORAL_FAN = c("BUBBLE_CORAL_FAN", 9558, WATERLOGGED_MDF);
		FIRE_CORAL_FAN = c("FIRE_CORAL_FAN", 9560, WATERLOGGED_MDF);
		HORN_CORAL_FAN = c("HORN_CORAL_FAN", 9562, WATERLOGGED_MDF);
		DEAD_TUBE_CORAL_FAN = c("DEAD_TUBE_CORAL_FAN", 9544, WATERLOGGED_MDF);
		DEAD_BRAIN_CORAL_FAN = c("DEAD_BRAIN_CORAL_FAN", 9546, WATERLOGGED_MDF);
		DEAD_BUBBLE_CORAL_FAN = c("DEAD_BUBBLE_CORAL_FAN", 9548, WATERLOGGED_MDF);
		DEAD_FIRE_CORAL_FAN = c("DEAD_FIRE_CORAL_FAN", 9550, WATERLOGGED_MDF);
		DEAD_HORN_CORAL_FAN = c("DEAD_HORN_CORAL_FAN", 9552, WATERLOGGED_MDF);
		TUBE_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "TUBE_CORAL_WALL_FAN", false, (short) 9604, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		BRAIN_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "BRAIN_CORAL_WALL_FAN", false, (short) 9612, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		BUBBLE_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "BUBBLE_CORAL_WALL_FAN", false, (short) 9620, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		FIRE_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "FIRE_CORAL_WALL_FAN", false, (short) 9628, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		HORN_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "HORN_CORAL_WALL_FAN", false, (short) 9636, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		DEAD_TUBE_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "DEAD_TUBE_CORAL_WALL_FAN", false, (short) 9564, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		DEAD_BRAIN_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "DEAD_BRAIN_CORAL_WALL_FAN", false, (short) 9572, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		DEAD_BUBBLE_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "DEAD_BUBBLE_CORAL_WALL_FAN", false, (short) 9580, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		DEAD_FIRE_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "DEAD_FIRE_CORAL_WALL_FAN", false, (short) 9588, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		DEAD_HORN_CORAL_WALL_FAN = new Material(NamespacedKey.MINECRAFT, "DEAD_HORN_CORAL_WALL_FAN", false, (short) 9596, (byte) 1, 0, 0, CORAL_WALL_FAN_MDF);
		BLUE_ICE = c("BLUE_ICE", 9652);
		CONDUIT = c("CONDUIT", 9653, WATERLOGGED_MDF);
		POLISHED_GRANITE_STAIRS = c("POLISHED_GRANITE_STAIRS", 9673, STAIRS_MDF);
		SMOOTH_RED_SANDSTONE_STAIRS = c("SMOOTH_RED_SANDSTONE_STAIRS", 9753, STAIRS_MDF);
		MOSSY_STONE_BRICK_STAIRS = c("MOSSY_STONE_BRICK_STAIRS", 9833, STAIRS_MDF);
		POLISHED_DIORITE_STAIRS = c("POLISHED_DIORITE_STAIRS", 9913, STAIRS_MDF);
		MOSSY_COBBLESTONE_STAIRS = c("MOSSY_COBBLESTONE_STAIRS", 9993, STAIRS_MDF);
		END_STONE_BRICK_STAIRS = c("END_STONE_BRICK_STAIRS", 10073, STAIRS_MDF);
		STONE_STAIRS = c("STONE_STAIRS", 10153, STAIRS_MDF);
		SMOOTH_SANDSTONE_STAIRS = c("SMOOTH_STONE_STAIRS", 10233, STAIRS_MDF);
		SMOOTH_QUARTZ_STAIRS = c("SMOOTH_QUARTZ_STAIRS", 10313, STAIRS_MDF);
		GRANITE_STAIRS = c("GRANITE_STAIRS", 10393, STAIRS_MDF);
		ANDESITE_STAIRS = c("ANDESITE_STAIRS", 10473, STAIRS_MDF);
		RED_NETHER_BRICK_STAIRS = c("RED_NETHER_BRICK_STAIRS", 10553, STAIRS_MDF);
		POLISHED_ANDESITE_STAIRS = c("POLISHED_ANDESITE_STAIRS", 10633, STAIRS_MDF);
		DIORITE_STAIRS = c("DIORITE_STAIRS", 10713, STAIRS_MDF);
		POLISHED_GRANITE_SLAB = c("POLISHED_GRANITE_SLAB", 10793, SLAB_MDF);
		SMOOTH_RED_SANDSTONE_SLAB = c("SMOOTH_RED_SANDSTONE_SLAB", 10799, SLAB_MDF);
		MOSSY_STONE_BRICK_SLAB = c("MOSSY_STONE_BRICK_SLAB", 10805, SLAB_MDF);
		POLISHED_DIORITE_SLAB = c("POLISHED_DIORITE_SLAB", 10811, SLAB_MDF);
		MOSSY_COBBLESTONE_SLAB = c("MOSSY_COBBLESTONE_SLAB", 10817, SLAB_MDF);
		END_STONE_BRICK_SLAB = c("END_STONE_BRICK_SLAB", 10823, SLAB_MDF);
		SMOOTH_SANDSTONE_SLAB = c("SMOOTH_SANDSTONE_SLAB", 10829, SLAB_MDF);
		SMOOTH_QUARTZ_SLAB = c("SMOOTH_QUARTZ_SLAB", 10835, SLAB_MDF);
		GRANITE_SLAB = c("GRANITE_SLAB", 10841, SLAB_MDF);
		ANDESITE_SLAB = c("ANDESITE_SLAB", 10847, SLAB_MDF);
		RED_NETHER_BRICK_SLAB = c("RED_NETHER_BRICK_SLAB", 10853, SLAB_MDF);
		POLISHED_ANDESITE_SLAB = c("POLISHED_ANDESITE_SLAB", 10859, SLAB_MDF);
		DIORITE_SLAB = c("DIORITE_SLAB", 10865, SLAB_MDF);
		SCAFFOLDING = c("SCAFFOLDING", 14759, SCAFFOLDING_MDF);
		IRON_DOOR = c("IRON_DOOR", 3809, DOOR_MDF);
		OAK_DOOR = c("OAK_DOOR", 3573, DOOR_MDF);
		SPRUCE_DOOR = c("SPRUCE_DOOR", 8742, DOOR_MDF);
		BIRCH_DOOR = c("BIRCH_DOOR", 8806, DOOR_MDF);
		JUNGLE_DOOR = c("JUNGLE_DOOR", 8870, DOOR_MDF);
		ACACIA_DOOR = c("ACACIA_DOOR", 8934, DOOR_MDF);
		DARK_OAK_DOOR = c("DARK_OAK_DOOR", 8998, DOOR_MDF);
		CRIMSON_DOOR = c("CRIMSON_DOOR", 15535, DOOR_MDF);
		WARPED_DOOR = c("WARPED_DOOR", 15599, DOOR_MDF);
		REPEATER = c("REPEATER", 4031, REPEATER_MDF);
		COMPARATOR = c("COMPARATOR", 6682, COMPARATOR_MDF);
		STRUCTURE_BLOCK = c("STRUCTURE_BLOCK", 15743, STRUCTURE_BLOCK_MDF);
		JIGSAW = c("JIGSAW", 15747, JIGSAW_MDF);
		TURTLE_HELMET = i("TURTLE_HELMET", 1, DAMAGEABLE_MDF);
		SCUTE = i("SCUTE");
		FLINT_AND_STEEL = i("FLINT_AND_STEEL", 1, DAMAGEABLE_MDF);
		APPLE = i("APPLE");
		ARROW = i("ARROW");
		COAL = i("COAL");
		CHARCOAL = i("CHARCOAL");
		DIAMOND = i("DIAMOND");
		IRON_INGOT = i("IRON_INGOT");
		GOLD_INGOT = i("GOLD_INGOT");
		NETHERITE_INGOT = i("NETHERITE");
		NETHERITE_SCRAP = i("NETHERITE_SCRAP");
		WOODEN_SWORD = i("WOODEN_SWORD", 1, DAMAGEABLE_MDF);
		WOODEN_SHOVEL = i("WOODEN_SHOVEL", 1, DAMAGEABLE_MDF);
		WOODEN_PICKAXE = i("WOODEN_PICKAXE", 1, DAMAGEABLE_MDF);
		WOODEN_AXE = i("WOODEN_AXE", 1, DAMAGEABLE_MDF);
		WOODEN_HOE = i("WOODEN_HOE", 1, DAMAGEABLE_MDF);
		STONE_SWORD = i("STONE_SWORD", 1, DAMAGEABLE_MDF);
		STONE_SHOVEL = i("STONE_SHOVEL", 1, DAMAGEABLE_MDF);
		STONE_PICKAXE = i("STONE_PICKAXE", 1, DAMAGEABLE_MDF);
		STONE_AXE = i("STONE_AXE", 1, DAMAGEABLE_MDF);
		STONE_HOE = i("STONE_HOE", 1, DAMAGEABLE_MDF);
		GOLDEN_SWORD = i("GOLDEN_SWORD", 1, DAMAGEABLE_MDF);
		GOLDEN_SHOVEL = i("GOLDEN_SHOVEL", 1, DAMAGEABLE_MDF);
		GOLDEN_PICKAXE = i("GOLDEN_PICKAXE", 1, DAMAGEABLE_MDF);
		GOLDEN_AXE = i("GOLDEN_AXE", 1, DAMAGEABLE_MDF);
		GOLDEN_HOE = i("GOLDEN_HOE", 1, DAMAGEABLE_MDF);
		IRON_SWORD = i("IRON_SWORD", 1, DAMAGEABLE_MDF);
		IRON_SHOVEL = i("IRON_SHOVEL", 1, DAMAGEABLE_MDF);
		IRON_PICKAXE = i("IRON_PICKAXE", 1, DAMAGEABLE_MDF);
		IRON_AXE = i("IRON_AXE", 1, DAMAGEABLE_MDF);
		IRON_HOE = i("IRON_HOE", 1, DAMAGEABLE_MDF);
		DIAMOND_SWORD = i("DIAMOND_SWORD", 1, DAMAGEABLE_MDF);
		DIAMOND_SHOVEL = i("DIAMOND_SHOVEL", 1, DAMAGEABLE_MDF);
		DIAMOND_PICKAXE = i("DIAMOND_PICKAXE", 1, DAMAGEABLE_MDF);
		DIAMOND_AXE = i("DIAMOND_AXE", 1, DAMAGEABLE_MDF);
		DIAMOND_HOE = i("DIAMOND_HOE", 1, DAMAGEABLE_MDF);
		NETHERITE_SWORD = i("NETHERITE_SWORD", 1, DAMAGEABLE_MDF);
		NETHERITE_SHOVEL = i("NETHERITE_SHOVEL", 1, DAMAGEABLE_MDF);
		NETHERITE_PICKAXE = i("NETHERITE_PICKAXE", 1, DAMAGEABLE_MDF);
		NETHERITE_AXE = i("NETHERITE_AXE", 1, DAMAGEABLE_MDF);
		NETHERITE_HOE = i("NETHERITE_HOE", 1, DAMAGEABLE_MDF);
		STICK = i("STICK");
		BOWL = i("BOWL");
		MUSHROOM_STEW = i("MUSHROOM_STEW", 1);
		STRING = i("STRING");
		FEATHER = i("FEATHER");
		GUN_POWDER = i("GUN_POWDER");
		WHEAT_SEEDS = i("WHEAT_SEEDS");
		WHEAT = c("WHEAT", 3357, AGEABLE7_MDF);
		BREAD = i("BREAD");
		LEATHER_HELMET = i("LEATHER_HELMET", 1, LEATHER_ARMOR_MDF);
		LEATHER_CHESTPLATE = i("LEATHER_CHESTPLATE", 1, LEATHER_ARMOR_MDF);
		LEATHER_LEGGINGS = i("LEATHER_LEGGINGS", 1, LEATHER_ARMOR_MDF);
		LEATHER_BOOTS = i("LEATHER_BOOTS", 1, LEATHER_ARMOR_MDF);
		CHAINMAIL_HELMET = i("CHAINMAIL_HELMET", 1, DAMAGEABLE_MDF);
		CHAINMAIL_CHESTPLATE = i("CHAINMAIL_CHESTPLATE", 1, DAMAGEABLE_MDF);
		CHAINMAIL_LEGGINGS = i("CHAINMAIL_LEGGINGS", 1, DAMAGEABLE_MDF);
		CHAINMAIL_BOOTS = i("CHAINMAIL_BOOTS", 1, DAMAGEABLE_MDF);
		IRON_HELMET = i("IRON_HELMET", 1, DAMAGEABLE_MDF);
		IRON_CHESTPLATE = i("IRON_CHESTPLATE", 1, DAMAGEABLE_MDF);
		IRON_LEGGINGS = i("IRON_LEGGINGS", 1, DAMAGEABLE_MDF);
		IRON_BOOTS = i("IRON_BOOTS", 1, DAMAGEABLE_MDF);
		DIAMOND_HELMET = i("DIAMOND_HELMET", 1, DAMAGEABLE_MDF);
		DIAMOND_CHESTPLATE = i("DIAMOND_CHESTPLATE", 1, DAMAGEABLE_MDF);
		DIAMOND_LEGGINGS = i("DIAMOND_LEGGINGS", 1, DAMAGEABLE_MDF);
		DIAMOND_BOOTS = i("DIAMOND_BOOTS", 1, DAMAGEABLE_MDF);
		GOLDEN_HELMET = i("GOLDEN_HELMET", 1, DAMAGEABLE_MDF);
		GOLDEN_CHESTPLATE = i("GOLDEN_CHESTPLATE", 1, DAMAGEABLE_MDF);
		GOLDEN_LEGGINGS = i("GOLDEN_LEGGINGS", 1, DAMAGEABLE_MDF);
		GOLDEN_BOOTS = i("GOLDEN_BOOTS", 1, DAMAGEABLE_MDF);
		NETHERITE_HELMET = i("NETHERITE_HELMET", 1, DAMAGEABLE_MDF);
		NETHERITE_CHESTPLATE = i("NETHERITE_CHESTPLATE", 1, DAMAGEABLE_MDF);
		NETHERITE_LEGGINGS = i("NETHERITE_LEGGINGS", 1, DAMAGEABLE_MDF);
		NETHERITE_BOOTS = i("NETHERITE_BOOTS", 1, DAMAGEABLE_MDF);
		FLINT = i("FLINT");
		PORKCHOP = i("PORKCHOP");
		COOKED_PORKCHOP = i("COOKED_PORKCHOP");
		PAINTING = i("PAINTING");
		GOLDEN_APPLE = i("GOLDEN_APPLE");
		ENCHANTED_GOLDEN_APPLE = i("ENCHANTED_GOLDEN_APPLE", 64);
		OAK_SIGN = c("OAK_SIGN", 3381, 16, SIGN_MDF);
		SPRUCE_SIGN = c("SPRUCE_SIGN", 3413, 16, SIGN_MDF);
		BIRCH_SIGN = c("BIRCH_SIGN", 3445, 16, SIGN_MDF);
		JUNGLE_SIGN = c("JUNGLE_SIGN", 3509, 16, SIGN_MDF);
		ACACIA_SIGN = c("ACACIA_SIGN", 3477, 16, SIGN_MDF);
		DARK_OAK_SIGN = c("DARK_OAK_SIGN", 3541, 16, SIGN_MDF);
		CRIMSON_SIGN = c("CRIMSON_SIGN", 15663, 16, SIGN_MDF);
		WARPED_SIGN = c("WARPED_SIGN", 15695, 16, SIGN_MDF);
		OAK_WALL_SIGN = new Material(NamespacedKey.MINECRAFT, "OAK_WALL_SIGN", false, (short) 3735, (byte) 1, 0, 0, WALL_SIGN_MDF);
		SPRUCE_WALL_SIGN = new Material(NamespacedKey.MINECRAFT, "SPRUCE_WALL_SIGN", false, (short) 3743, (byte) 1, 0, 0, WALL_SIGN_MDF);
		BIRCH_WALL_SIGN = new Material(NamespacedKey.MINECRAFT, "BIRCH_WALL_SIGN", false, (short) 3751, (byte) 1, 0, 0, WALL_SIGN_MDF);
		JUNGLE_WALL_SIGN = new Material(NamespacedKey.MINECRAFT, "JUNGLE_WALL_SIGN", false, (short) 3759, (byte) 1, 0, 0, WALL_SIGN_MDF);
		ACACIA_WALL_SIGN = new Material(NamespacedKey.MINECRAFT, "ACACIA_WALL_SIGN", false, (short) 3767, (byte) 1, 0, 0, WALL_SIGN_MDF);
		DARK_OAK_WALL_SIGN = new Material(NamespacedKey.MINECRAFT, "DARK_OAK_WALL_SIGN", false, (short) 3775, (byte) 1, 0, 0, WALL_SIGN_MDF);
		CRIMSON_WALL_SIGN = new Material(NamespacedKey.MINECRAFT, "CRIMSON_WALL_SIGN", false, (short) 15727, (byte) 1, 0, 0, WALL_SIGN_MDF);
		WARPED_WALL_SIGN = new Material(NamespacedKey.MINECRAFT, "WARPED_WALL_SIGN", false, (short) 15735, (byte) 1, 0, 0, WALL_SIGN_MDF);
		BUCKET = i("BUCKET", 16);
		WATER_BUCKET = i("WATER_BUCKET", 1);
		LAVA_BUCKET = i("LAVA_BUCKET", 1);
		MINECART = i("MINECART", 1);
		SADDLE = i("SADDLE", 1);
		REDSTONE = i("REDSTONE");
		SNOWBALL = i("SNOWBALL", 16);
		OAK_BOAT = i("OAK_BOAT", 1);
		LEATHER = i("LEATHER");
		MILK_BUCKET = i("MILK_BUCKET", 1);
		PUFFERFISH_BUCKET = i("PUFFERFISH_BUCKET", 1);
		SALMON_BUCKET = i("SALMON_BUCKET", 1);
		COD_BUCKET = i("COD_BUCKET", 1);
		TROPICAL_FISH_BUCKET = i("TROPICAL_FISH_BUCKET", 1, TROPICAL_BUCKET_MDF);
		BRICK = i("BRICK");
		CLAY_BALL = i("CLAY_BALL");
		DRIED_KELP_BLOCK = c("DRIED_KELP_BLOCK", 9501);
		PAPER = i("PAPER");
		BOOK = i("BOOK");
		SLIME_BALL = i("SLIME_BALL");
		CHEST_MINECART = i("CHEST_MINECART", 1);
		FURNACE_MINECART = i("FURNACE_MINECART", 1);
		EGG = i("EGG", 16);
		COMPASS = i("COMPASS", 64, COMPASS_MDF);
		FISHING_ROD = i("FISHING_ROD", 1, DAMAGEABLE_MDF);
		CLOCK = i("CLOCK");
		GLOWSTONE_DUST = i("GLOWSTONE_DUST");
		COD = i("COD");
		SALMON = i("SALMON");
		TROPICAL_FISH = i("TROPICAL_FISH");
		PUFFERFISH = i("PUFFERFISH");
		COOKED_COD = i("COOKED_COD");
		COOKED_SALMON = i("COOKED_SALMON");
		INK_SAC = i("INK_SAC");
		COCOA_BEANS = i("COCOA_BEANS");
		LAPIS_LAZULI = i("LAPIS_LAZULI");
		WHITE_DYE = i("WHITE_DYE");
		ORANGE_DYE = i("ORANGE_DYE");
		MAGENTA_DYE = i("MAGENTA_DYE");
		LIGHT_BLUE_DYE = i("LIGHT_BLUE_DYE");
		YELLOW_DYE = i("YELLOW_DYE");
		LIME_DYE = i("LIME_DYE");
		PINK_DYE = i("PINK_DYE");
		GRAY_DYE = i("GRAY_DYE");
		LIGHT_GRAY_DYE = i("LIGHT_GRAY_DYE");
		CYAN_DYE = i("CYAN_DYE");
		PURPLE_DYE = i("PURPLE_DYE");
		BLUE_DYE = i("BLUE_DYE");
		BROWN_DYE = i("BROWN_DYE");
		GREEN_DYE = i("GREEN_DYE");
		RED_DYE = i("RED_DYE");
		BLACK_DYE = i("BLACK_DYE");
		BONE_MEAL = i("BONE_MEAL");
		BONE = i("BONE");
		SUGAR = i("SUGAR");
		CAKE = c("CAKE", 4024, 1, CAKE_MDF);
		WHITE_BED = c("WHITE_BED", 1049, 1, BED_MDF);
		ORANGE_BED = c("ORANGE_BED", 1065, 1, BED_MDF);
		MAGENTA_BED = c("MAGENTA_BED", 1081, 1, BED_MDF);
		LIGHT_BLUE_BED = c("LIGHT_BLUE_BED", 1097, 1, BED_MDF);
		YELLOW_BED = c("YELLOW_BED", 1113, 1, BED_MDF);
		LIME_BED = c("LIME_BED", 1129, 1, BED_MDF);
		PINK_BED = c("PINK_BED", 1145, 1, BED_MDF);
		GRAY_BED = c("GRAY_BED", 1161, 1, BED_MDF);
		LIGHT_GRAY_BED = c("LIGHT_GRAY_BED", 1177, 1, BED_MDF);
		CYAN_BED = c("CYAN_BED", 1193, 1, BED_MDF);
		PURPLE_BED = c("PURPLE_BED", 1209, 1, BED_MDF);
		BLUE_BED = c("BLUE_BED", 1225, 1, BED_MDF);
		BROWN_BED = c("BROWN_BED", 1241, 1, BED_MDF);
		GREEN_BED = c("GREEN_BED", 1257, 1, BED_MDF);
		RED_BED = c("RED_BED", 1273, 1, BED_MDF);
		BLACK_BED = c("BLACK_BED", 1289, 1, BED_MDF);
		COOKIE = i("COOKIE");
		FILLED_MAP = i("FILLED_MAP", 64, MAP_MDF);
		SHEARS = i("SHEARS", 1, DAMAGEABLE_MDF);
		MELON_SLICE = i("MELON_SLICE");
		DRIED_KELP = i("DIRED_KELP");
		PUMPKIN_SEED = i("PUMPKIN_SEED");
		MELON_SEEDS = i("MELON_SEED");
		BEEF = i("BEEF");
		COOKED_BEEF = i("COOKED_BEEF");
		CHICKEN = i("CHICKEN");
		COOKED_CHICKEN = i("COOKED_CHICKEN");
		ROTTEN_FLESH = i("ROTTEN_FLESH");
		ENDER_PEARL = i("ENDER_PEARL", 16);
		BLAZE_ROD = i("BLAZE_ROD");
		GHAST_TEAR = i("GHAST_TEAR");
		GOLD_NUGGET = i("GOLD_NUGGET");
		NETHER_WART = c("NETHER_WART", 5132, AGEABLE3_MDF);
		POTION = i("POTION", 1, POTION_MDF);
		GLASS_BOTTLE = i("GLASS_BOTTLE");
		SPIDER_EYE = i("SPIDER_EYE");
		FERMENTED_SPIDER_EYE = i("FERMENTED_SPIDER_EYE");
		BLAZE_POWDER = i("BLAZE_POWDER");
		MAGMA_CREAM = i("MAGMA_CREAM");
		BREWING_STAND = c("BREWING_STAND", 5137, BREWING_STAND_MDF);
		CAULDRON = c("CAULDRON", 5146, LEVELLED_MDF);
		ENDER_EYE = i("ENDER_EYE");
		GLISTERING_MELON_SLICE = i("GLISTERING_MELON_SLICE");
		BAT_SPAWN_EGG = i("BAT_SPAWN_EGG");
		BEE_SPAWN_EGG = i("BEE_SPAWN_EGG");
		BLAZE_SPAWN_EGG = i("BLAZE_SPAWN_EGG");
		CAT_SPAWN_EGG = i("CAT_SPAWN_EGG");
		CAVE_SPIDER_SPAWN_EGG = i("CAVE_SPIDER_EGG");
		CHICKEN_SPAWN_EGG = i("CHICKEN_SPAWN_EGG");
		COD_SPAWN_EGG = i("COD_SPAWN_EGG");
		COW_SPAWN_EGG = i("COW_SPAWN_EGG");
		CREEPER_SPAWN_EGG = i("CREEPER_SPAWN_EGG");
		DOLPHIN_SPAWN_EGG = i("DOLPHIN_SPAWN_EGG");
		DONKEY_SPAWN_EGG = i("DONKEY_SPAWN_EGG");
		DROWNED_SPAWN_EGG = i("DROWNED_SPAWN_EGG");
		ELDER_GUARDIAN_SPAWN_EGG = i("ELDER_GUARIAN_SPAWN_EGG");
		ENDERMAN_SPAWN_EGG = i("ENDERMAN_SPAWN_EGG");
		ENDERMITE_SPAWN_EGG = i("ENDERMITE_SPAWN_EGG");
		EVOKER_SPAWN_EGG = i("EVOKER_SPAWN_EGG");
		FOX_SPAWN_EGG = i("FOX_SPAWN_EGG");
		GHAST_SPAWN_EGG = i("GHAST_SPAWN_EGG");
		GUARDIAN_SPAWN_EGG = i("GUARDIAN_SPAWN_EGG");
		HOGLIN_SPAWN_EGG = i("HOGLIN_SPAWN_EGG");
		HORSE_SPAWN_EGG = i("HORSE_SPAWN_EGG");
		HUSK_SPAWN_EGG = i("HUSK_SPAWN_EGG");
		LLAMA_SPAWN_EGG = i("LLAMA_SPAWN_EGG");
		MAGMA_CUBE_SPAWN_EGG = i("MAGMA_CUBE_SPAWN_EGG");
		MOOSHROOM_SPAWN_EGG = i("MOOSHROOM_SPAWN_EGG");
		MULE_SPAWN_EGG = i("MULE_SPAWN_EGG");
		OCELOT_SPAWN_EGG = i("OCELOT_SPAWN_EGG");
		PANDA_SPAWN_EGG = i("PANDA_SPAWN_EGG");
		PARROT_SPAWN_EGG = i("PARROT_SPAWN_EGG");
		PHANTOM_SPAWN_EGG = i("PHANTOM_SPAWN_EGG");
		PIG_SPAWN_EGG = i("PIG_SPAWN_EGG");
		PIGLIN_SPAWN_EGG = i("PIGLIN_SPAWN_EGG");
		PIGLIN_BRUTE_SPAWN_EGG = i("PIGLIN_BRUTE_SPAWN_EGG");
		PILLAGER_SPAWN_EGG = i("PILLAGER_SPAWN_EGG");
		POLAR_BEAR_SPAWN_EGG = i("POLAR_BEAR_SPAWN_EGG");
		PUFFERFISH_SPAWN_EGG = i("PUFFERFISH_SPAWN_EGG");
		RABBIT_SPAWN_EGG = i("RABBIT_SPAWN_EGG");
		RAVAGER_SPAWN_EGG = i("RAVAGER_SPAWN_EGG");
		SALMON_SPAWN_EGG = i("SALMON_SPAWN_EGG");
		SHEEP_SPAWN_EGG = i("SHEEP_SPAWN_EGG");
		SHULKER_SPAWN_EGG = i("SHULKER_SPAWN_EGG");
		SILVERFISH_SPAWN_EGG = i("SILVERFISH_SPAWN_EGG");
		SKELETON_SPAWN_EGG = i("SKELETON_SPAWN_EGG");
		SKELETON_HORSE_SPAWN_EGG = i("SKELETON_HORSE_SPAWN_EGG");
		SLIME_SPAWN_EGG = i("SLIME_SPAWN_EGG");
		SPIDER_SPAWN_EGG = i("SPIDER_SPAWN_EGG");
		SQUID_SPAWN_EGG = i("SQUID_SPAWN_EGG");
		STRAY_SPAWN_EGG = i("STRAY_SPAWN_EGG");
		STRIDER_SPAWN_EGG = i("STRIDER_SPAWN_EGG");
		TRADER_LLAMA_SPAWN_EGG = i("TRADER_LLAMA_SPAWN_EGG");
		TROPICAL_FISH_SPAWN_EGG = i("TROPICAL_FISH_SPAWN_EGG");
		TURTLE_SPAWN_EGG = i("TURTLE_SPAWN_EGG");
		VEX_SPAWN_EGG = i("VEX_SPAWN_EGG");
		VILLAGER_SPAWN_EGG = i("VILLAGER_SPAWN_EGG");
		VINDICATOR_SPAWN_EGG = i("VINDICATOR_SPAWN_EGG");
		WANDERING_TRADER_SPAWN_EGG = i("WANDERING_TRADER_SPAWN_EGG");
		WITCH_SPAWN_EGG = i("WITCH_SPAWN_EGG");
		WITHER_SKELETON_SPAWN_EGG = i("WITHER_SKELETON_SPAWN_EGG");
		WOLF_SPAWN_EGG = i("WOLF_SPAWN_EGG");
		ZOGLIN_SPAWN_EGG = i("ZOGLIN_SPAWN_EGG");
		ZOMBIE_SPAWN_EGG = i("ZOMBIE_SPAWN_EGG");
		ZOMBIE_VILLAGER_SPAWN_EGG = i("ZOMBIE_VILLAGER_SPAWN_EGG");
		ZOMBIEFIED_PIG_SPAWN_EGG = i("ZOMBIEFIED_PIG_SPAWN_EGG");
		EXPERIENCE_BOTTLE = i("EXPERIENCE_BOTTLE");
		FIRE_CHARGE = i("FIRE_CHARGE");
		WRITEABLE_BOOK = i("WRITEABLE_BOOK", 1);
		WRITTEN_BOOK = i("WRITTEN_BOOK", 16);
		EMERALD = i("EMERALD");
		ITEM_FRAME = i("ITEM_FRAME");
		FLOWER_POT = c("FLOWER_POT", 6309);
		POTTED_OAK_SAPLING = new Material(NamespacedKey.MINECRAFT, "POTTED_OAK_SAPLING", false, (short) 6310, (byte) 0, 0, 0);
		POTTED_SPRUCE_SAPLING = new Material(NamespacedKey.MINECRAFT, "POTTED_SPRUCE_SAPLING", false, (short) 6311, (byte) 0, 0, 0);
		POTTED_BIRCH_SAPLING = new Material(NamespacedKey.MINECRAFT, "POTTED_BIRCH_SAPLING", false, (short) 6312, (byte) 0, 0, 0);
		POTTED_JUNGLE_SAPLING = new Material(NamespacedKey.MINECRAFT, "POTTED_JUNGLE_SAPLING", false, (short) 6313, (byte) 0, 0, 0);
		POTTED_ACACIA_SAPLING = new Material(NamespacedKey.MINECRAFT, "POTTED_ACACIA_SAPLING", false, (short) 6314, (byte) 0, 0, 0);
		POTTED_DARK_OAK_SAPLING = new Material(NamespacedKey.MINECRAFT, "POTTED_DARK_OAK_SAPLING", false, (short) 6315, (byte) 0, 0, 0);
		POTTED_FERN = new Material(NamespacedKey.MINECRAFT, "POTTED_FERN", false, (short) 6316, (byte) 0, 0, 0);
		POTTED_DANDELION = new Material(NamespacedKey.MINECRAFT, "POTTED_DANDELION", false, (short) 6317, (byte) 0, 0, 0);
		POTTED_POPPY = new Material(NamespacedKey.MINECRAFT, "POTTED_POPPY", false, (short) 6318, (byte) 0, 0, 0);
		POTTED_BLUE_ORCHID = new Material(NamespacedKey.MINECRAFT, "POTTED_BLUE_ORCHID", false, (short) 6319, (byte) 0, 0, 0);
		POTTED_ALLIUM = new Material(NamespacedKey.MINECRAFT, "POTTED_ALLIUM", false, (short) 6320, (byte) 0, 0, 0);
		POTTED_AZURE_BLUET = new Material(NamespacedKey.MINECRAFT, "POTTED_AZURE_BLUET", false, (short) 6321, (byte) 0, 0, 0);
		POTTED_RED_TULIP = new Material(NamespacedKey.MINECRAFT, "POTTED_RED_TULIP", false, (short) 6322, (byte) 0, 0, 0);
		POTTED_ORANGE_TULIP = new Material(NamespacedKey.MINECRAFT, "POTTED_ORANGE_TULIP", false, (short) 6323, (byte) 0, 0, 0);
		POTTED_WHITE_TULIP = new Material(NamespacedKey.MINECRAFT, "POTTED_WHITE_TULIP", false, (short) 6324, (byte) 0, 0, 0);
		POTTED_PINK_TULIP = new Material(NamespacedKey.MINECRAFT, "POTTED_PINK_TULIP", false, (short) 6325, (byte) 0, 0, 0);
		POTTED_OXEYE_DAISY = new Material(NamespacedKey.MINECRAFT, "POTTED_OXEYE_DAISY", false, (short) 6326, (byte) 0, 0, 0);
		POTTED_CORNFLOWER = new Material(NamespacedKey.MINECRAFT, "POTTED_CORNFLOWER", false, (short) 6327, (byte) 0, 0, 0);
		POTTED_LILY_OF_THE_VALLEY = new Material(NamespacedKey.MINECRAFT, "POTTED_LILY_OF_THE_VALLEY", false, (short) 6328, (byte) 0, 0, 0);
		POTTED_WITHER_ROSE = new Material(NamespacedKey.MINECRAFT, "POTTED_WITHER_ROSE", false, (short) 6329, (byte) 0, 0, 0);
		POTTED_RED_MUSHROOM = new Material(NamespacedKey.MINECRAFT, "POTTED_RED_MUSHROOM", false, (short) 6330, (byte) 0, 0, 0);
		POTTED_BROWN_MUSHROOM = new Material(NamespacedKey.MINECRAFT, "POTTED_BROWN_MUSHROOM", false, (short) 6331, (byte) 0, 0, 0);
		POTTED_DEAD_BUSH = new Material(NamespacedKey.MINECRAFT, "POTTED_DEAD_BUSH", false, (short) 6332, (byte) 0, 0, 0);
		POTTED_CACTUS = new Material(NamespacedKey.MINECRAFT, "POTTED_CACTUS", false, (short) 6333, (byte) 0, 0, 0);
		POTTED_CRIMSON_FUNGUS = new Material(NamespacedKey.MINECRAFT, "POTTED_CRIMSON_FUNGUS", false, (short) 15842, (byte) 0, 0, 0);
		POTTED_WARPED_FUNGUS = new Material(NamespacedKey.MINECRAFT, "POTTED_WARPED_FUNGUS", false, (short) 15843, (byte) 0, 0, 0);
		POTTED_CRIMSON_ROOTS = new Material(NamespacedKey.MINECRAFT, "POTTED_CRIMSON_ROOTS", false, (short) 15844, (byte) 0, 0, 0);
		POTTED_WARPED_ROOTS = new Material(NamespacedKey.MINECRAFT, "POTTED_WARPED_ROOTS", false, (short) 15845, (byte) 0, 0, 0);
		CARROT = i("CARROT");
		CARROTS = new Material(NamespacedKey.MINECRAFT, "CARROTS", false, (short) 6334, (byte) 0, 0, 0, AGEABLE7_MDF);
		POTATO = i("POTATO");
		POTATOES = new Material(NamespacedKey.MINECRAFT, "POTATOES", false, (short) 6342, (byte) 0, 0, 0, AGEABLE7_MDF);
		BAKED_POTATO = i("BAKED_POTATO");
		POISONOUS_POTATO = i("POISONOUS_POTATO");
		MAP = i("MAP");
		GOLDEN_CARROT = i("GOLDEN_CARROT");
		SKELETON_SKULL = c("SKELETON_SKULL", 6494, ROTATABLE_MDF);
		SKELETON_WALL_SKULL = new Material(NamespacedKey.MINECRAFT, "SKELETON_WALL_SKULL", false, (short) 6510, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		WITHER_SKELETON_SKULL = c("WITHER_SKELETON_SKULL", 6514, ROTATABLE_MDF);
		WITHER_SKELETON_WALL_SKULL = new Material(NamespacedKey.MINECRAFT, "WITHER_SKELETON_WALL_SKULL", false, (short) 6530, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		PLAYER_HEAD = c("PLAYER_HEAD", 6554, PLAYER_HEAD_MDF);
		PLAYER_WALL_HEAD = new Material(NamespacedKey.MINECRAFT, "PLAYER_WALL_HEAD", false, (short) 6570, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		ZOMBIE_HEAD = c("ZOMBIE_HEAD", 6534, ROTATABLE_MDF);
		ZOMBIE_WALL_HEAD = new Material(NamespacedKey.MINECRAFT, "ZOMBIE_WALL_HEAD", false, (short) 6550, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		CREEPER_HEAD = c("CREEPER_HEAD", 6574, ROTATABLE_MDF);
		CREEPER_WALL_HEAD = new Material(NamespacedKey.MINECRAFT, "CREEPER_WALL_HEAD", false, (short) 6590, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		DRAGON_HEAD = c("DRAGON_HEAD", 6594, ROTATABLE_MDF);
		DRAGON_WALL_HEAD = new Material(NamespacedKey.MINECRAFT, "DRAGON_WALL_HEAD", false, (short) 6610, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		CARROT_ON_A_STICK = i("CARROT_ON_A_STICK", 1, DAMAGEABLE_MDF);
		WARPED_FUNGUS_ON_A_STICK = i("WARPED_FUNGUS_ON_A_STICK", 1, DAMAGEABLE_MDF);
		NETHER_STAR = i("NETHER_STAR");
		PUMPKIN_PIE = i("PUMPKIN_PIE");
		FIREWORK_ROCKET = i("FIREWORK_ROCKET", 64, FIREWORK_MDF);
		FIREWORK_STAR = i("FIREWORK_STAR", 64, FIREWORK_EFFECT_MDF);
		ENCHANTED_BOOK = i("ENCHANTED_BOOK", 1, ENCHANTMENT_STORAGE_MDF);
		NETHER_BRICK = i("NETHER_BRICK");
		QUARTZ = i("QUARTZ");
		TNT_MINECART = i("TNT_MINECART", 1);
		HOPPER_MINECART = i("HOPPER_MINECART", 1);
		PRISMARINE_SHARD = i("PRISMARINE_SHARD");
		PRISMARINE_CRYSTALS = i("PRISMARINE_CRYSTALS");
		RABBIT = i("RABBIT");
		COOKED_RABBIT = i("COOKED_RABBIT");
		RABBIT_STEW = i("RABBIT_STEW", 1);
		RABBIT_FOOT = i("RABBIT_FOOT");
		RABBIT_HIDE = i("RABBIT_HIDE");
		ARMOR_STAND = i("ARMOR_STAND", 16);
		IRON_HORSE_ARMOR = i("IRON_HORSE_ARMOR", 1);
		GOLDEN_HORSE_ARMOR = i("GOLDEN_HORSE_ARMOR", 1);
		DIAMOND_HORSE_ARMOR = i("DIAMOND_HORSE_ARMOR", 1);
		LEATHER_HORSE_ARMOR = i("LEATHER_HORSE_ARMOR", 1);
		LEAD = i("LEAD");
		NAME_TAG = i("NAME_TAG");
		COMMAND_BLOCK_MINECART = i("COMMAND_BLOCK_MINECART", 1);
		MUTTON = i("MUTTON");
		COOKED_MUTTON = i("COOKED_MUTTON");
		WHITE_BANNER = c("WHITE_BANNER", 7901, BANNER_MDF);
		ORANGE_BANNER = c("ORANGE_BANNER", 7917, BANNER_MDF);
		MAGENTA_BANNER = c("MAGENTA_BANNER", 7933, BANNER_MDF);
		LIGHT_BLUE_BANNER = c("LIGHT_BLUE_BANNER", 7949, BANNER_MDF);
		YELLOW_BANNER = c("YELLOW_BANNER", 7965, BANNER_MDF);
		LIME_BANNER = c("LIME_BANNER", 7981, BANNER_MDF);
		PINK_BANNER = c("PINK_BANNER", 7997, BANNER_MDF);
		GRAY_BANNER = c("GRAY_BANNER", 8013, BANNER_MDF);
		LIGHT_GRAY_BANNER = c("LIGHT_GRAY_BANNER", 8029, BANNER_MDF);
		CYAN_BANNER = c("CYAN_BANNER", 8045, BANNER_MDF);
		PURPLE_BANNER = c("PURPLE_BANNER", 8061, BANNER_MDF);
		BLUE_BANNER = c("BLUE_BANNER", 8077, BANNER_MDF);
		BROWN_BANNER = c("BROWN_BANNER", 8093, BANNER_MDF);
		GREEN_BANNER = c("GREEN_BANNER", 8109, BANNER_MDF);
		RED_BANNER = c("RED_BANNER", 8125, BANNER_MDF);
		BLACK_BANNER = c("BLACK_BANNER", 8141, BANNER_MDF);
		WHITE_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "WHITE_WALL_BANNER", false, (short) 8157, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		ORANGE_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "ORANGE_WALL_BANNER", false, (short) 8161, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		MAGENTA_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "MAGENTA_WALL_BANNER", false, (short) 8165, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		LIGHT_BLUE_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "LIGHT_BLUE_WALL_BANNER", false, (short) 8169, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		YELLOW_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "YELLOW_WALL_BANNER", false, (short) 8173, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		LIME_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "LIME_WALL_BANNER", false, (short) 8177, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		PINK_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "PINK_WALL_BANNER", false, (short) 8181, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		GRAY_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "GRAY_WALL_BANNER", false, (short) 8185, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		LIGHT_GRAY_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "LIGHT_GRAY_WALL_BANNER", false, (short) 8189, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		CYAN_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "CYAN_WALL_BANNER", false, (short) 8193, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		PURPLE_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "PURPLE_WALL_BANNER", false, (short) 8197, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		BLUE_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "BLUE_WALL_BANNER", false, (short) 8201, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		BROWN_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "BROWN_WALL_BANNER", false, (short) 8205, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		GREEN_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "GREEN_WALL_BANNER", false, (short) 8209, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		RED_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "RED_WALL_BANNER", false, (short) 8213, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		BLACK_WALL_BANNER = new Material(NamespacedKey.MINECRAFT, "BLACK_WALL_BANNER", false, (short) 8217, (byte) 0, 0, 0, DIRECTIONAL4F_MDF);
		END_CRYSTAL = i("END_CRYSTAL");
		CHORUS_FRUIT = i("CHORUS_FRUIT");
		POPPED_CHORUS_FRUIT = i("POPPED_CHORUS_FRUIT");
		BEETROOT = i("BEETROOT");
		BEETROOTS = new Material(NamespacedKey.MINECRAFT, "BEETROOTS", false, (short) 9223, (byte) 0, 0, 0, AGEABLE3_MDF);
		BEETROOT_SOUP = i("BEETROOT_SOUP");
		DRAGON_BREATH = i("DRAGON_BREATH");
		SPLASH_POTION = i("SPLASH_POTION", 64, POTION_MDF);
		SPECTRAL_ARROW = i("SPECTRAL_ARROW");
		TIPPED_ARROW = i("TIPPED_ARROW", 64, POTION_MDF);
		LINGERING_POTION = i("LINGERING_POTION", 64, POTION_MDF);
		SHIELD = i("SHIELD", 1, DAMAGEABLE_MDF);
		ELYTRA = i("ELYTRA", 1, DAMAGEABLE_MDF);
		SPRUCE_BOAT = i("SPRUCE_BOAT", 1);
		BIRCH_BOAT = i("BIRCH_BOAT", 1);
		JUNGLE_BOAT = i("JUNGLE_BOAT", 1);
		ACACIA_BOAT = i("ACACIA_BOAT", 1);
		DARK_OAK_BOAT = i("DARK_OAK_BOAT", 1);
		TOTEM_OF_UNDYING = i("TOTEM_OF_UNDYING", 1);
		SHULKER_SHELL = i("SHULKER_SHELL");
		IRON_NUGGET = i("IRON_NUGGET");
		KNOWLEDGE_BOOK = i("KNOWLEDGE_BOOK", 1, KNOWLEDGE_BOOK_MDF);
		DEBUG_STICK = i("DEBUG_STICK", 1);
		MUSIC_DISC_13 = i("MUSIC_DISC_13", 1);
		MUSIC_DISC_CAT = i("MUSIC_DISC_CAT", 1);
		MUSIC_DISC_BLOCKS = i("MUSIC_DISC_BLOCKS", 1);
		MUSIC_DISC_CHIRP = i("MUSIC_DISC_CHIRP", 1);
		MUSIC_DISC_FAR = i("MUSIC_DISC_FAR", 1);
		MUSIC_DISC_MALL = i("MUSIC_DISC_MALL", 1);
		MUSIC_DISC_STAL = i("MUSIC_DISC_STAL", 1);
		MUSIC_DISC_STRAD = i("MUSIC_DISC_STRAD", 1);
		MUSIC_DISC_WARD = i("MUSIC_DISC_WARP", 1);
		MUSIC_DISC_11 = i("MUSIC_DISC_11", 1);
		MUSIC_DISC_WAIT = i("MUSIC_DISC_WAIT", 1);
		MUSIC_DISC_PIGSTEP = i("MUSIC_DISC_PIGSTEP", 1);
		TRIDENT = i("TRIDENT", 1);
		PHANTOM_MEMBRANE = i("PHANTOM_MEMBRAN");
		NAUTILUS_SHELL = i("NAUTILUS_SHELL");
		HEART_OF_THE_SEA = i("HEART_OF_THE_SEA");
		CROSSBOW = i("CROSSBOW", 1, CROSSBOW_MDF);
		SUSPICIOUS_STEW = i("SUSPICIOUS_STEW", 1, SUSPICIOUS_STEW_MDF);
		LOOM = c("LOOM", 14791, DIRECTIONAL4F_MDF);
		FLOWER_BANNER_PATTERN = i("FLOWER_BANNER_PATTERN", 1);
		CREEPER_BANNER_PATTERN = i("CREEPER_BANNER_PATTERN", 1);
		SKULL_BANNER_PATTERN = i("SKULL_BANNER_PATTERN", 1);
		MOJANG_BANNER_PATTERN = i("MOJANG_BANNER_PATTER", 1);
		GLOBE_BANNER_PATTERN = i("GLOBE_BANNER_PATTERN", 1);
		PIGLIN_BANNER_PATTERN = i("PIGLIN_BANNER_PATTERN", 1);
		COMPOSTER = c("COMPOSTER", 15759, LEVELLED_MDF);
		BARREL = c("BARREL", 14795, BARREL_MDF);
		SMOKER = c("SMOKER", 14807, FURNACE_MDF);
		BLAST_FURNACE = c("BLAST_FURNACE", 14815, FURNACE_MDF);
		CARTOGRAPHY_TABLE = c("CARTOGRAPHY_TABLE", 14823);
		FLETCHING_TABLE = c("FLETCHING_TABLE", 14824);
		GRINDSTONE = c("GRINDSTONE", 14825, GRINDSTONE_MDF);
		LECTERN = c("LECTERN", 14837, LECTERN_MDF);
		SMITHING_TABLE = c("SMITHING_TABLE", 14853);
		STONECUTTER = c("STONECUTTER", 14854, DIRECTIONAL4F_MDF);
		BELL = c("BELL", 14858, BELL_MDF);
		LANTERN = c("LANTERN", 14890, LANTERN_MDF);
		SOUL_LANTERN = c("SOUL_LANTERN", 14894, LANTERN_MDF);
		SWEET_BERRIES = i("SWEET_BERRIES");
		SWEET_BERRY_BUSH = c("SWEET_BERRY_BUSH", 14962, AGEABLE3_MDF);
		CAMPFIRE = c("CAMPFIRE", 14898, CAMPFIRE_MDF);
		SOUL_CAMPFIRE = c("SOUL_CAMPFIRE", 14930, CAMPFIRE_MDF);
		SHROOMLIGHT = c("SHROOMLIGHT", 14997);
		HONEYCOMB = i("HONEYCOMB");
		BEE_NEST = c("BEE_NEST", 15784, BEEHIVE_MDF);
		BEEHIVE = c("BEEHIVE", 15808, BEEHIVE_MDF);
		HONEY_BOTTLE = i("HONEY_BOTTLE", 16);
		HONEY_BLOCK = c("HONEY_BLOCK", 15832);
		HONEYCOMB_BLOCK = c("HONEYCOMB_BLOCK", 15833);
		LODESTONE = c("LODESTONE", 15846);
		NETHERITE_BLOCK = c("NETHERITE_BLOCK", 15834);
		ANCIENT_DEBRIS = c("ANCIENT_DEBRIS", 15835);
		TARGET = c("TARGET", 15768, ANALOGUE_POWERABLE_MDF);
		CRYING_OBSIDIAN = c("CRYING_OBSIDIAN", 15836);
		BLACKSTONE = c("BLACKSTONE", 15847);
		BLACKSTONE_SLAB = c("BLACKSTONE_SLAB", 16252, SLAB_MDF);
		BLACKSTONE_STAIRS = c("BLACKSTONE_STAIRS", 15848, STAIRS_MDF);
		GILDED_BLACKSTONE = c("GILDED_BLACKSTONE", 16672);
		POLISHED_BLACKSTONE = c("POLISHED_BLACKSTONE", 16258);
		POLISHED_BLACKSTONE_SLAB = c("POLISHED_BLACKSTONE_SLAB", 16753, SLAB_MDF);
		POLISHED_BLACKSTONE_STAIRS = c("POLISHED_BLACKSTONE_STAIRS", 16673, STAIRS_MDF);
		CHISELED_POLISHED_BLACKSTONE = c("CHISELED_BLACKSTONE", 16261);
		POLISHED_BLACKSTONE_BRICKS = c("POLISHED_BLACKSTONE_BRICKS", 16259);
		POLISHED_BLACKSTONE_BRICK_SLAB = c("POLISHED_BLACKSTONE_BRICK_SLAB", 16262, SLAB_MDF);
		POLISHED_BLACKSTONE_BRICK_STAIRS = c("POLISHED_BLACKSTONE_BRICK_STAIRS", 16268, STAIRS_MDF);
		CRACKED_POLISHED_BLACKSTONE_BRICKS = c("CRACKED_POLISHED_BLACKSTONE_BRICKS", 16260);
		RESPAWN_ANCHOR = c("RESPAWN_ANCHOR", 15837, RESPAWN_ANCHOR_MDF);
		
		//--- TileEntityFactory --------------------------------------------------------------------------------
		
		TileEntityFactory
		BANNER_TEF = new ClassTileEntityFactory(Banner.class, CoreBanner.class),
		BARREL_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Barrel.class, de.atlascore.block.tile.CoreBarrel.class),
		BEACON_TEF = new ClassTileEntityFactory(Beacon.class, CoreBeacon.class),
		BEEHIVE_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Beehive.class, de.atlascore.block.tile.CoreBeehive.class),
		BREWING_STAND_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.BrewingStand.class, de.atlascore.block.tile.CoreBrewingStand.class),
		CAMPFIRE_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Campfire.class, de.atlascore.block.tile.CoreCampfire.class),
		COMMAND_BLOCK_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.CommandBlock.class, de.atlascore.block.tile.CoreCommandBlock.class),
		DISPENSER_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Dispenser.class, de.atlascore.block.tile.CoreDispenser.class),
		DROPPER_TEF = new ClassTileEntityFactory(Dropper.class, CoreDropper.class),
		ENCHANTING_TABLE_TEF = new ClassTileEntityFactory(EnchantingTable.class, CoreEnchantingTable.class),
		ENDER_CHEST_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.EnderChest.class, de.atlascore.block.tile.CoreEnderChest.class),
		END_GATEWAY_TEF = new ClassTileEntityFactory(EndGateway.class, CoreEndGateway.class),
		FURNACE_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Furnace.class, de.atlascore.block.tile.CoreFurnace.class),
		HOPPER_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Hopper.class, de.atlascore.block.tile.CoreHopper.class),
		JUKEBOX_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Jukebox.class, de.atlascore.block.tile.CoreJukebox.class),
		LECTERN_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Lectern.class, de.atlascore.block.tile.CoreLectern.class),
		MOB_SPAWNER_TEF = new ClassTileEntityFactory(MobSpawner.class, CoreMobSpawner.class),
		SHULKER_BOX_TEF = new ClassTileEntityFactory(ShulkerBox.class, CoreShulkerBox.class),
		SIGN_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Sign.class, de.atlascore.block.tile.CoreSign.class),
		SKULL_TEF = new ClassTileEntityFactory(Skull.class, CoreSkull.class),
		STRUCTURE_BLOCK_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.StructureBlock.class, de.atlascore.block.tile.CoreStructureBlock.class),
		CHEST_TEF = new ClassTileEntityFactory(de.atlasmc.block.tile.Chest.class, de.atlascore.block.tile.CoreChest.class);
		
		WHITE_BANNER.setTileEntityFactory(BANNER_TEF);
		ORANGE_BANNER.setTileEntityFactory(BANNER_TEF);
		MAGENTA_BANNER.setTileEntityFactory(BANNER_TEF);
		LIGHT_BLUE_BANNER.setTileEntityFactory(BANNER_TEF);
		YELLOW_BANNER.setTileEntityFactory(BANNER_TEF);
		LIME_BANNER.setTileEntityFactory(BANNER_TEF);
		PINK_BANNER.setTileEntityFactory(BANNER_TEF);
		GRAY_BANNER.setTileEntityFactory(BANNER_TEF);
		LIGHT_GRAY_BANNER.setTileEntityFactory(BANNER_TEF);
		CYAN_BANNER.setTileEntityFactory(BANNER_TEF);
		PURPLE_BANNER.setTileEntityFactory(BANNER_TEF);
		BLUE_BANNER.setTileEntityFactory(BANNER_TEF);
		BROWN_BANNER.setTileEntityFactory(BANNER_TEF);
		GREEN_BANNER.setTileEntityFactory(BANNER_TEF);
		RED_BANNER.setTileEntityFactory(BANNER_TEF);
		BLACK_BANNER.setTileEntityFactory(BANNER_TEF);
		WHITE_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		ORANGE_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		MAGENTA_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		LIGHT_BLUE_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		YELLOW_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		LIME_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		PINK_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		GRAY_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		LIGHT_GRAY_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		CYAN_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		PURPLE_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		BLUE_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		BROWN_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		GREEN_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		RED_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		BLACK_WALL_BANNER.setTileEntityFactory(BANNER_TEF);
		
		BARREL.setTileEntityFactory(BARREL_TEF);
		
		BEACON.setTileEntityFactory(BEACON_TEF);
		
		BEEHIVE.setTileEntityFactory(BEEHIVE_TEF);
		BEE_NEST.setTileEntityFactory(BEEHIVE_TEF);
		
		BREWING_STAND.setTileEntityFactory(BREWING_STAND_TEF);
		
		CAMPFIRE.setTileEntityFactory(CAMPFIRE_TEF);
		
		COMMAND_BLOCK.setTileEntityFactory(COMMAND_BLOCK_TEF);
		CHAIN_COMMAND_BLOCK.setTileEntityFactory(COMMAND_BLOCK_TEF);
		REPEATING_COMMAND_BLOCK.setTileEntityFactory(COMMAND_BLOCK_TEF);
		
		DISPENSER.setTileEntityFactory(DISPENSER_TEF);
		
		DROPPER.setTileEntityFactory(DROPPER_TEF);
		
		ENCHANTING_TABLE.setTileEntityFactory(ENCHANTING_TABLE_TEF);
		
		ENDER_CHEST.setTileEntityFactory(ENDER_CHEST_TEF);
		
		END_GATEWAY.setTileEntityFactory(END_GATEWAY_TEF);
		
		FURNACE.setTileEntityFactory(FURNACE_TEF);
		SMOKER.setTileEntityFactory(FURNACE_TEF);
		BLAST_FURNACE.setTileEntityFactory(FURNACE_TEF);
		
		HOPPER.setTileEntityFactory(HOPPER_TEF);
		
		JUKEBOX.setTileEntityFactory(JUKEBOX_TEF);
		
		LECTERN.setTileEntityFactory(LECTERN_TEF);
		
		SPAWNER.setTileEntityFactory(MOB_SPAWNER_TEF);
		
		SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		BLACK_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		BLUE_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		BROWN_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		CYAN_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		GRAY_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		GREEN_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		LIGHT_BLUE_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		LIGHT_GRAY_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		LIME_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		MAGENTA_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		ORANGE_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		PINK_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		PURPLE_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		RED_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		WHITE_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		YELLOW_SHULKER_BOX.setTileEntityFactory(SHULKER_BOX_TEF);
		
		ACACIA_SIGN.setTileEntityFactory(SIGN_TEF);
		ACACIA_WALL_SIGN.setTileEntityFactory(SIGN_TEF);
		BIRCH_SIGN.setTileEntityFactory(SIGN_TEF);
		BIRCH_WALL_SIGN.setTileEntityFactory(SIGN_TEF);
		CRIMSON_SIGN.setTileEntityFactory(SIGN_TEF);
		CRIMSON_WALL_SIGN.setTileEntityFactory(SIGN_TEF);
		DARK_OAK_SIGN.setTileEntityFactory(SIGN_TEF);
		DARK_OAK_WALL_SIGN.setTileEntityFactory(SIGN_TEF);
		JUNGLE_SIGN.setTileEntityFactory(SIGN_TEF);
		JUNGLE_WALL_SIGN.setTileEntityFactory(SIGN_TEF);
		OAK_SIGN.setTileEntityFactory(SIGN_TEF);
		OAK_WALL_SIGN.setTileEntityFactory(SIGN_TEF);
		SPRUCE_SIGN.setTileEntityFactory(SIGN_TEF);
		SPRUCE_WALL_SIGN.setTileEntityFactory(SIGN_TEF);
		WARPED_SIGN.setTileEntityFactory(SIGN_TEF);
		WARPED_WALL_SIGN.setTileEntityFactory(SIGN_TEF);
		
		PLAYER_HEAD.setTileEntityFactory(SKULL_TEF);
		PLAYER_WALL_HEAD.setTileEntityFactory(SKULL_TEF);
		
		STRUCTURE_BLOCK.setTileEntityFactory(STRUCTURE_BLOCK_TEF);
		
		CHEST.setTileEntityFactory(CHEST_TEF);
		TRAPPED_CHEST.setTileEntityFactory(CHEST_TEF);
	}

	private static Material c(String name, int blockID) {
		return new Material(NamespacedKey.MINECRAFT, name, (short) blockID, (byte) 64, 0, 0, null);
	}

	private static Material c(String name, int blockID, MetaDataFactory mdf) {
		return new Material(NamespacedKey.MINECRAFT, name, (short) blockID, (byte) 64, 0, 0, mdf);
	}

	private static Material c(String name, int blockID, int amount, MetaDataFactory mdf) {
		return new Material(NamespacedKey.MINECRAFT, name, (short) blockID, (byte) amount, 0, 0, mdf);
	}

	private static Material i(String name) {
		return new Material(NamespacedKey.MINECRAFT, name, (byte) 64, 0, 0, null);
	}

	private static Material i(String name, int amount) {
		return new Material(NamespacedKey.MINECRAFT, name, (byte) amount, 0, 0, null);
	}

	private static Material i(String name, int amount, MetaDataFactory mdf) {
		return new Material(NamespacedKey.MINECRAFT, name, (byte) amount, 0, 0, mdf);
	}

}
