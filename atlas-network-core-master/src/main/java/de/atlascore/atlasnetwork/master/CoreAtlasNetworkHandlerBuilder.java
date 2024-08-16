package de.atlascore.atlasnetwork.master;

import de.atlascore.atlasnetwork.CoreAbstractAtlasNetworkHandlerBuilder;
import de.atlasmc.atlasnetwork.AtlasNetworkHandler;

public class CoreAtlasNetworkHandlerBuilder extends CoreAbstractAtlasNetworkHandlerBuilder<CoreAtlasNetworkHandlerBuilder> {

	@Override
	public CoreAtlasNetworkHandlerBuilder getThis() {
		return this;
	}

	@Override
	public AtlasNetworkHandler build() {
		return new CoreAtlasNetworkHandler(this);
	}

}
