package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.entity.Merchant.MerchantRecipe;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_MERCHANT_OFFERS)
public class PacketOutMerchantOffers extends AbstractPacket implements PacketPlayOut {
	
	private int windowID;
	private int level;
	private int experience;
	private boolean regular;
	private boolean canRestock;
	private List<MerchantRecipe> trades;
	
	public int getWindowID() {
		return windowID;
	}

	public int getLevel() {
		return level;
	}

	public int getExperience() {
		return experience;
	}

	public boolean isRegular() {
		return regular;
	}

	public boolean canRestock() {
		return canRestock;
	}

	public List<MerchantRecipe> getTrades() {
		return trades;
	}

	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}

	public void setCanRestock(boolean canRestock) {
		this.canRestock = canRestock;
	}

	public void setTrades(List<MerchantRecipe> trades) {
		this.trades = trades;
	}

	@Override
	public int getDefaultID() {
		return OUT_MERCHANT_OFFERS;
	}

}
