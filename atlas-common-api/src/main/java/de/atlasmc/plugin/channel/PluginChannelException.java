package de.atlasmc.plugin.channel;

public class PluginChannelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public PluginChannelException(String msg) {
		super(msg);
	}
	
	public PluginChannelException(Throwable cause) {
		super(cause);
	}
	
	public PluginChannelException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
