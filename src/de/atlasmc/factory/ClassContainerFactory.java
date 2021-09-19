package de.atlasmc.factory;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryHolder;

/**
 * Class based {@link ContainerFactory} for Inventories or GUI by only using the Title and Holder
 */
public class ClassContainerFactory<I extends Inventory> extends ContainerFactory<I> {
	
	private final Class<? extends I> clazz; 

	/**
	 * 
	 * @param clazz must have a constructor ({@link String}, {@link InventoryHolder})
	 */
	public ClassContainerFactory(Class<? extends I> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public I create(InventoryType type, ChatComponent title, InventoryHolder holder) {
		try {
			return clazz.getConstructor(String.class, InventoryHolder.class).newInstance(title, holder);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
