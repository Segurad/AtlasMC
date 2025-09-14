package de.atlascore.network.master;

import de.atlasmc.core.network.CoreAbstractAtlasNetworkHandlerBuilder;
import de.atlasmc.network.AtlasNetworkHandler;

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
