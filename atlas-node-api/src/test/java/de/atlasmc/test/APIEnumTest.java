package de.atlasmc.test;

import de.atlasmc.Axis;
import de.atlasmc.BossBar.BarColor;
import de.atlasmc.BossBar.BarStyle;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.world.WorldEvent;
import de.atlasmc.Difficulty;
import de.atlasmc.Direction;
import de.atlasmc.DyeColor;
import de.atlasmc.Gamemode;
import de.atlasmc.Instrument;
import de.atlasmc.SoundCategory;

import static de.atlastest.util.EnumTest.testCacheAndID;
import static de.atlastest.util.EnumTest.testCacheMethods;

import org.junit.jupiter.api.Test;

public class APIEnumTest {
	
	@Test
	public void test() {
		testCacheMethods(Axis.class);
		testCacheAndID(Difficulty.class);
		testCacheAndID(DyeColor.class);
		testCacheAndID(Direction.class);
		testCacheAndID(WorldEvent.class);
		testCacheAndID(Gamemode.class);
		testCacheMethods(Instrument.class);
		testCacheAndID(SoundCategory.class);
		testCacheAndID(BarColor.class);
		// BarFlag.class
		testCacheAndID(BarStyle.class);
		testCacheAndID(ChatColor.class);
		testCacheAndID(InventoryType.class);
	}
	
	

}
