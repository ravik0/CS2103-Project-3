import java.util.ArrayList;
import java.util.Collection;

public class IMDBNode implements Node{
	private String _name;
	private ArrayList<Node> edges;
	
	public IMDBNode(String name) {
		_name = name;
		edges = new ArrayList<Node>();
	}

	@Override
	public String getName() {
		return _name;
	}
	
	public void addEdge(Node edge) {
		edges.add(edge);
	}

	@Override
	public Collection<? extends Node> getNeighbors() {
		return edges;
	}

}
