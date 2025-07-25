package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SpellcasterIllager;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreSpellcasterIllager extends CoreRaider implements SpellcasterIllager {

	protected static final MetaDataField<Byte>
	META_SPELL = new MetaDataField<>(CoreRaider.LAST_META_INDEX+1, (byte) Spell.NONE.getID(), MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreRaider.LAST_META_INDEX+1;

	private int spellcastTime;
	
	public CoreSpellcasterIllager(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SPELL);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Spell getSpell() {
		return Spell.getByID(metaContainer.getData(META_SPELL));
	}

	@Override
	public void setSpell(Spell spell) {
		if (spell == null)
			throw new IllegalArgumentException("Spell can not be null!");
		metaContainer.get(META_SPELL).setData((byte) spell.getID());
	}

	@Override
	public void setSpellcastTime(int time) {
		this.spellcastTime = time;
	}

	@Override
	public int getSpellcastTime() {
		return spellcastTime;
	}

}
