/**
 * 
 */
package tpajava.sequence;

import java.util.Set;

/**
 * @author mataivic
 *
 */
public interface KMers {
	
	/**
	 * Returns k
	 * @return the size l of a k-mer
	 */	
	public int k();	
	
	/**
	 * Returns the
	 * @param kMers
	 * @return
	 */
	Set<String> kMers();	
	
	/**
	 * Returns intersection between this and the given k-mers
	 * @param kMers k-mers
	 * @return the intersection
	 */
	Set<String> communs(KMers kMers);
	
	/**
	 * Returns the size of the intersection between this and the given k-mers
	 * @param kmers
	 * @return the size of the intersection
	 */
	int nombreCommuns(KMers kMers);

}