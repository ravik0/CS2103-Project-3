package tests;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.IMDBGraph;
import main.IMDBGraphImpl;
import main.Node;

import java.util.*;
import java.io.*;


/**
 * Code to test IMDBActorsGraph and IMDBMoviesGraph.
 */
public class DataLoadTest {
	IMDBGraph graph;

	@Test(timeout=5000)
	public void finishedLoading () {
		System.out.println("CS2103GRDR +2 finishedLoading");
	}

	@Test(timeout=5000)
	public void loadedApproximatelyCorrectNumberOfActors () {
		final int TOLERANCE = 250;
		final int CORRECT = 2184;
		System.out.println(graph.getActors().size());
		assertTrue(Math.abs(graph.getActors().size() - CORRECT) <= TOLERANCE);
		System.out.println("CS2103GRDR +2 loadedApproximatelyCorrectNumberOfActors");
	}

	@Test(timeout=5000)
	public void loadedApproximatelyCorrectNumberOfMovies () {
		final int TOLERANCE = 1500;
		final int CORRECT = 4519;
		System.out.println(graph.getMovies().size());
		assertTrue(Math.abs(graph.getMovies().size() - CORRECT) <= TOLERANCE);
		System.out.println("CS2103GRDR +2 loadedApproximatelyCorrectNumberOfMovies");
	}

	@Test(timeout=5000)
	public void testSpecificActor1 () {
		testFindNode(graph, "2 Chainz", true);
	}

	@Test(timeout=5000)
	public void testSpecificActress2 () {
		testFindNode(graph, "Abboud, Sereen", true);
	}

	@Test(timeout=5000)
	public void testSpecificMovie1 () {
		testFindNode(graph, "What a Way to Go (1977)", false);
	}

	@Test(timeout=5000)
	public void testSpecificMovie2 () {
		testFindNode(graph, "Pele's Appeal (1990)", false);
	}

	private static void testFindNode (IMDBGraph graph, String name, boolean isActor) {
		Collection<? extends Node> nodes = isActor ? graph.getActors() : graph.getMovies();
		boolean found = false;
		for (Node node : nodes) {
			if (node.getName().trim().equals(name)) {
				found = true;
			}
		}
		assertTrue(found);
		System.out.println("CS2103GRDR +2 testFind" + name.replace(" ", ""));
	}

	@Before
	public void setUp () throws IOException {
		graph = new IMDBGraphImpl("files/actors_first_10000_lines.list", "files/actresses_first_10000_lines.list");
	}
}