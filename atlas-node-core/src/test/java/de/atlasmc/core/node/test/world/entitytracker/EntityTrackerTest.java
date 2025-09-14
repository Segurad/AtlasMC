package de.atlasmc.core.node.test.world.entitytracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import de.atlasmc.core.node.entity.CoreEntity;
import de.atlasmc.core.node.world.entitytracker.CoreEntityTracker;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.world.entitytracker.EntityTracker;
import de.atlasmc.node.world.entitytracker.TrackerBinding;

public class EntityTrackerTest {
	
	@Test
	public void testRegister() {
		EntityTracker tracker = new CoreEntityTracker();
		assertTrue(tracker.getEntities().isEmpty(), "Tracker was not emtpy!");
		
		UUID uuid = UUID.randomUUID();
		Entity entity = new CoreEntity(null);
		entity.setUUID(uuid);
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
	
	@Test
	public void testMove() {
		EntityTracker tracker = new CoreEntityTracker();
		assertTrue(tracker.getEntities().isEmpty(), "Tracker was not emtpy!");
		
		Entity entity = new CoreEntity(null);
		TrackerBinding binding = tracker.register(entity, null);
		assertNotNull(binding, "Binding was null!");
		assertEquals(1, tracker.getEntities().size(), "Entity was not registered!");
		
		assertEquals(1, tracker.getEntities(0, 0).size(), "Entity was not in 0,0!");
		binding.updatePosition(64, 0, 64);
		assertEquals(0, tracker.getEntities(0, 0).size(), "Entity was still in 0,0!");
		assertEquals(1, tracker.getEntities(4, 4).size(), "Entity was not in 4,4!");
	}

}
