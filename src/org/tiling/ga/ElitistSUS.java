package org.tiling.ga;

import java.io.Serializable;
import java.util.Arrays;


import java.util.Collections;/**
 * The top 20% individuals (the elite) are copied to the next generation
 * without modification. The remaining 80% are selected from the
 * lower 80% uses SUS.
 */
public class ElitistSUS implements Selection, Serializable {





	public Chromosome[] select(Chromosome[] individuals) {

		int N = individuals.length;

		Chromosome[] sortedIndividuals = (Chromosome[]) individuals.clone();
		Arrays.sort(sortedIndividuals, Collections.reverseOrder());

		Chromosome[] newGeneration = new Chromosome[N];

		int eliteN = N / 5;
		for (int i = 0; i < eliteN; i++) {
			newGeneration[i] = (Chromosome) sortedIndividuals[i].clone();
		}

		Chromosome[] commoners = new Chromosome[N - eliteN];
		System.arraycopy(sortedIndividuals, eliteN, commoners, 0, commoners.length);
		System.arraycopy(sus.select(commoners), 0, newGeneration, eliteN, commoners.length);

		return newGeneration;

	}


	
	Selection sus = new StochasticUniversalSampling();}