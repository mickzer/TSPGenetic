import java.util.*;

public class Route {

	private ArrayList<Town> route = new ArrayList<Town>();
	private double fitness = 0;
	private double distance = 0;
	
	//constructor for an empty tour
	public Route() {
		
		for (int i = 0; i < RouteManager.numberOfTowns(); i++) {
			route.add(null);
	    }
		
	}
	
	//constructor for a premade tour
	public Route(ArrayList<Town> route) {
		
		this.route = route;
		
	}
	
	public void setFitness(double f) {
		
		fitness= f;
		
	}
	
	// Creates a random individual
    public void makeIndividual() {
        // add all cities to the route
        for (int i = 0; i < RouteManager.numberOfTowns(); i++) {
          setTown(i, RouteManager.getTown(i));
        }
        // Randomly reorder the tour
        Collections.shuffle(route);

    }
    
    // Gets a Town from the Route
    public Town getTown(int pos) {
        return (Town)route.get(pos);
    }
    
    public void addTown(Town t) {
    	
    	route.add(t);
    }

    // Sets a Route to a position in the route
    public void setTown(int pos, Town t) {
        route.set(pos, t);
        // if a tour is changed, fitness and distance need to reset
        fitness = 0;
        distance = 0;
    }
    
    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/getDistance();
        }
        return fitness;
    }
    
    //calculate total route distance
    public double getDistance() {
    	
    	Town previous = new Town();
    	double d = 0.0;
    	//Go through each town in the route
    	for(int i = 0; i<route.size()-1; i++) {
    		
    		if(route.get(i)==null)
				System.out.println("baf"+i);
    		
    		Town current = route.get(i);
    		
    		//ensure 1 town has been passed
    		if(i>1) {
    			
    			//add distance between current and previous town to total
    			d += current.distanceTo(previous);
    			if(current == null || previous == null)
    				System.out.println("here");
    			
    		}
    		
    		//current is the next previous
    		previous = current;
    				
    	}
    	//loop back distance
    	d += previous.distanceTo(route.get(0));
    	
    	distance = d;
    	
    	return distance;
    	
    }
    
    //get a routes path
    public String getPath() {
    
    	String path = "";
    	
    	//go through each town
		for(int i = 0; i< route.size()-1; i++) {
			
			Town cur = route.get(i);
			
			//construct path
			path += ((i==79) ?(cur.realId) :(cur.realId+"."));
			
		}
		
		return path;
    
    }
    
	//Check if the route contains the parameter
    public boolean containsTown(Town t){
        return route.contains(t);
    }
    
    //Get the route size
    public int routeSize() {
    	return route.size();
    }
    
    public SubRoute[] bestSubRoutes() {
    	
    	ArrayList<SubRoute> subs = new ArrayList<SubRoute>();
    	SubRoute[] bests = new SubRoute[(TSP.noTowns/2)/5];
    	int count =0;
    	Town[] sub = new Town[5];
    	
    	for(int i=0; i<route.size(); i+=5) {
    		
    		if(i>3) {
    			
    			sub[0] = route.get(i-4);
    			sub[0].sbIndex = i-4;
    			sub[1] = route.get(i-3);
    			sub[1].sbIndex = i-3;
    			sub[2] = route.get(i-2);
    			sub[2].sbIndex = i-2;
    			sub[3] = route.get(i-1);
    			sub[3].sbIndex = i-1;
    			sub[4] = route.get(i);
    			sub[4].sbIndex = i;
    			
    			
    			SubRoute sb = new SubRoute(sub);
 		   		subs.add(sb);	
    		}
    		
    	}
    	
		//set best to be the first few sub rotues
		for(int i =0 ; i<bests.length; i++) {
			
			bests[i] = subs.get(i);
			
		}
		
		//pick out best few
		for(int i = bests.length; i<subs.size(); i++) {
		
			//go through the best and compare
			for(int j = 0; j<bests.length; j++) {
				
				if(subs.get(i).getFitness()<bests[j].getFitness()) {
					
					bests[j]=subs.get(i);
					
				}
				
			}
			
		}
		
    	
    	return bests;
    	
    }
    
}
