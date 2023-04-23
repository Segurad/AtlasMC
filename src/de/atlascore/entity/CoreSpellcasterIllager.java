package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SpellcasterIllager;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreSpellcasterIllager extends CoreRaider implements SpellcasterIllager {

	protected static final MetaDataField<Byte>
	META_SPELL = new MetaDataField<>(CoreRaider.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreRaider.LAST_META_INDEX+1;
	
	protected static final CharKey
	NBT_SPELL_TICKS = CharKey.of("SpellTicks");
	
	static {
		NBT_FIELDS.setField(NBT_SPELL_TICKS, (holder, reader) -> {
			if (holder instanceof SpellcasterIllager) {
				((SpellcasterIllager) holder).setSpellcastTime(reader.readIntTag());
			} else reader.skipTag();
		});
	}

	private int spellcastTime;
	
	public CoreSpellcasterIllager(EntityType type, UUID uuid) {
		super(type, uuid);
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
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_SPELL_TICKS, getSpellcastTime());
	}

}
