package de.atlasmc.chat.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class ScoreComponent extends AbstractBaseComponent<ScoreComponent> {

	public static final NBTSerializationHandler<ScoreComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ScoreComponent.class)
					.include(AbstractBaseComponent.NBT_HANDLER)
					.beginComponent("score")
					.string("name", ScoreComponent::getName, ScoreComponent::setName)
					.string("objective", ScoreComponent::getObjective, ScoreComponent::setObjective)
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
	public NBTSerializationHandler<? extends ScoreComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
