package de.atlastest.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class PacketIOUtils {
	
	@SuppressWarnings("unchecked")
	public static HashMap<Integer, String>[] getPacketIDs(Class<?> clazz) throws Exception {
		Field[] fields = clazz.getFields();
		HashMap<Integer, String> packetIDsIn = new HashMap<>();
		HashMap<Integer, String> packetIDsOut = new HashMap<>();
		for (Field field : fields) {
			if (!Modifier.isStatic(field.getModifiers()))
				continue;
			if (field.getType() != int.class)
				continue;
			HashMap<Integer, String> map = null;
			if (field.getName().startsWith("IN_"))
				map = packetIDsIn;
			else if (field.getName().startsWith("OUT_"))
				map = packetIDsOut;
			else
				continue;
			int packetID = (int) field.get(null);
			map.put(packetID, field.getName());
		}
		return new HashMap[] {
			packetIDsIn,
			packetIDsOut
		};
	}

}
