package de.atlasmc.plugin;

public class PluginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PluginException() {}
	
	public PluginException(Throwable cause) {
		super(cause);
	}
	
	public PluginException(String msg) {
		super(msg);
	}
	
	public PluginException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
