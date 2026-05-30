package de.atlasmc.chat.component.event.click;

import de.atlasmc.dialog.Dialog;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.NotNull;

public final class ShowDialogClickEvent implements ClickEvent {
	
	@NotNull
	public static final NBTCodec<ShowDialogClickEvent>
	NBT_CODEC = NBTCodec
					.builder(ShowDialogClickEvent.class)
					.include(ClickEvent.NBT_CODEC)
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
		return NBT_CODEC;
	}

}
