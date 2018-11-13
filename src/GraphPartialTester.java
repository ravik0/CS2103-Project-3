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

	IMDBGraphImpl x = null;
	@Test(timeout=100000)
	public void testTest() {
		Node a = x.getActor("Sheen, Charlie");
		Node b = x.getActor("Marchi, Ann");
		List<Node> ah = searchEngine.findShortestPath(a,b);
		for(int i = 0; i < ah.size(); i++) {
			System.out.println(ah.get(i).getName());
		}
	}
	
	/**
	 * Verifies that there is no shortest path between a specific and actor and actress.
	 */
	//@Test(timeout=5000)
	public void findShortestPath () throws IOException {
		imdbGraph = new IMDBGraphImpl("tests/actors_test.list", "tests/actresses_test.list");
		final Node actor1 = imdbGraph.getActor("Actor1");
		final Node actress2 = imdbGraph.getActor("Actress2");
		//final List<Node> shortestPath = searchEngine.findShortestPath(actor1, actress2);
		//assertNull(shortestPath);  // there is no path between these people
		final List<Node> shortestPath2 = searchEngine.findShortestPath(imdbGraph.getActor("Actor5"), actor1);
		List<Node> findPath = new ArrayList<Node>();
		findPath.add(imdbGraph.getActor("Actor5"));
		findPath.add(imdbGraph.getMovie("Movie3 (2003)"));
		findPath.add(imdbGraph.getActor("Actor3"));
		findPath.add(imdbGraph.getMovie("Movie1 (2001)"));
		findPath.add(imdbGraph.getActor("Actor1"));
		//for(int i = 0; i < shortestPath2.size(); i++) System.out.println(shortestPath2.get(i).getName());
		assertEquals(findPath, shortestPath2);
	}

	@Before
	/**
	 * Instantiates the graph
	 */
	public void setUp () throws IOException {
		imdbGraph = new IMDBGraphImpl("tests/actors_test.list", "tests/actresses_test.list");
		searchEngine = new GraphSearchEngineImpl();
		x = new IMDBGraphImpl("D:/Downloads/IMDB/actors.list", "D:/Downloads/IMDB/actresses.list");
	}

	//@Test
	/**
	 * Just verifies that the graphs could be instantiated without crashing.
	 */
	public void finishedLoading () {
		assertTrue(true);
		// Yay! We didn't crash
	}

	//@Test
	/**
	 * Verifies that a specific movie has been parsed.
	 */
	public void testSpecificMovie () {
		Collection<? extends Node> x = imdbGraph.getActors();
		testFindNode(imdbGraph.getMovies(), "Movie1 (2001)");
	}

	//@Test
	/**
	 * Verifies that a specific actress has been parsed.
	 */
	public void testSpecificActress () {
		Collection<? extends Node> x = imdbGraph.getMovies();
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
	
}
