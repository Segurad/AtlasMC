package de.atlasmc.component;

import de.atlasmc.util.annotation.Nullable;

public interface HolderBoundComponent<H extends ComponentHolder<?>> extends Component {
	
	/**
	 * Registers the holder of this component.
	 * If another holder is already registered a exception is thrown.
	 * @param holder
	 * @throws IllegalStateException if already bound to a holder
	 */
	void registerHolder(H holder);
	
	/**
	 * Unregisters from the holder.
	 */
	void unregisterHolder();
	
	/**
	 * Returns the holder or null
	 * @return holder or null
	 */
	@Nullable
	H getHolder();

}
