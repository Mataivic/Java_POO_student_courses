package tpajava.sequence;

import java.util.HashMap;
import java.util.Map;

public class AbstractCodon implements Codon {
	
	static final String CODONS = "tttttcttattgtcttcctcatcgtattactaatagtgttgctgatgg"
            + "cttctcctactgcctcccccaccgcatcaccaacagcgtcgccgacgg"
            + "attatcataatgactaccacaacgaataacaaaaagagtagcagaagg"
            + "gttgtcgtagtggctgccgcagcggatgacgaagagggtggcggaggg";

	private Map<String,Character> table;
	
	void setMap(String acidesAmines) {
		table = new HashMap<String, Character>();
		int n = acidesAmines.length();
		for (int i=0; i <n; i++) {
			String codon = CODONS.substring(3*1, 3*i+3);
			char aa = acidesAmines.charAt(i);
			table.put(codon, aa);
		}		
	}	
	
	public char getAcideAmine(String codon) {
		try {
			return table.get(codon.toLowerCase());
		} catch (Exception e) {
			return '?';
		}
	}
}
