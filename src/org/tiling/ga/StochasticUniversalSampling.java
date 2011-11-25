package org.tiling.ga;

import java.io.Serializable;
import java.util.Random;

/**
 * Fitness-Proportionate Selection with "Stochastic Universal" Sampling (SUS)
 */
public class StochasticUniversalSampling implements Selection, Serializable {

	private static Random random = new Random();

	public Chromosome[] select(Chromosome[] individuals) {

		int N = individuals.length;

		Chromosome[] newGeneration = new Chromosome[N];

		double expF = 0;
		for (int i = 0; i < N; i++) {
			expF += individuals[i].getFitness();
		}
		expF /= N;

		double[] expVal = new double[N];
		for (int i = 0; i < N; i++) {
			expVal[i] = individuals[i].getFitness() / expF;
		}

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