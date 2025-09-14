package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.FoodComponent;
import io.netty.buffer.ByteBuf;

public class CoreFoodComponent extends AbstractItemComponent implements FoodComponent  {
	
	private int nutrition;
	private float saturation;
	private boolean canAlwaysEat;
	
	public CoreFoodComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreFoodComponent clone() {
		return (CoreFoodComponent) super.clone();
	}

	@Override
	public int getNutrition() {
		return nutrition;
	}

	@Override
	public void setNutrition(int nutrition) {
		this.nutrition = nutrition;
	}

	@Override
	public float getSaturation() {
		return saturation;
	}

	@Override
	public void setSaturation(float saturation) {
		this.saturation = saturation;
	}

	@Override
	public boolean isAlwaysEatable() {
		return canAlwaysEat;
	}

	@Override
	public void setAlwaysEatable(boolean eatable) {
		this.canAlwaysEat = eatable;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		nutrition = readVarInt(buf);
		saturation = buf.readFloat();
		canAlwaysEat = buf.readBoolean();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(nutrition, buf);
		buf.writeFloat(saturation);
		buf.writeBoolean(canAlwaysEat);
	}

}
