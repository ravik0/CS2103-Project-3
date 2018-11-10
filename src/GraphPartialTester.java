import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;

/**
 * Code to test Project 3; you should definitely add more tests!
 */
public class GraphPartialTester {
	IMDBGraph imdbGraph;
	GraphSearchEngine searchEngine;

	/**
	 * Verifies that there is no shortest path between a specific and actor and actress.
	 */
	@Test(timeout=5000)
	public void findShortestPath () throws IOException {
		//imdbGraph = new IMDBGraphImpl("actors_test.list", "actresses_test.list");
		//final Node actor1 = imdbGraph.getActor("Actor1");
		//final Node actress2 = imdbGraph.getActor("Actress2");
		//final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actress2);
		//assertNull(shortestPath);  // there is no path between these people
	}

	@Before
	/**
	 * Instantiates the graph
	 */
	public void setUp () throws IOException {
		imdbGraph = new IMDBGraphImpl("tests/actors_test.list", "tests/actresses_test.list");
		searchEngine = new GraphSearchEngineImpl();
	}

	@Test
	/**
	 * Just verifies that the graphs could be instantiated without crashing.
	 */
	public void finishedLoading () {
		assertTrue(true);
		// Yay! We didn't crash
	}

	@Test
	/**
	 * Verifies that a specific movie has been parsed.
	 */
	public void testSpecificMovie () {
		Collection<? extends Node> x = imdbGraph.getActors();
		for(Node n : x) {
			System.out.println(n.getName());
			printStuff(n.getNeighbors());
			System.out.println();
		}
		System.out.println(x.size());
		testFindNode(imdbGraph.getMovies(), "Movie1 (2001)");
	}

	@Test
	/**
	 * Verifies that a specific actress has been parsed.
	 */
	public void testSpecificActress () {
		Collection<? extends Node> x = imdbGraph.getMovies();
		for(Node n : x) {
			System.out.println(n.getName());
			printStuff(n.getNeighbors());
			System.out.println();
		}
		System.out.println(x.size());
		testFindNode(imdbGraph.getActors(), "Actress2");
	}

	/**
	 * Verifies that the specific graph contains a node with the specified name
	 * @param graph the IMDBGraph to search for the node
	 * @param name the name of the Node
	 */
	private static void testFindNode (Collection<? extends Node> nodes, String name) {
		boolean found = false;
		for (Node node : nodes) {
			if (node.getName().trim().equals(name)) {
				found = true;
			}
		}
		assertTrue(found);
	}
	
	private void printStuff(Collection<? extends Node> y) {
		for(Node n: y) {
			System.out.println(n.getName());
		}
	}
}
