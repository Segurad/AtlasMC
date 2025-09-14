package de.atlasmc.node.test.event;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.atlasmc.event.Event;
import de.atlasmc.test.util.ReflectionUtil;

public class EventStaticGetHandlerListTest {
	
	@Test
	void testBlockEvents() throws Exception {
		testEventPacket("de.atlasmc.event.block");
	}
	
	@Test
	void testEntityEvents() throws ClassNotFoundException {
		testEventPacket("de.atlasmc.event.entity");
	}
	
	@Test
	void testInventoryEvents() throws ClassNotFoundException {
		testEventPacket("de.atlasmc.event.inventory");
	}
	
	@Test
	void testNetworkEvents() throws ClassNotFoundException {
		testEventPacket("de.atlasmc.event.network");
	}
	
	@Test
	void testPlayerEvents() throws ClassNotFoundException {
		testEventPacket("de.atlasmc.event.player");
	}
	
	@Test
	void testProxyEvents() throws ClassNotFoundException {
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
