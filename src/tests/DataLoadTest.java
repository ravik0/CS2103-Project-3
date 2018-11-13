package tests;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;
import main.IMDBGraph;
import main.IMDBGraphImpl;
import main.Node;

/**
 * Code to test IMDBActorsGraph and IMDBMoviesGraph.
 */
public class DataLoadTest {
    IMDBGraph graph;

    @Test(timeout=5000)
    public void finishedLoading () {
        System.out.println("CS210XGRDR +2 finishedLoading");
    }

    @Test(timeout=5000)
    public void loadedApproximatelyCorrectNumberOfActors () {
        final int TOLERANCE = 100;
        final int CORRECT = 2184;
        System.out.println(graph.getActors().size());
        assertTrue(Math.abs(graph.getActors().size() - CORRECT) <= TOLERANCE);
        System.out.println("CS210XGRDR +3 loadedApproximatelyCorrectNumberOfActors");
    }

    @Test(timeout=5000)
    public void loadedApproximatelyCorrectNumberOfMovies () {
        final int TOLERANCE = 100;
        final int CORRECT = 4519;
        System.out.println(graph.getMovies().size());
        assertTrue(Math.abs(graph.getMovies().size() - CORRECT) <= TOLERANCE);
        System.out.println("CS210XGRDR +3 loadedApproximatelyCorrectNumberOfMovies");
    }

    @Test(timeout=5000)
    public void testSpecificActor1 () {
        testFindNode(graph.getActors(), "2 Chainz");
    }

    @Test(timeout=5000)
    public void testSpecificActor2 () {
        testFindNode(graph.getActors(), "Abad, Javier (III)");
    }

    @Test(timeout=5000)
    public void testSpecificActress2 () {
        testFindNode(graph.getActors(), "Abboud, Sereen");
    }

    @Test(timeout=5000)
    public void testSpecificMovie1 () {
        testFindNode(graph.getMovies(), "What a Way to Go (1977)");
    }

    @Test(timeout=5000)
    public void testSpecificMovie2 () {
        testFindNode(graph.getMovies(), "Pele's Appeal (1990)");
    }

    private static void testFindNode (Collection<? extends Node> nodes, String name) {
        boolean found = false;
        for (Node node : nodes) {
            if (node.getName().trim().equals(name)) {
                found = true;
            }
        }
        assertTrue(found);
        System.out.println("CS210XGRDR +2 testFind" + name.replace(" ", ""));
    }

    @Before
    public void setUp () throws IOException {
        graph = new IMDBGraphImpl("files/actors_first_10000_lines.list", "files/actresses_first_10000_lines.list");
    }
}