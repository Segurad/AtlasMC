package de.atlasmc.node.map;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.CloneException;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class MapIcon implements Cloneable, NBTSerializable {
	
	public static final NBTSerializationHandler<MapIcon>
	NBT_HANDLER = NBTSerializationHandler
					.builder(MapIcon.class)
					.defaultConstructor(MapIcon::new)
					.enumStringField("type", MapIcon::getType, MapIcon::setType, IconType::getByName, IconType.PLAYER)
					.doubleField("x", MapIcon::getX, MapIcon::setX, 0)
					.doubleField("z", MapIcon::getZ, MapIcon::setZ, 0)
					.floatField("rotation", MapIcon::getRotation, MapIcon::setRotation, 0)
					.build();
	
	private IconType type;
	private double x;
	private double z;
	private float rotation;
	private Chat name;
	
	public MapIcon() {
		
	}
	
	public MapIcon(IconType type) {
		this(type, 0, 0);
	}
	
	public MapIcon(IconType type, double x, double z) {
		this(type, x, z, 0);
	}
	
	public MapIcon(IconType type, double x, double z, float rotation) {
		this.type = type;
		this.x = x;
		this.z = z;
		this.rotation = rotation;
	}

	public IconType getType() {
		return type;
	}

	public double getX() {
		return x;
	}

	public double getZ() {
		return z;
	}

	public float getRotation() {
		return rotation;
	}

	public Chat getDisplayName() {
		return name;
	}

	public void setType(IconType type) {
		this.type = type;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setDisplayName(Chat name) {
		this.name = name;
	}
	
	public MapIcon clone() {
		try {
			return (MapIcon) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException(e);
		}
	}

	public boolean hasDisplayName() {
		return name != null;
	}
	
	@Override
	public NBTSerializationHandler<? extends MapIcon> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public enum IconType implements AtlasEnum {
		
		PLAYER,
		FRAME,
		RED_MARKER,
		BLUE_MARKER,
		TARGET_X,
		TARGET_POINT,
		PLAYER_OFF_MAP,
		PLAYER_OFF_LIMITS,
		MANSION,
		MONUMENT,
		BANNER_WHITE,
		BANNER_ORANGE,
		BANNER_MAGENTA,
		BANNER_LIGHT_BLUE,
		BANNER_YELLOW,
		BANNER_LIME,
		BANNER_PINK,
		BANNER_GRAY,
		BANNER_LIGHT_GRAY,
		BANNER_CYAN,
		BANNER_PURPLE,
		BANNER_BLUE,
		BANNER_BROWN,
		BANNER_GREEN,
		BANNER_RED,
		BANNER_BLACK,
		RED_X,
		VILLAGE_DESERT,
		VILLAGE_PLAINS,
		VILLAGE_SAVANNA,
		VILLAGE_SNOWY,
		VILLAGE_TAIGA,
		JUNGLE_TEMPLATE,
		SWAMP_HUT;
		
		private static List<IconType> VALUES;
		
		private final String name;
		
		private IconType() {
			name = "minecraft:" + name().toLowerCase();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static IconType getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<IconType> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		public static IconType getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<IconType> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				IconType value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
