package tpajava.sequence;

import java.io.BufferedReader;
import java.io.FileReader;

public class SequenceNucleiqueConcrete extends SequenceConcrete implements SequenceNucleique {
	
	public static final String alphabet = "TUAGCtuagc";

	public SequenceNucleiqueConcrete() {
		super();
	}

	public SequenceNucleiqueConcrete(String idf, String organism, String sequence) {
		super(idf, organism, sequence);
		// TODO Auto-generated constructor stub
	}
	
	public boolean read(BufferedReader reader) throws InvalidSequenceException {
		return validatedRead(reader, alphabet);
	}
	
	static String trans(String seq, String alpha1, String alpha2){ //fonction (pas méthode, ne dépend pas de la seqeuence)
		StringBuilder newSeq = new StringBuilder();
		int len = seq.length();
		for (int i=0; i < len; i++) {
			char c = seq.charAt(i);
			int code = alpha1.indexOf(c); //renvoie -1 si il trouve pas
			if (code >= 0) {
				c = alpha2.charAt(code);
			}
			newSeq.append(c);
		}
		return newSeq.toString();
	}
	
	public Proteine traduction() {
		return traduction(false);
	}
	
	@Override
	public Proteine traduction(boolean mitochondrial) {
		Codon codonTable = null;
		if (mitochondrial){
			codonTable = new MitochondrialCodon();
		}else{
			codonTable = new UniversalCodon();
		}
		String seq = getSequence();
		int len = seq.length();
		StringBuilder proteine = new StringBuilder();
		for (int i=0; i<len-2; i+=3){
			String codon = seq.substring(i, i+3);
			char aa = codonTable.getAcideAmine(codon);
			proteine.append(aa);
		}
		return new ProteineConcrete(getId(), getOrganism(), proteine.toString());
	}

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("adn.fasta"));
			SequenceNucleique sequence = new SequenceNucleiqueConcrete();
			sequence.read(reader);
			Proteine proteine = sequence.traduction();
			System.out.println(proteine.getSequence());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}