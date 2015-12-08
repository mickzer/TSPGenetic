
public class Population {

	// Holds population of tours
    Route[] routes;
    public static int id=0;

    public Population() {
    	
    }
    
    // create a population
    public Population(int popSize, boolean initialise) {
        routes = new Route[popSize];
        //Initalise if true
        if (initialise) {
            //create random individuals
            for (int i = 0; i < popSize(); i++) {
                Route rt = new Route();
                rt.makeIndividual();
                rt.addTown(rt.getTown(0));
                saveRoute(i, rt);
            }
        }
        id++;
    }
    
    //Save a route in the population
    public void saveRoute(int i, Route rt) {
        routes[i] = rt;
    }
    
    //Gets a route from population
    public Route getRoute(int i) {
        return routes[i];
    }
    
    public void setRoute(int i, Route rt) {
    	
    	routes[i] = rt;
    	
    	
    }

    // Gets the fittest route in the population
    public Route getFittest() {
    	//initalise to 1st
        Route fittest = routes[0];
        //check all other individuals
        for (int i = 1; i < popSize(); i++) {
        	//if fitter
            if (fittest.getFitness() <= getRoute(i).getFitness()) {
                fittest = getRoute(i);//set as fittest
            }
        }
        return fittest;
    }

    // Gets population size
    public int popSize() {
        return routes.length;
    }
	
}
