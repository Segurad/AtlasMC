package de.atlasmc.util;

import org.joml.Vector3d;

import de.atlasmc.SimpleLocation;

public class TeleportFlags {
	
	protected TeleportFlags() {}
	
	public static final int
	RELATIVE_X = 0x0001,
	RELATIVE_Y = 0x0002,
	RELATIVE_Z = 0x0004,
	RELATIVE_YAW = 0x0008,
	RELATIVE_PITCH = 0x0010,
	RELATIVE_VELOCITY_X = 0x0020,
	RELATIVE_VELOCITY_Y = 0x0040,
	RELATIVE_VELOCITY_Z = 0x0080,
	ROTATE_VELOCITY = 0x0100;
	
	public static boolean isRelativeX(int flags) {
		return (flags & RELATIVE_X) == RELATIVE_X;
	}
	
	public static boolean isRelativeY(int flags) {
		return (flags & RELATIVE_Y) == RELATIVE_Y;
	}
	
	public static boolean isRelativeZ(int flags) {
		return (flags & RELATIVE_Z) == RELATIVE_Z;
	}
	
	public static boolean isRelativeYaw(int flags) {
		return (flags & RELATIVE_YAW) == RELATIVE_YAW;
	}
	
	public static boolean isRelativePitch(int flags) {
		return (flags & RELATIVE_PITCH) == RELATIVE_PITCH;
	}
	
	public static boolean isRelativeVelocityX(int flags) {
		return (flags & RELATIVE_VELOCITY_X) == RELATIVE_VELOCITY_X;
	}
	
	public static boolean isRelativeVelocityY(int flags) {
		return (flags & RELATIVE_VELOCITY_Y) == RELATIVE_VELOCITY_Y;
	}
	
	public static boolean isRelativeVelocityZ(int flags) {
		return (flags & RELATIVE_VELOCITY_Z) == RELATIVE_VELOCITY_Z;
	}
	
	public static boolean isRotateVelocity(int flags) {
		return (flags & ROTATE_VELOCITY) == ROTATE_VELOCITY;
	}

	public static void set(SimpleLocation to, SimpleLocation from, int flags) {
		set(to, from.x, from.y, from.z, from.pitch, from.yaw, flags);
	}
	
	public static void set(SimpleLocation to, double x, double y, double z, int flags) {
		if ((flags & RELATIVE_X) == RELATIVE_X) {
			to.x += x;
		} else {
			to.x = x;
		}
		if ((flags & RELATIVE_Y) == RELATIVE_Y) {
			to.y += y;
		} else {
			to.y = y;
		}
		if ((flags & RELATIVE_Z) == RELATIVE_Z) {
			to.z += z;
		} else {
			to.z = z;
		}
	}

	public static void set(SimpleLocation to, double x, double y, double z, float pitch, float yaw, int flags) {
		if ((flags & RELATIVE_X) == RELATIVE_X) {
			to.x += x;
		} else {
			to.x = x;
		}
		if ((flags & RELATIVE_Y) == RELATIVE_Y) {
			to.y += y;
		} else {
			to.y = y;
		}
		if ((flags & RELATIVE_Z) == RELATIVE_Z) {
			to.z += z;
		} else {
			to.z = z;
		}
		if ((flags & RELATIVE_PITCH) == RELATIVE_PITCH) {
			to.pitch += pitch;
		} else {
			to.pitch = pitch;
		}
		if ((flags & RELATIVE_YAW) == RELATIVE_YAW) {
			to.yaw += yaw;
		} else {
			to.yaw = yaw;
		}
	}
	
	public static void set(Vector3d to, double x, double y, double z, int flags) {
		if ((flags & RELATIVE_VELOCITY_X) == RELATIVE_VELOCITY_X) {
			to.x += x;
		} else {
			to.x = x;
		}
		if ((flags & RELATIVE_VELOCITY_Y) == RELATIVE_VELOCITY_Y) {
			to.y += y;
		} else {
			to.y = y;
		}
		if ((flags & RELATIVE_VELOCITY_Z) == RELATIVE_VELOCITY_Z) {
			to.z += z;
		} else {
			to.z = z;
		}
	}

}
