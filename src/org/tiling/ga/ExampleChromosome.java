package org.tiling.ga;

/**
 * An example of a GA with a very simple fitness function. Useful for
 * testing.
 */
public class ExampleChromosome extends FixedLengthChromosome {

	public ExampleChromosome() {
		super(8);
	}

	public ExampleChromosome(int length) {
		super(length);
	}

	// used for testing
	public ExampleChromosome(String bitString) {
		super(8);
		string = new StringBuffer(bitString);
	}

	/**
	 * Computes the fitness of the Chromosome, defined to be
	 * simply the number of ones ('1') in the bit string
	 */
	public void computeFitness() {
		fitness = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '1') {
				fitness++;
			}
		}
	}

}