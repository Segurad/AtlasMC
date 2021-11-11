package de.atlascore.world.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import de.atlasmc.util.ByteDataBuffer;
import de.atlasmc.util.nbt.io.NBTIOReader;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

public class CoreAnvilChunkRegionIO {
	
	private final int x, z; // the regions x and z value equals (coordinate >> 9)
	private final World world;
	private final CoreAnvilRegionHeader header;
	private final File file;
	
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
	public CoreAnvilChunkRegionIO(World world, int x, int z, File file) {
		this.world = world;
		header = new CoreAnvilRegionHeader();
		if (!file.isDirectory()) {
			String[] parts = file.getName().split(".");
			if (!parts[0].equals("r")) throw new IllegalArgumentException("Invalid file name! Need to start with (r) found: " + parts[0]);
			if (!parts[3].equals("mcr")) throw new IllegalArgumentException("Invalid file name! Type .mcr excpected but found: " + parts[3]);
			try {
				x = Integer.parseInt(parts[1]);
				z = Integer.parseInt(parts[2]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid file name! Does not contian a valid coordinate pair: " + file.getName());
			}
		} else file = new File(file, "r." + x + "." + z + ".mcr");
		this.x = x;
		this.z = z;
		this.file = file;
	}
	
	public int getRegionX() {
		return x;
	}
	
	public int getRegionZ() {
		return z;
	}
	
	public File getFile() {
		return file;
	}
	
	public void readHeader() throws IOException {
		if (file.length() < 8192) return;
		FileInputStream input = new FileInputStream(file);
		header.readHeader(input);
		input.close();
	}
	
	public void writeHeader() throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		header.writeHeader(out);
		out.close();
	}

	public boolean isChunkPresent(int x, int z) {
		return header.getOffset(x, z) != 0;
	}
	
	public Chunk loadChunk(int x, int z) throws IOException {
		int off = header.getOffset(x, z);
		if (off == 0) return null;
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(off);
		int length = raf.readInt();
		byte compression = raf.readByte();
		length--; // reduce cause the length contains the compression byte
		byte[] data = compression == 0 ? readData(raf, length) : readCompressedData(raf, length);
		return createChunk(data);
	}
	
	protected Chunk createChunk(byte[] data) throws IOException {
		NBTIOReader reader = new NBTIOReader(new ByteDataBuffer(data));
		reader.readNextEntry();
		int version = reader.readIntTag();
		reader.readNextEntry();
		ChunkLoader loader = getChunkLoader(version);
		return loader.loadChunk(world, reader);
	}

	private ChunkLoader getChunkLoader(int version) {
		if (version >= 2566 && version <= 2586) // 1.16-1.16.5
			return new CoreAnvilChunkLoader();
		throw new IllegalArgumentException("Unsupported chunkversion: " + version);
	}

	protected byte[] readCompressedData(RandomAccessFile raf, int length) throws IOException {
		byte[] inbuffer = new byte[1024];
		byte[] outbuffer = new byte[1024];
		ByteArrayOutputStream outData = new ByteArrayOutputStream(length);
		Inflater inf = new Inflater();
		inf.setInput(inbuffer);
		while (length > 0) {
			int read = raf.read(outbuffer);
			if (read == -1) break;
			if (read < 1024) inf.setInput(inbuffer, 0, read);
			while (!inf.finished()) {
				int data = -1;
				try {
					data = inf.inflate(outbuffer);
				} catch (DataFormatException e) {
					e.printStackTrace();
				}
				outData.write(outbuffer, 0, data);
			}
			length -= read;
		}
		return outData.toByteArray();
	}
	
	protected byte[] readData(RandomAccessFile raf, int length) throws IOException {
		ByteArrayOutputStream data = new ByteArrayOutputStream(length);
		byte[] buffer = new byte[1024];
		while (length > 0) {
			int read = raf.read(buffer);
			if (read == -1) break;
			data.write(buffer, 0, read);
			length -= read;
		}
		return data.toByteArray();
	}
	
}
