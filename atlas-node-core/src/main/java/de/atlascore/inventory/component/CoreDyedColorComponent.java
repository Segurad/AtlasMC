package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DyedColorComponent;
import io.netty.buffer.ByteBuf;

public class CoreDyedColorComponent extends AbstractItemComponent implements DyedColorComponent {
	
	private Color color;
	
	public CoreDyedColorComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDyedColorComponent clone() {
		return (CoreDyedColorComponent) super.clone();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		color = Color.fromRGB(buf.readInt());
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		buf.writeInt(color != null ? color.asRGB() : 0);
	}

}
