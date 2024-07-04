package de.atlasmc.util.nbt;

public class NBTException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NBTException(String msg) {
		super(msg);
	}
	
	public NBTException(String msg, Throwable err) {
		super(msg, err);
	}

}
