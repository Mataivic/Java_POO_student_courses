package tp_Java;

import java.io.BufferedReader;
import java.io.FileReader;

public class SequenceNucleiqueConcrete extends SequenceConcrete implements SequenceNucleique {

	public static final String alphabet="ATUCGatucg";
	public SequenceNucleiqueConcrete() {
		super();
	}

	public SequenceNucleiqueConcrete(String idf, String organism, String sequence) {
		super(idf, organism, sequence);
	}
	
	static String trans(String seq, String alpha1, String alpha2){;
		int len = seq.length();
		StringBuilder newSeq = new StringBuilder();
		for (int i=0; i < len; i++){
			char c = seq.charAt(i);
			int code = alpha1.indexOf(c);
			if (code >= 0){
				c = alpha2.charAt(code);
				
			}
			newSeq.append(c);
		}
		return newSeq.toString();
	}
	public boolean read(BufferedReader reader) throws InvalidSequenceException{
		return validatedRead(reader, alphabet);
	}
	

	@Override
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
	public Sequence getSameTypeSequence(String id, String organisme, String sequence) {
		return new SequenceNucleiqueConcrete(id, organisme, sequence);
	}

	
	public static void main(String[] args) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader("adn.fasta"));
			SequenceNucleique sequence = new SequenceNucleiqueConcrete();
			sequence.read(reader);
			reader.close();
			Proteine proteine = sequence.traduction(true);
			System.out.println(proteine.getSequence());
		}catch (Exception e){
			System.out.println(e);
		}
		

	}
}
