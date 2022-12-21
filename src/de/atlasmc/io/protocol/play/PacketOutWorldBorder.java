package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_WORLD_BORDER)
public class PacketOutWorldBorder extends AbstractPacket implements PacketPlayOut {
	
	private double newDiameter, oldDiameter, x, z;
	private long speed;
	private int warnTime, warnBlocks, portalBoundary;
	private BorderAction action;
	
	public BorderAction getAction() {
		return action;
	}
	
	public void setAction(BorderAction action) {
		this.action = action;	
	}

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public double getZ() {
		return z;
	}
	
	public void setZ(double z) {
		this.z = z;
	}

	public double getNewDiameter() {
		return newDiameter;
	}
	
	public void setNewDiameter(double newDiameter) {
		this.newDiameter = newDiameter;
	}

	public double getOldDiameter() {
		return oldDiameter;
	}
	
	public void setOldDiameter(double oldDiameter) {
		this.oldDiameter = oldDiameter;
	}

	public long getSpeed() {
		return speed;
	}
	
	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public int getPortalBoundary() {
		return portalBoundary;
	}

	public void setPortalBoundary(int portalBoundary) {
		this.portalBoundary = portalBoundary;
	}
	
	public int getWarnTime() {
		return warnTime;
	}
	
	public void setWarnTime(int warnTime) {
		this.warnTime = warnTime;
	}

	public int getWarnBlocks() {
		return warnBlocks;
	}
	
	public void setWarnBlocks(int warnBlocks) {
		this.warnBlocks = warnBlocks;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_WORLD_BORDER;
	}

	public static enum BorderAction {
		SET_SIZE,
		LERP_SIZE,
		SET_CENTER,
		INITIALIZE,
		SET_WARNING_TIME,
		SET_WARNING_BLOCKS;
		
		private static List<BorderAction> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static BorderAction getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<BorderAction> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}
	
}
