package de.atlasmc.dialog;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryKey;

public interface Dialog extends NBTSerializable, Namespaced {

	static final RegistryKey<Dialog> REGISTRY_KEY = Registries.getRegistryKey(Dialog.class);
	
	static final NBTCodec<Dialog>
	NBT_HANDLER = NBTCodec
					.builder(Dialog.class)
					.searchKeyConstructor("type", Registries.getRegistry(DialogType.class), DialogType::createDialog, Dialog::getType)
					.build();
	
	DialogType getType();
	
	Chat getTitle();
	
	void setTitle(Chat title);
	
	Chat getExternalTitle();
	
	void setExternalTitle(Chat chat);
	
	boolean canCloseWithEscape();
	
	void setCloseWithEscape(boolean value);
	
	boolean isPause();
	
	void setPause(boolean pause);
	
	AfterAction getAfterAction();
	
	void setAfterAction(AfterAction action);
	
	public static enum AfterAction {
		
		CLOSE,
		NONE,
		WAIT_FOR_RESPONSE;
		
	}

}
