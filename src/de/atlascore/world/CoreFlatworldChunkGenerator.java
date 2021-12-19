package de.atlascore.world;

import java.util.Arrays;
import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.World;

public class CoreFlatworldChunkGenerator implements ChunkGenerator {

	private final BlockData[] palette;
	private final int[] stack;
	private final int startheight;
	
	/**
	 * Creates a new {@link CoreFlatworldChunkGenerator}<br>
	 * If palette or stack equal null the generator will generate empty chunks
	 * @param palette the palette used for generation 
	 * @param stack palette indizes ordered by the lowest block to the highest
	 * @param starthight the first block y set by this generator
	 */
	public CoreFlatworldChunkGenerator(List<BlockData> palette, int[] stack, int startheight) {
		if (palette == null || stack == null) {
			this.palette = null;
			this.stack = null;
			this.startheight = 0;
			return;
		}
		this.palette = new BlockData[palette.size()];
		for (int i = 0; i < palette.size(); i++) {
			this.palette[i] = palette.get(i).clone();
		}
		this.stack = Arrays.copyOf(stack, stack.length);
		this.startheight = startheight;
	}
	
	@Override
	public Chunk generate(World world, int x, int z) {
		CoreChunk chunk = new CoreChunk(world);
		if (palette != null) { // Fill chunk with Blocks
			int lastPaletteIndex = -1;
			short lastSectionPaletteIndex = -1;
			
			for (int y = startheight, i = 0; y < startheight+stack.length; y++, i++) {
				if (stack[i] == -1) continue;
				ChunkSection section = chunk.getSection(y);
				
				short sectionPaletteIndex = -1;
				int paletteIndex = stack[i];
				
				// Reduce palette searching time
				if (lastPaletteIndex == paletteIndex) 
					sectionPaletteIndex = lastSectionPaletteIndex;
				else {
					sectionPaletteIndex = section.getPaletteIndex(palette[paletteIndex]);
					lastPaletteIndex = paletteIndex;
					lastSectionPaletteIndex = sectionPaletteIndex;
				}
				
				// fill layer of section
				for (int secZ = 0; secZ < 16; secZ++) {
					for (int secX = 0; secX < 16; secX++) {
						section.setIndex(sectionPaletteIndex, secX, y, secZ);
					}
				}
			}
		}
		return chunk;
	}

}
