package main;

import handmade.HandMadeCalculate;

public class Main {

	private static final int LENGTH = 5000;

	public static void main(String[] args) {
		// System.out.println("---------------------------------------");
		// System.out.println("Apache library test:");
		// System.out.println("Initialisation:");
		// System.out.println(ApacheCalculate.init(LENGTH, LENGTH));
		// System.out.println("Calculation:");
		// System.out.println(ApacheCalculate.calculate());
		// System.out.println("---------------------------------------");
		// System.out.println("Jama library test:");
		// System.out.println("Initialisation:");
		// System.out.println(JamaCalculate.init(LENGTH, LENGTH));
		// System.out.println("Calculation:");
		// System.out.println(JamaCalculate.calculate());
		System.out.println("---------------------------------------");
		System.out.println("HandMade library test:");
		System.out.println("Initialisation:");
		System.out.println(HandMadeCalculate.init(LENGTH, LENGTH));
		System.out.println("Calculation:");
		System.out.println(HandMadeCalculate.calculate());
		System.out.println("---------------------------------------");

	}
}
