package de.atlascore.test;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlastest.util.ReflectionUtil;

/**
 * Tests classes that extends {@link AbstractNBTBase}
 * If static field NBT_FIELDS is present
 * check for getFieldContainerRoot
 */
public class NBT_FIELDSTest implements Consumer<Class<?>>{

	@Test
	public void test() throws ClassNotFoundException {
		ReflectionUtil.getClassesInPacket("de.atlascore.entity", this);
		ReflectionUtil.getClassesInPacket("de.atlascore.block.tile", this);
		ReflectionUtil.getClassesInPacket("de.atlascore.block.data", this);
		ReflectionUtil.getClassesInPacket("de.atlascore.block.data.type", this);
		ReflectionUtil.getClassesInPacket("de.atlascore.inventory.meta", this);
	}
	
	@Override
	public void accept(Class<?> t) {
		try {
			if (t.getDeclaredField("NBT_FIELDS") != null) {
				try {
					t.getDeclaredMethod("getFieldContainerRoot");
				} catch (NoSuchMethodException e) {
					Assertions.fail("No getFieldContainerRoot while NBT_FIELDS is present: " + t.getName());
				}
			}
		} catch (NoSuchFieldException e) {
			// no check required
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

}
