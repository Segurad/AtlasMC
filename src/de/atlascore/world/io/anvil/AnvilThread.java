package de.atlascore.world.io.anvil;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import de.atlasmc.util.nbt.io.NBTIOReader;
import de.atlasmc.world.Chunk;

public class AnvilThread extends Thread {
	
	private static final ConcurrentLinkedQueue<QueuedChunk> QUEUE = new ConcurrentLinkedQueue<>();
	private static final HashMap<CoreAnvilRegionFile, Integer> QUEUED_REGIONS = new HashMap<>();
	private static volatile long LAST_POLL = 0;
	private static final ConcurrentHashMap<AnvilThread, Object> THREADS = new ConcurrentHashMap<>();
	
	private static final int MIN_THREADS = 4;
	private static final int MAX_IDLE_TIME = 5000;
	
	private CoreAnvilChunkIO newestIO;
	private long lastWorkTime;
	private volatile boolean running;
	
	@Override
	public void run() {
		lastWorkTime = System.currentTimeMillis();
		while (running) {
			processQueuedChunk();
			// die
			if (System.currentTimeMillis() - lastWorkTime >= MAX_IDLE_TIME)
			if (THREADS.size() > MIN_THREADS) {
				synchronized (THREADS) {
					if (THREADS.size() <= MIN_THREADS)
						continue;
					THREADS.remove(this);
					shutdown();
				}
			}
		}
	}
	
	public void shutdown() {
		running = false;
	}
	
	private void processQueuedChunk() {
		QueuedChunk qchunk = QUEUE.poll();
		if (qchunk == null) 
			return;
		LAST_POLL = System.currentTimeMillis();
		if (newestIO == null)
			newestIO = new CoreAnvilChunkIO();
		// process chunk
		try {
			RandomAccessFile raf = new RandomAccessFile(qchunk.region.getFile(), "r");
			raf.seek(qchunk.offset);
			int length = raf.readInt();
			byte compression = raf.readByte();
			InputStream in = Channels.newInputStream(raf.getChannel()); 
			in = getCompressedStream(compression, in, 1024);
			NBTIOReader nbtReader = new NBTIOReader(in);
			newestIO.loadChunk(qchunk.chunk, nbtReader);
			nbtReader.close();
			in.close();
			raf.close();
		} catch (IOException e) {
			// TODO handle exception
		}
		// remove queued region
		removeRegionAccess(qchunk.region);
	}
	
	public static synchronized void queueChunkLoading(Chunk chunk, int offset, CoreAnvilRegionFile region) {
		QUEUE.add(new QueuedChunk(chunk, offset, region));
		int queued = QUEUED_REGIONS.get(region);
		QUEUED_REGIONS.put(region, queued+1);
	}
	
	private static synchronized void removeRegionAccess(CoreAnvilRegionFile region) {
		int count = QUEUED_REGIONS.get(region);
		if (count == 1)
			QUEUED_REGIONS.remove(region);
		else
			QUEUED_REGIONS.put(region, count);
	}
	
	static class QueuedChunk {
		
		public final Chunk chunk;
		public final int offset;
		public final CoreAnvilRegionFile region;
		
		public QueuedChunk(Chunk chunk, int offset, CoreAnvilRegionFile region) {
			this.chunk = chunk;
			this.offset = offset;
			this.region = region;
		}
		
	}
	
	private InputStream getCompressedStream(byte compression, InputStream in, int buffersize) throws IOException {
		switch(compression) {
		case 1: return new InflaterInputStream(in, new Inflater(), buffersize); // Zlib
		case 2: return new InflaterInputStream(in, new Inflater(true), buffersize); // GZip
		case 3: return in; // uncompressed
		default:
			throw new UnsupportedOperationException("Unsupported compression: " + compression);
		}
	}

}
