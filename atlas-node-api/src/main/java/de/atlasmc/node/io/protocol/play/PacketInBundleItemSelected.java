package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_BUNDLE_ITEM_SELECTED, definition = "bundle_item_selected")
public class PacketInBundleItemSelected extends AbstractPacket implements PacketPlayIn {

	/**
	 * Slot the bundle is located
	 */
	public int slot;
	/**
	 * Slot within the bundle
	 */
	public int bundleSlot;
	
	@Override
	public int getDefaultID() {
		return IN_BUNDLE_ITEM_SELECTED;
	}

}
