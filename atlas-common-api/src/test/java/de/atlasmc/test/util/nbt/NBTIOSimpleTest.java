package de.atlasmc.test.util.nbt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTIOReader;
import de.atlasmc.util.nbt.io.NBTIOWriter;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.SNBTReader;
import de.atlasmc.util.nbt.io.SNBTTokenizingReader;
import de.atlasmc.util.nbt.io.SNBTWriter;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.NBT;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

public class NBTIOSimpleTest {

	private static final NBT SIMPLE_TYPES_STRUCTURE;
	
	static {
		CompoundTag root = NBT.createCompoundTag("")
			.addTag(NBT.createCompoundTag("test_compound"))
			.addByteTag("test_byte", (byte) 85)
			.addShortTag("test_short", (short) 21845)
			.addIntTag("test_int", 1431655765)
			.addLongTag("test_long", 6148914691236517205L)
			.addStringTag("test_string", "some_test_string 0x42")
			.addFloatTag("test_float", 0.4242f)
			.addDoubleTag("test_double", 0.4242d)
			.addByteArrayTag("test_byte_array", new byte[] { (byte) 0xCA, (byte) 0xFF, (byte) 0xEE })
			.addIntArrayTag("test_int_array", new int[] { 0xCAFFEE42, 0xCAFFEE42, 0xCAFFEE42 })
			.addLongArrayTag("test_long_array", new long[] { 0xCAFFEE42CAFFEE42L, 0xCAFFEE42CAFFEE42L, 0xCAFFEE42CAFFEE42L })
			.addTag(
				NBT.createListTag("test_list", TagType.BYTE)
				.addByteTag(null, (byte) 0x55)
				.addByteTag(null, (byte) 0x55)
				.addByteTag(null, (byte) 0x55)
			).addTag(
				NBT.createListTag("test_compound_list", TagType.COMPOUND)
				.addTag(NBT.createCompoundTag("list_comp_1")
						.addIntTag("index", 1)
						.addStringTag("name", "list name 1"))
				.addTag(NBT.createCompoundTag("list_comp_2")
						.addIntTag("index", 2)
						.addStringTag("name", "list name 2"))
				.addTag(NBT.createCompoundTag("list_comp_3")
						.addIntTag("index", 3)
						.addStringTag("name", "list name 3"))
			).addTag(NBT.createListTag("test_empty_list", TagType.TAG_END))
			.addTag(NBT.createListTag("test_list_list", TagType.LIST)
				.addTag(NBT.createListTag(null, TagType.BYTE)
						.addByteTag(null, (byte) 0x55)
						.addByteTag(null, (byte) 0x55)
						.addByteTag(null, (byte) 0x55)));
		SIMPLE_TYPES_STRUCTURE = root;
	}
	
	@Test
	public void testNBTIOReader() throws IOException {
		InputStream in = NBTIOSimpleTest.class.getResourceAsStream("/nbt/simple_types.nbt");
		NBTIOReader reader = new NBTIOReader(in);
		testReader(reader, SIMPLE_TYPES_STRUCTURE);
	}
	
	@Test
	public void testNBTNIOReader() throws IOException {
		InputStream in = NBTIOSimpleTest.class.getResourceAsStream("/nbt/simple_types.nbt");
		ByteBuf buff = Unpooled.buffer();
		buff.writeBytes(in, in.available());
		NBTNIOReader reader = new NBTNIOReader(buff);
		testReader(reader, SIMPLE_TYPES_STRUCTURE);
	}

	@Disabled("waiting for snbt reader implementation")
	@Test
	public void testSNBTReader() throws IOException {
		InputStream in = NBTIOSimpleTest.class.getResourceAsStream("/nbt/simple_types.snbt");
		SNBTReader reader = new SNBTReader(new InputStreamReader(in));
		testReader(reader, SIMPLE_TYPES_STRUCTURE);
	}
	
	@Test
	public void testSNBTTokenizingReader() throws IOException {
		InputStream in = NBTIOSimpleTest.class.getResourceAsStream("/nbt/simple_types.snbt");
		BufferedReader bufreader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = bufreader.readLine()) != null) {
            result.append(line);
        }
		SNBTTokenizingReader reader = new SNBTTokenizingReader(result.toString());
		testReader(reader, SIMPLE_TYPES_STRUCTURE);
	}
	
	@Test
	public void testNBTIOWriter() throws IOException {
		ByteBuf buf = Unpooled.buffer(4096);
		ByteBufOutputStream out = new ByteBufOutputStream(buf);
		NBTIOWriter writer = new NBTIOWriter(out);
		writer.writeNBT(SIMPLE_TYPES_STRUCTURE);
		writer.close();
		// load test buffer
		InputStream in = NBTIOSimpleTest.class.getResourceAsStream("/nbt/simple_types.nbt");
		ByteBuf testbuf = Unpooled.buffer();
		testbuf.writeBytes(in, in.available());
		Assertions.assertEquals(testbuf, buf);
	}
	
	@Test
	public void testNBTNIOWriter() throws IOException {
		ByteBuf buf = Unpooled.buffer(4096);
		NBTNIOWriter writer = new NBTNIOWriter(buf);
		writer.writeNBT(SIMPLE_TYPES_STRUCTURE);
		writer.close();
		// load test buffer
		InputStream in = NBTIOSimpleTest.class.getResourceAsStream("/nbt/simple_types.nbt");
		ByteBuf testbuf = Unpooled.buffer();
		testbuf.writeBytes(in, in.available());
		Assertions.assertEquals(testbuf, buf);
	}
	
	@Test
	public void testSNBTWriter() throws IOException {
		StringWriter stringWriter = new StringWriter();
		SNBTWriter writer = new SNBTWriter(stringWriter);
		writer.writeNBT(SIMPLE_TYPES_STRUCTURE);
		writer.close();
		
		InputStream in = NBTIOSimpleTest.class.getResourceAsStream("/nbt/simple_types.snbt");
		InputStreamReader reader = new InputStreamReader(in);
		StringBuilder testString = new StringBuilder(in.available());
		for (int c; (c = reader.read()) != -1;)
			testString.append((char) c);
		Assertions.assertEquals(testString.toString(), stringWriter.toString());
	}
	
	private void testReader(NBTReader reader, NBT sampleData) {
		try {
			NBT nbt = reader.readNBT();
			Assertions.assertEquals(sampleData, nbt);
		} catch (IOException e) {
			Assertions.fail("Error while reading simple_types.nbt", e);
		}
	}
	
	/**
	 * Prints the contents read of a nbt stream containing a compound
	 * @param reader
	 * @throws IOException
	 */
	public static void traceReader(NBTReader reader) throws IOException {
		reader.readNextEntry();
		final int depth = reader.getDepth();
		while (reader.getDepth() >= depth) {
			TagType type = reader.getType();
			CharSequence name = reader.getFieldName();
			if (reader.isList()) {
				type = reader.getListType();
				name = "*listelement*";
			}
			System.out.println("read " + type + "(" + name + ")" + (type == TagType.LIST ? "[" + reader.getListType() + "]" : "") + " depth: " + reader.getDepth());
			if (type == TagType.COMPOUND || type == TagType.LIST || type == TagType.TAG_END) {
				reader.readNextEntry();
				System.out.println("next");
				continue;
			}
			System.out.println("value: " + reader.readNBT());
		}
	}
	
}
