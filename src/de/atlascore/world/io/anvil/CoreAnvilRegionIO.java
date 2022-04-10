package de.atlascore.world.io.anvil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import de.atlascore.world.CoreChunk;
import de.atlasmc.util.nbt.io.NBTIOReader;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

/**
 * Represents a {@code .mcr} file
 */
public class CoreAnvilRegionIO {
	
	private final int x, z; // the regions x and z value equals (coordinate >> 9)
	private final World world;
	private final CoreAnvilRegionHeader header;
	private final File mcrFile;
	
	/**
	 * Creates a new AnvilChunkReagionIO<br>
	 * If the given file is a directory the region file will be created in or a present one with the given coordinates will be used.<br>
	 * If a region file is present and the coordinates of the file are not equal to the given ones the files coordinates will be used instead.
	 * @param world
	 * @param x position of the region (only used if the file is a directory)
	 * @param z position of the region (only used if the file is a directory)
	 * @param file or directory of the region file
	 * @throws IllegalArgumentException if the file does have a invalid name
	 */
	public CoreAnvilRegionIO(World world, int x, int z, File file) {
		this.world = world;
		header = new CoreAnvilRegionHeader();
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
	
	public void readHeader() throws IOException {
		if (mcrFile.length() < 8192) return;
		FileInputStream input = new FileInputStream(mcrFile);
		header.readHeader(input);
		input.close();
	}
	
	public void writeHeader() throws IOException {
		FileOutputStream out = new FileOutputStream(mcrFile);
		header.writeHeader(out);
		out.close();
	}

	public boolean isChunkPresent(int x, int z) {
		return header.getOffset(x, z) != 0;
	}
	
	public Chunk loadChunk(int x, int z) throws IOException {
		int off = header.getOffset(x, z);
		if (off == 0) return null;
		RandomAccessFile raf = new RandomAccessFile(mcrFile, "rw");
		raf.seek(off);
		byte compression = raf.readByte();
		InputStream in = Channels.newInputStream(raf.getChannel()); 
		in = getCompressedStream(compression, in);
		Chunk chunk = createChunk(in);
		raf.close();
		return chunk;
	}
	
	protected Chunk createChunk(InputStream input) throws IOException {
		NBTIOReader reader = new NBTIOReader(input);
		reader.readNextEntry();
		int version = reader.readIntTag();
		reader.readNextEntry();
		CoreAnvilChunkIO loader = getChunkLoader(version);
		Chunk chunk = new CoreChunk(world);
		loader.loadChunk(chunk, reader);
		return chunk;
	}

	private CoreAnvilChunkIO getChunkLoader(int version) {
		if (version >= 2566 && version <= 2586) // 1.16-1.16.5
			return new CoreAnvilChunkIO();
		throw new IllegalArgumentException("Unsupported chunkversion: " + version);
	}

	protected InputStream getCompressedStream(byte compression, InputStream in) throws IOException {
		switch(compression) {
		case 0: return in;
		case 1: return new InflaterInputStream(in, new Inflater(), 1024);
		case 2: return new InflaterInputStream(in, new Inflater(true), 1024);
		default:
			throw new IllegalArgumentException("Unsupported compression: " + compression);
		}
	}
	
}
