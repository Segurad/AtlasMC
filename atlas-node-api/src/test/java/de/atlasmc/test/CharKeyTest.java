package de.atlasmc.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.map.key.CharKey;

public class CharKeyTest {
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testCharKeyHash() {
		final String[] values = { 
		"prQnpZGt7YV^So64Mgs48XuX8H%cb%4t!VKTy#TpJGzj4P!FUSKQb&nG848gc$vBXi3Ji&2pMbpG5o",
		"rv^Bd9UQN9fmNi#tTdn%dyte9wC3FD&f$zNuVu85TLWa7BqvLa6U8pmma#kaw@h@eJ&odhMWKLyorR",
		"cKNw8xgaYESsko5hE!BMLVNopbHwUZ4Fg2Vjq#TdHkC6aGJxAhM&c*ZQ^$A45fz94njmp%",
		"mF5SFY5E9Tm&ruZyFKt@8pi*gtun@3#V^c$zuMJ62iQ7meY^cp#UqWvfhc8p4RZ2",
		"9WRF9uZ32uzAxchVvELG7wCg4YkipHp@#v^DMEhV86Hq@xGV!#Yk*oz3T"
		};
		for (String value : values) {
			CharKey key = CharKey.of(value);
			if (!key.equals(value))
				Assertions.fail("Key not equals value!");
			if (key.hashCode() != value.hashCode())
				Assertions.fail("Key hashCode does not match value hashCode (" + key.hashCode() + " : " + value.hashCode());
		}
		
	}

}
