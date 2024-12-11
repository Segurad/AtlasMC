package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PROGRAM_STRUCTURE_BLOCK)
public class PacketInProgramStructureBlock extends AbstractPacket implements PacketPlayIn {
	
	private int action;
	private int mode;
	private int mirror;
	private int rotation;
	private String name;
	private String metadata;
	private int offsetX;
	private int offsetY;
	private int offsetZ;
	private int sizeX;
	private int sizeY;
	private int sizeZ;
	private int flags;
	private float integrity;
	private long seed;
	private long position;

	public int getAction() {
		return action;
	}

	public int getMode() {
		return mode;
	}

	public int getMirror() {
		return mirror;
	}

	public int getRotation() {
		return rotation;
	}

	public String getName() {
		return name;
	}

	public String getMetadata() {
		return metadata;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public int getOffsetZ() {
		return offsetZ;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getSizeZ() {
		return sizeZ;
	}

	public int getFlags() {
		return flags;
	}

	public float getIntegrity() {
		return integrity;
	}

	public long getSeed() {
		return seed;
	}

	public long getPosition() {
		return position;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setMirror(int mirror) {
		this.mirror = mirror;
	}

	public void setRotation(int roation) {
		this.rotation = roation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public void setOffsetZ(int offsetZ) {
		this.offsetZ = offsetZ;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public void setIntegrity(float integrity) {
		this.integrity = integrity;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	@Override
	public int getDefaultID() {
		return IN_PROGRAM_STRUCTURE_BLOCK;
	}
	
}
