package de.atlasmc.util.serialization;

import java.io.IOException;

import de.atlasmc.util.annotation.NotNull;

public interface SerializationHandler<T, I, O, C> {
	
	default void serialize(T value, @NotNull O ouput) throws IOException {
		serialize(value, ouput, getDefaultContext());
	}
	
	/**
	 * Serializes the given values to the given output
	 * @param value to serialize
	 * @param ouput the output
	 * @param context a arbitrary context for serialization
	 * @return true if success
	 * @throws IOException
	 */
	boolean serialize(T value, @NotNull O ouput, C context) throws IOException;
	
	default T deserialize(@NotNull I input) throws IOException {
		return deserialize(input, getDefaultContext());
	}
	
	default T deserialize(@NotNull I input, C context) throws IOException {
		return deserialize(null, input, context);
	}
	
	/**
	 * Deserialize the the input to a object
	 * @param value the object deserialized to
	 * @param input the input
	 * @param context a arbitrary context for serialization
	 * @return deserialized object
	 * @throws IOException
	 */
	T deserialize(T value, @NotNull I input, C context) throws IOException;
	
	C getDefaultContext();

}
