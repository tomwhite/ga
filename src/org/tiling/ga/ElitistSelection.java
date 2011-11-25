package org.tiling.ga;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import java.util.Collections;/**
 * The top 20% individuals (the elite) are copied to the next generation
 * without modification. The remaining 80% are the offspring of parents
 * selected from the elite at random, with replacement.
 */
public class ElitistSelection implements Selection, Serializable {

	private static Random random = new Random();




	public Chromosome[] select(Chromosome[] individuals) {

		int N = individuals.length;

		Chromosome[] sortedIndividuals = (Chromosome[]) individuals.clone();
		Arrays.sort(sortedIndividuals, Collections.reverseOrder());

		Chromosome[] newGeneration = new Chromosome[N];

		int eliteN = N / 5;
		for (int i = 0; i < eliteN; i++) {
			newGeneration[i] = (Chromosome) sortedIndividuals[i].clone();
		}

		for (int i = eliteN; i < N; i++) {
			newGeneration[i] = (Chromosome) sortedIndividuals[random.nextInt(eliteN)].clone();
		}
		
		// crossover and mutate
		int upperBound = (N % 2 == 0) ? N : N - 1; // ignore last individual if N is odd
		for (int i = eliteN; i < upperBound; i += 2) {
			newGeneration[i].crossover(newGeneration[i + 1]);

			newGeneration[i].mutate();
			newGeneration[i + 1].mutate();
		}

		return newGeneration;

	}


	
}