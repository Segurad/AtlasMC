package de.atlasmc.core.log;

import org.apache.logging.log4j.Marker;

import java.util.Objects;

import org.apache.logging.log4j.Logger;
import de.atlasmc.log.Log;

public class CoreLog implements Log {
	
	private static final org.apache.logging.log4j.Level[] LEVELS_TO_LOG4J = new org.apache.logging.log4j.Level[] {
			org.apache.logging.log4j.Level.OFF,
			org.apache.logging.log4j.Level.FATAL,
			org.apache.logging.log4j.Level.ERROR,
			org.apache.logging.log4j.Level.WARN,
			org.apache.logging.log4j.Level.INFO,
			org.apache.logging.log4j.Level.DEBUG,
			org.apache.logging.log4j.Level.TRACE,
			org.apache.logging.log4j.Level.ALL
	};
	
	private static final Level[] LEVELS = Level.values();
	
	private final String name;
	private final Logger log;
	private volatile boolean console;
	private final Marker marker;
	private final Level level;

	public CoreLog(String name, Logger log) {
		this.name = Objects.requireNonNull(name);
		this.log = Objects.requireNonNull(log);
		this.level = LEVELS[log.getLevel().getStandardLevel().ordinal()];
		this.marker = new CoreAtlasMarker(this);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isSendToConsole() {
		return console;
	}

	@Override
	public void sendToConsole(boolean console) {
		boolean isConsole = this.console;
		if (isConsole == console)
			return;
		synchronized (this) {
			if (isConsole == console)
				return;
			if (console) {
				this.console = true;
			} else {
				this.console = false;
			}
		}
	}

	@Override
	public void error(String message) {
		log.error(marker, message);
	}

	@Override
	public void error(String message, Throwable cause) {
		log.error(marker, message, cause);
	}

	@Override
	public void error(String message, Object p0) {
		log.error(marker, message, p0);
	}
	
	@Override
	public void error(String message, Object p0, Object p1) {
		log.error(marker, message, p0, p1);
	}
	
	@Override
	public void error(String message, Object p0, Object p1, Object p2) {
		log.error(marker, message, p0, p1, p2);
	}
	
	@Override
	public void error(String message, Object p0, Object p1, Object p2, Object p3) {
		log.error(marker, message, p0, p1, p2, p3);
	}
	
	@Override
	public void error(String message, Object... params) {
		log.error(marker, message, params);
	}

	@Override
	public void error(Throwable cause) {
		log.error(marker, (String) null, cause);
	}

	@Override
	public void info(String message) {
		log.info(marker, message);
	}

	@Override
	public void info(String message, Object... params) {
		log.info(marker, message, params);	
	}

	@Override
	public void warn(String message) {
		log.warn(marker, message);
	}

	@Override
	public void warn(String message, Object... params) {
		log.warn(marker, message, params);
	}

	@Override
	public void debug(String message) {
		log.debug(marker, message);	
	}

	@Override
	public void debug(String message, Object... params) {
		log.debug(marker, message, params);
	}

	@Override
	public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
		log.error(marker, message, p0, p1, p2, p3, p4);
	}

