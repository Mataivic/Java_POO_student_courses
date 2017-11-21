package tp_Java;

import java.io.BufferedReader;
import java.io.FileReader;

public class ProteineConcrete extends SequenceConcrete implements Proteine {

	public static final String alphabet = "ACDEFGHIKLMNPQRSTVWYacdefghiklmnpqrstvwy";
	
	public ProteineConcrete() {
	}
	
	public boolean read(BufferedReader reader) throws InvalidSequenceException{
		return validatedRead(reader, alphabet);
	}
	
	public ProteineConcrete(String idf, String organism, String sequence) {
		super(idf, organism, sequence);
	}
	

	@Override
	public Sequence getSameTypeSequence(String id, String organisme, String sequence) {
		return new ProteineConcrete(id, organisme, sequence);
	}

	public static void main(String[] args) {
		try{
			Proteine proteine = new ProteineConcrete();
			BufferedReader reader = new BufferedReader(new FileReader("sequence.fasta"));
			proteine.read(reader);
			reader.close();
			System.out.println(proteine.getSequence());
		}catch (Exception e){
			System.err.println(e);
		}
		
		

	}

}
