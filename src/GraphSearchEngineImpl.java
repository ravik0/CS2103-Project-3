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

	public List<Node> findShortestPath(Node s, Node t) {
		final Map<Node, NodeAndParent> nodes = findNodeDistances(s,t);
		final List<Node> tracking = new ArrayList<Node>();
		tracking.add(t);
		while(!tracking.get(tracking.size()-1).equals(s)) {
			final NodeAndParent ofNode = nodes.get(tracking.get(tracking.size()-1));
			tracking.add(ofNode.parent);
		}
		return reverse(tracking);
	}

	private Map<Node, NodeAndParent> findNodeDistances(Node s, Node t) {
		final Queue<Node> bfs = new LinkedList<Node>();
		final Map<Node, Integer> bfsContains = new HashMap<Node,Integer>();
		final Map<Node, NodeAndParent> nodes = new HashMap<Node, NodeAndParent>();
		bfs.offer(s);
		bfsContains.put(s, 0);
		untilFindT:
		while(bfs.size() > 0) {
			Node parent = bfs.poll();
			bfsContains.remove(parent);
			for(Node n1 : parent.getNeighbors()) {
				if(!bfsContains.containsKey(n1) && !nodes.containsKey(n1)) {
					bfs.offer(n1);
					bfsContains.put(n1, 0);
					nodes.put(n1, new NodeAndParent(parent, n1));
				}
				if(n1.equals(t)) break untilFindT;
			}
		}
		System.out.println(nodes.size());
		return nodes;
	}
	private List<Node> reverse(List<Node> in) {
		final List<Node> out = new ArrayList<Node>();
		for(int i = in.size()-1; i >= 0; i--) {
			out.add(in.get(i));
		}
		return out;
	}

	private class NodeAndParent {
		final public Node child;
		final public Node parent;
		
		public NodeAndParent(Node pa, Node ch) {
			child = ch;
			parent = pa;
		}
	}
}
