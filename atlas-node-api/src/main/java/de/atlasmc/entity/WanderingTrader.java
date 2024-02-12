package de.atlasmc.entity;

public interface WanderingTrader extends AbstractVillager {

	public void setDespawnDelay(int delay);

	/**
	 * Returns the time in ticks until this WanderingTrader will despawn or -1 if the Trader will not despawn by time
	 * @return ticks or -1
	 */
	public int getDespawnDelay();
	
}
