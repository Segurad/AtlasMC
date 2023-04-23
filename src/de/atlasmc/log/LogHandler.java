package de.atlasmc.log;

public interface LogHandler {
	
	public void userMessage(String msg);
	
	public void info(String msg);
	
	public void error(Throwable err);
	
	public void error(String msg);
	
	public void debug(String msg);
	
	public void warn(String msg);
	
	public void fatal(String msg);
	
	public void fatal(Throwable err);

}
