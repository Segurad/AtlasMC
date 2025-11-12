package de.atlasmc.chat.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public class ScoreComponent extends AbstractBaseComponent<ScoreComponent> {

	public static final NBTCodec<ScoreComponent>
	NBT_CODEC = NBTCodec
					.builder(ScoreComponent.class)
					.include(AbstractBaseComponent.NBT_CODEC)
					.beginComponent("score")
					.codec("name", ScoreComponent::getName, ScoreComponent::setName, NBTCodecs.STRING)
					.codec("objective", ScoreComponent::getObjective, ScoreComponent::setObjective, NBTCodecs.STRING)
					.endComponent()
					.build();
	
	private String name;
	private String objective;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getObjective() {
		return objective;
	}
	
	public void setObjective(String objective) {
		this.objective = objective;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.SCORE;
	}

	@Override
	protected ScoreComponent getThis() {
		return this;
	}
	
	@Override
	public NBTCodec<? extends ScoreComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
