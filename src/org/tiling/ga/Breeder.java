package org.tiling.ga;

import java.io.File;


import org.tiling.ga.*; 
 
 

import java.text.NumberFormat;import java.util.Arrays;import java.io.IOException;import org.tiling.util.Archiver;import java.beans.Beans;/**
 * I am an application that performs a specified number of runs of breeding
 * {@link Population}s for {@link Chromosome}s created by a given
 * {@link ChromosomeFactory}.
 */
public class Breeder implements Runnable {



	private Population population = new FixedSizePopulation();
	

	private int runLength = 50;
	

	

	

	
	public static void main(String[] args) {
		
		if (args.length == 0) {
			printUsage();
			System.exit(1);
		}

		try {

			Breeder breeder = new Breeder();
	
			int arg = 0;
			
			ChromosomeFactory chromosomeFactory = (ChromosomeFactory) Beans.instantiate(null, args[arg++]);
			breeder.setChromosomeFactory(chromosomeFactory);

			Population population = new FixedSizePopulation();
			Environment environment = null;
			Selection selection = null;

			while (arg < args.length) {
				String feature = args[arg++];
				if (arg == args.length) {
					System.err.println("Missing value for " + feature);
					printUsage();
					System.exit(1);
				} else {
					String value = args[arg++];
					if ("-populationBean".equals(feature)) {
						population = (Population) instantiate(value);
						if (environment != null) {
							population.setEnvironment(environment);
						}
						if (selection != null) {
							population.setSelection(selection);
						}
					} else if ("-populationSize".equals(feature)) {
						breeder.setPopulationSize(Integer.parseInt(value));
					} else if ("-environmentBean".equals(feature)) {
						environment = (Environment) instantiate(value);
						population.setEnvironment(environment);
					} else if ("-selectionBean".equals(feature)) {
						selection = (Selection) instantiate(value);
						population.setSelection(selection);
					} else if ("-runLength".equals(feature)) {
						breeder.setRunLength(Integer.parseInt(value));
					} else if ("-crossoverProbability".equals(feature)) {
						chromosomeFactory.setCrossoverProbability(Double.parseDouble(value));
					} else if ("-mutationProbability".equals(feature)) {
						chromosomeFactory.setMutationProbability(Double.parseDouble(value));
					} else {
						System.err.println("Unrecognised feature " + feature);
						printUsage();
						System.exit(1);
					}
				}

				breeder.setPopulation(population);
			}
			
			System.out.println("Chromosome factory: " + chromosomeFactory.getClass().getName());
			System.out.println("Population: " + breeder.getPopulation().getClass().getName());
			System.out.println("Population size: " + breeder.getPopulationSize()); 
			System.out.println("Environment: " + breeder.getPopulation().getEnvironment().getClass().getName());
			System.out.println("Selection: " + breeder.getPopulation().getSelection().getClass().getName());
			System.out.println("Run length: " + breeder.getRunLength());
			System.out.println("Crossover probability: " + chromosomeFactory.getCrossoverProbability());
			System.out.println("Mutation probability: " + chromosomeFactory.getMutationProbability());
			
			breeder.run();

		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}


	}

	private static void printUsage() {
		System.err.println("Usage: Breeder chromosome_factory_bean" +
			" [-populationBean <population bean>]" +
			" [-populationSize <population size>]" +
			" [-environmentBean <environment bean>]" +
			" [-selectionBean <selection bean>]" +
			" [-runLength <run length>]" +
			" [-crossoverProbability <crossover probability>]" +
			" [-mutationProbability <mutation probability>]");
	}
	private ChromosomeFactory chromosomeFactory;	private Chromosome fittestEver;	private int generationFittestEverFound;	private int populationSize = 30;	public Breeder() {
	}	public ChromosomeFactory getChromosomeFactory() {
		return chromosomeFactory;
	}	public Population getPopulation() {
		return population;
	}	public int getPopulationSize() {
		return populationSize;
	}	public int getRunLength() {
		return runLength;
	}	private static Object instantiate(String classname) throws Exception {
		return Beans.instantiate(null, classname);
	}	private Chromosome report(Population p, int generation) {
		Chromosome[] individuals = (Chromosome[]) p.getIndividuals().clone();
		Chromosome fittest = individuals[0];
		double maxFitness = individuals[0].getFitness();
		for (int i = 1; i < individuals.length; i++) {
			double fitness = individuals[i].getFitness();
			if (fitness > maxFitness) {
				fittest = individuals[i];
				maxFitness = fitness;
			}
		}

		if (fittestEver == null || maxFitness > fittestEver.getFitness()) {
			fittestEver = fittest;
			generationFittestEverFound = generation;
		}

		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		System.out.println(generation + ". Best: " + numberFormat.format(fittestEver.getFitness()) +
							" (gen. " + generationFittestEverFound + ")" +
							" Max: " + numberFormat.format(maxFitness) +
							" Mean: " + numberFormat.format(p.getMeanFitness()) +
							" Var: " + numberFormat.format(p.getFitnessVariance()));
		return fittest;
	}	public void run() {

		if (chromosomeFactory == null) {
			throw new IllegalStateException("No chromosome factory has been specified.");
		}

		// Create initial, random population
		Chromosome[] individuals = new Chromosome[populationSize];
		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = chromosomeFactory.create();
		}
		population.setIndividuals(individuals);

		// Iterate
		Chromosome fittest = null;
		for (int generation = 0; generation < runLength; generation++) {
			population.run(1);
			fittest = report(population, generation);
		}

		// archive fittest string and whole population
		try {
			Archiver.getInstance().store("fittest", fittest);
			Archiver.getInstance().store("population", population);
			System.out.println("Archived");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Fittest:");
		System.out.println(fittest);

	}	public void setChromosomeFactory(ChromosomeFactory chromosomeFactory) {
		this.chromosomeFactory = chromosomeFactory;
	}	public void setPopulation(Population population) {
		this.population = population;
	}	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}	public void setRunLength(int runLength) {
		this.runLength = runLength;
	}}