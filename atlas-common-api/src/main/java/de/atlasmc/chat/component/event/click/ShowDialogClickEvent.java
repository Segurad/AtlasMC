package de.atlasmc.chat.component.event.click;

import de.atlasmc.dialog.Dialog;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.registry.Registries;

public final class ShowDialogClickEvent implements ClickEvent {
	
	public static final NBTCodec<ShowDialogClickEvent>
	NBT_HANDLER = NBTCodec
					.builder(ShowDialogClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.codec("dialog", ShowDialogClickEvent::getDialog, ShowDialogClickEvent::setDialog, Registries.registryValueNBTCodec(Dialog.REGISTRY_KEY))
					.codec("dialog", ShowDialogClickEvent::getDialog, ShowDialogClickEvent::setDialog, Dialog.NBT_HANDLER)
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
