package de.atlasmc.network.io.protocol;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.network.NetworkType;
import de.atlasmc.network.io.RequestAction;

public abstract class AbstractPacketRequestType extends AbstractPacket {
	
	/**
	 * Requested type
	 */
	public NetworkType type;
	/**
	 * id of the transaction the server should respond with the same id may be 0 for no specific transaction.
	 */
	public int transactionID;
	/**
	 * Action for this request
	 */
	public RequestAction action;
	
}
