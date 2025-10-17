package de.atlasmc.chat.component.event.click;

import de.atlasmc.dialog.Dialog;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.nbt.codec.NBTCodec;

public final class ShowDialogClickEvent implements ClickEvent {
	
	public static final NBTCodec<ShowDialogClickEvent>
	NBT_HANDLER = NBTCodec
					.builder(ShowDialogClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.registryValue("dialog", ShowDialogClickEvent::getDialog, ShowDialogClickEvent::setDialog, Registries.getRegistry(Dialog.class), Dialog.NBT_HANDLER)
					.build();
	
	private Dialog dialog;
	
	@Override
	public ClickAction getAction() {
		return ClickAction.SHOW_DIALOG;
	}
	
	public Dialog getDialog() {
		return dialog;
	}
	
	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public NBTCodec<? extends ShowDialogClickEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
