
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 
 * @author Hendrick
 *
 */
public class Graph implements GraphInterface<Town, Road> {
	private Map<Town, List<Road>> adjacencyList;
	private Map<Town, Town> previousVertex;
	private Set<Town> towns;

	public Graph() {
		towns = new HashSet<Town>();
		adjacencyList = new HashMap<Town, List<Road>>();
		previousVertex = new HashMap<>();
	}

	/**
	 * Adds the specified vertex to this graph if not already present. More
	 * formally, adds the specified vertex, v, to this graph if this graph contains
	 * no vertex u such that u.equals(v). If this graph already contains such
	 * vertex, the call leaves this graph unchanged and returns false. In
	 * combination with the restriction on constructors, this ensures that graphs
	 * never contain duplicate vertices.
	 * 
	 * @paradm v vertex to be adde to this graph.
	 * @return true if this graph did not already contain the specified vertex.
	 * @throws NullPointerException if the specified vertex is null.
	 */
	@Override
	public boolean addVertex(Town v) {
		if (containsVertex(v) == true) {
			return false;
		} else if (v == null) {
			throw new NullPointerException();
		} else {
			this.adjacencyList.put(v, new LinkedList<>());
			towns.add(v);
			return true;
		}
	}

	/**
	 * Creates a new edge in this graph, going from the source vertex to the target
	 * vertex, and returns the created edge. The source and target vertices must
	 * already be contained in this graph. If they are not found in graph
	 * IllegalArgumentException is thrown.
	 * 
	 * @param sourceVertex      source vertex of the edge.
	 * @param destinationVertex target vertex of the edge.
	 * @param weight            weight of the edge
	 * @param description       description for edge
	 * @return The newly created edge if added to the graph, otherwise null.
	 * @throws IllegalArgumentException if source or target vertices are not found
	 *                                  in the graph.
	 * @throws NullPointerException     if any of the specified vertices is null.
	 */
	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		if (sourceVertex == null || destinationVertex == null) {
			throw new NullPointerException("Source or destination vertex cannot be null.");
		}
		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex)) {
			throw new IllegalArgumentException("Source or destination vertex not found in graph.");
		}

		Road existingEdge = getEdge(sourceVertex, destinationVertex);
		if (existingEdge != null) {
			return null;
		}
		Road road = new Road(sourceVertex, destinationVertex, weight, description);
		this.adjacencyList.get(sourceVertex).add(road);
		this.adjacencyList.get(destinationVertex).add(road);
		return road;
	}

	/**
	 * Returns an edge connecting source vertex to destination vertex if such
	 * vertices and such edge exist in this graph. Otherwise returns null. If any of
	 * the specified vertices is null, returns null
	 *
	 * In undirected graphs, the returned edge may have its source and target
	 * vertices in the opposite order.
	 *
	 * @param sourceVertex      source vertex of the edge.
	 * @param destinationVertex target vertex of the edge.
	 *
	 * @return an edge connecting source vertex to target vertex.
	 */
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		if (sourceVertex == null || destinationVertex == null)
			return null;

		else if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex))
			return null;

		for (Road r : adjacencyList.get(sourceVertex)) {
			if (r.contains(destinationVertex))
				return r;
		}

		for (Road r : adjacencyList.get(destinationVertex)) {
			if (r.contains(sourceVertex))
				return r;
		}

		return null;

	}

	/**
	 * Returns true if this graph contains the specified vertex. More formally,
	 * returns true if and only if this graph contains a vertex u such that
	 * u.equals(v). If the specified vertex is null returns false.
	 * 
	 * @param v vertex whose presence in this graph is to be tested.
	 * @return true if this graph contains the specified vertex.
	 */
	@Override
	public boolean containsVertex(Town v) {
		if (v == null)
			return false;
		if (adjacencyList.containsKey(v))
			return true;
		return false;

	}

	/**
	 * Returns true if and only if this graph contains an edge going from the source
	 * vertex to the target vertex. In undirected graphs the same result is obtained
	 * when source and target are inverted. If any of the specified vertices does
	 * not exist in the graph, or if is null, returns false.
	 * 
	 * @param sourceVertex      source vertex of the edge.
	 * @param destinationVertex target vertex of the edge.
	 * @return true if this graph contains the specified edge.
	 */
	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		if (sourceVertex == null || destinationVertex == null)
			return false;

		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex))
			return false;

		for (Road r : adjacencyList.get(sourceVertex)) {
			if (r.contains(destinationVertex))
				return true;
		}

		for (Road r : adjacencyList.get(destinationVertex)) {
			if (r.contains(sourceVertex))
				return true;
		}

		return false;
	}

	/**
	 * Returns a set of the edges contained in this graph. The set is backed by the
	 * graph, so changes to the graph are reflected in the set. If the graph is
	 * modified while an iteration over the set is in progress, the results of the
	 * iteration are undefined.
	 * 
	 * @return a set of the edges contained in this graph.
	 */
	@Override
	public Set<Road> edgeSet() {
		Set<Road> set = new HashSet<>();
		for (Map.Entry<Town, List<Road>> entry : adjacencyList.entrySet()) {
			for (Road r : entry.getValue()) {
				set.add(r);
			}
		}
		return set;
	}

	/**
	 * Returns a set of the vertices contained in this graph. The set is backed by
	 * the graph, so changes to the graph are reflected in the set. If the graph is
	 * modified while an iteration over the set is in progress, the results of the
	 * iteration are undefined.
	 * 
	 * @return a set view of the vertices contained in this graph.
	 */
	@Override
	public Set<Town> vertexSet() {
		return adjacencyList.keySet();
	}

	public Set<Road> edgesOf(Town vertex) {
		Set<Road> set = new HashSet<>();
		for (Road r : adjacencyList.get(vertex)) {
			set.add(r);
		}
		return set;
	}

	/**
	 * Removes an edge going from source vertex to target vertex, if such vertices
	 * and such edge exist in this graph.
	 * 
	 * If weight >- 1 it must be checked If description != null, it must be checked
	 * 
	 * Returns the edge if removed or null otherwise.
	 *
	 * @param sourceVertex      source vertex of the edge.
	 * @param destinationVertex target vertex of the edge.
	 * @param weight            weight of the edge
	 * @param description       description of the edge
	 * @return The removed edge, or null if no edge removed.
	 */
	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road roadToRemove = getEdge(sourceVertex, destinationVertex);
		if (roadToRemove != null && roadToRemove.getWeight() == weight && roadToRemove.getName().equals(description)) {
			adjacencyList.get(sourceVertex).remove(roadToRemove);
			adjacencyList.get(destinationVertex).remove(roadToRemove);
			return roadToRemove;
		}
		return null;
	}

	@Override
	public boolean removeVertex(Town v) {
		if (v == null)
			return false;
		if (!adjacencyList.containsKey(v))
			return false;
		adjacencyList.remove(v);
		adjacencyList.keySet().remove(v);
		return true;
	}

	/**
	 * Dijkstra's Shortest Path Method. Internal structures are built which hold the
	 * ability to retrieve the path, shortest distance from the sourceVertex to all
	 * the other vertices in the graph, etc.
	 * 
	 * @param sourceVertex the vertex to find shortest path from
	 * 
	 */
	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		if (!containsVertex(sourceVertex)) {
			throw new IllegalArgumentException("Source vertex not found in graph.");
		}
		Map<Town, Integer> shortestDistanceMap = new HashMap<>();
		for (Town vertex : adjacencyList.keySet()) {
			shortestDistanceMap.put(vertex, Integer.MAX_VALUE);
		}
		shortestDistanceMap.put(sourceVertex, 0);
		PriorityQueue<Town> pq = new PriorityQueue<>((a, b) -> shortestDistanceMap.get(a) - shortestDistanceMap.get(b));
		pq.add(sourceVertex);
		previousVertex.clear();
		Set<Town> visitedVertices = new HashSet<>();

		while (!pq.isEmpty()) {
			Town visitingVertex = pq.poll();
			if (visitedVertices.contains(visitingVertex)) {
				continue;
			}
			for (Road r : edgesOf(visitingVertex)) {
				Town neighbor = (r.getSource().equals(visitingVertex)) ? r.getDestination() : r.getSource();
				if (neighbor.equals(visitingVertex))
					continue;
				int newDistance = shortestDistanceMap.get(visitingVertex) + r.getWeight();
				if (newDistance < shortestDistanceMap.get(neighbor)) {
					shortestDistanceMap.put(neighbor, newDistance);
					previousVertex.put(neighbor, visitingVertex);
					pq.add(neighbor);
				}
			}
			visitedVertices.add(visitingVertex);
		}
	}

	/**
	 * Find the shortest path from the sourceVertex to the destinationVertex call
	 * the dijkstraShortestPath with the sourceVertex
	 * 
	 * @param sourceVertex      starting vertex
	 * @param destinationVertex ending vertex
	 * @return An arraylist of Strings that describe the path from sourceVertex to
	 *         destinationVertex They will be in the format: startVertex "via" Edge
	 *         "to" endVertex weight As an example: if finding path from Vertex_1 to
	 *         Vertex_10, the ArrayList<String> would be in the following
	 *         format(this is a hypothetical solution): Vertex_1 via Edge_2 to
	 *         Vertex_3 4 (first string in ArrayList) Vertex_3 via Edge_5 to
	 *         Vertex_8 2 (second string in ArrayList) Vertex_8 via Edge_9 to
	 *         Vertex_10 2 (third string in ArrayList)
	 */
	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		if (!adjacencyList.containsKey(sourceVertex) || !adjacencyList.containsKey(destinationVertex)) {
			return new ArrayList<>();
		}

		dijkstraShortestPath(sourceVertex);
		ArrayList<String> path = new ArrayList<>();
		Town currentVertex = destinationVertex;

		while (currentVertex != null && previousVertex.containsKey(currentVertex)) {
			Town previous = previousVertex.get(currentVertex);
			if (previous == null) {
				break;
			}
			Road road = getEdge(previous, currentVertex);
			if (road == null) {
				break;
			}
			path.add(0, previous.getName() + " via " + road.getName() + " to " + currentVertex.getName() + " "
					+ road.getWeight() + " mi");
			currentVertex = previous;
		}

		if (!path.isEmpty() && !path.get(0).startsWith(sourceVertex.getName())) {
			path.clear();
		}

		return path;
	}

}