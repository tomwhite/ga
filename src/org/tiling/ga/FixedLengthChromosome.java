package org.tiling.ga;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * FixedLengthChromosome is an implementation of Chromosome that uses 
 * a fixed-length character sequence as the encoding.
 */
public abstract class FixedLengthChromosome implements Chromosome, Serializable {

	protected final static Random r = new Random();

	protected final double crossoverProbability;
	protected final double mutationProbability;

	protected double fitness;
	protected char[] alleles;
	protected StringBuffer string;
	
	// used to signal when fitness needs to be re-calculated
	protected boolean stringModified;

	public FixedLengthChromosome(int length) {
		this(new char[] {'0', '1'}, length);
	}

	public FixedLengthChromosome(char[] alleles, int length) {
		this(alleles, length, CROSSOVER_PROBABILITY, MUTATION_PROBABILITY);
	}

	/**
	 * @param alleles an array of characters that represent the alleles of this chromosome in unicode-sorted order.
	 */
	public FixedLengthChromosome(char[] alleles, int length, double crossoverProbability, double mutationProbability) {
		this.alleles = alleles;

		string = new StringBuffer();
		for (int i = 0; i < length; i++) {
			string.append(getRandomAllele());
		}
		stringModified = true;		

		this.crossoverProbability = crossoverProbability;
		this.mutationProbability = mutationProbability;
	}

	private char getRandomAllele() {
		return alleles[r.nextInt(alleles.length)];
	}

	// May get odd results if the chromosomes have different lengths
	public Chromosome crossover(Chromosome c) {
		performParameterizedUniformCrossover(this, (FixedLengthChromosome) c);
		return c;
	}

	protected void performSinglePointCrossover(FixedLengthChromosome chromosome1, FixedLengthChromosome chromosome2) {

		if (r.nextDouble() > crossoverProbability) {
			return;
		}

		int locus = getRandomCrossoverLocus();
		String swap = chromosome1.string.substring(locus);
		chromosome1.string.replace(locus, chromosome1.string.length(), chromosome2.string.substring(locus));
		chromosome2.string.replace(locus, chromosome2.string.length(), swap);
		chromosome1.stringModified = true;
		chromosome2.stringModified = true;
	}

	public void mutate() {
		for (int i = 0; i < string.length(); i++) {
			if (r.nextDouble() < mutationProbability) {
				char mutation = getRandomAllele();
				while (mutation == string.charAt(i)) { // ensure mutation is different
					mutation = getRandomAllele();
				}
				string.setCharAt(i, mutation);
				stringModified = true;
			}
		}
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * This implementation does nothing. The fitness can only be updated using
	 * <code>setFitness</code>.
	 * <p>
	 * Subclasses that know how to calculate their own fitness should
	 * override this method.
	 * Typically, this will involve translating the DNA string into
	 * a candidate solution to the problem and assessing how well the candidate
	 * solution solves the problem by assigning it a numerical value for the
	 * 'fitness'.
	 */
	public void computeFitness() {
	}

	/**
	 * Compared using fitness.
	 */
	public int compareTo(Object obj) {
		Chromosome c = (Chromosome) obj;
		return new Double(getFitness()).compareTo(new Double(c.getFitness()));
	}

	public String toString() {
		return new String(string);
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			FixedLengthChromosome chromosome = (FixedLengthChromosome) obj;
			return Arrays.equals(this.alleles, chromosome.alleles)
				&& this.string.toString().equals(chromosome.string.toString());
		}
		return false;
	}

	public Object clone() {
		try {
			FixedLengthChromosome clone = (FixedLengthChromosome) super.clone();
			clone.string = new StringBuffer(string.toString());
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return null;
	}

	protected int getRandomCrossoverLocus() {
		return r.nextInt(string.length());
	}	protected void performParameterizedUniformCrossover(FixedLengthChromosome chromosome1, FixedLengthChromosome chromosome2) {
		int segmentlength = 1;
		for (int locus = 0; locus < string.length() - segmentlength; locus += segmentlength) {
			if (r.nextDouble() > crossoverProbability) {
				continue;
			}
			String swap1 = chromosome1.string.substring(locus, locus + segmentlength);
			String swap2 = chromosome2.string.substring(locus, locus + segmentlength);
			chromosome1.string.replace(locus, locus + segmentlength, swap2);
			chromosome2.string.replace(locus, locus + segmentlength, swap1);
			chromosome1.stringModified = true;
			chromosome2.stringModified = true;
		}
	}	protected void performTwoPointCrossover(FixedLengthChromosome chromosome1, FixedLengthChromosome chromosome2) {

		if (r.nextDouble() > crossoverProbability) {
			return;
		}

		int locus1 = getRandomCrossoverLocus();
		int locus2 = getRandomCrossoverLocus();
		if (locus1 < locus2) {
			String swap1 = chromosome1.string.substring(locus1, locus2);
			String swap2 = chromosome2.string.substring(locus1, locus2);
			chromosome1.string.replace(locus1, locus2, swap2);
			chromosome2.string.replace(locus1, locus2, swap1);
			chromosome1.stringModified = true;
			chromosome2.stringModified = true;
		} else if (locus1 > locus2) {
			String swap1 = chromosome1.string.substring(locus2, locus1);
			String swap2 = chromosome2.string.substring(locus2, locus1);
			chromosome1.string.replace(locus2, locus1, swap2);
			chromosome2.string.replace(locus2, locus1, swap1);
			chromosome1.stringModified = true;
			chromosome2.stringModified = true;
		}
	}}