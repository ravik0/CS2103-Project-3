import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GraphSearchEngineImpl implements GraphSearchEngine{
	public List<Node> findShortestPath(Node s, Node t) {
		final Map<Node, Node> nodes = findNodeDistances(s,t);
		final List<Node> tracking = new ArrayList<Node>();
		tracking.add(t);
		if(!nodes.containsKey(t)) return null;
		while(!tracking.get(tracking.size()-1).equals(s)) {
			final Node ofNode = nodes.get(tracking.get(tracking.size()-1));
			tracking.add(ofNode);
		}
		return reverse(tracking);
	}

	private Map<Node, Node> findNodeDistances(Node s, Node t) {
		final Queue<Node> bfs = new LinkedList<Node>();
		final Map<Node, Node> nodes = new HashMap<Node, Node>();
		bfs.offer(s);
		untilFindT:
		while(bfs.size() > 0) {
			Node parent = bfs.poll();
			for(Node n1 : parent.getNeighbors()) {
				if(!nodes.containsKey(n1) && !nodes.containsKey(n1)) {
					bfs.offer(n1);
					nodes.put(n1, parent);
					if(n1.equals(t)) break untilFindT;
				}
			}
		}
		return nodes;
	}
	
	private List<Node> reverse(List<Node> in) {
		final List<Node> out = new ArrayList<Node>();
		for(int i = in.size()-1; i >= 0; i--) {
			out.add(in.get(i));
		}
		return out;
	}
}
