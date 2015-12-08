
public class Town {

	public String name;
	public int id;
	public int realId;
	public double lon;
	public double lat;
	public boolean visited;
	public double[] distances;
	public int sbIndex;
	
	public Town(String name, int id, double lat, double lon, int noTowns) {
		
		this.name = name;
		this.id = id-1;
		this.lat = lat;
		this.lon = lon;
		realId = id;
		visited = false;
		distances = new double[noTowns];
		
	}

	public Town() {
		// TODO Auto-generated constructor stub
	}
	
	//get the distance from the current town to the parameter
	public double distanceTo(Town t) {
		
		if(t==null)
			System.out.println("Here");
		
		return distances[t.id];
		
	}	
	
}
