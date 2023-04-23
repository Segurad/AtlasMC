package de.atlascore.world.io.anvil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.world.CoreChunk;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

/**
 * Represents a {@code .mcr} file and provides methods for interaction
 */
public class CoreAnvilRegionFile {
	
	private final int x, z; // the regions x and z value equals (coordinate >> 9)
	private final World world;
	private final File mcrFile;
	
	/**
	 * Creates a new CoreAnvilRegionFile<br>
	 * If the given file is a directory the region file will be created in or a present one with the given coordinates will be used.<br>
	 * If a region file is present and the coordinates of the file are not equal to the given ones the files coordinates will be used instead.
	 * @param world
	 * @param x position of the region (only used if the file is a directory)
	 * @param z position of the region (only used if the file is a directory)
	 * @param file or directory of the region file
	 * @throws IllegalArgumentException if the file does have a invalid name
	 */
	public CoreAnvilRegionFile(World world, int x, int z, File file) {
		if (!file.isDirectory()) {
			String[] parts = file.getName().split(".");
			if (!parts[0].equals("r")) 
				throw new IllegalArgumentException("Invalid file name! Need to start with (r) found: " + parts[0]);
			if (!parts[3].equals("mcr")) 
				throw new IllegalArgumentException("Invalid file name! Type .mcr excpected but found: " + parts[3]);
			try {
				x = Integer.parseInt(parts[1]);
				z = Integer.parseInt(parts[2]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid file name! Does not contian a valid coordinate pair: " + file.getName());
			}
		} else file = new File(file, "r." + x + "." + z + ".mcr");
		this.world = world;
		this.x = x;
		this.z = z;
		this.mcrFile = file;
	}
	
	public int getRegionX() {
		return x;
	}
	
	public int getRegionZ() {
		return z;
	}
	
	public File getFile() {
		return mcrFile;
	}
	
	public CoreAnvilRegionFileHeader readHeader() throws IOException {
		return readHeader(new CoreAnvilRegionFileHeader());
	}
	
	public CoreAnvilRegionFileHeader readHeader(CoreAnvilRegionFileHeader header) throws IOException {
		if (header == null)
			throw new IllegalArgumentException("Header can not be null!");
		if (mcrFile.length() < 8192) 
			throw new IOException("Region does not contain header information! Not existing?");
		FileInputStream input = new FileInputStream(mcrFile);
		header.readHeader(input);
		input.close();
		return header;
	}
	
	public void writeHeader(CoreAnvilRegionFileHeader header) throws IOException {
		FileOutputStream out = new FileOutputStream(mcrFile);
		header.writeHeader(out);
		out.close();
	}

	public boolean isChunkPresent(int x, int z) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(mcrFile, "r");
		int index = CoreAnvilRegionFileHeader.getIndex(x, z)*4;
		raf.seek(index);
		int offset = raf.readInt();
		raf.close();
		return offset != 0;
	}
	
	public boolean isChunkPresent(CoreAnvilRegionFileHeader header, int x, int z) {
		return header.getOffset(x, z) != 0;
	}
	
	public Chunk loadChunk(int x, int z) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(mcrFile, "r");
		int index = CoreAnvilRegionFileHeader.getIndex(x, z)*4;
		raf.seek(index);
		int off = raf.readInt();
		raf.close();
		off >>= 8; // remove sector count information
		if (off == 0) { // chunk not present
			return null;
		}
		return queueChunkForLoading(off, x, z);
	}
	
	public Chunk loadChunk(CoreAnvilRegionFileHeader header, int x, int z) {
		int off = header.getOffset(x, z);
		if (off == 0) 
			return null; // not present
		return queueChunkForLoading(off, x, z);
	}
	
	private Chunk queueChunkForLoading(int offset, int x, int z) {
		Chunk chunk = new CoreChunk(world, x, z);
		AnvilThread.queueChunkLoading(chunk, offset, this);
		return chunk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoreAnvilRegionFile other = (CoreAnvilRegionFile) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public List<Chunk> loadChunks() throws IOException {
		CoreAnvilRegionFileHeader header = readHeader();
		return loadChunks(header);
	}
	
	public List<Chunk> loadChunks(CoreAnvilRegionFileHeader header) {
		List<Chunk> chunks = new ArrayList<>(128);
		for (int x = 0; x < 32; x++) {
			for (int z = 0; z < 32; z++) {
				chunks.add(loadChunk(header, x, z));
			}
		}
		return chunks;
	}
	
}
