package de.atlasmc.log;

import java.util.List;

public interface Logger extends LogHandler {
	
	public boolean logInfo();
	
	public boolean logError();
	
	public boolean logDebug();
	
	public boolean logWarn();
	
	public boolean logFatal();
	
	public boolean logUserMsg();
	
	public void removeHandler(LogHandler handler);
	
	public void addHandler(LogHandler handler);
	
	public List<LogHandler> getHandlers();

}
