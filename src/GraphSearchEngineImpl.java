import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GraphSearchEngineImpl implements GraphSearchEngine{

	@Override
	public List<Node> findShortestPath(Node s, Node t) {
		return null;
	}

	private Map<Node, Integer> findNodeDistances(Node s, Node t) {
		final Queue<Node> bfs = new ConcurrentLinkedQueue<Node>();
		final Map<Node, Integer> nodeDistances = new HashMap<Node, Integer>();
		
		bfs.offer(s);
		return null;
	}
}
