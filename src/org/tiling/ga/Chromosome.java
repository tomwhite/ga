package org.tiling.ga;

/**
 * A Chromosome encodes a string of DNA, typically represented by
 * a sequence of characters, although this is implementation dependent.
 * <p>
 * A Chromosome is mutable. Changes may occur in two ways:
 * <ol>
 * <li> By calling the <code>crossover</code> method, which randomly performs
 * a crossover with another chromosome.
 * <li> By calling the <code>mutate</code> method, which randomly changes some
 * of the bits in the string each with a small, but equal, probability.
 * </ol>
 * Notice however that these methods may leave the chromosome unchanged.
 * <p>
 * The Chromosome interface extends java.lang.Comparable, and implementations
 * should perform the comparison on the basis of the fitness function.
 * <p>
 * The fitness of a Chromosome is a real number representing how well the
 * candidate solution - as encoded by the chromosome - solves the problem the
 * GA is designed to solve. 
 */
public interface Chromosome extends Cloneable, Comparable {

	public static double CROSSOVER_PROBABILITY = 0.9;
	public static double MUTATION_PROBABILITY = 0.005;

	/**
	 * Performs crossover between this and chromosome, with
	 * a given probability. The probability may be set upon
	 * construction, otherwise it defaults to CROSSOVER_PROBABILITY.
	 * @return chromosome after crossover with this
	 */
	public Chromosome crossover(Chromosome chromosome);

	/**
	 * Randomly flip some of the bits in the string. Mutation can
	 * occur at each bit position with some probability.
	 * The probability may be set upon
	 * construction, otherwise it defaults to MUTATION_PROBABILITY.
	 */
	public void mutate();

	/**
	 * @return the fitness of the chromosome, which may be a misleading
	 * value if neither <code>setFitness</code> nor <code>computeFitness</code>
	 * has been called previously.
	 */
	public double getFitness();

	/**
	 * Sets the fitness of this individual - this should be used
	 * (as opposed to <code>computeFitness</code>) when an Environment
	 * is responsible for determining the fitness values of the individuals
	 * in it.
	 * @param fitness the fitness of the chromosome.
	 * @see #computeFitness
	 */
	public void setFitness(double fitness);

	/**
	 * Computes the fitness in the case of fitness being
	 * independent of other individuals in the population (i.e.
	 * the individual knows how to calculate its own fitness).
	 * Otherwise does nothing.
	 * @see #setFitness
	 */
	public void computeFitness();

	/**
	 * @return the the chromosome as a string of characters.
	 */
	public String toString();
	
	public Object clone();

}