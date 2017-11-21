package tpajava.sequence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequenceConcrete implements Sequence {

	private String id;
	private String organism;
	private String sequence;
	
	public static int TAILLE_LIGNE = 50;
	
	@Override
	public String getId() {		
		return id;
	}

	@Override
	public String getOrganism() {		
		return organism;
	}

	@Override
	public String getSequence() {		
		return sequence;
	}

	@Override
	public int length() {		
		return sequence.length();
	}

	@Override
	public char get(int pos) {		
		return sequence.charAt(pos-1);
	}
	
	private static boolean isValid(String sequence, String alphabet) {
		int len = sequence.length();
		for (int i=0; i < len; i++) {
			char c = sequence.charAt(i);
			if (alphabet.indexOf(c) < 0) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean validatedRead(BufferedReader reader, String alphabet) throws InvalidSequenceException {
		boolean ret = rawRead(reader);
		if (! isValid(getSequence(), alphabet)) {
			throw new InvalidSequenceException("invalid sequence"); //trhow sans s
		}
		return ret;
	}
	
	protected boolean rawRead(BufferedReader reader) throws InvalidSequenceException {
		try {
			// >gi|5524211|gb|AAD44166.1| cytochrome b |Elephas maximus
			String annotations = reader.readLine();
			String [] annot = annotations.split("\\|");
			id = annot[3];
			organism = annot[4];
			StringBuilder seq = new StringBuilder();
			String line = null;
			while ((line=reader.readLine()) != null && line.trim().length() > 0) {
				seq.append(line.replaceAll(" ", ""));
			}
			sequence = seq.toString();
			return true;
		} 
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean read(BufferedReader reader) throws InvalidSequenceException {
		return rawRead(reader);
	}

	@Override
	public void write(PrintWriter writer) {
		try {
			// >gi|5524211|gb|AAD44166.1| cytochrome b |Elephas maximus
			String annotations = ">|||" + getId() + "|" + getOrganism();
			writer.println(annotations);
			String seq = getSequence();
			int len = seq.length();
			for (int i=0; i < len; i+=TAILLE_LIGNE) {
				int end = Math.min(i+TAILLE_LIGNE, len);
				writer.println(seq.substring(i, end));
			}
			writer.println();
		}
		catch (Exception e) {
			return;
		}
	}
	
	public SequenceConcrete(){
		
	}
	
	public SequenceConcrete(String idf, String organism, String sequence){
		id = idf;
		this.organism = organism;
		this.sequence = sequence;
	}
	
	public Matcher matcher(String motif) { //Recherche de motif
		Pattern p = Pattern.compile(motif);
		return p.matcher(getSequence()); // ou this.sequence
	}
	
	public boolean contains(String motif) {
		return matcher(motif).find();
	}
	
	public String occurrence(String motif) { //première occurence - utilise la fonction matcher ci dessus
		Matcher matcher = matcher(motif);
		if (matcher.find()) {
			return matcher.group(0);
		} else {
			return null;
		}
	}
	
	public String[] occurrences(String motif) { // toutes les occurrences, dans l'hypothèse ou les motifs ne sont pas chevauchants
		// On va créer un matcher pour chaque séquence : le 0 se déplace de end+1 en end+1
		Pattern p = Pattern.compile(motif);
		int end = -1;
		List<String> tab = new ArrayList<String>();
		boolean fin = false;
		while (!fin) {
			String chaine = getSequence().substring(end+1);
			Matcher m = p.matcher(chaine);
			if (m.find()) {
				String occ = m.group(0);
				tab.add(occ);
				//System.out.println(occ); //affiche l'occurence au lieu de la mettre dans un tableau
				end += m.end();
			} else {
				fin = true;
			}			
		}
		return tab.toArray(new String[0]); // on crée à la volée un tableau vide pour indiquer le type
	}
	
	public String repetition() {
		Matcher matcher = matcher("(.{3,}).*\\1");
		//exp régulière : qq chose qui se répète au moins une fois mais je sais pas ce que je sais : () puis \1
		// de longueur au moins 3 : .{3,} - les accolades servent de puissance - . : n'importe quel caractere
		// entre les deux occurences : n'importe quoi .*
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}
	
	public String repetition(int minSize) {
		Matcher matcher = matcher("(.{" + minSize + ",}?).*\\1"); // '?' arrete toi à minSize. Ou mettre {3} et pas {3,}	
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}
	
	public Sequence getSameTypeSequence(String id, String organisme, String sequence){
		return new SequenceConcrete(id, organisme, sequence);
	}
	
	public Occurence findFirst(Sequence sequence, int k, int delta, int scoreMin) {
		KMers kmers = new kMersConcrets(k, sequence);
		String chaine = getSequence();
		int len = length();
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
					return occ;
				}
			}
		}
		return null;
	}
	
	public List<Occurence> find(Sequence sequence, int k, int delta, int scoreMin) {
		
		List<Occurence> occur = new ArrayList<Occurence>();
		
		KMers kmers = new kMersConcrets(k, sequence);
		String chaine = getSequence();
		int len = length();
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
		
		Sequence seq = new SequenceConcrete("", "", "ATGCCATTCGCGAGAGCAGCCAGCAGCCCTCATTATTAT");
		Sequence requete = new SequenceConcrete("", "", "GACCAGTCAGCAG");
		Occurence occ = seq.findFirst(requete,  2,  1,  2);
		if (occ != null) {
			System.out.println(occ.start +  " " + occ.end + " " + seq.getSequence().substring(occ.start, occ.end));
		}
		List<Occurence> occu = seq.find(requete, 2, 1, 2);
		if (!occu.isEmpty()) {
			for (Occurence occ1 : occu){				
				System.out.println(occ1.start +  " " + occ1.end + " " + seq.getSequence().substring(occ1.start, occ1.end));
			}
		}
		
	/*
		try {
			//Sequence sequence = new SequenceConcrete();
			//BufferedReader reader = new BufferedReader(new FileReader("sequence.fasta"));
			//sequence.read(reader);
			//reader.close();
			
			//PrintWriter writer = new PrintWriter(new FileWriter("out.fasta"));
			//sequence.write(writer);
			//writer.close();
			//System.out.println(sequence.length());
			//System.out.println(sequence.getSequence());
			//System.out.println(sequence.contains("M.TA"));
			//System.out.println(sequence.occurrence("M.TA"));
			//System.out.println(sequence.contains("LC"));
			//System.out.println(sequence.occurrence("LC"));
			//String[] tab = sequence.occurrences("L.L");
			//System.out.println(Arrays.toString(tab)); //Afficher un tableau d'objets : toString
			//System.out.println(sequence.repetition());
			//System.out.println(sequence.repetition(4));
			
			// C-x(2)-P-F-x-[FYWIV]-x(7)-C-x(8,10)-W-C-x(4)-[DNSR]-[FYW]-x(3,5)-[FYW]-x-[FYWI]-C.
			
			//ESSAI 2 comm au dessus
			Sequence sequence2 = new SequenceConcrete();
			BufferedReader reader2 = new BufferedReader(new FileReader("proteine.fasta"));
			sequence2.read(reader2);
			reader2.close();
			String ps23 ="C..PF.[FYWIV].{7}C.{8,10}WC....[DNSR][FYW].{3,5}[FYW].[FYWI].C";
			System.out.println(sequence2.occurrence(ps23));
		
			for (int i=1; i <= sequence2.length(); i++){ //i=1 car pos-1 Mettre sequence au lieu de sequence 2 pour les exos précédents
				System.out.print(sequence2.get(i));
			}
			// ESSAI 3 comm au dessus
			EnsembleSequences sequences = new EnsembleSequencesConcretes();
			BufferedReader reader3 = new BufferedReader(new FileReader("proteines.fasta"));
			sequences.read(reader3);
			reader3.close();			
			String ps24 = "MTALL";
			System.out.println(sequences.getNombre()); //pb dans le read
			for (Sequence sequence:sequences) {
				System.out.println(sequence.getId() + " motif : "+sequence.occurrence(ps24));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible d'ouvrir le fichier");
		}*/
	}
	
}
