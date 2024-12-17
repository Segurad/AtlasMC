package de.atlascore.test.world.entitytracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import de.atlascore.entity.CoreEntity;
import de.atlascore.world.entitytracker.CoreEntityTracker;
import de.atlasmc.entity.Entity;
import de.atlasmc.world.entitytracker.EntityTracker;
import de.atlasmc.world.entitytracker.TrackerBinding;

public class EntityTrackerTest {
	
	@Test
	public void testRegister() {
		EntityTracker tracker = new CoreEntityTracker();
		assertTrue(tracker.getEntities().isEmpty(), "Tracker was not emtpy!");
		
		UUID uuid = UUID.randomUUID();
		Entity entity = new CoreEntity(null, uuid);
		TrackerBinding binding = tracker.register(entity, null);
		assertNotNull(binding, "Binding was null!");
		assertEquals(1, tracker.getEntities().size(), "Entity was not registered!");
		Entity returned = tracker.getEntity(binding.getID());
		assertSame(entity, returned, "By id returned not same entity!");
		returned = tracker.getEntity(uuid);
		assertSame(entity, returned, "By uuid returned not same entity!");
		binding.unregister();
		assertEquals(0, tracker.getEntities().size(), "Entity was not unregistered!");
	}

}
