package org.tiling.ga;

import java.io.Serializable;


/**
 * I perform crossover and mutation on a collection of chromosomes.
 */
public class CrossoverAndMutate implements Selection, Serializable {


	public Chromosome[] select(Chromosome[] individuals) {
		int N = individuals.length;
		int upperBound = (N % 2 == 0) ? N : N - 1; // ignore last individual if N is odd
		for (int i = 0; i < upperBound; i += 2) {
			individuals[i].crossover(individuals[i + 1]);

			individuals[i].mutate();
			individuals[i + 1].mutate();
		}
		return individuals;
	}

}