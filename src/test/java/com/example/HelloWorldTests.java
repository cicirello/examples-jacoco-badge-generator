package com.example;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * JUnit test cases for the example of test coverage.
 */
public class HelloWorldTests {
	
	@Test
	public void test1() {
		HelloWorld hw = new HelloWorld();
		assertEquals("Hello World!", hw.getGreeting());
	}
	
	@Test
	public void test2() {
		HelloWorld hw = new HelloWorld();
		assertTrue(hw.didWeGreet("World"));
	}
}
