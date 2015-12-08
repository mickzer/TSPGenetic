import java.util.ArrayList;


public class RouteManager {

	private static ArrayList<Town> allTowns = new ArrayList<Town>();
	
	public static void addTown(Town t) {
        allTowns.add(t);
    }
    
    // Get a town
    public static Town getTown(int index){
        return (Town)allTowns.get(index);
    }
    
    // Get the number of destination cities
    public static int numberOfTowns(){
        return allTowns.size();
    } 
	
}
