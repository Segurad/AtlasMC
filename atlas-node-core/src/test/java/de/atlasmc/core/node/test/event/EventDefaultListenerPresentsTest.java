package de.atlasmc.core.node.test.event;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import de.atlasmc.core.node.event.inventory.CoreInventoryListener;
import de.atlasmc.core.node.event.player.CorePlayerListener;
import de.atlasmc.core.plugin.CorePluginManager;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.MethodEventExecutor;
import de.atlasmc.test.util.ReflectionUtil;

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
	
	@Disabled("Waiting for implementation")
	@Test
	void testBlockEvents() throws Exception {
		testEventPacket("de.atlasmc.event.block");
	}
	
	@Disabled("Waiting for implementation")
	@Test
	void testEntityEvents() throws Exception {
		testEventPacket("de.atlasmc.event.entity");
	}
	
	@Disabled("Waiting for implementation")
	@Test
	void testInventoryEvents() throws Exception {
		initDefaultExecutor(new CoreInventoryListener());
		testEventPacket("de.atlasmc.event.inventory");
	}
	
	@Disabled("Waiting for implementation")
	@Test
	void testNetworkEvents() throws Exception {
		testEventPacket("de.atlasmc.event.network");
	}
	
	@Disabled("Waiting for implementation")
	@Test
	void testPlayerEvents() throws Exception {
		initDefaultExecutor(new CorePlayerListener());
		testEventPacket("de.atlasmc.event.player");
	}
	
	@Disabled("Waiting for implementation")
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
				HandlerList handlers = null;
				try {
					handlers = (HandlerList) m.invoke(clazz);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					fail(e);
				}
				if (handlers.getDefaultExecutor() == null || handlers.getDefaultExecutor() == EventExecutor.NULL_EXECUTOR) {
					checks.add(() -> fail("Missing DefaultExecutor: " + clazz.getName()));
				}
				break;
			}
		});
		assertAll("Missing default Executors", checks);
	}

}
