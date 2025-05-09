/**
 * @param <T> The type of the satellite data of the elements in the data structure.
 */
public class MyFirstDataStructure<T> {
	private MyAVLTree<T> mainTree;
	private MyLinkedList<T> order;
	private Element<T> max;

	/***
     * This function is the Init function.
	 * @param N The maximum number of elements in the data structure at each time.
     */
	public MyFirstDataStructure(int N) {
		this.order = new MyLinkedList<>();
		this.mainTree = new MyAVLTree<>();
		this.max = null;
	}
	
	public void insert(Element<T> x) {
		ListLink<T> link = new ListLink<>(x.key(), x.satelliteData());
		TreeNode<T> insert = new TreeNode<>(x.key(), x.satelliteData(),link);
		this.mainTree.insert(insert);
		this.order.insert(link);
		if (max == null || x.key() > this.max.key()) {
			this.max = x;
		}
	}
	public void findAndRemove(int k) {
		TreeNode<T> remove = this.mainTree.search(k);
		if (remove == null) {
			return;
		}
		this.mainTree.delete(remove);
		if (max.key() == k) {
			TreeNode<T> newMax = this.mainTree.maxValueNode(this.mainTree.root());
			this.max = new Element<>(newMax.key(),newMax.satelliteData());
		}
		this.order.delete(remove.getLink());

	}

	public Element<T> maximum() {
		return this.max;
	}

	public Element<T> first() {
		return this.order.tail();
	}

	public Element<T> last() {
		return this.order.head();
	}

}
