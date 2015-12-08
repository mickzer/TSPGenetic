	//this is where the magic happens
public class Genetics {

	//rate at which to perform mutations
    private static final double mutationRate = 0.015;
    //amount of individuals to compete for crossover
    private static final int tournamentSize = 5;
    //don't change the best individuals when put into the next gen
    private static final boolean elitism = true;
    
    //2Opt swap
    public static Route twoOptSwap(int i, int k, Route rt) {
    	
		Route nr = new Route();
		
		
		
		//put route[0] tp route [i-1\] in new route
		for(int j = 0; j < i; j++) {
			
			nr.setTown(j, rt.getTown(j));
			
		}
		
		//put route[i] to route [k]
		int in = i;
		for(int j = k; j >= i; j--) {

			nr.setTown(in, rt.getTown(j));
			in++;
			
		}
		
		//put route[k+1] to end in new route
		for(int j = k+1; j<rt.routeSize()-1; j++) {
			
			nr.setTown(j, rt.getTown(j));
			
		}
	
		return nr;
		
	}
    
    //run 2opt on a route
    public static Population twoOpt(Population pop) {
    	
    	/*//get top 50% of population
    	int min;
    	for(int i = 0; i<500; i++) {
    		
    		min=i;
    		for(int k = i+1; k<500; k++) {
    			
    			if(pop.getRoute(k).getFitness()<pop.getRoute(min).getFitness()) {
    				
    				min = k;
    				
    			}
    			
    		}
    		Route swap = pop.getRoute(i);
    		Route swap2 = pop.getRoute(min);
    		pop.setRoute(min, swap);
    		pop.setRoute(i, swap2);
    		
    		
    	}
    	
    	for(int j = 0; j<500; j++) {
    		
    		if(pop.getRoute(j)==null)
    			System.out.println("null");
    		
    	}
    	*/
    	//run 2opt on top 50 
    	for(int j = 0; j<TSP.noTowns; j++) {
    		Route rt = pop.getRoute(j);
    		
    		Route nr = pop.getRoute(j);
	    	double bestFitness = rt.getFitness();
	    	for(int i = 0; i<rt.routeSize()-1; i++) {
	    		
	    		for(int k = i+1; k<rt.routeSize()-1; k++) {
	    			
	    			nr = twoOptSwap(i, k, rt);
	    			
	    		}
	    		
	    		if(nr==null)
	    			System.out.println("nullnr");
	    		if(nr.getFitness() <= bestFitness) {
	    			
	    			rt = nr;
	    			bestFitness = rt.getFitness();
	    			
	    		}
	    	}
    	}
    	//put back two opted routes into the population
    	return pop;
    }
    
  //run 2opt on a route
//    public static Population twoOpt(Population pop) {
//    	  	
//    	//run 2opt on top 50 
//    	for(int j = 0; j<50; j++) {
//    		Route rt = pop.getRoute(j);
//    		
//    		Route nr = pop.getRoute(j);
//	    	double bestFitness = rt.getFitness();
//	    	for(int i = 0; i<rt.routeSize()-1; i++) {
//	    		
//	    		for(int k = i+1; k<rt.routeSize()-1; k++) {
//	    			
//	    			nr = twoOptSwap(i, k, rt);
//	    			
//	    		}
//	    		
//	    		if(nr.getFitness() <= bestFitness) {
//	    			
//	    			rt = nr;
//	    			bestFitness = rt.getFitness();
//	    			
//	    		}
//	    	}
//    	}
//    	//put back two opted routes into the population
//    	return pop;
//    }
    
    //Evolves a population to a new generation
    public static Population evolve(Population p) {
        Population np = new Population(p.popSize(), false);

        //Keep the best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            np.saveRoute(0, p.getFittest());
            //record the insertion of the fittest
            elitismOffset = 1;
        }

        //Go through the population and crossover
        //i = elitismOffset in case best route is already inserted
        for (int i = elitismOffset; i < np.popSize(); i++) {
            //Get two parents from a tournament
            Route parent1 = tournamentSelection(p);
            Route parent2 = tournamentSelection(p);
            //Perform crossover to get a child
            Route child = twoPtCrossover(parent1, parent2);
           // child.setTown(child.routeSize(), child.getTown(0));
            //Put child in the new population
            np.saveRoute(i, child);
        }

        /* Mutate the population otherwise everything in the next get would
         * be in the next generation. 
         */
        for (int i = elitismOffset; i < np.popSize(); i++) {
            mutate(np.getRoute(i));
        }
        
        //twoOpt(np);

