package de.atlasmc.io.protocol;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.protocol.play.*;

public interface ProtocolPlay extends Protocol {

	public PacketOutSetSlot createPacketOutSetSlot(byte windowID, int slot, ItemStack item);

}
