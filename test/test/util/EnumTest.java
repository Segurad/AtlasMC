package test.util;

/**
 * Test enum classes
 */
public class EnumTest {
	
	private EnumTest() {}
	
	/**
	 * Test a enum class if a static getValues() and freeValues() method is present
	 * @param clazz
	 */
	public static void testCacheMethods(Class<? extends Enum<?>> clazz) {
		ReflectionUtil.isMethodPresentAndStatic(clazz, "getValues");
		ReflectionUtil.isMethodPresentAndStatic(clazz, "freeValues");
	}
	
	/**
	 * Test a enum class if a getID() and static getByID(int) method is present
	 * @param clazz
	 */
	public static void testIDMethods(Class<? extends Enum<?>> clazz) {
		ReflectionUtil.isMethodPresent(clazz, "getID");
		ReflectionUtil.isMethodPresentAndStatic(clazz, "getByID", int.class);
	}

}
