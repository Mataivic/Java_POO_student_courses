package tpajava.sequence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SequenceADNConcrete extends SequenceNucleiqueConcrete implements SequenceADN {

	public static final String alphabet = "ATCGatcg";
	public SequenceADNConcrete() {
	}

	public SequenceADNConcrete(String idf, String organism, String sequence) {
		super(idf, organism, sequence);		
	}

	@Override
	public SequenceARN transcript() {
		String arn = trans(getSequence(), alphabet, SequenceARNConcrete.alphabet);
		return new SequenceARNConcrete(getId(), getOrganism(), arn.toString());
	}
	
	public boolean read(BufferedReader reader) throws InvalidSequenceException {
		return validatedRead(reader, alphabet);
	}
	
	public Sequence getSameTypeSequence(String id, String organisme, String sequence){
		return new SequenceADNConcrete(id, organisme, sequence);
	}
	
	// TODO
	public List<Occurence> find(Proteine sequence, int k, int delta, int scoreMin) {
		List<Occurence> occur = new ArrayList<Occurence>();		
		Proteine seqProt = this.traduction();		
		
		KMers kmers = new kMersConcrets(k, sequence);
		String chaine = seqProt.getSequence();
		int len = seqProt.length();
		int lp = sequence.length();
		int last_i = len - (lp - delta);
		int min_l = Math.max(0,  lp-delta);
		int max_l = lp+delta;
		
		for (int i=0; i < last_i; i++) {
			for (int l=min_l; l <= max_l && i+l < len; l++) {
				String sousChaine = chaine.substring(i, i+l);
				KMers kmersSousChaine = new kMersConcrets(k, sousChaine);
				int score = kmers.nombreCommuns(kmersSousChaine);
				
				if (score >= scoreMin) {
					Occurence occ = new Occurence();
					occ.start = i;
					occ.end = i + l;
					occur.add(occ);
				}
			}
		}
		return occur;
	}

	public static void main(String[] args) {
		try {
			SequenceADN adn = new SequenceADNConcrete(); //("","","ACGCTACGAT");
			BufferedReader reader = new BufferedReader(new FileReader("adn2.fasta"));
			adn.read(reader);
			reader.close();
			SequenceARN arn = adn.transcript();			
			System.out.println(arn.getSequence());			
			
			// Essai de find
			Sequence requete = new ProteineConcrete("", "", "F");
			List<Occurence> occu = adn.find(requete, 2, 1, 2);
			if (!occu.isEmpty()) {
				for (Occurence occ1 : occu){				
					System.out.println(occ1.start +  " " + occ1.end + " " + adn.getSequence().substring(occ1.start, occ1.end));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}