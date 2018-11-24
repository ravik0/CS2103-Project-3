package tests;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.GraphSearchEngine;
import main.GraphSearchEngineImpl;
import main.IMDBGraph;
import main.IMDBGraphImpl;
import main.Node;

import java.util.*;
import java.io.*;

/**
 * Code to test <tt>GraphSearchEngineImpl</tt>.
 */
public class GraphSearchTest {
	IMDBGraph graph;
	GraphSearchEngine searchEngine;

	private boolean isEqualSet (String[] nodeNames, Collection<? extends Node> nodes) {
		TreeSet<String> nodeNameSet1 = new TreeSet<String>();
		for (Node node : nodes) {
			nodeNameSet1.add(node.getName());
		}
		
		TreeSet<String> nodeNameSet2 = new TreeSet<String>();
		for (String nodeName : nodeNames) {
			nodeNameSet2.add(nodeName);
		}

		return nodeNameSet1.equals(nodeNameSet2);
	}

	private void findShortestPathHelper (String a1Name, String a2Name, String[] solutionPathNames, int numPoints) {
		Node a1 = graph.getActor(a1Name);
		Node a2 = graph.getActor(a2Name);
		List<Node> shortestPath = searchEngine.findShortestPath(a1, a2);
		for (Node n : shortestPath) {
			System.out.println(n.getName());
		}
		assertEquals(solutionPathNames.length, shortestPath.size());
		System.out.println("CS2103GRDR +" + numPoints + " findShortestPathLength");

		assertTrue(isEqualSet(solutionPathNames, shortestPath));
		System.out.println("CS2103GRDR +" + numPoints + " findShortestPathSet");

		// Allow for the path to be reversed
		for (int i = 0; i < solutionPathNames.length; i++) {
			assertTrue(solutionPathNames[i].equals(shortestPath.get(i).getName()) ||
			           solutionPathNames[i].equals(shortestPath.get(solutionPathNames.length - i - 1).getName()));
		}
		System.out.println("CS2103GRDR +" + (numPoints/2) + " findShortestPathMaybeReversed");

		for (int i = 0; i < solutionPathNames.length; i++) {
			assertEquals(solutionPathNames[i], shortestPath.get(i).getName());
		}
		System.out.println("CS2103GRDR +" + (numPoints/2) + " findShortestPathExact");
	}

	@Test(timeout=5000)
	public void findShortestPath () {
		final String[] solutionPathNames = new String[] { "Actor1", "Movie1 (2001)", "Actress1", "Movie4 (2004)", "Actress2" };
		findShortestPathHelper("Actor1", "Actress2", solutionPathNames, 4);
	}

	@Test(timeout=5000)
	public void returnsNonNull () {
		Node actor1 = graph.getActor("Actor1");
		Node actress2 = graph.getActor("Actress2");
		List<Node> shortestPath = searchEngine.findShortestPath(actor1, actress2);
		assertNotNull(shortestPath);
		System.out.println("CS2103GRDR +2 returnsNonNull");
	}

	@Test(timeout=5000)
	public void findShortestPathSimple () {
		final String[] solutionPathNames = new String[] { "Actor1", "Movie1 (2001)", "Actress1" };
		findShortestPathHelper("Actor1", "Actress1", solutionPathNames, 2);
	}

	@Before
	public void setUp () throws IOException {
		graph = new IMDBGraphImpl("files/actors_tiny.list", "files/actresses_tiny.list");
		searchEngine = new GraphSearchEngineImpl();
	}
}