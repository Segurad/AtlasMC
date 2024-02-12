package de.atlascore.world;

import java.util.concurrent.ConcurrentLinkedQueue;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;

public class ChunkWorker extends Thread {
	
	private static final ConcurrentLinkedQueue<ChunkWorkerTask> QUEUE = new ConcurrentLinkedQueue<>();
	
	public static final int DEFAULT_THREADS = 4;
	private static boolean init = false;
	
	private final Log logger;
	private volatile boolean running;
	
	private ChunkWorker(int id) {
		super("ChunkWorker-" + id);
		setDaemon(true);
		logger = Logging.getLogger(getName(), "Atlas");
		logger.sendToConsole(true);
		start();
	}
	
	@Override
	public void run() {
		logger.debug("Starting...");
		running = true;
		while (running) {
			if (!processQueuedChunk()) {
				try {
					sleep(50);
				} catch (InterruptedException e) {}
			}
		}
		logger.debug("Shutting down...");
	}
	
	public void shutdown() {
		running = false;
	}
	
	private boolean processQueuedChunk() {
		ChunkWorkerTask task = QUEUE.poll();
		if (task == null) 
			return false;
		// process chunk
		task.run(logger);
		return true;
	}
	
	public static void queueTask(ChunkWorkerTask task) {
		QUEUE.add(task);
	}
	
	public static synchronized void init(int threadcount) {
		if (init)
			throw new IllegalStateException("Anvil threads already initialized!");
		if (threadcount < 1)
			throw new IllegalArgumentException("Threat count must be greater than 0: " + threadcount);
		for (int i = 1; i <= threadcount; i++)
			new ChunkWorker(i);
	}
	
	public static interface ChunkWorkerTask {
		
		void run(Log logger);
		
	}

}
