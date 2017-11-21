package tp_Java;

import java.io.BufferedReader;
import java.io.FileReader;

public class SequenceADNConcrete extends SequenceNucleiqueConcrete implements SequenceADN {

	public static final String alphabet="ATCGatcg";
	public SequenceADNConcrete() {
		super();
	}

	public SequenceADNConcrete(String idf, String organism, String sequence) {
		super(idf, organism, sequence);
	}

	@Override
	public SequenceARN transcript() {
		String arn = trans(getSequence(), alphabet, SequenceARNConcrete.alphabet);
		return new SequenceARNConcrete(getId(), getOrganism(), arn.toString());
	}
	public boolean read(BufferedReader reader) throws InvalidSequenceException{
		return validatedRead(reader, alphabet);
	}
	
	public Sequence getSameTypeSequence(String id, String organisme, String sequence) {
		return new SequenceADNConcrete(id, organisme, sequence);
	}

	
	public static void main(String[] args) {
		try{
			SequenceADN adn = new SequenceADNConcrete();
			BufferedReader reader = new BufferedReader(new FileReader("adn2.fasta"));
			adn.read(reader);
			reader.close();
			SequenceARN arn =adn.transcript();
			System.out.println(arn.getSequence());
		}
		catch (Exception e){
			e.printStackTrace();;
		}
	
	}

}
