package de.atlasmc.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.atlasmc.NamespacedKey;

public class NamespacedKeyTest {
	
	private static final String
	TEST_NAMESPACED_KEY = "testspace:testkey",
	TEST_KEY = "testkey",
	TEST_NAMESPACE = "testspace";
	
	@Test
	public void testOfString() {
		NamespacedKey key = NamespacedKey.of(TEST_NAMESPACED_KEY);
		Assertions.assertNotNull(key, "NamespacedKey was null!");
		Assertions.assertEquals(TEST_NAMESPACED_KEY, key.toString(), "Full namespaced key missmatch!");
		Assertions.assertEquals(TEST_KEY, key.key(), "Key missmatch!");
		Assertions.assertEquals(TEST_NAMESPACE, key.namespace(), "Namespace missmatch!");
	}
	
	@Test
	public void testOfStringString() {
		NamespacedKey key = NamespacedKey.of(TEST_NAMESPACE, TEST_KEY);
		Assertions.assertNotNull(key, "NamespacedKey was null!");
		Assertions.assertEquals(TEST_NAMESPACED_KEY, key.toString(), "Full namespaced key missmatch!");
		Assertions.assertEquals(TEST_KEY, key.key(), "Key missmatch!");
		Assertions.assertEquals(TEST_NAMESPACE, key.namespace(), "Namespace missmatch!");
	}
	
	@Test
	public void testOfLiteral() {
		NamespacedKey key = NamespacedKey.literal(TEST_NAMESPACED_KEY);
		Assertions.assertNotNull(key, "NamespacedKey was null!");
		Assertions.assertEquals(TEST_NAMESPACED_KEY, key.toString(), "Full namespaced key missmatch!");
		Assertions.assertEquals(TEST_KEY, key.key(), "Key missmatch!");
		Assertions.assertEquals(TEST_NAMESPACE, key.namespace(), "Namespace missmatch!");
	}

}
