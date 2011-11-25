package org.tiling.ga;

import java.io.Serializable;
 
/**
 * A simple Environment in which individuals do not directly
 * compete with one another. This is suitable for situations
 * where an individual's fitness is determined by, for example, 
 * its ability to perform a particular task.
 * @see Population
 * @see Chromosome
 */
public class NonInteractiveEnvironment implements Environment, Serializable {

	/**
	 * @param individuals the individuals in this Environment
	 */
	public void calculateFitnessValues(Chromosome[] individuals) {
		for (int i = 0; i < individuals.length; i++) {
			individuals[i].computeFitness();
		}		
	}
	
}