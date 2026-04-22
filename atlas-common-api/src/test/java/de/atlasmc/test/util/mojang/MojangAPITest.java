package de.atlasmc.test.util.mojang;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.mojang.MojangAPI;

public class MojangAPITest {

	private static final String UUID_STRING = "853c80ef-3c37-49fd-aa49-938b674adae6";
	private static final UUID UUID_VALUE = UUID.fromString(UUID_STRING);
	private static final String UUID_NO_SPACE = "853c80ef3c3749fdaa49938b674adae6";
	private static final String PLAYER_NAME = "jeb_";
	
	@Test
	public void testIDStringSpaced() {
		Assertions.assertEquals(UUID_STRING, MojangAPI.spaceIDString(UUID_NO_SPACE));
	}
	
	@Test
	public void testUUIDToIDString() {
		Assertions.assertEquals(UUID_NO_SPACE, MojangAPI.uuidToIDString(UUID.fromString(UUID_STRING)));
	}
	
	@Test
	public void testIDStringToUUID() {
		Assertions.assertEquals(UUID.fromString(UUID_STRING), MojangAPI.uuidFromString(UUID_NO_SPACE));
	}
	
	@Test
	public void testPlayerByName() throws IOException, InterruptedException {
		MojangAPI api = new MojangAPI();
		var info = api.playerByName(PLAYER_NAME);
		Assertions.assertNotNull(info);
		Assertions.assertEquals(PLAYER_NAME, info.name());
		Assertions.assertEquals(UUID_VALUE, info.uuid());
		api.close();
	}
	
	@Test
	public void testPlayerByID() throws IOException, InterruptedException {
		MojangAPI api = new MojangAPI();
		var info = api.playerByUUID(UUID_NO_SPACE);
		Assertions.assertNotNull(info);
		Assertions.assertEquals(PLAYER_NAME, info.name());
		Assertions.assertEquals(UUID_VALUE, info.uuid());
		api.close();
	}
	
	@Test
	public void testPlayerByUUID() throws IOException, InterruptedException {
		MojangAPI api = new MojangAPI();
		var info = api.playerByUUID(UUID.fromString(UUID_STRING));
		Assertions.assertNotNull(info);
		Assertions.assertEquals(PLAYER_NAME, info.name());
		Assertions.assertEquals(UUID_VALUE, info.uuid());
		api.close();
	}
	
}
