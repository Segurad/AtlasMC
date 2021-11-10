package de.atlascore.world.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import de.atlasmc.world.Chunk;

public class CoreAnvilChunkRegionIO {
	
	private final int x, z; // the regions x and z value equals (coordinate >> 9)
	private final CoreAnvilRegionHeader header;
	private final File file;
	
	/**
	 * Creates a new AnvilChunkReagionIO<br>
	 * If the given file is a directory the region file will be created in or a present one with the given coordinates will be used.<br>
	 * If a region file is present and the coordinates of the file are not equal to the given ones the files coordinates will be used instead.
	 * @param x position of the region (only used if the file is a directory)
	 * @param z position of the region (only used if the file is a directory)
	 * @param file or directory of the region file
	 * @throws IllegalArgumentException if the file does have a invalid name
	 */
	public CoreAnvilChunkRegionIO(int x, int z, File file) {
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
		byte[] data = new byte[length-1];
		raf.read(data);

		return null;
	}
	
}