	@Override
	public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		log.error(marker, message, p0, p1, p2, p3, p4, p5);
	}

	@Override
	public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		log.error(marker, message, p0, p1, p2, p3, p4, p5, p6);
	}

	@Override
	public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7) {
		log.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
	}

	@Override
	public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7, Object p8) {
		log.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
	}

	@Override
	public void error(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7, Object p8, Object p9) {
		log.error(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
	}

	@Override
	public void info(String message, Object p0) {
		log.info(marker, message, p0);
	}

	@Override
	public void info(String message, Object p0, Object p1) {
		log.info(marker, message, p0, p1);
	}

	@Override
	public void info(String message, Object p0, Object p1, Object p2) {
		log.info(marker, message, p0, p1, p2);
	}

	@Override
	public void info(String message, Object p0, Object p1, Object p2, Object p3) {
		log.info(marker, message, p0, p1, p2, p3);
	}

	@Override
	public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
		log.info(marker, message, p0, p1, p2, p3, p4);
	}

	@Override
	public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		log.info(marker, message, p0, p1, p2, p3, p4, p5);
	}

	@Override
	public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		log.info(marker, message, p0, p1, p2, p3, p4, p5, p6);
	}

	@Override
	public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7) {
		log.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
	}

	@Override
	public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7, Object p8) {
		log.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
	}

	@Override
	public void info(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7, Object p8, Object p9) {
		log.info(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
	}

	@Override
	public void warn(String message, Object p0) {
		log.warn(marker, message, p0);
	}

	@Override
	public void warn(String message, Object p0, Object p1) {
		log.warn(marker, message, p0, p1);
	}

	@Override
	public void warn(String message, Object p0, Object p1, Object p2) {
		log.warn(marker, message, p0, p1, p2);
	}

	@Override
	public void warn(String message, Object p0, Object p1, Object p2, Object p3) {
		log.warn(marker, message, p0, p1, p2, p3);
	}

	@Override
	public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
		log.warn(marker, message, p0, p1, p2, p3, p4);
	}

	@Override
	public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		log.warn(marker, message, p0, p1, p2, p3, p4, p5);
	}

	@Override
	public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		log.warn(marker, message, p0, p1, p2, p3, p4, p5, p6);
	}

	@Override
	public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7) {
		log.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
	}

	@Override
	public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7, Object p8) {
		log.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
	}

	@Override
	public void warn(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7, Object p8, Object p9) {
		log.warn(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
	}

	@Override
	public void debug(String message, Object p0) {
		log.debug(marker, message, p0);
	}

	@Override
	public void debug(String message, Object p0, Object p1) {
		log.debug(marker, message, p0, p1);
	}

	@Override
	public void debug(String message, Object p0, Object p1, Object p2) {
		log.debug(marker, message, p0, p1, p2);
	}

	@Override
	public void debug(String message, Object p0, Object p1, Object p2, Object p3) {
		log.debug(marker, message, p0, p1, p2, p3);
	}

	@Override
	public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
		log.debug(marker, message, p0, p1, p2, p3, p4);
	}

	@Override
	public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		log.debug(marker, message, p0, p1, p2, p3, p4, p5);
	}

	@Override
	public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
		log.debug(marker, message, p0, p1, p2, p3, p4, p5, p6);
	}

	@Override
	public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7) {
		log.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
	}

	@Override
	public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7, Object p8) {
		log.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
	}

	@Override
	public void debug(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6,
			Object p7, Object p8, Object p9) {
		log.debug(marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public void log(Level level, String message) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message);
	}

	@Override
	public void log(Level level, String message, Throwable cause) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, cause);
	}

	@Override
	public void log(Level level, String message, Object p0) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1, Object p2) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1, p2);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1, p2, p3);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1, p2, p3, p4);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1, p2, p3, p4, p5);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5,
			Object p6) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1, p2, p3, p4, p5, p6);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5,
			Object p6, Object p7) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1, p2, p3, p4, p5, p6, p7);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5,
			Object p6, Object p7, Object p8) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
	}

	@Override
	public void log(Level level, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5,
			Object p6, Object p7, Object p8, Object p9) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
	}

	@Override
	public void log(Level level, String message, Object... params) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, message, params);
	}

	@Override
	public void log(Level level, Throwable cause) {
		log.log(LEVELS_TO_LOG4J[level.ordinal()], marker, (String) null, cause);
	}

	@Override
	public void info(String message, Throwable cause) {
		log.info(marker, message, cause);
	}

	@Override
	public void info(Throwable cause) {
		log.info(marker, (String) null, cause);
	}

	@Override
	public void warn(String message, Throwable cause) {
		log.warn(marker, message, cause);
	}

	@Override
	public void warn(Throwable cause) {
		log.warn(marker, (String) null, cause);
	}

	@Override
	public void debug(String message, Throwable cause) {
		log.debug(marker, message, cause);
	}

	@Override
	public void debug(Throwable cause) {
		log.debug(marker, (String) null, cause);
	}

}
