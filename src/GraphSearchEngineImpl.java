import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GraphSearchEngineImpl implements GraphSearchEngine{

	@Override
	public List<Node> findShortestPath(Node s, Node t) {
		final MapHolder<Node, Integer> temp = findNodeDistances(s,t);
		final Map<Node,Integer> nodeDistances = temp.frontMap;
		final Map<Integer,Node> nodeDistancesSwapped = temp.backMap;
		final List<Node> tracking = new ArrayList<Node>();
		tracking.add(t);
		if(nodeDistances.get(t) == null) return null;
		int dist = nodeDistances.get(t)-1;
		while(dist >= 0) {
			Node possible = nodeDistancesSwapped.get(dist);
			if(tracking.get(tracking.size()-1).getNeighbors().contains(possible)) {
				tracking.add(possible);
				dist--;
			}
			else {
				nodeDistances.remove(possible);
				nodeDistancesSwapped.remove(dist);
			}
		}
		return reverse(tracking);
	}

	private MapHolder<Node,Integer> findNodeDistances(Node s, Node t) {
		final Queue<Node> bfs = new ConcurrentLinkedQueue<Node>();
		final Queue<Integer> nodeDist = new ConcurrentLinkedQueue<Integer>();
		final Map<Node, Integer> nodeDistances = new HashMap<Node, Integer>();
		final Map<Integer, Node> nodeDistancesSwapped = new HashMap<Integer, Node>();
		
		bfs.offer(s);
		nodeDist.offer(0);
		while(bfs.size() > 0) {
			final Node n = bfs.poll();
			final Integer distance = nodeDist.poll();
			nodeDistances.put(n, distance);
			nodeDistancesSwapped.put(distance, n);
			if(n.equals(t)) break;
			for(Node n1 : n.getNeighbors()) {
				if(!bfs.contains(n1) && !nodeDistances.containsKey(n1)) {
					bfs.offer(n1);
					nodeDist.offer(distance+1);
				}
			}
		}
		return new MapHolder<Node, Integer>(nodeDistances, nodeDistancesSwapped);
	}
	
	private static class MapHolder<K,V> {
		final public Map<K,V> frontMap;
		final public Map<V,K> backMap;
		
		public MapHolder(Map<K,V> one, Map<V,K> two) {
			frontMap = one;
			backMap = two;
		}
	}
	
	private List<Node> reverse(List<Node> in) {
		final List<Node> out = new ArrayList<Node>();
		for(int i = in.size()-1; i >= 0; i--) {
			out.add(in.get(i));
		}
		return out;
	}
}
