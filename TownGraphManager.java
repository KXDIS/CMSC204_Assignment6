import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TownGraphManager implements TownGraphManagerInterface {
	Graph graph;

	/**
	 * Constructor for TownGraphManager
	 */
	public TownGraphManager() {
		this.graph = new Graph();
	}

	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Town source = getTown(town1);
		Town dest = getTown(town2);

		if (source == null || dest == null) {

			return false;
		}

		graph.addEdge(source, dest, weight, roadName);
		return true;

	}

	@Override
	public String getRoad(String town1, String town2) {
		for (Road road : graph.edgeSet()) {
			if ((road.getSource().getName().equals(town1) && road.getDestination().getName().equals(town2))
					|| (road.getSource().getName().equals(town2) && road.getDestination().getName().equals(town1))) {
				return road.getName();
			}
		}
		return null;
	}

	public boolean addTown(String v) {
		Town town = new Town(v);
		return this.graph.addVertex(town);
	}

	@Override
	public Town getTown(String name) {
		Town result = null;
		if (containsTown(name) == true) {
			for (Town town : this.graph.vertexSet()) {
				if (town.getName().equals(name)) {
					result = town;
				}
			}
		}
		return result;
	}

	@Override
	public boolean containsTown(String v) {
		for (Town town : this.graph.vertexSet()) {
			if (town.getName().equals(v)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if a road is in the graph
	 * 
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return true if the road is in the graph, false if not
	 */

	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		for (Road road : graph.edgeSet()) {
			if ((road.getSource().getName().equals(town1) && road.getDestination().getName().equals(town2))
					|| (road.getSource().getName().equals(town2) && road.getDestination().getName().equals(town1)))
				return true;
		}
		return false;
	}

	/**
	 * Creates an arraylist of all road titles in sorted order by road name
	 * 
	 * @return an arraylist of all road titles in sorted order by road name
	 */
	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> roadNames = new ArrayList<>();
		for (Road road : graph.edgeSet()) {
			roadNames.add(road.getName());
		}

		roadNames.sort(String.CASE_INSENSITIVE_ORDER);
		return roadNames;
	}

	/**
	 * Deletes a road from the graph
	 * 
	 * @param town1    name of town 1 (lastname, firstname)
	 * @param town2    name of town 2 (lastname, firstname)
	 * @param roadName the road name
	 * @return true if the road was successfully deleted, false if not
	 */
	@Override

	public boolean deleteRoadConnection(String town1, String town2, String road) {
		Town t1 = getTown(town1);
		Town t2 = getTown(town2);
		if (containsRoadConnection(town1, town2)) {

			Road a_road = graph.getEdge(t1, t2);
			graph.removeEdge(t1, t2, a_road.getWeight(), road);
			return true;
		}
		return false;
	}

	/**
	 * Deletes a town from the graph
	 * 
	 * @param v name of town (lastname, firstname)
	 * @return true if the town was successfully deleted, false if not
	 */
	@Override
	public boolean deleteTown(String v) {
		Town town = getTown(v);
		return graph.removeVertex(town);
	}

	/**
	 * Creates an arraylist of all towns in alphabetical order (last name, first
	 * name)
	 * 
	 * @return an arraylist of all towns in alphabetical order (last name, first
	 *         name)
	 */
	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> townNames = new ArrayList<>();
		for (Town town : graph.vertexSet()) {
			townNames.add(town.getName());
		}

		townNames.sort(String.CASE_INSENSITIVE_ORDER);
		return townNames;
	}

	/**
	 * Returns the shortest path from town 1 to town 2
	 * 
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return an Arraylist of roads connecting the two towns together, null if the
	 *         towns have no path to connect them.
	 */
	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		Town source = getTown(town1);
		Town destination = getTown(town2);
		if (source == null || destination == null) {
			return new ArrayList<>();
		}
		return graph.shortestPath(source, destination);
	}

	public void populateTownGraph(File selectedFile) throws FileNotFoundException {
		List<String> inList = new ArrayList<>();

		if (!selectedFile.exists())
			throw new FileNotFoundException();

		Scanner inFile = new Scanner(selectedFile);

		while (inFile.hasNextLine()) {
			inList.add(inFile.nextLine());
		}

		for (String line : inList) {
			String[] currentLine = line.split(";");
			int commaIndex = currentLine[0].indexOf(",");
			String roadName = currentLine[0].substring(0, commaIndex);
			String weight = currentLine[0].substring(commaIndex + 1, currentLine[0].length());
			String source = currentLine[1];
			String destination = currentLine[2];

			addTown(source);
			addTown(destination);

			addRoad(source, destination, Integer.parseInt(weight), roadName);
		}

		inFile.close();

	}
}