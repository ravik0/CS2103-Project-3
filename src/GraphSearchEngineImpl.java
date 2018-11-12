import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GraphSearchEngineImpl implements GraphSearchEngine{

	@Override
	public List<Node> findShortestPath(Node s, Node t) {
		final List<NodeDistancePair> nodeDistances = findNodeDistances(s, t);
		final List<Node> tracking = new ArrayList<Node>();
		tracking.add(t);
		if(find(nodeDistances, t) == null) return null;
		int dist = find(nodeDistances, t).dist-1;
		while(dist >= 0) {
			NodeDistancePair temp = find(nodeDistances, dist);
			Node possible = temp.node;
			if(tracking.get(tracking.size()-1).getNeighbors().contains(possible)) {
				tracking.add(possible);
				dist--;
			}
			else {
				nodeDistances.remove(temp);
			}
		}
		return reverse(tracking);
	}

	private List<NodeDistancePair> findNodeDistances(Node s, Node t) {
		final LinkedHashMap<Node, Integer> bfs = new LinkedHashMap<Node,Integer>();
		final List<NodeDistancePair> nodeDistances = new ArrayList<NodeDistancePair>();
		final Map<Node, Integer> nodes = new HashMap<Node,Integer>();
		
		bfs.put(s,0);
		while(bfs.size() > 0) {
			final NodeDistancePair node = getFirst(bfs);
			bfs.remove(node.node);
			final Node n = node.node;
			final Integer distance = node.dist;
			nodes.put(n, distance);
			nodeDistances.add(node);
			if(n.equals(t)) break;
			for(Node n1 : n.getNeighbors()) {
				if(!bfs.containsKey(n1) && !nodes.containsKey(n1)) {
					bfs.put(n1, distance+1);
				}
			}
		}
		System.out.println(nodeDistances.size());
		return nodeDistances;
	}
	
	private class NodeDistancePair {
		public int dist;
		public Node node;
		
		public NodeDistancePair(int d, Node n) {
			dist = d;
			node = n;
		}
		
		public boolean equals(Object o) {
			NodeDistancePair x = (NodeDistancePair)o;
			return x.dist == dist && node.equals(x.node);
		}
	}
	

	private List<Node> reverse(List<Node> in) {
		final List<Node> out = new ArrayList<Node>();
		for(int i = in.size()-1; i >= 0; i--) {
			out.add(in.get(i));
		}
		return out;
	}
	
	private NodeDistancePair find(List<NodeDistancePair> x, Node n) {
		for(int i = 0; i < x.size(); i++) {
			if(x.get(i).node.equals(n)) return x.get(i);
		}
		return null;
	}
	
	private NodeDistancePair find(List<NodeDistancePair> x, int d) {
		for(int i = 0; i < x.size(); i++) {
			if(x.get(i).dist == d) return x.get(i);
		}
		return null;
	}
	
	private NodeDistancePair getFirst(LinkedHashMap<Node, Integer> x) {
		if(x.isEmpty()) return null;
		else {
			for(Map.Entry<Node, Integer> entry : x.entrySet() ) {
				return new NodeDistancePair(entry.getValue(), entry.getKey());
			}
		}
		return null;
	}
	
}
