package de.atlasmc.io.protocol.play;

import java.util.BitSet;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_UPDATE_LIGHT)
public class PacketOutUpdateLight extends AbstractPacket implements PacketPlayOut {
	
	private int chunkX;
	private int chunkZ;
	private BitSet skyMask;
	private BitSet blockMask;
	private BitSet emptySkyMask;
	private BitSet emptyBlockMask;
	private boolean trustEdges;
	private byte[][] blockLight;
	private byte[][] skyLight;
	
	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	/**
	 * Returns a BitSet with bits for each section of a chunk within the normal world height + 2.
	 * The most significant bit represents light information for a 1-16 blocks above the normal world height (one chunk section above).
	 * The least significant bit represents light information for a 1-16 block below the normal world height (one chunk section below). 
	 * @return sky mask
	 */
	public BitSet getSkyMask() {
		return skyMask;
	}

	/**
	 * Returns a BitSet with bits for each section of a chunk within the normal world height + 2.
	 * The most significant bit represents light information for a 1-16 blocks above the normal world height (one chunk section above).
	 * The least significant bit represents light information for a 1-16 block below the normal world height (one chunk section below).
	 * @return sky mask
	 */
	public BitSet getBlockMask() {
		return blockMask;
	}
	
	public BitSet getEmptySkyMask() {
		return emptySkyMask;
	}

	public BitSet getEmptyBlockMask() {
		return emptyBlockMask;
	}

	public boolean getTrustEdges() {
		return trustEdges;
	}

	/**
	 * Returns the block light as two dimensional array.
	 * The first dimension equals the number of bits set in the {@link #getBlockMask()} BitSet.
	 * (index 0 hold the data for the section of the first bit set in the BitSet)
	 * The seconds dimension stores the light data for the ChunkSection
	 * @return block light
	 */
	public byte[][] getBlockLight() {
		return blockLight;
	}

	/**
	 * Returns the sky light as two dimensional array.
	 * The first dimension equals the number of bits set in the {@link #getSkyMask()} BitSet.
	 * (index 0 hold the data for the section of the first bit set in the BitSet)
	 * The seconds dimension stores the light data for the ChunkSection
	 * @return block light
	 */
	public byte[][] getSkyLight() {
		return skyLight;
	}

	public void setChunkX(int chunkX) {
		this.chunkX = chunkX;
	}

	public void setChunkZ(int chunkZ) {
		this.chunkZ = chunkZ;
	}

	public void setSkyMask(BitSet skyMask) {
		this.skyMask = skyMask;
	}

	public void setBlockMask(BitSet blockMask) {
		this.blockMask = blockMask;
	}

	public void setEmptySkyMask(BitSet emptySkyMask) {
		this.emptySkyMask = emptySkyMask;
	}

	public void setEmptyBlockMask(BitSet emptyBlockMask) {
		this.emptyBlockMask = emptyBlockMask;
	}

	public void setTrustEdges(boolean trustEdges) {
		this.trustEdges = trustEdges;
	}

	public void setBlockLight(byte[][] blockLight) {
		this.blockLight = blockLight;
	}

	public void setSkyLight(byte[][] skyLightSections) {
		this.skyLight = skyLightSections;
	}

	@Override
	public int getDefaultID() {
		return OUT_UPDATE_LIGHT;
	}

}
