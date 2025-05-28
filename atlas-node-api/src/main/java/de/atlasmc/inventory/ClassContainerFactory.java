package de.atlasmc.inventory;

import java.lang.reflect.Constructor;

import de.atlasmc.chat.Chat;
import de.atlasmc.util.factory.ClassFactory;
import de.atlasmc.util.factory.Factory;

/**
 * Class based {@link ContainerFactory} for Inventories or GUI by only using the Title and Holder
 */
public class ClassContainerFactory<I extends Inventory> extends ContainerFactory<I> implements Factory {
	
	private final Constructor<? extends I> constructor;

	/**
	 * 
	 * @param clazz must have a constructor ({@link Chat}, {@link InventoryHolder})
	 */
	public ClassContainerFactory(Class<? extends I> clazz) {
		this.constructor = ClassFactory.getConstructor(clazz, Chat.class, InventoryHolder.class);
	}
	
	@Override
	public I create(InventoryType type, Chat title, InventoryHolder holder) {
		return ClassFactory.createInstance(constructor, title, holder);
	}

}
