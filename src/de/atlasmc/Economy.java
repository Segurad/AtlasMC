package de.atlasmc;

import java.util.UUID;

public interface Economy {

	public void create(UUID uuid, double value);
	public boolean contains(UUID uuid);
	public double getMoney(UUID uuid);
	public void addMoney(UUID uuid, double value);
	public void setMoney(UUID uuid, double value);
	public void removeMoney(UUID uuid, double value);
	public void remove(UUID uuid);
}
