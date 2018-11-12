public class NodeDistancePair {
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