package de.atlasmc.util;

import de.atlasmc.util.annotation.ThreadSafe;

public abstract class AbstractTickingThread extends Thread {

	private final short ticktime; // time interval of one tick
	private volatile boolean running;
	private int lastTickTime = -1, // time needed for the last tick
				skipped = 0, // number of skipped ticks
				ticks = 0; // number of ticks executed by this thread
	
	public AbstractTickingThread(String name, int ticktime) {
		super(name);
		this.ticktime = (short) ticktime;
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
	
	protected abstract void tick();
	
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
