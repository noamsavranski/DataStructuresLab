/**
 * @param <T> The type of the satellite data of the elements in the dynamic-set.
 */
public class MyDynamicSet<T> {
	private ArrayElement<T>[] array;
	public int size;
	private ArrayElement<T> min;
	private ArrayElement<T> max;
	private ArrayElement<T>[] successors;
	private ArrayElement<T>[] predecessors;

	/**
	 * The constructor should initiate an empty dynamic-set.
	 * @param N The maximum number of elements in the dynamic set at each time.
	 */
	public MyDynamicSet(int N) {
		this.array = new ArrayElement[N];
		this.size = 0;
		this.min = null;
		this.max = null;
		this.successors = new ArrayElement[N];
		this.predecessors = new ArrayElement[N];
	}
	
	public Element<T> search(int k) {
		boolean found = false;
		for (int i = 0; !found && i < this.array.length; i++){
			if(this.array[i] != null && this.array[i].key() == k){
				return this.array[i];
			}
		}
		return null;
	}
	
	public void insert(Element<T> x) {
		if (search(x.key()) == null) {
			ArrayElement<T> add = new ArrayElement<T>(x);
			add.setIndex(size);
			this.array[size] = add;
			this.size = this.size + 1;

			if (this.min == null || x.key() < this.min.key()) {
				this.min = add;
			}
			if (this.max == null || x.key() > this.max.key()) {
				this.max = add;
			}

			int predIndex = -1;
			int succIndex = -1;
			for (int i = 0; i < this.size - 1; i++) {
				if (array[i].key() < add.key() && (predIndex == -1 || array[i].key() > array[predIndex].key())) {
					predIndex = i;
				}
				if (array[i].key() > add.key() && (succIndex == -1 || array[i].key() < array[succIndex].key())) {
					succIndex = i;
				}
			}

			if (predIndex != -1) {
				predecessors[add.index()] = array[predIndex];
				successors[array[predIndex].index()] = add;
			}
			if (succIndex != -1) {
				successors[add.index()] = array[succIndex];
				predecessors[array[succIndex].index()] = add;
			}
		}
	}
	
	public void delete(Element<T> x) {
		ArrayElement<T> toDelete = (ArrayElement<T>) x;
		int index = toDelete.index();
		int lastIndex = this.size - 1;
		if (index < 0 || index >= this.size) {
			return;
		}
		if (index != lastIndex) {
			this.array[index] = this.array[lastIndex];
			this.array[index].setIndex(index);
		}

		ArrayElement<T> pred = predecessors[toDelete.index()];
		ArrayElement<T> succ = successors[toDelete.index()];
		if (pred != null) {
			successors[pred.index()] = succ;
		}
		if (succ != null) {
			predecessors[succ.index()] = pred;
		}

		if (toDelete == min) {
			if(succ != null) {
				min = succ;
			}
			else {
				min = null;
			}
		}
		if (toDelete == max){
			if (pred != null) {
				max = pred;
			}
			else {
				max = null;
			}
		}

		this.array[lastIndex] = null;
		this.size = this.size - 1;
	}
	
	public Element<T> minimum() {
		return min;
	}
	
	public Element<T> maximum() {
		return max;
	}
	
	public Element<T> successor(Element<T> x) {
		ArrayElement<T> elem = (ArrayElement<T>) x;
		return successors[elem.index()];
	}
	
	public Element<T> predecessor(Element<T> x) {
		ArrayElement<T> elem = (ArrayElement<T>) x;
		return predecessors[elem.index()];
	}
}
