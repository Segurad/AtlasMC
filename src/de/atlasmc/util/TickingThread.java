package de.atlasmc.util;

import de.atlasmc.util.annotation.ThreadSafe;

/**
 * Thread that ticks at a fixed rate
 */
public class TickingThread extends Thread {

	private final short ticktime; // time interval of one tick
	private final Runnable runner;
	private volatile boolean running;
	private int lastTickTime = -1, // time needed for the last tick
				skipped = 0, // number of skipped ticks
				ticks = 0; // number of ticks executed by this thread
	
	public TickingThread(String name, int ticktime) {
		this(name, ticktime, null);
	}
	
	public TickingThread(String name, int ticktime, Runnable runner) {
		super(name);
		this.ticktime = (short) ticktime;
		this.runner = runner;
	}
	
	@Override
	public void run() {
		if (isRunning()) throw new RuntimeException("Thread already running!");
		running = true;
		long startTime;
		long endTime;
		long remaining;
		while (isRunning()) {
			startTime = System.currentTimeMillis();
			// Start process;
			tick();
			// End process
			endTime = System.currentTimeMillis();
			remaining = endTime - startTime;
			lastTickTime = (int) remaining;
			ticks++;
			if (remaining > ticktime) {
				skipped += remaining / ticktime;
				remaining = ticktime - (remaining % ticktime);
			} else remaining = ticktime - remaining;
			try {
				Thread.sleep(remaining);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Stop process
	}
	
	protected void tick() {
		if (runner == null)
			return;
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
	
	public int getLastTickTime() {
		return lastTickTime;
	}
	
	public int getTicks() {
		return ticks;
	}
	
	public int getSkipped() {
		return skipped;
	}

}
