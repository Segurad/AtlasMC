package de.atlasmc.test.util;

import org.junit.jupiter.api.Test;

import de.atlasmc.util.CharBuffer;

import static org.junit.jupiter.api.Assertions.*;

class CharBufferTest {

    @Test
    void testAppendSingleChar() {
        CharBuffer buffer = new CharBuffer();
        buffer.append('a');
        assertEquals(1, buffer.length());
        assertEquals('a', buffer.get(0));
    }

    @Test
    void testAppendCharSequence() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("hello");
        assertEquals(5, buffer.length());
        assertEquals("hello", buffer.subSequence(0, 5).toString());
    }

    @Test
    void testAppendCharArray() {
        CharBuffer buffer = new CharBuffer();
        buffer.append(new char[]{'t', 'e', 's', 't'});
        assertEquals(4, buffer.length());
        assertEquals("test", buffer.subSequence(0, 4).toString());
    }

    @Test
    void testReadChar() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("abc");
        assertEquals('a', buffer.read());
        assertEquals('b', buffer.read());
        assertEquals('c', buffer.read());
    }

    @Test
    void testReadThrowsExceptionWhenEmpty() {
        CharBuffer buffer = new CharBuffer();
        assertThrows(IndexOutOfBoundsException.class, buffer::read);
    }

    @Test
    void testEnsureSize() {
        CharBuffer buffer = new CharBuffer(2);
        buffer.append("ab");
        buffer.append('c'); // Should trigger resize
        assertEquals(3, buffer.length());
        assertEquals("abc", buffer.subSequence(0, 3).toString());
    }

    @Test
    void testCapacityIncrease() {
        CharBuffer buffer = new CharBuffer(2);
        assertEquals(2, buffer.capacity());
        buffer.capacity(10);
        assertTrue(buffer.capacity() >= 10);
    }

    @Test
    void testCapacityReduction() {
        CharBuffer buffer = new CharBuffer(10);
        buffer.append("test");
        buffer.capacity(2);
        assertEquals(2, buffer.getWriterIndex());
    }

    @Test
    void testSetIndex() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("test");
        buffer.setIndex(2, 4);
        assertEquals(2, buffer.getReaderIndex());
        assertEquals(4, buffer.getWriterIndex());
    }

    @Test
    void testClear() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("clear me");
        buffer.clear();
        assertEquals(0, buffer.length());
        assertEquals(0, buffer.getReaderIndex());
        assertEquals(0, buffer.getWriterIndex());
    }

    @Test
    void testDiscardReadChars() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("discard");
        buffer.read(); // Read 'd'
        buffer.discardReadChars();
        assertEquals('i', buffer.get(0)); // 'i' should now be at index 0
    }

    @Test
    void testGetWithInvalidIndex() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("test");
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.get(10));
    }

    @Test
    void testGetCharArray() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("test");
        char[] dst = new char[2];
        buffer.get(1, dst);
        assertArrayEquals(new char[]{'e', 's'}, dst);
    }

    @Test
    void testReadCharArray() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("read");
        char[] dst = new char[2];
        buffer.read(dst);
        assertArrayEquals(new char[]{'r', 'e'}, dst);
    }

    @Test
    void testSetReaderIndex() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("test");
        buffer.setReaderIndex(2);
        assertEquals(2, buffer.getReaderIndex());
    }

    @Test
    void testSetWriterIndex() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("test");
        buffer.setWriterIndex(2);
        assertEquals(2, buffer.getWriterIndex());
    }

    @Test
    void testSetInvalidReaderIndex() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("test");
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.setReaderIndex(10));
    }

    @Test
    void testSubSequence() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("hello world");
        CharSequence sub = buffer.subSequence(0, 5);
        assertEquals("hello", sub.toString());
    }

    @Test
    void testCharAt() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("char");
        assertEquals('c', buffer.charAt(0));
        assertEquals('a', buffer.charAt(2));
        assertEquals('r', buffer.charAt(3));
    }
    
    @Test
    void testGet() {
        CharBuffer buffer = new CharBuffer();
        buffer.append("char");
        assertEquals('c', buffer.get());
        assertEquals('c', buffer.get());
        buffer.setReaderIndex(2);
        assertEquals('a', buffer.get());
    }
}
