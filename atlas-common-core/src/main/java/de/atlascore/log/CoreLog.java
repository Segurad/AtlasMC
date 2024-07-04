package de.atlascore.log;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.Logger;
import de.atlasmc.log.Log;

public class CoreLog implements Log {
	
	private final String name;
	private final Logger log;
	private volatile boolean console;
	private final Marker marker;

	public CoreLog(String name, Logger log) {
		this.log = log;
		this.name = name;
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
	public void error(String message, Object... params) {
		log.error(marker, message, params);
	}

	@Override
	public void error(Throwable cause) {
		log.error(marker, "", cause);
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

}
