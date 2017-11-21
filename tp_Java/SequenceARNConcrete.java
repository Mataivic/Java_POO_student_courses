package tp_Java;

import java.io.BufferedReader;

public class SequenceARNConcrete extends SequenceNucleiqueConcrete implements SequenceARN {

	public static final String alphabet="UAGCuagc";
	public SequenceARNConcrete() {
		super();
	}

	public SequenceARNConcrete(String idf, String organism, String sequence) {
		super(idf, organism, sequence);
	}

	@Override
	public SequenceADN transcriptInv() {
		String adn = trans(getSequence(), alphabet, SequenceADNConcrete.alphabet);
		return new SequenceADNConcrete(getId(), getOrganism(), adn.toString());
	}
	public boolean read(BufferedReader reader) throws InvalidSequenceException{
		return validatedRead(reader, alphabet);
	}
	
	public Sequence getSameTypeSequence(String id, String organisme, String sequence) {
		return new SequenceADNConcrete(id, organisme, sequence);
	}

	public static void main(String[] args) {
		SequenceARN arn = new SequenceARNConcrete("","","uagcggau");
		SequenceADN adn = arn.transcriptInv();
		System.out.print(adn.getSequence());
	}

}
