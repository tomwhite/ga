package org.tiling.ga;

/**
 * An Environment allows a collection of Chromosomes to
 * interact with one another at each generation in order
 * that their individual fitness values may be determined.
 * <p>
 * Make sure implementations are beans, so
 * they can be used with {@link Breeder}.
 * @see Population
 * @see Chromosome
 */
public interface Environment {

	/**
	 * Calculates the fitness of each Chromosome in the Population.
	 * This may be done by allowing the individuals to interact with
	 * one another and assigning a fitness measure based on their relative
	 * performance. Alternatively, an individual's fitness may be calculated
	 * using external criteria, e.g. its ability to perform a particular task.
	 * Whatever means are used to calculate fitness values, when this method
	 * returns each individual must have a fitness values set on it.
	 * @param individuals the individuals in this Environment
	 */
	public void calculateFitnessValues(Chromosome[] individuals);
	
}