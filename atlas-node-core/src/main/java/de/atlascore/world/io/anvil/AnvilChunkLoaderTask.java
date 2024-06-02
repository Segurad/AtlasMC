package de.atlascore.world.anvil.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import de.atlascore.world.ChunkWorker.ChunkWorkerTask;
import de.atlasmc.log.Log;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.nbt.io.NBTIOReader;
import de.atlasmc.world.Chunk;

public class AnvilChunkLoaderTask implements ChunkWorkerTask {
	
	public final CompletableFuture<Chunk> future;
	public final Chunk chunk;
	public final int offset;
	public final CoreAnvilRegionFile region;
	
	public AnvilChunkLoaderTask(CompletableFuture<Chunk> future, Chunk chunk, int offset, CoreAnvilRegionFile region) {
		this.future = future;
		this.chunk = chunk;
		this.offset = offset;
		this.region = region;
	}
	
	@Override
	public void run(Log logger) {
		RandomAccessFile raf = null;
		InputStream in = null;
		NBTIOReader nbtReader = null;
		try {
			raf = new RandomAccessFile(region.getFile(), "r");
			raf.seek(offset);
			/*int length =*/ raf.readInt();
			byte compression = raf.readByte();
			in = Channels.newInputStream(raf.getChannel()); 
			in = getCompressedStream(compression, in, 1024);
			nbtReader = new NBTIOReader(in);
			// TODO reuse chunk io buffer + add support for multiple versions
			new CoreAnvilChunkIO().loadChunk(chunk, nbtReader);
		} catch (IOException e) {
			logger.error("Error while processing chunk!", e);
			future.finish(null, e);
		} finally {
			if (nbtReader != null) {
				nbtReader.close();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {}
			}
		}
		future.finish(chunk);
	}
	
	private InputStream getCompressedStream(byte compression, InputStream in, int buffersize) throws IOException {
		switch(compression) {
		case 1: return new InflaterInputStream(in, new Inflater(false), buffersize); // Zlib
		case 2: return new InflaterInputStream(in, new Inflater(true), buffersize); // GZip
		case 3: return in; // uncompressed
		default:
			throw new UnsupportedOperationException("Unsupported compression: " + compression);
		}
	}

}
