package tpajava.sequence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProteineConcrete extends SequenceConcrete implements Proteine{
	
	public static final String alphabet = "ABCDEFGHIJKLMPQRSTVWabcdefghijklmnpqrstvw";

	public ProteineConcrete() {
		super();
	}

	public ProteineConcrete(String idf, String organism, String sequence) {
		super(idf, organism, sequence);
	}
	
	public Sequence getSameTypeSequence(String id, String organisme, String sequence){
		return new ProteineConcrete(id, organisme, sequence);
	}
	
	@Override
	public List<Occurence> find(SequenceADN sequence, int k, int delta, int scoreMin) {		
		Proteine prot = sequence.traduction(); // utiliser sequence ADN		
			
			List<Occurence> occur = new ArrayList<Occurence>();
			
			KMers kmers = new kMersConcrets(k, prot);
			String chaine = getSequence();
			int len = length();
			int lp = prot.length();
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
			Proteine proteine = new ProteineConcrete();
			BufferedReader reader = new BufferedReader(new FileReader("sequence.fasta"));
			proteine.read(reader);
			reader.close();
			System.out.println(proteine.getSequence());
			
			Sequence requete = new ProteineConcrete("", "", "SET");
			List<Occurence> occu = proteine.find(requete, 2, 1, 2);
			if (!occu.isEmpty()) {
				for (Occurence occ1 : occu){				
					System.out.println(occ1.start +  " " + occ1.end + " " + proteine.getSequence().substring(occ1.start, occ1.end));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}