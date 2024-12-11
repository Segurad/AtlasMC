package de.atlasmc.io.protocol.common;

import java.util.BitSet;

import de.atlasmc.io.AbstractPacket;

public abstract class AbstractPacketChunkLight extends AbstractPacket {
	
	public int chunkX;
	public int chunkZ;
	/**
	 * A BitSet with bits for each section of a chunk within the normal world height + 2.
	 * The most significant bit represents light information for a 1-16 blocks above the normal world height (one chunk section above).
	 * The least significant bit represents light information for a 1-16 block below the normal world height (one chunk section below). 
	 */
	public BitSet skyMask;
	/**
	 * A BitSet with bits for each section of a chunk within the normal world height + 2.
	 * The most significant bit represents light information for a 1-16 blocks above the normal world height (one chunk section above).
	 * The least significant bit represents light information for a 1-16 block below the normal world height (one chunk section below).
	 */
	public BitSet blockMask;
	public BitSet emptySkyMask;
	public BitSet emptyBlockMask;
	/**
	 * The block light as two dimensional array.
	 * The first dimension equals the number of bits set in the {@link #getBlockMask()} BitSet.
	 * (index 0 hold the data for the section of the first bit set in the BitSet)
	 */
	public byte[][] blockLight;
	/**
	 * The sky light as two dimensional array.
	 * The first dimension equals the number of bits set in the {@link #getSkyMask()} BitSet.
	 * (index 0 hold the data for the section of the first bit set in the BitSet)
	 * The seconds dimension stores the light data for the ChunkSection
	 */
	public byte[][] skyLight;

}
