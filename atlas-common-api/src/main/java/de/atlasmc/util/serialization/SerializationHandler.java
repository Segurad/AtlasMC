package de.atlasmc.util.serialization;

import java.io.IOException;

public interface SerializationHandler<T, I, O, C> {
	
	default void serialize(T value, O ouput) throws IOException {
		serialize(value, ouput, getDefaultContext());
	}
	
	/**
	 * Serializes the given values to the given output
	 * @param value to serialize
	 * @param ouput the output
	 * @param context a arbitrary context for serialization
	 * @throws IOException
	 */
	void serialize(T value, O ouput, C context) throws IOException;
	
	default T deserialize(I input) throws IOException {
		return deserialize(input, getDefaultContext());
	}
	
	/**
	 * Deserialize the the input to a object
	 * @param input the input
	 * @return deserialized object
	 * @param context a arbitrary context for serialization
	 * @throws IOException
	 */
	T deserialize(I input, C context) throws IOException;
	
	C getDefaultContext();

}
