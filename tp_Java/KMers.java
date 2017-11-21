package tp_Java;

import java.util.*;

public interface KMers {
	
	/**
	 * Returns k
	 * @return the size k of a k-mer
	 */
	int k();
	
	/**
	 * Returns the set of k-mers
	 * @return k-mers
	 */
	Set<String> kMers();
	
	/**
	 * Returns the intersection between this and the given k-mers
	 * @param kMers k-mers
	 * @return the intersection
	 */
	Set<String> communs(KMers kMers);
	
	/**
	 * Return the size of the intersection between this and the given k-mers
	 * @param kMers k-mers
	 * @return the size of the intersection
	 */
	int nombreCommuns(KMers kMers);
}
