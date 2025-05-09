
public class MySecondDataStructure {
	private Product[][] qualityBuckets;
	private int[] raise;
	private int sumQuality;
	private int[] qualityCounts;
	private Product[] maxProduct;
	private int size;

	/***
	 * This function is the Init function.
	 * @param N The maximum number of elements in the data structure at each time.
	 */
	public MySecondDataStructure(int N) {
		this.size = 0;
		this.sumQuality = 0;
		this.maxProduct = new Product[6];
		this.qualityBuckets = new Product[6][N];
		this.raise = new int[6];
		this.qualityCounts = new int[6];
	}

	public void insert(Product product) {
		int q = product.quality();
		this.size = this.size + 1;
		int index = qualityCounts[q];
		qualityBuckets[q][index] = product;
		qualityCounts[q] = qualityCounts[q] + 1;
		sumQuality = sumQuality + q;

		product.setPrice(product.price() - raise[product.quality()]);
		if ((maxProduct[q] == null || product.price() > maxProduct[q].price())) {
			maxProduct[q] = product;
		}
	}

	public void findAndRemove(int id) {
		for (int q = 0; q < 6; q = q + 1) {
			for (int i = 0; i < qualityCounts[q]; i = i + 1) {
				Product p = qualityBuckets[q][i];
				if (p.id() == id) {
					qualityBuckets[q][i] = qualityBuckets[q][qualityCounts[q] - 1];
					qualityBuckets[q][qualityCounts[q] - 1] = null;
					size = size - 1;
					sumQuality = sumQuality - q;
					qualityCounts[q] = qualityCounts[q] - 1;
					if (maxProduct[q] == p) {
						if (qualityBuckets[q][0] == null) {
							maxProduct[q] = null;
						} else {
							maxProduct[q] = qualityBuckets[q][0];
							for (int j = 1; j < qualityCounts[q]; j = j + 1) {
								if (qualityBuckets[q][j].price() > maxProduct[q].price()) {
									maxProduct[q] = qualityBuckets[q][j];
								}
							}
						}
					}
				}
			}
		}


	}

	public int medianQuality() {
		if (size == 0) {
			return -1;
		}
		int med = (size - 1) / 2;
		int count = 0;
		for (int q = 0; q < 6; q = q + 1) {
			count = count + qualityCounts[q];
			if (count > med) {
				return q;
			}
		}
		return -1;
	}

	public double avgQuality() {
		if (size == 0) {
			return -1;
		}
		return (double) sumQuality / size;
	}

	public void raisePrice(int raise, int quality) {
		this.raise[quality] = this.raise[quality] + raise;
	}

	public Product mostExpensive() {
		Product max = null;
		int maxprice = -1;
		for (int i = 0; i < 6; i++) {
			if (maxProduct[i] != null) {
				int candidatePrice = maxProduct[i].price() + raise[i];
				if (max == null || candidatePrice > maxprice) {
					max = maxProduct[i];
					maxprice = candidatePrice;
				}
			}
		}
		return max;
	}
}
