package com.example;

/**
 * A really weird Hello World class to create an
 * artificial example of test coverage.
 */
public class HelloWorld {
	
	private String greeting;
	
	/**
	 * Constructs the really weird Hello World object.
	 */
	public HelloWorld() {
		// This is deliberately weird to artificially create
		// an example for test coverage.
		String[] characters = {
			"H", "e", "l", "l", "o", " ", "W", "o", "r", "l", "d", "!"
		};
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < characters.length; i++) {
			builder.append(characters[i]);
		}
		greeting = builder.toString();
	}
	
	/**
	 * Gets the greeting.
	 * @return the greeting
	 */ 
	public String getGreeting() {
		return greeting;
	}
	
	/**
	 * Checks if someone was the one greeted.
	 * @param name The name to check.
	 * @return true if name is greeted by this Hello World object.
	 */
	public boolean didWeGreet(String name) {
		// This can obviously be condensed into a single return statement.
		// Deliberately making more complex so that we don't get 100% coverage.
		if (name.equals(greeting.substring(6, greeting.length()-1))) {
			return true;
		} else {
			// weird unnecessary meaningless calculation that our tests won't cover
			int x = name.length() * 100;
			return x < 0;
		}
	}
}
