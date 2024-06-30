package de.atlascore.test.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import de.atlascore.event.inventory.CoreInventoryListener;
import de.atlascore.event.player.CorePlayerListener;
import de.atlascore.plugin.CorePluginManager;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.MethodEventExecutor;
import de.atlastest.util.ReflectionUtil;

class EventDefaultListenerPresentsTest {

	int totalWarnings = 0;
	int totalTested;
	int tested = 0;
	int warnings = 0;
	
	boolean printMissingClasses = true;
	
	private static void initDefaultExecutor(Listener listener) {
		List<EventExecutor> exes = MethodEventExecutor.getExecutors(CorePluginManager.SYSTEM, listener);
		for (EventExecutor exe : exes) {
			Class<? extends Event> clazz = exe.getEventClass();
			HandlerList handlers = HandlerList.getHandlerListOf(clazz);
			handlers.setDefaultExecutor(exe);
		}
	}
	
	@Test
	void testBlockEvents() throws Exception {
		testEventPacket("de.atlasmc.event.block");
	}
	
	@Test
	void testEntityEvents() throws Exception {
		testEventPacket("de.atlasmc.event.entity");
	}
	
	@Test
	void testInventoryEvents() throws Exception {
		initDefaultExecutor(new CoreInventoryListener());
		testEventPacket("de.atlasmc.event.inventory");
	}
	
	@Test
	void testNetworkEvents() throws Exception {
		testEventPacket("de.atlasmc.event.network");
	}
	
	@Test
	void testPlayerEvents() throws Exception {
		initDefaultExecutor(new CorePlayerListener());
		testEventPacket("de.atlasmc.event.player");
	}
	
	@Test
	void testProxyEvents() throws Exception {
		testEventPacket("de.atlasmc.event.proxy");
	}
	
	void testEventPacket(String packet) throws Exception {
		LinkedList<Executable> checks = new LinkedList<>();
		ReflectionUtil.getClassesInPacket(packet, (clazz) -> {
			if (!Event.class.isAssignableFrom(clazz)) 
				return;
			for (Method m : clazz.getMethods()) {
				if (!m.getName().equals("getHandlerList")) 
					continue;
				if (!Modifier.isStatic(m.getModifiers())) 
					continue;
				m.setAccessible(true);
				HandlerList handlers = null;
				try {
					handlers = (HandlerList) m.invoke(clazz);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				if (handlers.getDefaultExecutor() == null || handlers.getDefaultExecutor() == EventExecutor.NULL_EXECUTOR) {
					checks.add(() -> Assertions.fail("Missing DefaultExecutor: " + clazz.getName()));
				}
				break;
			}
		});
		Assertions.assertAll("Missing default Executors", checks);
	}

}
