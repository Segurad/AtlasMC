package de.atlasmc.util.nbt.serialization;

public class NBTSerializationContext {

	public static final NBTSerializationContext
	DEFAULT_CLIENT = new NBTSerializationContext(true, null),
	DEFAULT_SERVER = new NBTSerializationContext(false, null);
	
	/**
	 * Defines whether or not a serialization is for send to a client
	 */
	public final boolean clientSide;
	
	/**
	 * Local used for localization or null if default or no localization
	 */
	public final String local;
	
	public NBTSerializationContext(boolean clientSide, String local) {
		this.clientSide = clientSide;
		this.local = local;
	}
	
}
