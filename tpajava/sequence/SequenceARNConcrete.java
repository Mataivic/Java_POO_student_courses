package tpajava.sequence;

public class SequenceARNConcrete extends SequenceNucleiqueConcrete implements SequenceARN {

	public static final String alphabet = "UAGCuagc";
	public SequenceARNConcrete() {
		// TODO Auto-generated constructor stub
	}

	public SequenceARNConcrete(String idf, String organism, String sequence) {
		super(idf, organism, sequence);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SequenceADN transcriptInv() {
		String adn = trans(getSequence(), alphabet, SequenceADNConcrete.alphabet);
		return new SequenceADNConcrete(getId(), getOrganism(), adn.toString());
	}
	
	public Sequence getSameTypeSequence(String id, String organisme, String sequence){
		return new SequenceARNConcrete(id, organisme, sequence);
	}

	public static void main(String[] args) {
		SequenceARN arn = new SequenceARNConcrete("","","ACUCUCNNGCCCCCUAA");
		System.out.println(arn.transcriptInv().getSequence());
	}

}