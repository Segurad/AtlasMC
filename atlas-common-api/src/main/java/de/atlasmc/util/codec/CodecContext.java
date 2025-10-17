package de.atlasmc.util.codec;

public class CodecContext {

	public static final CodecContext
	DEFAULT_CLIENT = new CodecContext(true, null),
	DEFAULT_SERVER = new CodecContext(false, null);
	
	/**
	 * Defines whether or not a serialization is for send to a client
	 */
	public final boolean clientSide;
	
	/**
	 * Local used for localization or null if default or no localization
	 */
	public final String local;
	
	public CodecContext(boolean clientSide, String local) {
		this.clientSide = clientSide;
		this.local = local;
	}
	
}