        return np;
    }
    
    //Perform crossover of two parents to produce a child
    /*
     * This method performs two point crossover. That means it takes
     * a subset from parent 1, and inserts it into the child. The
     * 2nd parent is then inserted into the child in it's original order
     * just either side where parent1 has been inserted
     */
    public static Route twoPtCrossover(Route parent1, Route parent2) {
        // Create new child tour
        Route child = new Route();

        // Get start and end for a subset of parent 1
        int start = (int) (Math.random() * (parent1.routeSize()-1));
        int end= (int) (Math.random() * (parent1.routeSize()-1));

        //Add this subset into the child
        for (int i = 0; i < child.routeSize()-1; i++) {
            //If start < than end, insert normally
            if (start < end && i > start && i < end) {
                child.setTown(i, parent1.getTown(i));
            } //If start > end, treat start as end and end as start
            else if (start > end) {
                if (!(i < start && i > end)) {
                    child.setTown(i, parent1.getTown(i));
                }
            }
        }

        //Put parent 2 intp the child around the parent 1 subset
        for (int i = 0; i < parent2.routeSize()-1; i++) {
            //Check if the child has the town already
            if (!child.containsTown(parent2.getTown(i))) {
                //Find a spare position in the child 
                for (int ii = 0; ii < child.routeSize(); ii++) {
                    //Insert at a spare position
                    if (child.getTown(ii) == null) {
                        child.setTown(ii, parent2.getTown(i));
                        break; //move to the next parent 2 town
                    }
                }
            }
        }
        
        child.addTown(child.getTown(0));
        
        return child;
    }
    
    public static Route uniformCrossover(Route parent1, Route parent2) {
        // Create new child tour
        Route child = new Route();
        //array for start and end of parent subsets
        int[] p1sf = new int[(TSP.noTowns/2)+1];
        int[] p2sf = new int[(TSP.noTowns/2)+1];
        
        for(int i = 0; i<p1sf.length; i++) {
        	
        	p1sf[i] = (int) (Math.random() * parent1.routeSize());
        	
        }
        
        //put towns in child
        for(int i = 0; i<parent1.routeSize(); i++) {
        	
        
        	
        }
        
        return child;
    }
    
    public static Route myCrossover(Route parent1, Route parent2) {
        // Create new child tour
        Route child = new Route();
        
        //get random parent for fairness
        Route parent;
        Route other;
        if((int)Math.random()*2==1) {
        	parent = parent1;
        	other = parent2;
        }
        	
        else {
    		parent = parent2;   
    		other = parent1;
        }
        
        //Get the best few sub routes of parent
        SubRoute[] best = parent.bestSubRoutes();
        int index;
        Town t;
        for(int i =0; i<best.length; i++) {
        	
        	for(int j =0; j<best[i].routeSize(); j++) {
        		
        		//get town and position
        		t = best[i].getTown(j);
        		index = t.sbIndex;
        		//insert into child
        		child.setTown(index, t);
        		
        	}
        }
        
        for(int i =0; i<parent1.routeSize(); i++) {
        	
        	if(child.getTown(i)==null) {
        		
        		//find a spare town
        		for(int j = 0; j<other.routeSize(); j++) {
        			
        			if(!child.containsTown(other.getTown(j))) {
        				
        				child.setTown(i, other.getTown(j));
        				
        			}
        			
        		}
        		
        	}
        	
        }
        
        return child;
    }
    
    // Mutate a route
    /*
     * To make things simple, just swap two random towns
     */
    private static void mutate(Route rt) {
        //Go through all the towns in the route
        for(int pos1=0; pos1 < rt.routeSize()-1; pos1++) {
            //Apply mutation rate
            if(Math.random() < mutationRate){
                //Get a second random position in the tour
                int pos2 = (int) ((rt.routeSize()-1) * Math.random());

                // Get the towns to be swapped from the route
                Town t1 = rt.getTown(pos1);
                Town t2 = rt.getTown(pos2);

                //Swap them 
                rt.setTown(pos2, t1);
                rt.setTown(pos1, t2);
            }
        }
    }
    
    //Perform tournament selection
    /*
     * Random selection of a number of individuals by running a "tournament" 
     * where the fittest route is the winner
     */
    private static Route tournamentSelection(Population p) {
    	
        //Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        
        //Fill the tournament population with random routes
        for (int i = 0; i < tournamentSize; i++) {
            int rand = (int)(Math.random() * p.popSize());
            tournament.saveRoute(i, p.getRoute(rand));
        }
        // Get the fittest tour
        Route fittest = tournament.getFittest();
        return fittest;
    }
    

}
