package de.atlasmc.util;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import de.atlasmc.log.Logging;

public class ThreadWatchdog extends TickingThread {

	private static ThreadWatchdog DOG = new ThreadWatchdog();
	private static final Set<WatchableThread> watched;
	private static final long DIE_AFTER = TimeUnit.SECONDS.toMillis(60);
	
	static {
		watched = ConcurrentHashMap.newKeySet();
	}
	
	private ThreadWatchdog() {
		super("Watchdog", 5000, Logging.getLogger("Watchdog", "Atlas"), false);
		logger.sendToConsole(true);
		setDaemon(true);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void tick(int tick) {
		if (watched.isEmpty())
			return;
		long time = System.currentTimeMillis() - DIE_AFTER;
		ArrayList<Thread> dead = null;
		for (WatchableThread ref : watched) {
			Thread thread = ref.getThread();
			if (thread.getState() == State.TERMINATED) {
				watched.remove(ref);
				logger.debug("Removing terminated Thread: {} ID {} | State {}", thread.getName(), thread.getId());
				continue;
			}
			if (ref.lastHeartBeat() > time)
				continue;
			if (dead == null)
				dead = new ArrayList<>();
			dead.add(thread);
			watched.remove(ref);
			logger.debug("Removing killed Thread: {} ID {} | State {}", thread.getName(), thread.getId());
		}
		if (dead == null)
			return;
		ThreadInfo[] info = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
		for (Thread thread : dead) {
			logger.error("Thread stopped responding for to long and was killed: {} ID: {}", thread.getName(), thread.getId());
			thread.stop();
		}
		for (ThreadInfo i : info) {
			dumpInfo(i);
		}
		logger.error("");
		logger.error("###############################################################################");
		logger.error("");
	}
	
	private void dumpInfo(ThreadInfo info) {
		logger.error("");
		logger.error("###############################################################################");
		logger.error("");
		logger.error("--- ThreadInfo ----------------------------------------------------------------");
		logger.error("Name: {} ID: {} | Suspended: {} Native: {} State: {}", 
				info.getThreadName(), 
				info.getThreadId(), 
				info.isSuspended(), 
				info.isInNative(), 
				info.getThreadState());
		if (info.getLockedMonitors().length > 0) {
			logger.error("");
			logger.error("--- Lock Monitors -------------------------------------------------------------");
			logger.error("Waiting with Monitors: {}", info.getLockedMonitors().length);
			for (MonitorInfo lock : info.getLockedMonitors()) {
				logger.error("Lock on: {}", lock.getLockedStackFrame());
			}
			
		}
		if (info.getLockedSynchronizers().length > 0) {
			logger.error("");
			logger.error("--- Lock Synchronizer -------------------------------------------------------------");
			logger.error("Waiting with Synchronizer: {}", info.getLockedSynchronizers().length);
			for (LockInfo lock : info.getLockedSynchronizers()) {
				logger.error("Lock on: {}#{}", lock.getClassName(), lock.getIdentityHashCode());
			}
		}
		logger.error("");
		logger.error("--- StackTrace ----------------------------------------------------------------");
		StackTraceElement[] stack = info.getStackTrace();
		for (StackTraceElement element : stack)
			logger.error("Stack: {}", element);
	}

	public static void watch(WatchableThread thread) {
		Thread t = thread.getThread();
		DOG.logger.debug("Added Thread: {} ID {} | State {}", t.getName(), t.getId(), t.getState());
		watched.add(thread);
	}
	
	public static void unwatch(WatchableThread thread) {
		if (watched.remove(thread)) {
			Thread t  = thread.getThread();
			DOG.logger.debug("Removed Thread: {} ID {} | State {}", t.getName(), t.getId(), t.getState());	
		}
	}
	
	public static void init() {
		synchronized (ThreadWatchdog.class) {
			if (DOG.isRunning())
				return;
			DOG.logger.debug("Starting...");
			DOG.start();
		}
	}

}
