package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GraphSearchEngineImpl implements GraphSearchEngine{
	
	public List<Node> findShortestPath(Node s, Node t) {
		final Map<Node, Node> nodes = findNodeDistances(s,t); 
		if(!nodes.containsKey(t)) return null; //if t isn't in the BFS list, return null.
		final List<Node> path = new ArrayList<Node>(); 
		path.add(t); //add t to the current path
		while(!path.get(path.size()-1).equals(s)) { //while the end of the path is not equal to s
			final Node ofNode = nodes.get(path.get(path.size()-1)); //get the parent node of the node at the end of the list
			path.add(ofNode); //put that in as the new end of the path
		}
		return reverse(path);
	}

	/**
	 * Returns a Map of a node and its parent node using a Breadth-First Search algorithm.
	 * @param s starting node
	 * @param t target node
	 * @return a map of a node and its parent node
	 */
	private Map<Node, Node> findNodeDistances(Node s, Node t) {
		final Queue<Node> bfs = new LinkedList<Node>();
		final Map<Node, Node> nodes = new HashMap<Node, Node>();
		bfs.offer(s); //put starting node in the queue
		untilFindT: //until we find t, do this
		while(bfs.size() > 0) {
			final Node parent = bfs.poll(); //remove first node in queue
			for(final Node n1 : parent.getNeighbors()) { //for every node in the queue,
				if(!nodes.containsKey(n1)) { //if the node isnt already in the return map
					bfs.offer(n1); //put it at back of queue
					nodes.put(n1, parent); //add it as a key in the list, with its value as the parent node
					if(n1.equals(t)) break untilFindT; //if we found t, break the while loop
				}
			}
		}
		return nodes;
	}
	
	/**
	 * Reverses a list of nodes.
	 * @param in the original list
	 * @return the reversed list
	 */
	private List<Node> reverse(List<Node> in) {
		final List<Node> out = new ArrayList<Node>();
		for(int i = in.size()-1; i >= 0; i--) {
			out.add(in.get(i));
		}
		return out;
	}
}
