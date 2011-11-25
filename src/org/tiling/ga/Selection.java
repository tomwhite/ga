package org.tiling.ga;

/**
 * A Selection is a strategy for selecting individuals from a 
 * collection of Chromosomes based on their fitness.
 * <p>
 * Make sure implementations are beans, so
 * they can be used with {@link Breeder}.
 * @see Population
 * @see Chromosome
 */
public interface Selection {

	/**
	 * @return selected individuals in the population that will
	 * create offspring for the next generation. The selection is
	 * the same size as the current generation and can contain
	 * repeated (cloned) chromosomes. Selection should favour fitter
	 * individuals.
	 * @see Chromosome	
	 */
	public Chromosome[] select(Chromosome[] individuals);
	
}