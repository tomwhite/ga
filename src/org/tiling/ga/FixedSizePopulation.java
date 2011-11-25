package org.tiling.ga;

import java.io.Serializable;

/**
 * FixedSizePopulation is an implementation of Population that has 
 * a fixed generation size, lives in a NonInteractiveEnvironment, and
 * selects using RouletteWheelSampling.
 */
public class FixedSizePopulation implements Population, Serializable {



	protected Chromosome[] individuals;
	protected Environment environment = new NonInteractiveEnvironment();
	protected Selection selection = new RouletteWheelSampling();



	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public void run(int generations) {

		for (int generation = 0; generation < generations; generation++) {
			environment.calculateFitnessValues(individuals);
			individuals = selection.select(individuals);
		}

	}

	// debugging aid
	private void printIndividuals() {
		for (int i = 0; i< individuals.length; i++) {
			System.out.println(individuals[i]);
			System.out.println();
		}
	}


	
	public double getFitnessVariance() {
		double expFsquared = 0;
		for (int i = 0; i < individuals.length; i++) {
			double fitness = individuals[i].getFitness();
			expFsquared += fitness * fitness;
		}
		expFsquared /= individuals.length;
		double expF = getMeanFitness();
		return expFsquared - expF * expF;
	}

	public Chromosome[] getIndividuals() {
		return individuals;
	}

	public FixedSizePopulation() {
	}	public double getMeanFitness() {
		double expF = 0;
		for (int i = 0; i < individuals.length; i++) {
			expF += individuals[i].getFitness();
		}
		expF /= individuals.length;
		return expF;
	}	public void setIndividuals(Chromosome[] individuals) {
		this.individuals = individuals;
	}}