package test.atlasmc.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;
import test.util.ReflectionUtil;

class EventDefaultListenerPresentsTest {

	int totalWarnings = 0, totalTested;
	int tested = 0, warnings = 0;
	
	boolean printMissingClasses = true;
	
	@Test
	void test() throws Exception {
		testEventPacket("de.atlasmc.event.block");
		testEventPacket("de.atlasmc.event.entity");
		testEventPacket("de.atlasmc.event.inventory");
		testEventPacket("de.atlasmc.event.network");
		testEventPacket("de.atlasmc.event.player");
		testEventPacket("de.atlasmc.event.proxy");
		System.out.println((totalTested-totalWarnings) + "|" + totalTested + "\tDefaultEventExecutors set...");
	}
	
	void testEventPacket(String packet) throws Exception {
		tested = 0;
		warnings = 0;
		ReflectionUtil.getClassesInPacket(packet, (clazz) -> {
			if (!Event.class.isAssignableFrom(clazz)) return;
			for (Method m : clazz.getMethods()) {
				if (!m.getName().equals("getHandlerList")) continue;
				if (!Modifier.isStatic(m.getModifiers())) continue;
				m.setAccessible(true);
				HandlerList handlers = null;
				try {
					handlers = (HandlerList) m.invoke(clazz);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				if (handlers.getDefaultExecutor() == null || handlers.getDefaultExecutor() == EventExecutor.NULL_EXECUTOR) {
					warnings++;
					if (printMissingClasses)
						System.out.println("[Warning] Event class does not have a DefaultExecutor: " + clazz.getName());
				}
				tested++;
				break;
			}
		});
		System.out.println((tested-warnings) + "|" + tested + "\tDefaultEventExecutors set in " + packet);
		totalTested += tested;
		totalWarnings += warnings;
	}

}
