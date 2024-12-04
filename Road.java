/**
 * 
 * @author Hendrick Nguyen
 *
 */
public class Road implements Comparable<Road> {

	private String name;
	private Town source;
	private Town destination;
	private int weight;

	/**
	 * 
	 * @param source
	 * @param destination
	 * @param degrees
	 * @param name
	 */
	public Road(Town source, Town destination, int degrees, String name) {
		this.name = name;
		this.source = source;
		this.destination = destination;
		this.weight = degrees;

	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @param name
	 */
	public Road(Town source, Town destination, String name) {
		this.name = name;
		this.source = source;
		this.destination = destination;
		this.weight = 1;
	}

	/**
	 * 
	 * @param town
	 * @return
	 */
	public boolean contains(Town town) {
		if (town == null) {
			return false;
		}

		if (this.source.equals(town) || this.destination.equals(town)) {
			return true;
		}
		return false;
	}

	public boolean equals(Object r) {
		Road newRoad = (Road) r;
		return ((newRoad.destination == destination && newRoad.source == source)
				|| (newRoad.destination == source && newRoad.source == destination));
	}

	@Override
	public int compareTo(Road o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "Road name: " + name + '\n' + "Origin: " + source.getName() + '\n' + "Destination: "
				+ destination.getName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Town getSource() {
		return source;
	}

	public void setSource(Town source) {
		this.source = source;
	}

	public Town getDestination() {
		return destination;
	}

	public void setDestination(Town destination) {
		this.destination = destination;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String toStringForShortestPath() {

		return name;
	}

}
