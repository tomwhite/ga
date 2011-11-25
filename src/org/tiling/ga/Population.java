package org.tiling.ga;

/**
 * A Population is a collection of Chromosomes that
 * are bred to produce new generations of offspring.
 * Each iteration involves <i>selection</i> of the
 * fittest individuals, then pairwise breeding to
 * produce a pair of offspring. The breeding process
 * consists of <i>crossover</i> followed by <i>mutation</i>.
 * The entire set of generations is called a <i>run</i>.
 * <p>
 * Make sure implementations are beans, so
 * they can be used with {@link Breeder}.
 * @see Chromosome
 * @see Environment
 */
public interface Population {

	/**
	 * @return the Environment in which this Population
	 * lives 
	 * @see Environment	
	 */
	public Environment getEnvironment();

	/**
	 * @param environment the Environment in which this Population
	 * lives 
	 * @see Environment	
	 */
	public void setEnvironment(Environment environment);

	/**
	 * @return the Selection strategy
	 * @see Selection	
	 */
	public Selection getSelection();

	/**
	 * @param selection the Selection strategy
	 * @see Selection	
	 */
	public void setSelection(Selection selection);

	/**
	 * Iterate this population.
	 * @param generations the number of generations to iterate
	 */
	public void run(int generations);

	/**
	 * @return the individuals that make up the current
	 * generation.
	 * @see Chromosome
	 */
	public Chromosome[] getIndividuals();


	
	/**
	 * @return the variance of the fitness of the individuals in the current
	 * generation.
	 */
	public double getFitnessVariance();
	
	/**
	 * @return the mean fitness of the individuals in the current
	 * generation.
	 */
	public double getMeanFitness();	/**
	 * I set the individuals that make up the current
	 * generation.
	 * @see Chromosome
	 */
	public void setIndividuals(Chromosome[] individuals);}