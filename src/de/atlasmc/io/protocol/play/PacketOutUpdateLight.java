package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_UPDATE_LIGHT)
public class PacketOutUpdateLight extends AbstractPacket implements PacketPlayOut {
	
	private int chunkX, chunkZ, skyMask, blockMask, emptySkyMask, emptyBlockMask;
	private boolean trustEdges;
	private byte[] blockLight, skyLight;
	
	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public int getSkyMask() {
		return skyMask;
	}

	public int getBlockMask() {
		return blockMask;
	}

	public int getEmptySkyMask() {
		return emptySkyMask;
	}

	public int getEmptyBlockMask() {
		return emptyBlockMask;
	}

	public boolean getTrustEdges() {
		return trustEdges;
	}

	public byte[] getBlockLight() {
		return blockLight;
	}

	public byte[] getSkyLight() {
		return skyLight;
	}

	public void setChunkX(int chunkX) {
		this.chunkX = chunkX;
	}

	public void setChunkZ(int chunkZ) {
		this.chunkZ = chunkZ;
	}

	public void setSkyMask(int skyMask) {
		this.skyMask = skyMask;
	}

	public void setBlockMask(int blockMask) {
		this.blockMask = blockMask;
	}

	public void setEmptySkyMask(int emptySkyMask) {
		this.emptySkyMask = emptySkyMask;
	}

	public void setEmptyBlockMask(int emptyBlockMask) {
		this.emptyBlockMask = emptyBlockMask;
	}

	public void setTrustEdges(boolean trustEdges) {
		this.trustEdges = trustEdges;
	}

	public void setBlockLight(byte[] blockLight) {
		this.blockLight = blockLight;
	}

	public void setSkyLight(byte[] skyLight) {
		this.skyLight = skyLight;
	}

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_LIGHT;
	}

}
