package tp_Java;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCodon implements Codon {
	static final String CODONS = "tttttcttattgtcttcctcatcgtattactaatagtgttgctgatgg"
            + "cttctcctactgcctcccccaccgcatcaccaacagcgtcgccgacgg"
            + "attatcataatgactaccacaacgaataacaaaaagagtagcagaagg"
            + "gttgtcgtagtggctgccgcagcggatgacgaagagggtggcggaggg";
	
	private Map<String,Character> table;
	public AbstractCodon() {
	}

	@Override
	public char getAcideAmine(String codon) {
		try{
			return table.get(codon.toLowerCase());
		}catch (Exception e){
			return '?';
		}
	}
	
	void setMap(String acidesAmines){
		table = new HashMap<String, Character>();
		int n = acidesAmines.length();
		for (int i=0; i < n; i++){
			String codon= CODONS.substring(3*i, 3*i+3);
			char aa= acidesAmines.charAt(i);
			table.put(codon, aa);
		}
	}

	public static void main(String[] args) {

	}

}
