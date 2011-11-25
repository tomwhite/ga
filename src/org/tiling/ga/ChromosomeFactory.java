package org.tiling.ga;

import java.io.Serializable;

/**
 * I create new {@link Chromosome}s. Make sure subclasses are beans so
 * they can be used with {@link Breeder}.
 */
public abstract class ChromosomeFactory implements Serializable {
	private double crossoverProbability = Chromosome.CROSSOVER_PROBABILITY;
	private double mutationProbability = Chromosome.MUTATION_PROBABILITY;
	public abstract Chromosome create();
	public double getCrossoverProbability() {
		return crossoverProbability;
	}
	public double getMutationProbability() {
		return mutationProbability;
	}
	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}
	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
}
