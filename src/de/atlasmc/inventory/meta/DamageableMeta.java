package de.atlasmc.inventory.meta;

public interface DamageableMeta extends ItemMeta {
	
	public DamageableMeta clone();
	
	@Override
	public default Class<? extends DamageableMeta> getInterfaceClass() {
		return DamageableMeta.class;
	}
	
	public void setDamage(int damage);
	
	public int getDamage();
	
	public boolean hasDamage();

}
