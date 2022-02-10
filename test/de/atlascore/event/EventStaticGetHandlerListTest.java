package de.atlascore.event;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.sun.jdi.event.Event;

import test.util.ReflectionUtil;

public class EventStaticGetHandlerListTest {
	
	@Test
	void test() throws Exception {
		testEventPacket("de.atlasmc.event.block");
		testEventPacket("de.atlasmc.event.entity");
		testEventPacket("de.atlasmc.event.inventory");
		testEventPacket("de.atlasmc.event.network");
		testEventPacket("de.atlasmc.event.player");
		testEventPacket("de.atlasmc.event.proxy");
	}

	private void testEventPacket(String string) throws ClassNotFoundException {
		ReflectionUtil.getClassesInPacket(string, (clazz) -> {
			if (!Event.class.isAssignableFrom(clazz))
				return;
			if (Modifier.isAbstract(clazz.getModifiers()))
				return;
			try {
				Method m = clazz.getDeclaredMethod("getHandlerList");
				if (!Modifier.isStatic(m.getModifiers()))
					Assertions.fail("getHandlerList method not static for class: " + clazz.getName());
			} catch (NoSuchMethodException e) {
				Assertions.fail("No static getHandlerList method found in class: " + clazz.getName());
			}
		});
	}

}
