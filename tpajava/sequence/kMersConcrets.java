/**
 * 
 */
package tpajava.sequence;

import java.util.*;

/**
 * @author mataivic
 *
 */
public class kMersConcrets implements KMers {

	private Set<String> kmers;
	private int k;

	/**
	 * Create new instance of empty KMers 
	 * @param k
	 */
	public kMersConcrets(int k){
		this.k = k;
		kmers = new HashSet<String>();
	}
	
	/**
	 * Create new instance of KMers for a given string
	 * @param k
	 * @param chaine the given string
	 */
	public kMersConcrets(int k, String chaine){
		this(k);
		int len = chaine.length();
		for (int i=0; i <= len-k; i++){
			String kmer = chaine.substring(i, i+k);
			kmers.add(kmer);
		}
		
	}
	
	/**
	 * Create new instance of KMers for a given Sequence
	 * @param k
	 * @param sequence
	 */
	public kMersConcrets(int k, Sequence sequence){
		this(k,sequence.getSequence());
	}

	/** 
	 * @see tpjava.sequence.KMers#k()
	 */
	@Override
	public int k() {
		return k;
	}

	/**
	 * @see tpjava.sequence.KMers#kMers()
	 */
	@Override
	public Set<String> kMers() {
		return new HashSet<String>(kmers);
	}

	/** 
	 * @see tpjava.sequence.KMers#communs(tpjava.sequence.KMers)
	 */
	@Override
	public Set<String> communs(KMers kMers) {
		Set<String> inter = new HashSet<String>(kmers);
		inter.retainAll(kMers.kMers());
		return inter; 
	}

	/**
	 * @see tpjava.sequence.KMers#nombreCommuns(tpjava.sequence.KMers)
	 */
	@Override
	public int nombreCommuns(KMers kMers) {
		return communs(kMers).size();
	}
	
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Sequence u = new SequenceConcrete("","","ATGCAT");
		Sequence v = new SequenceConcrete("","","ACGCA");
		int k = 2;
		KMers kmersU = new kMersConcrets(k,u);
		KMers kmersV = new kMersConcrets(k,v);
		System.out.println(kmersU.communs(kmersV));
		System.out.println(kmersU.nombreCommuns(kmersV));
	}

}