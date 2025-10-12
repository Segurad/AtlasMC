package de.atlasmc.node.io.protocol.common;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.dialog.Dialog;

public abstract class AbstractPacketShowDialog extends AbstractPacket {

	public NamespacedKey dialogID;
	
	public Dialog dialog;
	
}
