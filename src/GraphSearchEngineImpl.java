import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
		NodeDistancePair found = find(nodeDistances, t);
		if(found == null) return null;
		int dist = found.dist-1;
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
		final IMDBQueue bfs = new IMDBQueue();
		final List<NodeDistancePair> nodeDistances = new LinkedList<NodeDistancePair>();
		final Map<Node, Integer> nodes = new HashMap<Node,Integer>();
		
		bfs.put(s, 0);
		untilFindT:
		while(bfs.size() > 0) {
			final NodeDistancePair node = bfs.getFirst();
			final Node n = node.node;
			final Integer distance = node.dist;
			bfs.remove(n);
			nodes.put(n, distance);
			nodeDistances.add(node);
			for(Node n1 : n.getNeighbors()) {
				if(!bfs.contains(n1) && !nodes.containsKey(n1)) {
					bfs.put(n1, distance+1);
					if(n1.equals(t)) break untilFindT;
				}
			}
		}
		System.out.println(nodeDistances.size());
		return nodeDistances;
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
	
}
