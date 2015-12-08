import java.io.IOException;
import java.util.ArrayList;


public class TSP {

	static int noTowns = 80;
	static double[][] distances;

	public static void fixText() throws IOException {

		FileIO reader = new FileIO();
		String[] towns = reader.load("ftowns.txt");

		int i = 0;
		for (String town : towns) {

			town = town.replaceAll(" ", ",");
			town = town.replaceAll("\t", ",");
			if (i % 2 == 0) {
				town = town.replace("\r", "");
				town = town.replace("\n", "");
			}
			System.out.println(town);
			towns[i] = town;
			i ++ ;

		}

		reader.save("ffixed.txt", towns);

	}

	public static void getTowns() {

		FileIO reader = new FileIO();
		Town[] output = new Town[noTowns];
		String[] towns = reader.load("fixed.txt");
		for (int i = 0; i < towns.length; i ++ ) {

			String[] town = towns[i].split(",");
			int id = Integer.parseInt(town[0]);
			String name = town[1];
			double lat = Double.parseDouble(town[2]);
			double lon = Double.parseDouble(town[3]);

			Town nt = new Town(name, id, lat, lon, noTowns);
			output[i] = nt;

		}
		
		//get distances between every town
		for(Town t : output) {
			int i=0;
			for(Town k : output) {
				
				t.distances[i] = getDistance(t.lat, t.lon, k.lat, k.lon);
				i++;
				
			}
			
		}
		
		//put each town into the TourManager
		for(Town t: output) {
			
			RouteManager.addTown(t);
			
		}

	}
	
	//haversine method
	public static double getDistance(double lat1, double lon1, double lat2,
			double lon2) {
		double R = 6371;
		double dLat = Math.toRadians((lat2 - lat1));
		double dLon = Math.toRadians((lon2 - lon1));
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;
		return d;

	}
	
	public static void main(String args[]) throws IOException {
		
		//Get towns from text file and put them in the RouteManager
		getTowns();
    	
    	
		//Initialize population
        Population pop = new Population(1500, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());
        for(int i= 0; i<pop.routes.length; i++) {
        	
        	pop.routes[i].getDistance();
        	
        }
        // Evolve population
        Route best = new Route();
        double p;
        for (int i = 0; i < 1000; i++) {

             	
             	
            
            pop = Genetics.evolve(pop);
            pop = Genetics.twoOpt(pop);

        }

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest().getPath());
        
       // pop = Genetics.twoOpt(pop);
	
	}
	
}
