import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class IMDBQueue{
	final private Map<Node,ValueObj> _queue;
	private ValueObj _front;
	private ValueObj _back;

	public IMDBQueue() {
		_queue = new HashMap<Node,ValueObj>();
	}

	public boolean contains(Node o) {
		return _queue.containsKey(o);
	}
	
	public int size() {
		return _queue.size();
	}

	public boolean isEmpty() {
		return _queue.isEmpty();
	}

	
	public NodeDistancePair getFirst() {
		return new NodeDistancePair(_front.obj, _front.key);
	}

	private ValueObj evict(ValueObj x) {
		final ValueObj newBefore = x.before; //the node before the one we're removing
		final ValueObj newAfter = x.after; //the node after the one we're removing
		if(newAfter != null) {
			newAfter.before = newBefore;
			//if newAfter isn't null, change its "behind" pointer to the ValueObj behind x
		}
		if(newBefore != null) {
			newBefore.after = newAfter;
			//if newBefore isn't null, change its "after" pointer to the ValueObj after x
		}
		if(newBefore == null) {
			_front = newAfter;
			//if newBefore is null (we are evicting the first element) 
			//then set the new front to the node after x
		}
		if(newAfter == null) {
			_back = newBefore;
			//if newAfter is null (we are evicting the last element)
			//then set the new back to the node before x
		}
		return x; //return object we evicted
	}
	
	public void remove(Node n) {
		evict(_queue.get(n));
		_queue.remove(n);
	}
	
	public void put(Node t, Integer x) {
		ValueObj toAdd = new ValueObj(t, x);
		toAdd.after = null;
		toAdd.before = _back;
		if(_back != null) _back.after = toAdd;
		_back = toAdd;
		if(_front == null) _front = _back;
		_queue.put(t, toAdd);
	}

	private class ValueObj {
		final public Integer obj;
		final public Node key;
		public ValueObj before;
		public ValueObj after;
		public ValueObj(Node key, Integer object) {
			obj = object;
			before = null;
			after = null;
			this.key = key;
		}
	}
	
}
