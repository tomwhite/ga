package org.tiling.ga;

import java.io.Serializable;
import java.util.Random;

/**
 * Fitness-Proportionate Selection with "Roulette Wheel" Sampling
 */
public class RouletteWheelSampling implements Selection, Serializable {

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

		// calculate total expected value of individuals of the population
		double T = 0;
		for (int i = 0; i < N; i++) {
			T += expVal[i];
		}

		// select N individuals
		for (int i = 0; i < N; i++) {
			int r = random.nextInt((int) T);

			int j = 0;
			double sum = expVal[j];
			while (sum < r) {
				j++;
				sum += expVal[j];
			}
			// select individual j
			newGeneration[i] = (Chromosome) individuals[j].clone();
		}

		return crossoverAndMutate.select(newGeneration);

	}

	private Selection crossoverAndMutate = new CrossoverAndMutate();}