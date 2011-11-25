package org.tiling.ga.test;

import java.util.*;

import junit.framework.*;

import org.tiling.ga.*;

public class GATest extends TestCase {
	
	public GATest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(GATest.class);
	}

	public void testChromosomeEquals() {
		Chromosome c1 = new ExampleChromosome("01010101");
		Chromosome c2 = new ExampleChromosome("01010101");
		Chromosome clone = (Chromosome) c1.clone();
		assertEquals("c1 equals c2", c1, c2);
		assertEquals("clone", c1, clone);
	}

	public void testChromosomeFitness() {
		Chromosome c1 = new ExampleChromosome("01010101");
		Chromosome c2 = new ExampleChromosome("11111111");
		Chromosome c3 = new ExampleChromosome("00000000");
		c1.computeFitness();
		assertTrue(4.0 == c1.getFitness());
		c2.computeFitness();
		assertTrue(8.0 == c2.getFitness());
		c3.computeFitness();
		assertTrue(0.0 == c3.getFitness());
	}

	public void testChromosomeCrossover() {
		Chromosome c1 = new ExampleChromosome("01010101");
		Chromosome c1Copy = new ExampleChromosome("01010101");
		Chromosome c2 = new ExampleChromosome("10101010");
		Chromosome c2Copy = new ExampleChromosome("10101010");
		c1.crossover(c2);
		c1.computeFitness();
		c1Copy.computeFitness();
		c2.computeFitness();
		c2Copy.computeFitness();
		assertTrue(c1.getFitness() + c2.getFitness() == c1Copy.getFitness() + c2Copy.getFitness());
	}

	public void testChromosomeMutation() {
		Chromosome c = new ExampleChromosome("00000000");
		Chromosome cCopy = new ExampleChromosome("00000000");
		for (int i = 0; i < 5000; i++) { // hope mutation occurs within 5000 attempts!
			c.mutate();
		}
		assertTrue(!c.equals(cCopy));
	}

	public void testExampleChromosome() {
		Chromosome[] individuals = new ExampleChromosome[4];
		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = new ExampleChromosome();
		}
		Population population = new FixedSizePopulation();
		population.setIndividuals(individuals);
		
		double initialMeanFitness = population.getMeanFitness();
		population.run(20);
		
		assertTrue(population.getMeanFitness() > initialMeanFitness);
		
	}

/*	
	public void testExampleChromosome2() {
		
		Chromosome[] individuals = new ExampleChromosome[100];
		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = new ExampleChromosome(20);
		}
		Population population = new FixedSizePopulation(individuals);

		for (int i = 0; i < 50; i++) {
			population.run(1);
			report(population);
		}

	}
	
	public void report(Population p) {
		Chromosome[] individuals = (Chromosome[]) p.getIndividuals().clone();
		Arrays.sort(individuals);
		System.out.println("Fittest: " + individuals[individuals.length - 1] + " Avg: " + p.getAverageFitness());
	}
	
	public void fullReport(Population p) {
		Chromosome[] individuals = p.getIndividuals();
		for (int i = 0; i < individuals.length; i++)
			System.out.println(individuals[i] + " " + individuals[i].computeFitness() + " " + individuals[i].hashCode());
		System.out.println(p.getAverageFitness());
	}
*/
	
}