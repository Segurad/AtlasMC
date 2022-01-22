package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SpellcasterIllager;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreSpellcasterIllager extends CoreRaider implements SpellcasterIllager {

	protected static final MetaDataField<Byte>
	META_SPELL = new MetaDataField<>(CoreRaider.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreRaider.LAST_META_INDEX+1;
	
	public CoreSpellcasterIllager(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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

}
