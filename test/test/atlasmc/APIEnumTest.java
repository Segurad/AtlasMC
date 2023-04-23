package test.atlasmc;

import org.junit.Test;

import de.atlasmc.Axis;
import de.atlasmc.BossBar.BarColor;
import de.atlasmc.BossBar.BarStyle;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatType;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.Difficulty;
import de.atlasmc.Direction;
import de.atlasmc.DyeColor;
import de.atlasmc.Effect;
import de.atlasmc.Gamemode;
import de.atlasmc.Instrument;
import de.atlasmc.Particle;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;

import static test.util.EnumTest.testCacheMethods;
import static test.util.EnumTest.testIDMethods;

public class APIEnumTest {
	
	@Test
	public void test() {
		testCacheMethods(Axis.class);
		testCacheAndID(Difficulty.class);
		testCacheAndID(DyeColor.class);
		testCacheAndID(Direction.class);
		testCacheAndID(Effect.class);
		testCacheAndID(Gamemode.class);
		testCacheMethods(Instrument.class);
		testCacheAndID(Particle.class);
		testCacheAndID(Sound.class);
		testCacheAndID(SoundCategory.class);
		testCacheAndID(BarColor.class);
		// BarFlag.class
		testCacheAndID(BarStyle.class);
		testCacheAndID(ChatType.class);
		testCacheAndID(ChatColor.class);
		testCacheAndID(InventoryType.class);
	}
	
	private void testCacheAndID(Class<? extends Enum<?>> clazz) {
		testCacheMethods(clazz);
		testIDMethods(clazz);
	}

}
