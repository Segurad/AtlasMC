package de.atlascore;

import org.slf4j.Logger;

import de.atlasmc.util.TickingThread;

public class CoreAtlasThread extends TickingThread {

	private int ticks;
	private int oversleeped;
	private int maxOversleeped;
	private int minOversleeped;
	private long start;
	private int msError = 50;
	
	public CoreAtlasThread(Logger logger) {
		super("Atlas-Main", 50, logger, false);
	}
	
	@Override
	public void run() {
		start = System.currentTimeMillis();
		super.run();
	}
	
	@Override
	protected void tick(int tick) {
		oversleeped += getOversleeped();
		if (maxOversleeped < getOversleeped())
			maxOversleeped = getOversleeped();
		if (minOversleeped == -1 || minOversleeped >= getOversleeped()) {
			minOversleeped = getOversleeped();
		}
		if (++ticks == 20) {
			ticks = 0;
			long end = System.currentTimeMillis();
			long time = end - start;
			msError += time-1000;
			logger.info("20 Tick MS: {}", time);
			logger.info("MS Error: {}", msError);
			logger.info("Oversleeped: {}", oversleeped);
			logger.info("Avg. Oversleeped: {}", oversleeped/20);
			logger.info("Max. Oversleeped: {}", maxOversleeped);
			logger.info("Min. Oversleeped: {}", minOversleeped);
			oversleeped = 0;
			maxOversleeped = 0;
			minOversleeped = -1;
			start = System.currentTimeMillis();
		}
	}

}
