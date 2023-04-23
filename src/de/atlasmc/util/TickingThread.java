package de.atlasmc.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

import org.slf4j.Logger;

import de.atlasmc.util.annotation.ThreadSafe;

/**
 * Thread that ticks at a fixed rate
 */
public class TickingThread extends Thread {

	private final short ticktime; // time interval of one tick
	private Runnable runner;
	protected final Logger logger;
	private final boolean exitOnError;
	private volatile boolean running;
	private int tick = 0; // number of ticks executed by this thread
	private long nextTick = 0;
	private long tickStart = 0;
	private long tickEnd = 0;
	private final Queue<Runnable> queue;
	
	public TickingThread(String name, int ticktime, Logger logger, boolean exitOnError) {
		this(name, ticktime, logger, exitOnError, null);
	}
	
	/**
	 * Creates a new ticking thread
	 * @param name
	 * @param ticktime time in milliseconds available for each tick
	 * @param logger the logger for this thread
	 * @param runner
	 */
	public TickingThread(String name, int ticktime, Logger logger, boolean exitOnError, Runnable runner) {
		super(name);
		this.logger = logger;
		this.exitOnError = exitOnError;
		this.ticktime = (short) ticktime;
		this.runner = runner;
		this.queue = new ConcurrentLinkedQueue<>();
	}
	
	@Override
	public void run() {
		if (isRunning()) 
			throw new RuntimeException("Thread already running!");
		running = true;
		nextTick = System.currentTimeMillis();
		while (isRunning()) {
			tickStart = System.currentTimeMillis();
			// Start process;
			try {
				tick(tick);
			} catch (Exception e) {
				logger.error("Error in tick process!", e);
				if (exitOnError)
					stopThread();
			}
			// End process
			tickEnd = System.currentTimeMillis();
			tick++;
			nextTick += ticktime;
			awaitTick();
		}
		// Stop process
		runner = null;
	}
	
	protected boolean executeNextTask() {
		Runnable r = queue.peek();
		if (r == null)
			return false;
		try {
			queue.remove().run();
		} catch (Exception e) {
			logger.error("Error while executing task!", e);
		}
		return true;
	}
	
	private void awaitTick() {
		while (true) {
			int restTime = (int) (nextTick-System.currentTimeMillis());
			if (restTime <= 0)
				break;
			if (executeNextTask())
				continue;
			Thread.yield();
			LockSupport.parkNanos("waiting for Tasks", (long) 1e6);
		}
	}
	
	/**
	 * Called each tick by this thread.
	 * Subclasses should of {@link TickingThread} override this.
	 * @throws IllegalStateException if no runner is set
	 */
	protected void tick(int tick) {
		if (runner == null) {
			stopThread();
			throw new IllegalStateException("Tick invokation without runner!");
		}
		runner.run();
	}
	
	@ThreadSafe
	public boolean isRunning() {
		return running;
	}
	
	@ThreadSafe
	public void stopThread() {
		running = false;
	}
	
	public int getTick() {
		return tick;
	}
	
	public int getOversleeped() {
		return (int) (System.currentTimeMillis() - nextTick);
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public void runTask(Runnable task) {
		if (task == null)
			throw new IllegalArgumentException("Task can not be null!");
		queue.add(task);
	}

}
