import java.util.ArrayList;


public class SubRoute {

	private ArrayList<Town> route = new ArrayList<Town>();
	private double fitness = 0;
	private double distance = 0;
	
	public SubRoute() {
		
		fitness = 10000000;
		for (int i = 0; i < (TSP.noTowns/2)/5; i++) {
			route.add(null);
	    }
	}
	
	public SubRoute(Town[] rt) {
		
		for(Town t : rt)
			route.add(t);
		
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
    	for(int i = 0; i<route.size(); i++) {
    		
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
    
 // Gets a Town from the Route
    public Town getTown(int pos) {
        return (Town)route.get(pos);
    }
    
  //Check if the route contains the parameter
    public boolean containsTown(Town t){
        return route.contains(t);
    }
    
    //Get the route size
    public int routeSize() {
    	return route.size();
    }

	
}
