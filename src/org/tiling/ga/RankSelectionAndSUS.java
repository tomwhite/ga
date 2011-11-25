package org.tiling.ga;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * Individuals are ranked according to fitness, then selected using
 * "Stochastic Universal" Sampling (SUS).
 */
public class RankSelectionAndSUS implements Selection, Serializable {

	private static Random random = new Random();

	public static final double MAX = 1.1;
	public static final double MIN = 2 - MAX;

	public Chromosome[] select(Chromosome[] individuals) {

		int N = individuals.length;

		double[] expVal = new double[N];

		// highly inefficient!
		Chromosome[] sortedIndividuals = (Chromosome[]) individuals.clone();
		Arrays.sort(sortedIndividuals);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (individuals[i] == sortedIndividuals[j]) {
					expVal[i] = MIN + (MAX - MIN) * j / (N - 1);
					break;
				}
			}
		}

		Chromosome[] newGeneration = new Chromosome[N];

		double r = random.nextDouble();
		double sum = 0;
		int j = 0;
		// select N individuals
		for (int i = 0; i < N; i++) {
			for (sum +=expVal[i]; sum > r; r++) {
				// select individual i
				newGeneration[j++] = (Chromosome) individuals[i].clone();
			}
		}
		return crossoverAndMutate.select(newGeneration);

	}


	
	private Selection crossoverAndMutate = new CrossoverAndMutate();}