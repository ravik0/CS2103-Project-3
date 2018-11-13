package main;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An implementation of the Node interface, designed to hold an actor or movie from IMDB.
 * @author Ravi Kirschner
 *
 */
public class IMDBNode implements Node{
	final private String _name;
	final private ArrayList<Node> edges;
	
	/**
	 * Constructs a new IMDBNode
	 * @param name the name of the node
	 */
	public IMDBNode(String name) {
		_name = name;
		edges = new ArrayList<Node>();
	}

	/**
	 * Returns the name of this node
	 * @return the name of the node
	 */
	@Override
	public String getName() {
		return _name;
	}
	
	/**
	 * Adds an edge to the node
	 * @param edge a new outgoing edge of this node
	 */
	public void addEdge(Node edge) {
		edges.add(edge);
	}

	/**
	 * Returns the edges of this node.
	 * @return the edges of the node
	 */
	@Override
	public Collection<? extends Node> getNeighbors() {
		return edges;
	}

}
